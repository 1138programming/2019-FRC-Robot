package frc.commands.Climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.ClimbSubsystem;

public class ClimbUp extends Command {
	public ClimbUp() {
		requires(Robot.CLIMB_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.CLIMB_SUBSYSTEM.moveClimb(ClimbSubsystem.KClimbSpeed);
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
