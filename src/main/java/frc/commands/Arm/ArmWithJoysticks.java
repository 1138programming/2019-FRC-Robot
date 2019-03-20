package frc.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

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

		Robot.ARM_SUBSYSTEM.moveArm(joystickValue, joystickValue);

		SmartDashboard.putNumber("Joystick value is: ", joystickValue);
		SmartDashboard.putNumber("Right Arm Encoder Position", Robot.ARM_SUBSYSTEM.getRightArmEncoder());
		SmartDashboard.putNumber("Left Arm Encoder Position", Robot.ARM_SUBSYSTEM.getLeftArmEncoder());
		SmartDashboard.putBoolean("Left Limit Closed", Robot.ARM_SUBSYSTEM.getLeftLimit());
		SmartDashboard.putBoolean("Right Limit Closed", Robot.ARM_SUBSYSTEM.getRightLimit());
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
