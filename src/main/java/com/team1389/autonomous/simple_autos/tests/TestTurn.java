package com.team1389.autonomous.simple_autos.tests;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.autonomous.simple_autos.RobotCommands;
import com.team1389.robot.RobotSoftware;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class TestTurn extends AutoModeBase
{

	RobotSoftware robot;
	RobotCommands commands;
	public TestTurn(RobotSoftware robot)
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
		return "Turn";
	}

	@Override
	protected void routine() throws AutoModeEndedException
	{
		runCommand(commands.new TurnAngle(90, true));
		
	}

}
