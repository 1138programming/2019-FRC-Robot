package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Lift.ResetLift;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;

public class ResetRobot extends CommandGroup {
	public ResetRobot() {
        requires(Robot.ARM_SUBSYSTEM);
		requires(Robot.LIFT_SUBSYSTEM);

		addSequential(new ResetLift());
		addSequential(new MoveArmToPosition(ArmSubsystem.KArmFullDown));
	}
}