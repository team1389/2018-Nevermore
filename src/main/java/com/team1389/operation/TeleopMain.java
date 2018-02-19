package com.team1389.operation;

import com.team1389.hardware.controls.ControlBoard;
import com.team1389.robot.RobotConstants;
import com.team1389.robot.RobotSoftware;
import com.team1389.system.Subsystem;
import com.team1389.system.SystemManager;
import com.team1389.system.drive.CurvatureDriveSystem;
import com.team1389.systems.SimpleElevator;
import com.team1389.systems.Vision;
import com.team1389.watch.Watcher;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		//elevatorSystem = setUpElevatorSystem();
		//armSystem = setUpArmSystem();
		// visionSystem = setUpVisionSystem();
		manager = new SystemManager(driveSystem, elevatorSystem);// ,
																	// armSystem);
		manager.init();
		watcher.watch(driveSystem, elevatorSystem);

	}

	private Subsystem setUpDriveSystem()
	{
		return new CurvatureDriveSystem(robot.drive.getAsTank(), controls.xDriveY(), controls.xDriveX(),
				controls.rightBumper(), robot.robotAngle.get(), RobotConstants.GyroCorrection);

	}

	private Subsystem setUpSimpleElevatorSystem()
	{
		return new SimpleElevator(controls.leftStickYAxis(),
				robot.elevatorLeft.getVoltageController().addFollowers(robot.elevatorRight.getVoltageController()));
	}

	/*
	 * private Subsystem setUpElevatorSystem() {
	 * 
	 * return new TeleopElevator(robot.armZero.getSwitchInput(),
	 * robot.elevatorPosition, robot.elevatorSpeed,
	 * robot.elevatorLeft.getVoltageController(), controls.startButton(),
	 * controls.xButton(), controls.aButton(), controls.bButton(),
	 * controls.yButton(), controls.startButton(), controls.leftStickYAxis());
	 * 
	 * // return new TeleopElevator(controls.leftStickYAxis(), //
	 * robot.elevatorLeft.getVoltageController().addFollowers(robot.
	 * elevatorRight.getVoltageController())); }
	 */

	/*private Subsystem setUpArmSystem()
	{
		return new TeleopArm(robot.armAngle, controls.rightStickYAxis(), robot.armIntakeA.getVoltageOutput(),
				robot.armLiftLeft.getVoltageController(), robot.armSpeed, robot.beambreak.getSwitchInput(),
				robot.armZero.getSwitchInput(), controls.upDPad(), controls.leftBumper(), controls.rightBumper(),
				controls.backButton(), controls.leftDPad(), controls.rightDPad());
	}*/

	private Subsystem setUpVisionSystem()
	{
		return new Vision(robot.drive.getAsTank());
	}

	public void periodic()
	{
		vision = vision ^ controls.startButton().get();
		if (vision)
		{
			visionSystem.update();
		} else
		{
			manager.update();
		}
		Watcher.update();
	}
}
