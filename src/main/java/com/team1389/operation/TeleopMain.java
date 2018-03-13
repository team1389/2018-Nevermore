package com.team1389.operation;

import com.team1389.hardware.controls.ControlBoard;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.value_types.Speed;
import com.team1389.robot.RobotSoftware;
import com.team1389.system.Subsystem;
import com.team1389.system.SystemManager;
import com.team1389.system.drive.CheesyDriveSystem;
import com.team1389.system.drive.DriveOut;
import com.team1389.systems.SimpleArm;
import com.team1389.systems.SimpleElevator;
import com.team1389.systems.TeleopArm;
import com.team1389.systems.TeleopElevator;
import com.team1389.watch.Watcher;

public class TeleopMain
{
	SystemManager manager;
	ControlBoard controls;
	RobotSoftware robot;
	Watcher watcher;
	Subsystem driveSystem;
	Subsystem elevatorSystem;
	Subsystem armSystem;
	Subsystem visionSystem;
	boolean vision = false;

	public TeleopMain(RobotSoftware robot)
	{
		this.robot = robot;
	}

	public void init()
	{
		Watcher watcher = new Watcher();
		controls = ControlBoard.getInstance();
		driveSystem = setUpDriveSystem();
		elevatorSystem = setUpSimpleElevatorSystem();
		armSystem = setUpSimpleArm();
		manager = new SystemManager(elevatorSystem);
		;
		manager.init();

	}

	private Subsystem setUpDriveSystem()
	{
		;
		return new CheesyDriveSystem(
				new DriveOut(robot.drive.getAsTank().left().scale(.4), robot.drive.getAsTank().right().scale(.4)),
				controls.xLeftDriveY(), controls.xDriveX(), controls.xDriveBtn());

	}

	private Subsystem setUpSimpleElevatorSystem()
	{
		return new SimpleElevator(controls.leftStickYAxis().copy().invert(), robot.elevator);
	}

	private Subsystem setUpElevatorSystem()
	{

		return new TeleopElevator(robot.elevatorZero.getSwitchInput(), robot.elevatorPositionleft,
				new RangeIn<Speed>(Speed.class, () -> 0.0, 0, 1), robot.elevator, controls.startButton(),
				controls.xButton(), controls.aButton(), controls.bButton(), controls.yButton(), controls.startButton(),
				controls.leftStickYAxis());
	}

	private Subsystem setUpSimpleArm()
	{
		return new SimpleArm(controls.leftStickYAxis().invert(), robot.arm, controls.aButton(), controls.bButton(),
				controls.yButton(), robot.armIntake);
	}

	public void periodic()
	{

		manager.update();

	}
}
