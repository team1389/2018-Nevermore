package com.team1389.robot;

import java.util.function.Supplier;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.drive.SixDriveOut;

public class RobotSoftware extends RobotHardware
{
	private static RobotSoftware INSTANCE = new RobotSoftware();

	public final RangeOut<Percent> right = driveRightT.getVoltageController()
			.addFollowers(driveRightVA.getVoltageOutput());
	public final RangeOut<Percent> left = driveLeftT.getVoltageController()
			.addFollowers(driveLeftVA.getVoltageOutput());

	public final SixDriveOut<Percent> drive = new SixDriveOut<Percent>(driveLeftT.getVoltageController(),
			driveRightT.getVoltageController(), driveLeftVA.getVoltageOutput(), driveRightVA.getVoltageOutput(),
			driveLeftVB.getVoltageOutput(), driveRightVB.getVoltageOutput());
	public final AngleIn<Position> robotAngle = gyro.getAngleInput();
	private final RangeIn<Position> elevatorPositionleft = elevatorLeft.getSensorPositionStream();
	private final RangeIn<Position> elevatorPositionRight = elevatorRight.getSensorPositionStream();
	public final RangeIn<Position> elevatorPosition = new RangeIn<Position>(Position.class,
			() -> (elevatorPositionleft.get() + elevatorPositionRight.get() / 2), Double.MIN_VALUE, Double.MAX_VALUE);
	public final AngleIn<Position> armAngle = armLiftRight.getSensorPositionStream().mapToAngle(Position.class);
	public final RangeIn<Speed> armSpeed = new RangeIn<Speed>(Speed.class,
			(Supplier<Double>) () -> ((armLiftLeft.getVelocityStream().get() + armLiftRight.getVelocityStream().get())
					/ 2),
			Double.MIN_VALUE, Double.MAX_VALUE);
	public final RangeIn<Speed> elevatorSpeed = new RangeIn<Speed>(Speed.class,
			(Supplier<Double>) () -> ((elevatorRight.getVelocityStream().get()
					+ elevatorLeft.getVelocityStream().get() / 2)),
			Double.MIN_VALUE, Double.MAX_VALUE);

	public static RobotSoftware getInstance()
	{
		return INSTANCE;
	}

	public RobotSoftware()
	{
	}

}
