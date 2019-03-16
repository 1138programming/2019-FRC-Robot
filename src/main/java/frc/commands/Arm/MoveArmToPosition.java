package frc.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class MoveArmToPosition extends Command {
	
	private int target;
	private double error;
	private Command commandToCancel;

	private static final double allowableError = 50;

	public MoveArmToPosition(int target) {
		requires(Robot.ARM_SUBSYSTEM);
		this.target = target;
		this.commandToCancel = null;
	}

	public MoveArmToPosition(int target, Command commandToCancel) {
		requires(Robot.ARM_SUBSYSTEM);
		this.target = target;
		this.commandToCancel = commandToCancel;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		error = Robot.ARM_SUBSYSTEM.moveArmTo(target);

		SmartDashboard.putNumber("Error Arm", error);
		SmartDashboard.putNumber("Left Arm Encoder Position", Robot.ARM_SUBSYSTEM.getLeftArmEncoder());
		SmartDashboard.putNumber("Right Arm Encoder Position", Robot.ARM_SUBSYSTEM.getRightArmEncoder());
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(error) <= allowableError;
	}

	@Override
	protected void end() {
		Robot.ARM_SUBSYSTEM.moveArm(0, 0);
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