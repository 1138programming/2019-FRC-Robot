package frc.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;
import frc.robot.OI;

public class ArmWithJoysticks extends Command
{
	public ArmWithJoysticks() {
		requires(Robot.ARM_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double joystickValue = Robot.oi.getRightXbox();

		if(Robot.ARM_SUBSYSTEM.leftLimitClosed() == true) {
			if (joystickValue > 0 && Robot.ARM_SUBSYSTEM.getLeftArmEncoder() <= ArmSubsystem.KArmTopReset) {
			}
			else if (joystickValue < 0 && Robot.ARM_SUBSYSTEM.getLeftArmEncoder() <= ArmSubsystem.KArmTopReset) {
				joystickValue = 0;
			}
			else if (joystickValue > 0 && Robot.ARM_SUBSYSTEM.getLeftArmEncoder() >= ArmSubsystem.KArmBottomReset) {
				joystickValue = 0;
			}
			else if (joystickValue < 0 && Robot.ARM_SUBSYSTEM.getLeftArmEncoder() >= ArmSubsystem.KArmBottomReset) {
			}

			Robot.ARM_SUBSYSTEM.leftLimitReset();
		} 

		if(Robot.ARM_SUBSYSTEM.rightLimitClosed() == true) {
			if (joystickValue > 0 && Robot.ARM_SUBSYSTEM.getRightArmEncoder() <= ArmSubsystem.KArmTopReset) {
			}
			else if (joystickValue < 0 && Robot.ARM_SUBSYSTEM.getRightArmEncoder() <= ArmSubsystem.KArmTopReset) {
				joystickValue = 0;
			}
			else if (joystickValue > 0 && Robot.ARM_SUBSYSTEM.getRightArmEncoder() >= ArmSubsystem.KArmBottomReset) {
				joystickValue = 0;
			}
			else if (joystickValue < 0 && Robot.ARM_SUBSYSTEM.getRightArmEncoder() >= ArmSubsystem.KArmBottomReset) {
			}

			Robot.ARM_SUBSYSTEM.rightLimitReset();
		}

		SmartDashboard.putNumber("Right Arm Encoder Position", Robot.ARM_SUBSYSTEM.getRightArmEncoder());
		SmartDashboard.putNumber("Left Arm Encoder Position", Robot.ARM_SUBSYSTEM.getLeftArmEncoder());

		Robot.ARM_SUBSYSTEM.moveArm(Robot.oi.getRightXbox());
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
