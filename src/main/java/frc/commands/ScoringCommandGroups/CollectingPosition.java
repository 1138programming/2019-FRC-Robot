package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Collector.CollectWithButtons;
import frc.commands.Lift.MoveLiftToPosition;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;
import frc.subsystems.LiftSubsystem.LiftPosition;

public class CollectingPosition extends CommandGroup {
	int c = 0;
	int d = 1;

	public CollectingPosition() {
		// requires(Robot.ARM_SUBSYSTEM);
		// requires(Robot.COLLECTOR_SUBSYSTEM);
		// requires(Robot.CARRIAGE_SUBSYSTEM);
		// requires(Robot.LIFT_SUBSYSTEM);

		addSequential(new MoveArmToPosition(ArmSubsystem.KArmMiddle));
		addSequential(new MoveLiftToPosition(LiftPosition.FULLDOWN));
		addSequential(new CollectWithButtons(Robot.oi.btnX));
		SmartDashboard.putBoolean("bumpswitch", Robot.CARRIAGE_SUBSYSTEM.bumpSwitchClosed());
		SmartDashboard.putNumber("amount cycled entered", c);
		// addSequential(new CollectingPositionPostMortem());
	}
}