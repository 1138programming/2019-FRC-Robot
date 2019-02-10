package frc.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;

public class MoveArmToPosition extends Command {
    double armPosition;

	public MoveArmToPosition(double armPosition) {
        requires(Robot.ARM_SUBSYSTEM);
        this.armPosition = armPosition;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.ARM_SUBSYSTEM.moveArmWithEncoders(armPosition);
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