package com.team1389.robot;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeExecuter;
import com.team1389.autonomous.simple_autos.RobotCommands;
import com.team1389.autonomous.simple_autos.RobotCommands.DriveStraightOpenLoop;
import com.team1389.autonomous.simple_autos.tests.OpenSwitchAutoRight;
import com.team1389.hardware.registry.Registry;
import com.team1389.operation.TeleopMain;
import com.team1389.util.Timer;
import com.team1389.watchers.DashboardInput;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot
{
	RobotSoftware robot;
	TeleopMain teleOperator;
	AutoModeExecuter autoModeExecuter;

	Registry registry;
	DriveStraightOpenLoop auto;
	RobotCommands commands;
	Timer t;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{

		registry = new Registry();
		robot = RobotSoftware.getInstance();
		teleOperator = new TeleopMain(robot);
		autoModeExecuter = new AutoModeExecuter();
		DashboardInput.getInstance().init();

		commands = new RobotCommands(robot);

		// auto = commands.new DriveStraightOpenLoop(2, .25);

	}

	@Override
	public void autonomousInit()
	{

		AutoModeBase selectedAutonMode = new OpenSwitchAutoRight(robot);
		autoModeExecuter.setAutoMode(selectedAutonMode);
		autoModeExecuter.run();

	}

	@Override
	public void autonomousPeriodic()
	{
	}

	@Override
	public void teleopInit()
	{

		autoModeExecuter.stop();
		robot.armIntake.set(0);

		// robot.armLiftLeft.getVoltageController().set(.6);

		 teleOperator.init();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
//		 robot.driveLeftVA.getVoltageController().set(.75);
//		 robot.driveLeftVB.getVoltageController().set(.75);
//		 robot.driveRightVA.getVoltageController().set(.75);
//		 robot.driveRightVB.getVoltageController().set(.75);

		 //robot.armLiftRight.getVoltageController().set(1);
		 //robot.armLiftLeft.getVoltageController().set(1);
		// SmartDashboard.putNumber("Angle", robot.robotAngle.get());

		// robot.armIntakeA.getVoltageController().set(1);
		// robot.armIntakeB.getVoltageController().set(1);

		// robot.elevatorLeft.getVoltageController().set(.5);
		// robot.elevatorRight.getVoltageController().set(1);

//		 robot.armIntakeA.getVoltageController().set(1);
//		 robot.armIntakeB.getVoltageController().set(1);
//		robot.armIntake.set(1);
		SmartDashboard.putNumber("raw elevator val", robot.rawElevatorPos.get());
		SmartDashboard.putNumber("elevator in meters", robot.elevatorPositionleft.get()); 
		
		teleOperator.periodic();
	}

	@Override

	public void disabledInit()
	{
	}
}
