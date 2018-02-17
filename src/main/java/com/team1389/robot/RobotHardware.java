package com.team1389.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.team1389.hardware.inputs.hardware.SpartanGyro;
import com.team1389.hardware.outputs.hardware.CANTalonHardware;
import com.team1389.hardware.outputs.hardware.VictorHardware;
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.registry.port_types.SPIPort;

import edu.wpi.first.wpilibj.SPI.Port;

/**
 * responsible for initializing and storing hardware objects defined in
 * {@link RobotLayout}
 * 
 * @author amind
 * @see RobotLayout
 * @see RobotMap
 */
public class RobotHardware extends RobotLayout
{

	/**
	 * Initializes robot hardware by subsystem. <br>
	 * note: use this method as an index to show hardware initializations that
	 * occur, and to find the init code for a particular system's hardware
	 */
	protected RobotHardware()
	{
		registry = new Registry();
		System.out.println("initializing hardware");
		gyro = new SpartanGyro(spi_GyroPort, registry);
		initDriveTrain();
	}

	private void initDriveTrain()
	{
		driveLeftV = new VictorHardware(inv_LEFT_DRIVE_V, pwm_LEFT_DRIVE_V, registry);
		driveRightV = new VictorHardware(inv_RIGHT_DRIVE_V, pwm_RIGHT_DRIVE_V, registry);
		driveLeftT = new CANTalonHardware(inv_LEFT_DRIVE_T, sinv_LEFT_DRIVE_T, FeedbackDevice.QuadEncoder, 1024,
				can_LEFT_DRIVE_T, registry, t -> t.setSelectedSensorPosition(0, 0, 10));
		driveRightT = new CANTalonHardware(inv_RIGHT_DRIVE_T, sinv_RIGHT_DRIVE_T, FeedbackDevice.QuadEncoder, 1024,
				can_RIGHT_DRIVE_T, registry, t -> t.setSelectedSensorPosition(0, 0, 10));
	}
	

	public Registry getRegistry()
	{
		return registry;
	}

}