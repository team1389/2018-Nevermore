package com.team1389.autonomous;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.autonomous.paths.GeneratePaths;
import com.team1389.robot.RobotSoftware;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class DriveStraightPathFinder extends AutoModeBase
{
	RobotSoftware robot;
	
	public DriveStraightPathFinder(RobotSoftware robot) 
	{
		this.robot = robot;
		
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem)
	{
		return null;
	}

	@Override
	public String getIdentifier()
	{
		return "Drive Straight Path Finder";
	}

	@Override
	protected void routine() throws AutoModeEndedException
	{
		GeneratePaths path = new GeneratePaths(robot);
	}
	
}
