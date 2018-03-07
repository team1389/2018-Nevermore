package com.team1389.systems;

import com.team1389.command_framework.CommandUtil;
import com.team1389.command_framework.command_base.Command;
import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.PercentIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;

/**
 * The
 * 
 * @author Raffi
 *
 */
public class TeleopArm extends Arm
{
	AngleIn<Position> armPos;
	RangeOut<Percent> intakeVolt;
	RangeOut<Percent> armVolt;
	RangeIn<Speed> armVel;
	PercentIn ctrlAxis;
	DigitalIn beambreak;
	DigitalIn zero;
	DigitalIn vertBtn, frontBtn, rearBtn, manualBtn, intakeBtn, outtakeBtn;
	boolean manual;

	public TeleopArm(PercentIn ctrlAxis, RangeOut<Percent> intakeVolt, RangeOut<Percent> armVolt, RangeIn<Speed> armVel,
			DigitalIn beambreak, DigitalIn zero, DigitalIn vertical, DigitalIn rear, DigitalIn vertBtn,
			DigitalIn frontBtn, DigitalIn rearBtn, DigitalIn manualBtn, DigitalIn intakeBtn, DigitalIn outtakeBtn)
	{
		super(intakeVolt, armVolt, beambreak, zero, vertical, rear, armVel);
		this.intakeBtn = intakeBtn;
		this.outtakeBtn = outtakeBtn;
		this.frontBtn = frontBtn;
		this.rearBtn = rearBtn;
		this.manualBtn = manualBtn;
		this.vertBtn = vertBtn;
	}

	@Override
	public void update()
	{
		manual = manual ^ manualBtn.get();
		if (manual)
		{
			updateManual();
		} else
		{
			updateAdvanced();
		}
	}

	public void updateAdvanced()
	{
		Command intakeCommand = determineIntakeCommand();
		if (frontBtn.get())
		{
			scheduler.cancelAll();
			scheduler.schedule(CommandUtil.combineSequential(goToFront(), intakeCommand));
		} else if (rearBtn.get())
		{
			scheduler.cancelAll();
			scheduler.schedule(CommandUtil.combineSequential(goToRear(), intakeCommand));
		} else if (vertBtn.get())
		{
			scheduler.cancelAll();
			scheduler.schedule(CommandUtil.combineSequential(goToVertical(), intakeCommand));

		}
		// if intake pressed, intake, if outtake pressed, outtake, if neither,
		// neutral
		super.update();
	}

	private Command determineIntakeCommand()
	{
		if (intakeBtn.get())
		{
			return setIntaking();
		}
		if (outtakeBtn.get())
		{
			return setOuttaking();
		} else
		{
			return setNeutral();
		}
	}

	public void updateManual()
	{
		if (zero.get())
		{
			armPos.offset(-armPos.get());
		}
		armVolt.set(ctrlAxis.get());
		setIntakeState(((intakeBtn.get()) ? IntakeState.INTAKING
				: ((outtakeBtn.get()) ? IntakeState.OUTTAKING : IntakeState.NEUTRAL)));
		intakeVolt.set(getIntakeState().voltage);
	}
}
