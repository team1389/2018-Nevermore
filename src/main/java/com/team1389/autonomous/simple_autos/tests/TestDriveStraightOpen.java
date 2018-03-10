package com.team1389.autonomous.simple_autos.tests;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.autonomous.simple_autos.RobotCommands;
import com.team1389.autonomous.simple_autos.RobotCommands.DriveStraightOpenLoop;
import com.team1389.robot.RobotSoftware;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class TestDriveStraightOpen extends AutoModeBase
{

	RobotSoftware robot;
	RobotCommands commands;

	public TestDriveStraightOpen(RobotSoftware robot)
	{
		this.robot = robot;
		commands = new RobotCommands(robot);
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> arg0)
	{
		return arg0;
	}

	@Override
	public String getIdentifier()
	{
		return "straight open";
	}

	@Override
	protected void routine() throws AutoModeEndedException
	{
		runCommand(commands.new DriveStraightOpenLoop(2, 0.75));

	}

}
