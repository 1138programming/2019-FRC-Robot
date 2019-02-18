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
		double joystickValue = (Robot.ARM_SUBSYSTEM.checkLeftArmLimits(Robot.oi.getRightXbox()) + Robot.ARM_SUBSYSTEM.checkRightArmLimits(Robot.oi.getRightXbox()))/2;
    
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
