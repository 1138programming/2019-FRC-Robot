package frc.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class MoveArmToPosition extends Command {
	
	private ArmSubsystem.ArmPosition armPosition;
	private double error;
	private Command commandToCancel;

	private static final double allowableError = 50;

	public MoveArmToPosition(ArmSubsystem.ArmPosition armPosition) {
		requires(Robot.ARM_SUBSYSTEM);
		this.armPosition = armPosition;
		this.commandToCancel = null;
	}

	public MoveArmToPosition(ArmSubsystem.ArmPosition armPosition, Command commandToCancel) {
		requires(Robot.ARM_SUBSYSTEM);
		this.armPosition = armPosition;
		this.commandToCancel = commandToCancel;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		// if (Robot.useDualArmPID) {
		// 	error = Robot.ARM_SUBSYSTEM.moveArmToPosition(armPosition);
		// } else {
		// 	error = Robot.ARM_SUBSYSTEM.moveArmToPosition(armPosition);
		// }

		error = Robot.ARM_SUBSYSTEM.moveArmToPosition(armPosition);

		SmartDashboard.putNumber("Error Arm", error);
		SmartDashboard.putNumber("Left Arm Encoder Position", Robot.ARM_SUBSYSTEM.getLeftArmEncoder());
		SmartDashboard.putNumber("Right Arm Encoder Position", Robot.ARM_SUBSYSTEM.getRightArmEncoder());
	}

	@Override
	protected boolean isFinished() {
		// if (Robot.ARM_SUBSYSTEM.armInPosition == 0)
			return Math.abs(error) <= allowableError;
		// else if (Robot.ARM_SUBSYSTEM.armInPosition == 1)
		// 	return Robot.ARM_SUBSYSTEM.leftLimitClosed() || Robot.ARM_SUBSYSTEM.rightLimitClosed();
		// else 
		// 	return
	}

	@Override
	protected void end() {
		Robot.ARM_SUBSYSTEM.moveArm(0);
		if (commandToCancel != null) {
			commandToCancel.cancel();
		}
	}

	@Override
	protected void interrupted() {
	}

	public void removeRequirements() {
		this.clearRequirements();
	}
}