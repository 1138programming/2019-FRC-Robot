package frc.commands.Drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.DriveSubsystem;

public class DriveForward extends Command {
	public DriveForward() {
		requires(Robot.DRIVE_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		//Robot.DRIVE_SUBSYSTEM.moveForward(DriveSubsystem.KDrivePosition);
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
