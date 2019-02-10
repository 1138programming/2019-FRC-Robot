package frc.commands.Drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ShiftDrive extends Command {
	public ShiftDrive() {
		requires(Robot.DRIVE_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.DRIVE_SUBSYSTEM.toggleShift();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end(){
	}

	@Override
	protected void interrupted() {
	}
}
