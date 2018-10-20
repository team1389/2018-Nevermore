package com.team1389.robot;

import java.util.function.Supplier;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.drive.DriveOut;
import com.team1389.system.drive.SixDriveOut;

//all mapped to meters
public class RobotSoftware extends RobotHardware {
	private static RobotSoftware INSTANCE = new RobotSoftware();

	// misc
	public final AngleIn<Position> robotAngle = gyro.getAngleInput();

	// Drivetrain

	// this(new DriveOut<T>(frontLeft,frontRight),new
	// DriveOut<T>(middleLeft,middleRight),new DriveOut<T>(backLeft,backRight));

	public final RangeOut<Percent> right = driveRightT.getVoltageController()
			.addFollowers(driveRightVA.getVoltageController().addFollowers(driveRightVB.getVoltageController()));
	public final RangeOut<Percent> left = driveLeftT.getVoltageController()
			.addFollowers(driveLeftVA.getVoltageController().addFollowers(driveLeftVB.getVoltageController()));
	public final SixDriveOut<Percent> drive = new SixDriveOut<Percent>(driveLeftT.getVoltageController().scale(0.5),
			driveRightT.getVoltageController().scale(0.5), driveLeftVA.getVoltageController().scale(0.5),
			driveRightVA.getVoltageController().scale(0.5), driveLeftVB.getVoltageController().scale(0.5),
			driveRightVB.getVoltageController().scale(0.5));
	public final DriveOut<Percent> front = new DriveOut<>(driveLeftT.getVoltageController(),
			driveRightT.getVoltageController());
	public final DriveOut<Percent> middle = new DriveOut<Percent>(driveLeftVA.getVoltageController(),
			driveRightVA.getVoltageController());
	public final DriveOut<Percent> back = new DriveOut<>(driveLeftVB.getVoltageController(),
			driveRightVB.getVoltageController());
	public final RangeOut<Percent> arm = armLiftRight.getVoltageController()
			.addFollowers(armLiftLeft.getVoltageController());
	public final RangeOut<Percent> armIntake = armIntakeA.getVoltageController()
			.addFollowers(armIntakeB.getVoltageController());
	public final RangeOut<Percent> elevator = elevatorLeft.getVoltageController()
			.addFollowers(elevatorRight.getVoltageController());
	// in meters
	public final RangeIn<Position> lPos = driveLeftT.getSensorPositionStream().mapToRange(0, 1)
			.scale(RobotConstants.WheelDiameter);
	public final RangeIn<Position> rPos = driveRightT.getSensorPositionStream().mapToRange(0, 1)
			.scale(RobotConstants.WheelDiameter);

	// Elevator
	// 18.66 scaling is 9.33 gear ratio * 2 for cascading elevator
	public final RangeIn<Position> rawElevatorPos = elevatorLeft.getSensorPositionStream();
	public final RangeIn<Position> elevatorPositionleft = elevatorLeft.getSensorPositionStream().scale(10).mapToRange(0,
			3.14);

	// in inches
	/*
	 * private final RangeIn<Position> elevatorPositionRight =
	 * elevatorRight.getSensorPositionStream().scale(18.66) .mapToRange(0,
	 * 0.087776098768); public final RangeIn<Position> elevatorPosition = new
	 * RangeIn<Position>(Position.class, () -> ((elevatorPositionleft.get() +
	 * elevatorPositionRight.get()) / 2), 0, 2.159);
	 */

	public static RobotSoftware getInstance() {
		return INSTANCE;
	}

	public RobotSoftware() {
	}

}
