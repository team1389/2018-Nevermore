package com.team1389.robot;

/**
 * Constants for the Robot.
 * All units are standard metric units
 * @author Raffi
 *
 */
public class RobotConstants {
	public static final int MaxConcurrentThreads = 3;

	/**
	 * constants for odometry calculations
	 */
	public static final double WheelDiameter = .127;
	public static final double WheelBase = 0.67945; // 
	public static final double TrackLength = 23;
	public static final double TrackScrub = 1;

	/**
	 * constants for motion profiling
	 */
	public static final double MaxVelocity = 3; // m/s
	public static final double MaxAcceleration = .5; // m/s^2
	public static final double MaxDeceleration = 4.5; // m/s^2
	public static final double MaxJerk = 30; // m/s^3
	
	/**
	 * constants for elevator motion profiling
	 * 
	 */
	public static final double ElevMaxVelocity = 0; 
	public static final double ElevMaxAcceleration = 0;
	public static final double ElevMaxDeceleration = 0; 
	public static final double ElevMaxJerk = 0; 
	
	/**
	 * GyroCorrection for Driving straight
	 */
	public static final double GyroCorrection = 0.05;

	public static double feetToMeters(double ft)
	{
		return ft * 0.3048;
	}
	public static double rotationsToMeters(double rotations)
	{
		return rotations * Math.PI * WheelDiameter;
	}

}
