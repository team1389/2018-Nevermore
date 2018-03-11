package com.team1389.systems;

import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.PercentIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.system.Subsystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class SimpleArm extends Subsystem
{
	PercentIn ctrlAxis;
	RangeOut<Percent> armVolt;
	DigitalIn intake;
	DigitalIn outtake;
	DigitalIn neutral;
	RangeOut<Percent> intakeVolt;

	
	public SimpleArm(PercentIn ctrlAxis, RangeOut<Percent> armVolt)
	{
		this.ctrlAxis = ctrlAxis;
		this.armVolt = armVolt;
	}
	
	public SimpleArm(PercentIn ctrlAxis, RangeOut<Percent> armVolt, DigitalIn intake, DigitalIn outtake, DigitalIn neutral, RangeOut<Percent> intakeVolt)
	{
		this.intake = intake;
		this.outtake = outtake;
		this.neutral = neutral;
		this.intakeVolt = intakeVolt;
		this.ctrlAxis = ctrlAxis;
		this.armVolt = armVolt;
		
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem)
	{
		return stem.put(ctrlAxis.getWatchable("Arm control axis"), armVolt.getWatchable("Arm volt"));
	}

	@Override
	public String getName()
	{
		return "Simple Arm";
	}

	@Override
	public void init()
	{
		
	}
	

	@Override
	public void update()
	{
		if(intake.get())
		{
			intakeVolt.set(1);
		}
		else if(outtake.get()) {
			intakeVolt.set(-1);
			
		}
		else if(neutral.get()) {
			intakeVolt.set(0);
		}
		armVolt.set(ctrlAxis.get());
	}

}
