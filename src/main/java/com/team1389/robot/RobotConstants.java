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
	public static final double MaxVelocity = 22; // m/s
	public static final double MaxAcceleration = 22; // m/s^2
	public static final double MaxDeceleration = 22; // m/s^2
	public static final double MaxJerk = 22; // no idea
	
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
	public static final double GYROCorrection = 0.05;


}
