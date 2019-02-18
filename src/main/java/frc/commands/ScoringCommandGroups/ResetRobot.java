package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.commands.Arm.ResetArm;
import frc.commands.Lift.ResetLift;
import frc.robot.Robot;

public class ResetRobot extends CommandGroup {
	public ResetRobot() {
        requires(Robot.ARM_SUBSYSTEM);
		requires(Robot.LIFT_SUBSYSTEM);

		addSequential(new ResetLift());
		addSequential(new ResetArm());
	}
}