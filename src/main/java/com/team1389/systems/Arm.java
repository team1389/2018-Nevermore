package com.team1389.systems;

import com.team1389.command_framework.CommandUtil;
import com.team1389.command_framework.command_base.Command;
import com.team1389.control.MotionProfileController;
import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.motion_profile.MotionProfile;
import com.team1389.motion_profile.ProfileUtil;
import com.team1389.robot.RobotConstants;
import com.team1389.system.Subsystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

/**
 * Provides control for the Arm Subsystem. Only controls the arms orientation,
 * the height of the arm is controlled by the {@link Elevator} system. This is
 * all of the control, that can be utilized in teleop or auto. See
 * {@link TeleopArm} for controller implementation
 * 
 * @author Raffi
 *
 */
public class Arm extends Subsystem
{

	AngleIn<Position> armPos;
	protected RangeOut<Percent> intakeVolt;
	RangeOut<Percent> armVolt;
	DigitalIn beambreak;
	DigitalIn zero;
	RangeIn<Speed> armVel;
	PositionState posState;
	IntakeState intakeState;
	MotionProfileController profileController;

	public Arm(AngleIn<Position> armPos, RangeOut<Percent> intakeVolt, RangeOut<Percent> armVolt, RangeIn<Speed> armVel,
			DigitalIn beambreak, DigitalIn zero)
	{
		super();
		this.armPos = armPos;
		this.intakeVolt = intakeVolt;
		this.armVolt = armVolt;
		this.beambreak = beambreak;
		this.zero = zero;
		this.armVel = armVel;
	}

	/**
	 * The current state of the Arm's angle
	 * 
	 * @author Raffi
	 *
	 */
	public enum PositionState
	{
		FRONT(0), VERTICAL(90), REAR(180);
		private final double angle;

		private PositionState(double angle)
		{
			this.angle = angle;
		}
	}

	/**
	 * The current state of the intake
	 * 
	 * @author Raffi
	 *
	 */
	public enum IntakeState
	{
		INTAKING(1), NEUTRAL(0), OUTTAKING(-1);
		protected final double voltage;

		private IntakeState(double voltage)
		{
			this.voltage = voltage;
		}
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem)
	{
		return stem;
	}

	@Override
	public String getName()
	{
		return "Arm System";
	}

	@Override
	public void init()
	{
		posState = PositionState.FRONT;
		intakeState = IntakeState.NEUTRAL;
		profileController = new MotionProfileController(0.1, 0, 0, 0, armPos, armVel, armVolt);

	}

	/**
	 * The update called on ever tick (50 hz) Zero's upon the hall effect being
	 * triggered Then it update the motionprofile and sets the intake
	 */
	@Override
	public void update()
	{
		if (zero.get())
		{
			armPos.offset(-armPos.get());
		}
		profileController.update();

	}

	public IntakeState getIntakeState()
	{
		return intakeState;
	}

	public PositionState getPositionState()
	{
		return posState;
	}

	public Command goToFront()
	{
		return goTo(PositionState.FRONT);
	}

	public Command goToRear()
	{
		return goTo(PositionState.REAR);
	}

	public Command goToVertical()
	{
		return goTo(PositionState.VERTICAL);
	}

	public Command setIntaking()
	{
		setIntakeState(IntakeState.INTAKING);
		return CommandUtil.createCommand(() ->
		{

			intakeVolt.set(1);
			return beambreak.get();
		});
	}

	public Command setOuttaking()
	{
		setIntakeState(IntakeState.OUTTAKING);
		return CommandUtil.createCommand(() ->
		{
			intakeVolt.set(-1);
			return !beambreak.get();
		});
	}

	public Command setNeutral()
	{
		setIntakeState(IntakeState.NEUTRAL);
		return CommandUtil.createCommand(() -> intakeVolt.set(0));
	}

	/**
	 * Goes to the desired angle depending on the current state
	 * 
	 * @param desired
	 *            the state you want to enter
	 */
	private Command goTo(PositionState desired)
	{
		setPositionState(desired);
		MotionProfile profile = calculateProfile(desired);
		return profileController.followProfileCommand(profile);

	}

	private void setPositionState(PositionState desired)
	{
		posState = desired;
	}

	/**
	 * Generates the motion profile to get to the angle of the desired state
	 * 
	 * @param desired
	 *            the desired state to enter
	 * @return the motion profile to get to the angle of the desired state
	 */
	private MotionProfile calculateProfile(PositionState desired)
	{
		return ProfileUtil.trapezoidal(desired.angle, armVel.get(), RobotConstants.ElevMaxAcceleration,
				RobotConstants.ElevMaxDeceleration, RobotConstants.ElevMaxVelocity);
	}

	public void setIntakeState(IntakeState desired)
	{
		intakeState = desired;
	}

}
