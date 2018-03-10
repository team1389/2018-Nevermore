package com.team1389.robot;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeExecuter;
import com.team1389.auto.command.DriveStraightCommand;
import com.team1389.autonomous.simple_autos.tests.TestDriveStraightOpen;
import com.team1389.hardware.outputs.hardware.CANTalonHardware;
import com.team1389.hardware.registry.Registry;
import com.team1389.operation.TeleopMain;
import com.team1389.watchers.DashboardInput;

import edu.wpi.first.wpilibj.IterativeRobot;

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
	CANTalonHardware masterTalon;
	CANTalonHardware followerTalon;
	Registry registry;
	TestDriveStraightOpen auto;

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

	}

	@Override
	public void autonomousInit()
	{

		// autoModeExecuter.stop();
		// AutoModeBase selectedAutonMode =
		// DashboardInput.getInstance().getSelectedAutonMode();
		// autoModeExecuter.setAutoMode(selectedAutonMode);
		auto = new TestDriveStraightOpen(robot);
		auto.run();

	}

	@Override
	public void teleopInit()
	{

		// autoModeExecuter.stop();

		auto.stop();

		teleOperator.init();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
		// robot.driveLeftVA.getVoltageController().set(1);
		// robot.driveLeftVB.getVoltageController().set(1);
		// robot.driveLeftT.getVoltageController().set(1);
		// robot.driveRightT.getVoltageController().set(1);
		// robot.driveRightVA.getVoltageController().set(1);
		// robot.driveRightVB.getVoltageController().set(1);

		// robot.armLiftLeft.getVoltageController().set(1);
		// robot.armLiftRight.getVoltageController().set(1);
		// robot.armIntakeA.getVoltageController().set(1);
		// robot.armIntakeB.getVoltageController().set(1);

		// robot.elevatorLeft.getVoltageController().set(.5);
		// robot.elevatorRight.getVoltageController().set(1);

		// robot.armIntakeA.getVoltageController().set(.25);
		// robot.armIntakeB.getVoltageController().set(.25);

		teleOperator.periodic();
	}

	@Override

	public void disabledInit()
	{
	}
}
