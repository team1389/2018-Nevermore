package com.team1389.autonomous;

import java.util.Optional;
import java.util.function.Function;

import com.team1389.auto.AutoModeBase;
import com.team1389.autonomous.simple_autos.CrossAutoline;
import com.team1389.autonomous.simple_autos.SameSideLeftSwitch;
import com.team1389.autonomous.simple_autos.SameSideRightSwitch;
import com.team1389.robot.RobotSoftware;

/**
 * all autons
 * 
 * @author Quunii
 *
 */
public enum AutoOption
{
	CROSS_AUTOLINE(CrossAutoline::new), SAME_SIDE_LEFT_SWITCH(SameSideLeftSwitch::new), SAME_SIDE_RIGHT_SWITCH(SameSideRightSwitch::new);
	Optional<Function<RobotSoftware, AutoModeBase>> autoConstructor;

	AutoOption(Function<RobotSoftware, AutoModeBase> autoConstructor)
	{
		this.autoConstructor = Optional.of(autoConstructor);
	}

	AutoOption()
	{
		this.autoConstructor = Optional.empty();
	}

	public AutoModeBase setupAutoModeBase(RobotSoftware robot)
	{
		if (autoConstructor.isPresent())
		{
			return autoConstructor.get().apply(robot);
		} else
		{
			throw new RuntimeException("cannot auto insantiate a complex style auto option!");
		}
	}
}
