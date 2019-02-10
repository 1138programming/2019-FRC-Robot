package frc.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;

public class MoveArmToPosition extends Command {
	double armPosition;
	double error;
	boolean runOnce;

	public MoveArmToPosition(double armPosition) {
        requires(Robot.ARM_SUBSYSTEM);
		this.armPosition = armPosition;
		runOnce = true;
	}

	public MoveArmToPosition(double armPosition, boolean runOnce) {
        requires(Robot.ARM_SUBSYSTEM);
		this.armPosition = armPosition;
		this.runOnce = runOnce;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		error = Robot.ARM_SUBSYSTEM.moveArmWithEncoders(armPosition);
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