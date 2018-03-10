package com.team1389.systems;

import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.PercentIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.system.Subsystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SimpleElevator extends Subsystem
{

	PercentIn ctrlAxis;
	RangeOut<Percent> elevVolt;
	RangeOut<Percent> intakeVolt;
	DigitalIn intake;
	SmartDashboard dash;
	double prevEncoder;

	public SimpleElevator(PercentIn ctrlAxis, DigitalIn intake, RangeOut<Percent> elevVolt,
			RangeOut<Percent> intakeVolt)
	{
		this.ctrlAxis = ctrlAxis;
		this.elevVolt = elevVolt;
		this.intake = intake;
		this.intakeVolt = intakeVolt;
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem)
	{
		return stem.put(elevVolt.getWatchable("Elev volt"), ctrlAxis.getWatchable("Ctrl axis"));

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
		elevVolt.set(ctrlAxis.scale(.5).get());
		if (intake.get())
		{
			intakeVolt.set(1);
		}
		/*if (prevEncoder > pos.get() + 10) 
		{
			elevVolt.set(.1);
		}
		prevEncoder = pos.get();
*/
	}

}
