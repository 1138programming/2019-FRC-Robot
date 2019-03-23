package frc.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class GetArmPast extends Command {
	
	private int target;
	boolean forward;

	public GetArmPast(int target, boolean forward) {
		requires(Robot.ARM_SUBSYSTEM);
		this.target = target;
		this.forward = forward;
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Arm past: ", "no :(");
	}

	@Override
	protected void execute() {
		if (forward)
			Robot.ARM_SUBSYSTEM.moveArm(1, 1);
		else
			Robot.ARM_SUBSYSTEM.moveArm(-1, -1);
	}

	@Override
	protected boolean isFinished() {
		int leftPos = Robot.ARM_SUBSYSTEM.getLeftArmEncoder();
		int rightPos = Robot.ARM_SUBSYSTEM.getRightArmEncoder();
		if (forward) {
			return leftPos > target && rightPos > target;
		} else {
			return leftPos < target && rightPos < target;
		}
			
	}

	@Override
	protected void end() {
		SmartDashboard.putString("Arm past: ", "yes!");
	}

	@Override
	protected void interrupted() {
	}

	public void removeRequirements() {
		this.clearRequirements();
	}
}