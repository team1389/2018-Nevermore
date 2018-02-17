package com.team1389.robot;

import com.team1389.hardware.inputs.hardware.PDPHardware;
import com.team1389.hardware.inputs.hardware.SpartanGyro;
import com.team1389.hardware.outputs.hardware.CANTalonHardware;
import com.team1389.hardware.outputs.hardware.VictorHardware;
import com.team1389.hardware.registry.Registry;

/**
 * contains a list of declared hardware objects for this robot. Separated from {@link RobotHardware}
 * to make it easier to see what hardware is connected to the robot.
 * 
 */
public class RobotLayout extends RobotMap {
	public Registry registry;
	public PDPHardware pdp;
	public VictorHardware driveLeftV;
	public VictorHardware driveRightV;
	public CANTalonHardware driveLeftT;
	public CANTalonHardware driveRightT;
	public CANTalonHardware elevatorRight;
	public SpartanGyro gyro;
	

}
