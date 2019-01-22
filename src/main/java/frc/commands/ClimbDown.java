package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.ClimbSubsystem;

public class ClimbDown extends Command {
	public ClimbDown() {
		requires(Robot.CLIMB_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.CLIMB_SUBSYSTEM.moveClimb(-ClimbSubsystem.KClimbSpeed);
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
