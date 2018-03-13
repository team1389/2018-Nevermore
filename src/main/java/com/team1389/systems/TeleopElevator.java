package com.team1389.systems;

import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.PercentIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

/**
 * Teleop Elevator System, implements buttons for manipulator control
 * 
 * @author Raffi
 *
 */
public class TeleopElevator extends Elevator
{

	// can have max bound to start, decided that low and high switch can just be
	// high switch
	DigitalIn zeroBtn;
	DigitalIn switchBtn;
	DigitalIn scaleLowBtn;
	DigitalIn scaleMiddleBtn;
	DigitalIn scaleHighBtn;
	DigitalIn manualBtn;
	PercentIn intakeAxis;
	PercentIn ctrlAxis;
	RangeOut<Percent> elevVolt;
	RangeOut<Percent> intakeVolt;
	boolean manual;

	public TeleopElevator(DigitalIn zero, RangeIn<Position> elevPos, RangeIn<Speed> elevVel, RangeOut<Percent> elevVolt,
			RangeOut<Percent> intakeVolt, DigitalIn zeroBtn, DigitalIn switchBtn, DigitalIn scaleLowBtn,
			DigitalIn scaleMiddleBtn, DigitalIn scaleHighBtn, DigitalIn manualBtn, PercentIn ctrlAxis,
			PercentIn intakeAxis)
	{
		super(zero, elevPos, elevVel, elevVolt);
		this.zeroBtn = zeroBtn;
		this.switchBtn = switchBtn;
		this.scaleLowBtn = scaleLowBtn;
		this.scaleMiddleBtn = scaleMiddleBtn;
		this.scaleHighBtn = scaleHighBtn;
		this.manualBtn = manualBtn;
		this.ctrlAxis = ctrlAxis;
		this.intakeAxis = intakeAxis;
		this.intakeVolt = intakeVolt;
	}

	/**
	 * If we're in a manual or the manual mode is being pressed, update manual
	 * mode, else update advanced mode
	 */

	@Override
	public void update()
	{
		intakeVolt.set(intakeAxis.invert().get());
		manual = manual ^ manualBtn.get();

		if (manual)
		{
			updateManual();
		} else
		{
			updateAdvanced();
		}

	}

	/**
	 * updates the mode that uses motion profiles to go to desired heights
	 */
	private void updateAdvanced()
	{

		if (zeroBtn.get())
		{
			scheduler.cancelAll();
			scheduler.schedule(goToZero());
		} else if (switchBtn.get())
		{
			scheduler.cancelAll();
			scheduler.schedule(goToSwitch());
		} else if (scaleLowBtn.get())
		{
			scheduler.cancelAll();
			scheduler.schedule(goToScaleLow());
		} else if (scaleHighBtn.get())
		{
			scheduler.cancelAll();
			scheduler.schedule(goToScaleHigh());
		}

		super.update();
	}

	/**
	 * controls the height of the elevator directly proportional to how much the
	 * manipulator's stick is being pushed
	 */
	private void updateManual()
	{
		elevVolt.set(ctrlAxis.get());

	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem)
	{
		return stem;
	}

	@Override
	public String getName()
	{
		return "Teleop Elevator";
	}

	@Override
	public void init()
	{
		super.init();
		scheduler.cancelAll();
	}

}
