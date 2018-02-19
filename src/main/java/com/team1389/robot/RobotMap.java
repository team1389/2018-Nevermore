package com.team1389.robot;

import com.team1389.hardware.registry.port_types.CAN;
import com.team1389.hardware.registry.port_types.DIO;
import com.team1389.hardware.registry.port_types.PWM;
import com.team1389.hardware.registry.port_types.SPIPort;

import edu.wpi.first.wpilibj.SPI;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * <p>
 * <b>Conventions</b>: <br>
 * For I/O ports, the naming convention is <em>type_ALL_CAPS_IDENTIFIER</em>.
 * for example, a talon port might be named can_LEFT_MOTOR_A. Possible port
 * types and identifiers are CAN (can), Analog (anlg), PWM (pwm), USB (usb), PCM
 * (pcm), DIO (dio), etc
 * <p>
 * Inputs and Outputs may be inverted. The inversions in this map should only
 * relate to the physical configuration of the robot. A positive value should
 * cause the output to move in the most logical direction (I.e, the drive motors
 * should move forward with positive voltage values) <br>
 * the convention for inversion constants is
 * <em>inv_ASSOCIATED_IO_IDENTIFIER</em> for outputs and
 * <em>sinv_ASSOCIATED_IO_IDENTIFIER</em> for inputs.
 */
public class RobotMap
{
	// Drivetrain
	protected final PWM pwm_LEFT_DRIVE_V_A = new PWM(1);
	protected final boolean inv_LEFT_DRIVE_V_A = false;

	protected final PWM pwm_LEFT_DRIVE_V_B = new PWM(0);
	protected final boolean inv_LEFT_DRIVE_V_B = false;

	protected final CAN can_LEFT_DRIVE_T = new CAN(1);
	protected final boolean inv_LEFT_DRIVE_T = false;
	protected final boolean sinv_LEFT_DRIVE_T = false;

	protected final PWM pwm_RIGHT_DRIVE_V_A = new PWM(2);
	protected final boolean inv_RIGHT_DRIVE_V_A = false;

	protected final PWM pwm_RIGHT_DRIVE_V_B = new PWM(3);
	protected final boolean inv_RIGHT_DRIVE_V_B = true;

	protected final CAN can_RIGHT_DRIVE_T = new CAN(2);
	protected final boolean inv_RIGHT_DRIVE_T = true;
	protected final boolean sinv_RIGHT_DRIVE_T = false;

	// Elevator
	protected final CAN can_RIGHT_ELEVATOR = new CAN(3);
	protected final boolean inv_RIGHT_ELEVATOR = false;
	protected final boolean sinv_RIGHT_ELEVATOR = false;

	protected final CAN can_LEFT_ELEVATOR = new CAN(4);
	protected final boolean inv_LEFT_ELEVATOR = false;
	protected final boolean sinv_LEFT_ELEVATOR = false;

	protected final DIO dio_ELEVATOR_ZERO = new DIO(0);
	protected final boolean inv_ELEVATOR_ZERO = false;

	protected final DIO dio_ELEVATOR_TOP = new DIO(1);
	protected final boolean inv_ELEVATOR_TOP = false;

	// Arm
	protected final CAN can_RIGHT_ARM_LIFT = new CAN(5);
	protected final boolean inv_RIGHT_ARM_LIFT = false;
	protected final boolean sinv_ARM_RIGHT_LIFT = false;

	protected final CAN can_LEFT_ARM_LIFT = new CAN(6);
	protected final boolean inv_LEFT_ARM_LIFT = false;
	protected final boolean sinv_LEFT_ARM_LIFT = false;

	protected final PWM pwm_ARM_INTAKE_A = new PWM(14);
	protected final boolean inv_ARM_INTAKE_A = true;

	protected final PWM pwm_ARM_INTAKE_B = new PWM(5);
	protected final boolean inv_ARM_INTAKE_B = true;

	protected final DIO dio_BEAMBREAK = new DIO(2);
	protected final boolean inv_BEAMBREAK = false;

	protected final DIO dio_ARM_ZERO = new DIO(3);
	protected final boolean inv_ARM_ZERO = false;

	// gyro
	protected final SPIPort spi_GyroPort = new SPIPort(SPI.Port.kOnboardCS0);

}
