package frc.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class MoveLiftToPosition extends Command {
	double liftPosition;
	double error;

	public MoveLiftToPosition(double liftPosition) {
        requires(Robot.LIFT_SUBSYSTEM);
		this.liftPosition = liftPosition;
	}

	public MoveLiftToPosition(double liftPosition, boolean runOnce) {
        requires(Robot.LIFT_SUBSYSTEM);
		this.liftPosition = liftPosition;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		error = Robot.LIFT_SUBSYSTEM.moveLiftWithEncoders(liftPosition);
		SmartDashboard.putNumber("Lift Encoder", Robot.LIFT_SUBSYSTEM.getLiftEncoder());
		SmartDashboard.putNumber("error", error);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(error) < 600;
	}

	@Override
	protected void end() {
		Robot.LIFT_SUBSYSTEM.moveLift(0);
	}

	@Override
	protected void interrupted() {
	}
}