package com.team1389.robot;

import java.util.function.Supplier;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.drive.SixDriveOut;

/**
 * all mapping to meters
 * 
 * @author Quunii
 *
 */
public class RobotSoftware extends RobotHardware
{
	private static RobotSoftware INSTANCE = new RobotSoftware();

	// misc
	public final AngleIn<Position> robotAngle = gyro.getAngleInput();

	// Drivetrain
	public final RangeOut<Percent> right = driveRightT.getVoltageController()
			.addFollowers(driveRightVA.getVoltageOutput());
	public final RangeOut<Percent> left = driveLeftT.getVoltageController()
			.addFollowers(driveLeftVA.getVoltageOutput());
	public final SixDriveOut<Percent> drive = new SixDriveOut<Percent>(driveLeftT.getVoltageController(),
			driveRightT.getVoltageController(), driveLeftVA.getVoltageOutput(), driveRightVA.getVoltageOutput(),
			driveLeftVB.getVoltageOutput(), driveRightVB.getVoltageOutput());

	// Elevator
	// 18.66 scaling is 9.33 gear ratio * 2 for cascading elevator
	/*private final RangeIn<Position> elevatorPositionleft = elevatorLeft.getSensorPositionStream().scale(18.66)
			.mapToRange(0, 0.087776098768);
	private final RangeIn<Position> elevatorPositionRight = elevatorRight.getSensorPositionStream().scale(18.66)
			.mapToRange(0, 0.087776098768);
	public final RangeIn<Position> elevatorPosition = new RangeIn<Position>(Position.class,
			() -> ((elevatorPositionleft.get() + elevatorPositionRight.get()) / 2), 0, 2.159);

	// Arm
	public final AngleIn<Position> armAngle = armLiftRight.getSensorPositionStream().mapToAngle(Position.class);
	public final RangeIn<Speed> armSpeed = armLiftRight.getSensorPositionStream().scale(10).mapToAngle(Speed.class);
	// 93.3 scaling is for gear ratio * conversion to (eventually) meters/sec
	private final RangeIn<Speed> elevatorSpeedLeft = elevatorLeft.getVelocityStream().scale(93.3).mapToRange(0,
			0.087776098768);
	private final RangeIn<Speed> elevatorSpeedRight = elevatorRight.getVelocityStream().scale(93.3).mapToRange(0,
			0.087776098768);
	public final RangeIn<Speed> elevatorSpeed = new RangeIn<Speed>(Speed.class,
			(Supplier<Double>) () -> ((elevatorRight.getVelocityStream().get() + elevatorLeft.getVelocityStream().get())
					/ 2),
			Double.MIN_VALUE, Double.MAX_VALUE);*/

	public static RobotSoftware getInstance()
	{
		return INSTANCE;
	}

	public RobotSoftware()
	{
	}

}
