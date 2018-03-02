package com.team1389.autonomous.simple_autos;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.robot.RobotConstants;
import com.team1389.robot.RobotSoftware;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

/**
 * assumed starting point is 1.0668 meters (3.5 ft) from right side, i.e. touching edge of
 * portal
 * 
 * @author Quunii
 *
 */
public class SameSideRightSwitch extends AutoModeBase
{
	RobotSoftware robot;
	RobotCommands commands;

	public SameSideRightSwitch(RobotSoftware robot)
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
		return "Same Side Right Switch";
	}

	@Override
	protected void routine() throws AutoModeEndedException
	{
		runCommand(commands.new DriveStraight(RobotConstants.feetToMeters(14)));
		runCommand(commands.new TurnAngle(-90, true));
		runCommand(commands.new DriveStraight(RobotConstants.feetToMeters(3.5)));

	}

}
