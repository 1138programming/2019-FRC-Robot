package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.subsystems.DriveSubsystem;

public class NintendoSwitch extends Command {

int SwitchCase = 0;

	public NintendoSwitch() {
		requires(Robot.CLIMB_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
        SwitchCase++;
	}

	@Override
	protected void execute() {
	SmartDashboard.putNumber("SwitchCase", SwitchCase);
    switch (SwitchCase) {
        case 1: 
			Robot.CAMERA.switchToCamera2();
			Robot.DRIVE_SUBSYSTEM.switchDriveBase(true);
			SwitchCase++;
            break;
        case 3:
			Robot.CAMERA.switchToCamera1();
			Robot.DRIVE_SUBSYSTEM.switchDriveBase(false);
            SwitchCase = 0;
            break;
        default:
        
        }
    }

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
