package com.team1389.autonomous;

import com.team1389.auto.AutoModeBase;
import com.team1389.robot.RobotSoftware;

/**
 * returns the selected auton
 * 
 * @author Quunii
 *
 */
public class AutoModeSelector
{
	/**
	 * 
	 * @param autonOption
	 *            the selected Auton
	 * @return the corresponding auton to that which is selected
	 */
	public static AutoModeBase createAutoMode(AutoOption autonOption)
	{
		if (autonOption == null)
		{
			return AutoOption.CROSS_AUTOLINE.setupAutoModeBase(RobotSoftware.getInstance());
		} else
		{
			return autonOption.setupAutoModeBase(RobotSoftware.getInstance());
		}
	}
}
