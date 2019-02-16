package frc.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class MoveLiftToPosition extends Command {
	double liftPosition;
	double error;
	boolean runOnce;

	public MoveLiftToPosition(double liftPosition) {
        requires(Robot.LIFT_SUBSYSTEM);
		this.liftPosition = liftPosition;
		runOnce = true;
	}

	public MoveLiftToPosition(double liftPosition, boolean runOnce) {
        requires(Robot.LIFT_SUBSYSTEM);
		this.liftPosition = liftPosition;
		this.runOnce = runOnce;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		error = Robot.LIFT_SUBSYSTEM.moveLiftWithEncoders(liftPosition);
		SmartDashboard.putNumber("Lift Encoder", Robot.LIFT_SUBSYSTEM.getLiftEncoder());
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(error) < 15 || runOnce;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}