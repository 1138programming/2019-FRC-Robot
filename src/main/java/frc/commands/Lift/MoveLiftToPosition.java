package frc.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class MoveLiftToPosition extends Command {
	LiftSubsystem.LiftPosition liftPosition;
	double error;

	private static final double allowableError = 500;

	public MoveLiftToPosition(LiftSubsystem.LiftPosition liftPosition) {
        requires(Robot.LIFT_SUBSYSTEM);
		this.liftPosition = liftPosition;
	}

	public MoveLiftToPosition(LiftSubsystem.LiftPosition liftPosition, boolean runOnce) {
        requires(Robot.LIFT_SUBSYSTEM);
		this.liftPosition = liftPosition;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		error = Robot.LIFT_SUBSYSTEM.moveLiftToPosition(liftPosition);
		SmartDashboard.putNumber("error", error);
		SmartDashboard.putNumber("Lift Encoder", Robot.LIFT_SUBSYSTEM.getLiftEncoder());
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(error) < allowableError;
	}

	@Override
	protected void end() {
		Robot.LIFT_SUBSYSTEM.moveLift(0);
	}

	@Override
	protected void interrupted() {
	}
}