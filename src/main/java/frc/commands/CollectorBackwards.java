package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.CollectorSubsystem;

public class CollectorBackwards extends Command {
	public CollectorBackwards() {
		requires(Robot.COLLECTOR_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
	Robot.COLLECTOR_SUBSYSTEM.moveCollector(-CollectorSubsystem.KCollecterSpeed);
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
