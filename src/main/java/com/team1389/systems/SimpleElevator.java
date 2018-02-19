package com.team1389.systems;

import com.team1389.hardware.inputs.software.PercentIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.system.Subsystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SimpleElevator extends Subsystem
{

	PercentIn ctrlAxis;
	RangeOut<Percent> elevVolt;
	SmartDashboard dash;


	public SimpleElevator(PercentIn ctrlAxis, RangeOut<Percent> elevVolt)
	{
		this.ctrlAxis = ctrlAxis;
		this.elevVolt = elevVolt;
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem)
	{
		return stem.put(elevVolt.getWatchable("Elev volt"),  ctrlAxis.getWatchable("Ctrl axis"));
		
	}

	@Override
	public String getName()
	{
		return "Simple Elevator";
	}

	@Override
	public void init()
	{

	}

	@Override
	public void update()
	{
		SmartDashboard.putNumber("elevVolt", elevVolt.get());
		SmartDashboard.putNumber("ctrlAxis", ctrlAxis.get());
		elevVolt.set(ctrlAxis.invert().get());
	}

}
