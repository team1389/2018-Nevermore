package com.team1389.autonomous.paths;

import com.team1389.robot.RobotConstants;
import com.team1389.robot.RobotSoftware;
import com.team1389.trajectory.Path.Waypoint;
import com.team1389.trajectory.PathFollowingSystem;
import com.team1389.trajectory.PathFollowingSystem.Constants;
import com.team1389.trajectory.Translation2d;

public class GeneratePaths
{
	PathFollowingSystem path;
	Constants constants;

	public GeneratePaths(RobotSoftware robo)
	{
		constants = new Constants(RobotConstants.MaxJerk, RobotConstants.MaxAcceleration, RobotConstants.MaxVelocity, 1,
				0, 0, robo.pos.get(), 2);
		path = new PathFollowingSystem(robo.drive.getAsTank(), robo.leftDriveT.getSensorPositionStream(),
				robo.rightDriveT.getSensorPositionStream(), robo.pos, constants);
	}

	public void generateDriveStraight()
	{
		Waypoint[] points = new Waypoint[1];
		Translation2d desiredPos = new Translation2d(0, 5);
		Waypoint p1 = new Waypoint(desiredPos, 5);
		points[0] = p1;
		//Trajectory traj;
	}
	
	public void driveStraight()
	{
		generateDriveStraight();
	
	}

}
