package frc.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class MoveArmToPosition extends Command {
	double armPosition;
	double error;

	public MoveArmToPosition(double armPosition) {
        requires(Robot.ARM_SUBSYSTEM);
		this.armPosition = armPosition;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		error = Robot.ARM_SUBSYSTEM.moveArmWithEncoders(armPosition);
		SmartDashboard.putNumber("Error Arm", error);
		SmartDashboard.putNumber("Left Arm Encoder Position", Robot.ARM_SUBSYSTEM.getLeftArmEncoder());
		SmartDashboard.putNumber("Right Arm Encoder Position", Robot.ARM_SUBSYSTEM.getRightArmEncoder());
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(error) < 50;
	}

	@Override
	protected void end() {
		Robot.ARM_SUBSYSTEM.moveArm(0);
	}

	@Override
	protected void interrupted() {
	}
}