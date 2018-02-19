package com.team1389.systems;

import com.team1389.command_framework.command_base.Command;
import com.team1389.configuration.PIDConstants;
import com.team1389.control.MotionProfileController;
import com.team1389.control.SmoothSetController;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.PercentOut;
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
import com.team1389.watch.info.BooleanInfo;

/**
 * Provides control of the elevator system. Controls both the first and second
 * stage, the second stage travels simultaneously with the first, but at twice
 * the speed This only provides the control stucture which can be utilized in
 * teleop or auto, see {@link TeleopElevator} for controller use
 * 
 * @author Raffi
 *
 */
public class Elevator extends Subsystem
{
	DigitalIn zero;
	RangeIn<Position> elevPos;
	RangeIn<Speed> elevVel;
	RangeOut<Percent> elevVolt;
	State currentState;
	State desiredState;
	MotionProfileController profileController;
	SmoothSetController controller;
	PIDConstants pid;

	public Elevator(DigitalIn zero, RangeIn<Position> elevPos, RangeIn<Speed> elevVel, RangeOut<Percent> elevVolt)
	{
		super();
		this.zero = zero;
		this.elevPos = elevPos;
		this.elevVolt = elevVolt;
		this.elevVel = elevVel;
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem)
	{
		return stem.put(zero.getWatchable("elev zero"), elevPos.getWatchable("elev pos"),
				elevVel.getWatchable("elev vel"),
				new BooleanInfo("profile finished", () -> profileController.isFinished()));
	}

	@Override
	public String getName()
	{
		return "Elevator";
	}

	@Override
	public void init()
	{
		currentState = State.ZERO;
		desiredState = State.ZERO;
		pid = new PIDConstants(0.1, 0, 0, 0);
		profileController = new MotionProfileController(0.1, 0, 0, 0, elevPos, elevVel, elevVolt);
		controller = new SmoothSetController(pid, RobotConstants.ElevMaxAcceleration,
				RobotConstants.ElevMaxDeceleration, RobotConstants.ElevMaxVelocity, elevPos, elevVel,
				(PercentOut) elevVolt);
		controller.enable();

	}

	@Override
	public void update()
	{
		if (zero.get())
		{
			elevPos.offset(-elevPos.get());
		}
		profileController.update();
		if(profileController.isFinished()) {
			setState(desiredState);
		}
	}

	public Command goToZero()
	{
		desiredState = State.ZERO;
		return controller.followProfileCommand(calculateProfile(State.ZERO));
	}

	public Command goToSwitch()
	{
		desiredState = State.SWITCH;
		return controller.followProfileCommand(calculateProfile(State.SWITCH));

	}

	public Command goToScaleHigh()
	{
		desiredState = State.SCALE_HIGH;
		return controller.followProfileCommand(calculateProfile(State.SCALE_HIGH));

	}

	public Command goToScaleLow()
	{
		desiredState = State.SCALE_LOW;
		return controller.followProfileCommand(calculateProfile(State.SCALE_LOW));

	}

	/**
	 * check if we have max hall effect, if so we dont have to zero beforehand
	 * these are in meters
	 */

	public enum State
	{
		ZERO(0), SWITCH(0.5), SCALE_LOW(1.24), SCALE_MIDDLE(1.544), SCALE_HIGH(1.849);
		private final double pos;

		private State(double pos)
		{
			this.pos = pos;
		}
	}

	private void setState(State toSet)
	{
		currentState = toSet;
	}

	public State getState()
	{
		return currentState;
	}

	private MotionProfile calculateProfile(State desired)
	{
		return ProfileUtil.trapezoidal(desired.pos, elevVel.get(), RobotConstants.ElevMaxAcceleration,
				RobotConstants.ElevMaxDeceleration, RobotConstants.ElevMaxVelocity);
	}

}
