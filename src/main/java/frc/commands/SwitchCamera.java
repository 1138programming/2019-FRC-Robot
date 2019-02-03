package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SwitchCamera extends Command {

int SwitchCase = 0;

	public SwitchCamera() {
		requires(Robot.CLIMB_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
        SwitchCase++;
	}

	@Override
	protected void execute() {
    switch (SwitchCase) {
        case 1: 
            Robot.CAMERA.switchToCamera1();
            SwitchCase++;
            break;
        case 3:
            Robot.CAMERA.switchToCamera2();
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
