package com.team1389.systems;

import com.team1389.command_framework.CommandUtil;
import com.team1389.command_framework.command_base.Command;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Speed;
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

	protected RangeOut<Percent> intakeVolt;
	RangeOut<Percent> armVolt;
	DigitalIn beambreak;
	DigitalIn zero;
	DigitalIn vertical;
	DigitalIn rear;
	RangeIn<Speed> armVel;
	PositionState posState;
	IntakeState intakeState;
	CommandUtil commands;

	public Arm(RangeOut<Percent> intakeVolt, RangeOut<Percent> armVolt, DigitalIn beambreak, DigitalIn zero,
			DigitalIn vertical, DigitalIn rear, RangeIn<Speed> armVel)
	{
		super();
		this.intakeVolt = intakeVolt;
		this.armVolt = armVolt;
		this.beambreak = beambreak;
		this.zero = zero;
		this.vertical = vertical;
		this.rear = rear;
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
		commands = new CommandUtil(); 
		
		

	}

	/**
	 * The update called on ever tick (50 hz) Zero's upon the hall effect being
	 * triggered Then it update the motionprofile and sets the intake
	 */
	@Override
	public void update()
	{

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
		double setVolt = ((desired.angle == getPositionState().angle) ? 0
				: (desired.angle > getPositionState().angle) ? -0.5 : 0.5);
		DigitalIn SwitchToHit = ((desired.equals(PositionState.FRONT)) ? zero
				: (desired.equals(PositionState.VERTICAL)) ? vertical : rear);
		return commands.createCommand(() ->
		{
			armVolt.set(setVolt);
			return SwitchToHit.get();
		});

	}

	private void setPositionState(PositionState desired)
	{
		posState = desired;
	}

	public void setIntakeState(IntakeState desired)
	{
		intakeState = desired;
	}

}
