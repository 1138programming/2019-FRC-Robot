package frc.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;
import frc.subsystems.ArmSubsystem.ArmPosition;

public class ArmWithJoysticks extends Command
{
	private double joystickValue;

	public ArmWithJoysticks() {
		requires(Robot.ARM_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.ARM_SUBSYSTEM.moveArmWithJoysticks();

		SmartDashboard.putNumber("Right Arm Encoder Position", Robot.ARM_SUBSYSTEM.getRightArmEncoder());
		SmartDashboard.putNumber("Left Arm Encoder Position", Robot.ARM_SUBSYSTEM.getLeftArmEncoder());
		SmartDashboard.putBoolean("Left Limit Closed", Robot.ARM_SUBSYSTEM.leftLimitClosed());
		SmartDashboard.putBoolean("Right Limit Closed", Robot.ARM_SUBSYSTEM.rightLimitClosed());
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
