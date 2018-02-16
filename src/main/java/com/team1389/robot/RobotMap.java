package com.team1389.robot;

import com.team1389.hardware.registry.port_types.CAN;
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
	protected final PWM pwm_LEFT_DRIVE_V = new PWM(1);
	protected final boolean inv_LEFT_DRIVE_V = false;
	
	protected final CAN can_LEFT_DRIVE_T = new CAN(1);
	protected final boolean inv_LEFT_DRIVE_T = false;
	protected final boolean sinv_LEFT_DRIVE_T = false;
	
	protected final PWM pwm_RIGHT_DRIVE_V = new PWM(0);
	protected final boolean inv_RIGHT_DRIVE_V = true;
	
	protected final CAN can_RIGHT_DRIVE_T = new CAN(2);
	protected final boolean inv_RIGHT_DRIVE_T = true;
	protected final boolean sinv_RIGHT_DRIVE_T = false;
	
	//gyro
	protected final SPIPort spi_GyroPort = new SPIPort(SPI.Port.kOnboardCS0);
	
	
	
	

}
