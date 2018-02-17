package com.team1389.robot;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.PositionEncoderIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.system.drive.FourDriveOut;

public class RobotSoftware extends RobotHardware
{
	private static RobotSoftware INSTANCE = new RobotSoftware();

	public final RangeOut<Percent> right = driveRightT.getVoltageController()
			.addFollowers(driveRightV.getVoltageOutput());
	public final RangeOut<Percent> left = driveLeftT.getVoltageController().addFollowers(driveLeftV.getVoltageOutput());
	// public final DriveOut<Percent> drive = new
	// DriveOut<Percent>(leftDriveT.getVoltageController(),
	// rightDriveT.getVoltageController());
	public final FourDriveOut<Percent> drive = new FourDriveOut<Percent>(driveLeftT.getVoltageController(),
			driveRightT.getVoltageController(), driveLeftV.getVoltageOutput(), driveRightV.getVoltageOutput());
	public final AngleIn<Position> robotAngle = gyro.getAngleInput();
	//public final AngleIn<Position> elevatorPosition = 

	public static RobotSoftware getInstance()
	{
		return INSTANCE;
	}

	public RobotSoftware()
	{
		PositionEncoderIn.setGlobalWheelDiameter(RobotConstants.WheelDiameter);
	}

}
