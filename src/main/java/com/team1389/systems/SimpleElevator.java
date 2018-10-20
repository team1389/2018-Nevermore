package com.team1389.systems;

import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.system.Subsystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class SimpleElevator extends Subsystem {
	private RangeIn<Percent> ctrlAxis;
	private RangeOut<Percent> elevVolt;
	private RangeOut<Percent> elevIntake;
	private RangeIn<Percent> intakeCtrl;

	public SimpleElevator(RangeIn<Percent> ctrlAxis, RangeOut<Percent> elevVolt, RangeOut<Percent> elevIntake,
			RangeIn<Percent> intakeCtrl) {
		super();
		this.ctrlAxis = ctrlAxis;
		this.elevVolt = elevVolt;
		this.elevIntake = elevIntake;
		this.intakeCtrl = intakeCtrl;
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> arg0) {
		return arg0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "elevator";
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		elevVolt.set(ctrlAxis.get() * .5);
		elevIntake.set(intakeCtrl.get());
	}

}
