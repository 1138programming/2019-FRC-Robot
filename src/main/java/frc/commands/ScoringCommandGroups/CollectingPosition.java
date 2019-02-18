package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Collector.CollectWithButtons;
import frc.commands.Lift.MoveLiftToPosition;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem.ArmPosition;
import frc.subsystems.LiftSubsystem.LiftPosition;

public class CollectingPosition extends CommandGroup {
	public CollectingPosition() {
		requires(Robot.ARM_SUBSYSTEM);
		requires(Robot.COLLECTOR_SUBSYSTEM);
		requires(Robot.CARRIAGE_SUBSYSTEM);
		requires(Robot.LIFT_SUBSYSTEM);

		addSequential(new MoveArmToPosition(ArmPosition.MIDDLE));
		addSequential(new MoveLiftToPosition(LiftPosition.FULLDOWN));
		addSequential(new CollectWithButtons(Robot.oi.btnX));
		if(Robot.CARRIAGE_SUBSYSTEM.bumpSwitchClosed()) {
			addSequential(new MoveArmToPosition(ArmPosition.MIDDLE));
			addSequential(new MoveLiftToPosition(LiftPosition.CARGO));
			addSequential(new MoveArmToPosition(ArmPosition.FULLUP));
		}
	}
}