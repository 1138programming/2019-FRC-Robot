package frc.commands.Climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.ClimbSubsystem;

public class ClimbReset extends Command {
	public ClimbReset() {
		requires(Robot.CLIMB_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.CLIMB_SUBSYSTEM.moveClimb(0);
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
