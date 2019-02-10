package frc.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.LiftSubsystem;

public class MoveLiftToPosition extends Command {
    double liftPosition;

	public MoveLiftToPosition(double liftPosition) {
        requires(Robot.LIFT_SUBSYSTEM);
        this.liftPosition = liftPosition;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.LIFT_SUBSYSTEM.moveLiftWithEncoders(liftPosition);
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