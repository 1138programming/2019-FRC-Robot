package frc.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class MoveLiftToPosition extends Command {
	double error;
	int target;

	private static final double allowableError = 10;

	public MoveLiftToPosition(int target) {
		requires(Robot.LIFT_SUBSYSTEM);
		this.target = target;
	}

	public MoveLiftToPosition(int target, boolean runOnce) {
        requires(Robot.LIFT_SUBSYSTEM);
		this.target = target;
	}

	@Override
	protected void initialize() {
		Robot.LIFT_SUBSYSTEM.initMoveTo();
	}

	@Override
	protected void execute() {
		error = Robot.LIFT_SUBSYSTEM.moveLiftTo(target);
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

	public void removeRequirements() {
		this.clearRequirements();
	}
}