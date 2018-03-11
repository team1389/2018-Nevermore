package com.team1389.autonomous.simple_autos.tests;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.auto.command.WaitTimeCommand;
import com.team1389.autonomous.simple_autos.RobotCommands;
import com.team1389.command_framework.CommandUtil;
import com.team1389.robot.RobotSoftware;
import com.team1389.system.drive.DriveSignal;
import com.team1389.system.drive.SixWheelSignal;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

import edu.wpi.first.wpilibj.DriverStation;

public class OpenSwitchAutoRight extends AutoModeBase
{
	RobotCommands commands;
	RobotSoftware robot;

	public OpenSwitchAutoRight(RobotSoftware robot)
	{
		this.robot = robot;
		commands = new RobotCommands(robot);
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem)
	{
		return stem;
	}

	@Override
	public String getIdentifier()
	{
		return "Open Swich Auto Right";
	}

	@Override
	protected void routine() throws AutoModeEndedException
	{
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		if (gameData.length() > 0)
		{
			if (gameData.charAt(0) == 'R')
			{
				runCommand(commands.new DriveStraightOpenLoop(3, .25));
				runCommand(CommandUtil.createCommand(() -> robot.armIntake.set(-1)));

			}

			else
			{
				runCommand(CommandUtil.createCommand(() -> robot.drive.getAsTank().set(new DriveSignal(1, -1))));
		runCommand(CommandUtil.createCommand(() -> robot.drive.getAsTank().set(new DriveSignal(1, -1))));
		runCommand(CommandUtil.createCommand(() -> robot.drive.getAsTank().set(new DriveSignal(1, -1))));

		runCommand(commands.new DriveStraightOpenLoop(2, .25));
		runCommand(CommandUtil.createCommand(() -> robot.armIntake.set(-1)));
		runCommand(new WaitTimeCommand(.5));
		runCommand(CommandUtil.createCommand(() -> robot.armIntake.set(0)));

			}
		}
		
	}

}
