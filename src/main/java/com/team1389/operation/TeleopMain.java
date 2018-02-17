package com.team1389.operation;

import com.team1389.hardware.controls.ControlBoard;
import com.team1389.robot.RobotConstants;
import com.team1389.robot.RobotSoftware;
import com.team1389.system.Subsystem;
import com.team1389.system.SystemManager;
import com.team1389.system.drive.CurvatureDriveSystem;
import com.team1389.systems.TeleopElevator;
import com.team1389.systems.Vision;
import com.team1389.watch.Watcher;

public class TeleopMain
{
	SystemManager manager;
	ControlBoard controls;
	RobotSoftware robot;
	Watcher watcher;
	Subsystem driveSystem;
	Subsystem elevator;
	Subsystem arm;
	Subsystem visionSystem;
	boolean vision;

	public TeleopMain(RobotSoftware robot)
	{
		this.robot = robot;
	}

	public void init()
	{
		controls = ControlBoard.getInstance();
		driveSystem = setUpDriveSystem();
		visionSystem = setUpVisionSystem();
		manager = new SystemManager(driveSystem);
		manager.init();
		// watcher.watch(driveSystem);

	}

	private Subsystem setUpDriveSystem()
	{
		return new CurvatureDriveSystem(robot.drive.getAsTank(), controls.xDriveY(), controls.xDriveX(),
				controls.rightBumper(), robot.robotAngle.get(), RobotConstants.GYROCorrection);

	}
	
	/*private Subsystem setUpElevatorSystem()
	{
		return new TeleopElevator(controls.startButton(), elevPos, elevVel, elevVolt, zeroBtn, switchBtn, scaleLowBtn, scaleMiddleBtn, scaleHighBtn, manualBtn, ctrlAxis)
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
	}
}
