package frc.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem.ArmPosition;

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

		if(Robot.ARM_SUBSYSTEM.leftLimitClosed()) {
			if (joystickValue > 0 && Robot.ARM_SUBSYSTEM.getLeftArmPosition() == ArmPosition.FULLUP)
				joystickValue = 0;
			else if (joystickValue < 0 && Robot.ARM_SUBSYSTEM.getLeftArmPosition() == ArmPosition.FULLDOWN)
				joystickValue = 0;
			
			Robot.ARM_SUBSYSTEM.identifyLeftLimitandResetEncoder();
		} 

		if(Robot.ARM_SUBSYSTEM.rightLimitClosed()) {
			if (joystickValue > 0 && Robot.ARM_SUBSYSTEM.getRightArmPosition() == ArmPosition.FULLUP)
				joystickValue = 0;
			else if (joystickValue < 0 && Robot.ARM_SUBSYSTEM.getRightArmPosition() == ArmPosition.FULLDOWN)
				joystickValue = 0;
			
			Robot.ARM_SUBSYSTEM.identifyRightLimitandResetEncoder();
		} 

		if((Robot.ARM_SUBSYSTEM.getLeftArmPosition() == ArmPosition.FULLUP) || (Robot.ARM_SUBSYSTEM.getLeftArmPosition() == ArmPosition.FULLDOWN) || 
		   (Robot.ARM_SUBSYSTEM.getRightArmPosition() == ArmPosition.FULLUP) || (Robot.ARM_SUBSYSTEM.getRightArmPosition() == ArmPosition.FULLDOWN))	
				Robot.ARM_SUBSYSTEM.moveArm(joystickValue/2);
		else
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
