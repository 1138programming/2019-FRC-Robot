package frc.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem.ArmPosition;
import frc.subsystems.LiftSubsystem.LiftPosition;

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

		 if ((!Robot.ARM_SUBSYSTEM.leftLimitClosed() || Robot.ARM_SUBSYSTEM.getLeftArmEncoder() != 0 || 
      		!Robot.ARM_SUBSYSTEM.rightLimitClosed() || Robot.ARM_SUBSYSTEM.getRightArmEncoder() != 0) && 
      		Robot.armHasBeenReset == false && Robot.oi.getRightXbox() >= 0) {
        		joystickValue = 0;
    	}
    	else if ((Robot.ARM_SUBSYSTEM.leftLimitClosed() && Robot.ARM_SUBSYSTEM.getLeftArmEncoder() == 0 && Robot.ARM_SUBSYSTEM.rightLimitClosed() && Robot.ARM_SUBSYSTEM.getRightArmEncoder() == 0) && Robot.armHasBeenReset == false) {
      		Robot.armHasBeenReset = true;
		}

		Robot.ARM_SUBSYSTEM.moveArm(joystickValue);

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
