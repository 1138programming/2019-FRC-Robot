package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.X_TableSubsystem;

public class X_TableCenter extends Command {
	public X_TableCenter() {
		requires(Robot.X_TABLE_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.X_TABLE_SUBSYSTEM.moveX_Table(0);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
