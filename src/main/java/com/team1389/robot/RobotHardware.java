package com.team1389.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.team1389.hardware.inputs.hardware.SpartanGyro;
import com.team1389.hardware.inputs.hardware.SwitchHardware;
import com.team1389.hardware.outputs.hardware.CANTalonHardware;
import com.team1389.hardware.outputs.hardware.VictorHardware;
import com.team1389.hardware.registry.Registry;

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
		initElevator();
		//initArm();
	}

	private void initDriveTrain()
	{
		driveLeftVA = new VictorHardware(inv_LEFT_DRIVE_V_A, pwm_LEFT_DRIVE_V_A, registry);
		driveRightVA = new VictorHardware(inv_RIGHT_DRIVE_V_A, pwm_RIGHT_DRIVE_V_A, registry);
		driveLeftVB = new VictorHardware(inv_LEFT_DRIVE_V_B, pwm_LEFT_DRIVE_V_B, registry);
		driveRightVB = new VictorHardware(inv_RIGHT_DRIVE_V_B, pwm_RIGHT_DRIVE_V_B, registry);
		driveLeftT = new CANTalonHardware(inv_LEFT_DRIVE_T, sinv_LEFT_DRIVE_T, FeedbackDevice.QuadEncoder, 1024,
				can_LEFT_DRIVE_T, registry);
		driveRightT = new CANTalonHardware(inv_RIGHT_DRIVE_T, sinv_RIGHT_DRIVE_T, FeedbackDevice.QuadEncoder, 1024,
				can_RIGHT_DRIVE_T, registry, t -> t.setSelectedSensorPosition(0, 0, 10));
	}

	private void initElevator()
	{
		elevatorLeft = new CANTalonHardware(inv_LEFT_ELEVATOR, sinv_LEFT_ELEVATOR, FeedbackDevice.QuadEncoder, 1024,
				can_LEFT_ELEVATOR, registry, t -> t.setSelectedSensorPosition(0, 0, 10));
		elevatorRight = new CANTalonHardware(inv_RIGHT_ELEVATOR, sinv_RIGHT_ELEVATOR, FeedbackDevice.QuadEncoder, 1024,
				can_RIGHT_ELEVATOR, registry, t -> t.setSelectedSensorPosition(0, 0, 10));
		elevatorZero = new SwitchHardware(inv_ELEVATOR_ZERO, dio_ELEVATOR_ZERO, registry);
		elevatorTop = new SwitchHardware(inv_ELEVATOR_TOP, dio_ELEVATOR_TOP, registry);
	}
 
	private void initArm()
	{
		armIntakeA = new VictorHardware(inv_ARM_INTAKE_A, pwm_ARM_INTAKE_A, registry);
		armIntakeB = new VictorHardware(inv_ARM_INTAKE_B, pwm_ARM_INTAKE_B, registry);
		armLiftLeft = new CANTalonHardware(inv_LEFT_ARM_LIFT, can_LEFT_ARM_LIFT, registry);
		armLiftRight = new CANTalonHardware(inv_RIGHT_ARM_LIFT, sinv_ARM_RIGHT_LIFT, FeedbackDevice.QuadEncoder, 1024,
				can_RIGHT_ARM_LIFT, registry, t -> t.setSelectedSensorPosition(0, 0, 10));
		beambreak = new SwitchHardware(inv_BEAMBREAK, dio_BEAMBREAK, registry);
		armZero = new SwitchHardware(inv_ARM_ZERO, dio_ARM_ZERO, registry);
	}

	public Registry getRegistry()
	{
		return registry;
	}

}