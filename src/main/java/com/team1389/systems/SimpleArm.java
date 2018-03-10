package com.team1389.systems;

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
		armVolt.set(ctrlAxis.invert().get());
	}

}
