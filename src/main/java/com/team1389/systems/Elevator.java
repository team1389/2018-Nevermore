package com.team1389.systems;

import com.team1389.command_framework.CommandUtil;
import com.team1389.command_framework.command_base.Command;
import com.team1389.configuration.PIDConstants;
import com.team1389.control.SynchronousPIDController;
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
	PIDConstants pid;
	SynchronousPIDController pidController;
	RangeOut<Position> servoVolt;
	public Elevator(DigitalIn zero, RangeIn<Position> elevPos, RangeIn<Speed> elevVel, RangeOut<Percent> elevVolt, RangeOut<Position> servoVolt)
	{
		super();
		this.zero = zero;
		this.elevPos = elevPos;
		this.elevVolt = elevVolt;
		this.elevVel = elevVel;
		this.servoVolt = servoVolt;
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem)
	{
		return stem.put(zero.getWatchable("elev zero"), elevPos.getWatchable("elev pos"),
				elevVel.getWatchable("elev vel"));
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
		pidController = new SynchronousPIDController<>(pid, elevPos, elevVolt);
	}

	@Override
	public void update()
	{
		if (zero.get())
		{
			elevPos.offset(-elevPos.get());
		}
		
	}

	public Command goToZero()
	{
		desiredState = State.ZERO;
		return pidToCommand(State.ZERO.pos);
	}

	public Command goToSwitch()
	{
		desiredState = State.SWITCH;
		return pidToCommand(State.SWITCH.pos);

	}

	public Command goToScaleHigh()
	{
		desiredState = State.SCALE_HIGH;
		return pidToCommand(State.SCALE_HIGH.pos);

	}

	public Command goToScaleLow()
	{
		desiredState = State.SCALE_LOW;
		return pidToCommand(State.SCALE_LOW.pos);

	}

	/**
	 * check if we have max hall effect, if so we dont have to zero beforehand
	 * these are in meters
	 */

	public Command pidToCommand(double setPoint)
	
	{
		servoVolt.set(50);
		pidController.setSetpoint(setPoint);
		return CommandUtil.createCommand(() -> 
		{
			pidController.update();
			return pidController.onTarget(0.05);
		});
	}
	
	/*public Command brake(ServoState desired)
	{
		return CommandUtil.createCommand(() -> servoVolt.set(desired.servoPos));
	}*/
	public enum State
	{
		ZERO(0), SWITCH(0.5), SCALE_LOW(1.24), SCALE_MIDDLE(1.544), SCALE_HIGH(1.849);
		private final double pos;

		private State(double pos)
		{
			this.pos = pos;
		}
	}
	
/*	public enum ServoState
	{
		BRAKE(0), FREE(50);
		private final double servoPos;
		
		private ServoState(double servoPos)
		{
			this.servoPos = servoPos;
		}
		
	}*/

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
