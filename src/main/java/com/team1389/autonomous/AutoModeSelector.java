package com.team1389.autonomous;

import com.team1389.auto.AutoModeBase;

public class AutoModeSelector {
	public static AutoModeBase createAutoMode(AutoOption autonOption) {
		switch (autonOption) {
		case DRIVE_STRAIGHT:
			return null;
		default:
			System.out.println("ERROR: unexpected auto mode: " + autonOption);
			return null;
		}
	}
}
