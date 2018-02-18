package com.team1389.watchers;

import com.team1389.auto.AutoModeBase;
import com.team1389.autonomous.AutoModeSelector;
import com.team1389.autonomous.AutoOption;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardInput {
	private static DashboardInput instance = new DashboardInput();

	public static DashboardInput getInstance() {
		return instance;
	}

	public DashboardInput() {
		init();
	}

	private SendableChooser<AutoOption> autonSelector;

	public void init() {
		autonSelector = new SendableChooser<AutoOption>();
		for (AutoOption autonOption : AutoOption.values()) {
			autonSelector.addObject(autonOption.name, autonOption);
		}

		SmartDashboard.putData(SELECTED_AUTO_MODE, autonSelector);
	}

	private static final String SELECTED_AUTO_MODE = "selected_auto_mode";
	private static final AutoOption DEFAULT_MODE = AutoOption.DRIVE_STRAIGHT;

	public AutoModeBase getSelectedAutonMode() {
		String autoModeString = SmartDashboard.getString(SELECTED_AUTO_MODE, DEFAULT_MODE.name);
		AutoOption selectedOption = DEFAULT_MODE;
		for (AutoOption autonOption : AutoOption.values()) {
			if (autonOption.name.equals(autoModeString)) {
				selectedOption = autonOption;
				break;
			}
		}
		selectedOption = (AutoOption) autonSelector.getSelected();
		return AutoModeSelector.createAutoMode(selectedOption);
	}

}
