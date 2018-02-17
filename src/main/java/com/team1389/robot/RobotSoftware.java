package com.team1389.robot;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.system.drive.SixDriveOut;

public class RobotSoftware extends RobotHardware
{
	private static RobotSoftware INSTANCE = new RobotSoftware();

	public final RangeOut<Percent> right = driveRightT.getVoltageController()
			.addFollowers(driveRightV1.getVoltageOutput());
	public final RangeOut<Percent> left = driveLeftT.getVoltageController()
			.addFollowers(driveLeftV1.getVoltageOutput());

	public final SixDriveOut<Percent> drive = new SixDriveOut<Percent>(driveLeftT.getVoltageController(),
			driveRightT.getVoltageController(), driveLeftV1.getVoltageOutput(), driveRightV1.getVoltageOutput(),
			driveLeftV2.getVoltageOutput(), driveRightV2.getVoltageOutput());
	public final AngleIn<Position> robotAngle = gyro.getAngleInput();
	public final RangeIn<Position> elevatorPositionleft = elevatorLeft.getSensorPositionStream();
	public final RangeIn<Position> elevatorPositionRight = elevatorRight.getSensorPositionStream();
	public final AngleIn<Position> armAngleLeft = armLiftLeft.getSensorPositionStream().mapToAngle(Position.class);
	public final AngleIn<Position> armAngleRIght = armLiftRight.getSensorPositionStream().mapToAngle(Position.class);

	public static RobotSoftware getInstance()
	{
		return INSTANCE;
	}

	public RobotSoftware()
	{
	}

}
