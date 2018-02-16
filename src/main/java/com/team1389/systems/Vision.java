package com.team1389.systems;

import com.team1389.hardware.value_types.Percent;
import com.team1389.system.Subsystem;
import com.team1389.system.drive.DriveOut;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;
import com.team1389.watch.info.NumberInfo;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Turns to track cube given error over networktables, updating is toggled on
 * and off via btn
 * 
 * @author Quunii
 *
 */
public class Vision extends Subsystem
{
	private static final String tableName = "Vision";
	private static final String errorKey = "error";
	private static final NetworkTable table = NetworkTableInstance.getDefault().getTable(tableName);
	double kP;
	DriveOut<Percent> drive;

	public Vision(DriveOut<Percent> drive)
	{
		this.drive = drive;
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> arg0)
	{
		return arg0.put(drive, new NumberInfo("error", () -> table.getEntry(errorKey).getDouble(0.0)));
	}

	@Override
	public String getName()
	{
		return "vision";
	}

	@Override
	public void init()
	{
		kP = 0.01;
	}

	
	@Override
	public void update()
	{
		double error = table.getEntry(errorKey).getDouble(0.0);
		drive.set(0 + error * kP, 0 - error * kP);
	}

}
