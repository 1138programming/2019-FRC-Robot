package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;

import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Lift.MoveLiftToPosition;
import frc.commands.Collector.Collect;

public class ScoreCargoInShip extends CommandGroup {
	public ScoreCargoInShip() {
		requires(Robot.ARM_SUBSYSTEM);
		requires(Robot.COLLECTOR_SUBSYSTEM);
		requires(Robot.CARRIAGE_SUBSYSTEM);
		requires(Robot.LIFT_SUBSYSTEM);

		addSequential(new MoveArmToPosition(Robot.ARM_SUBSYSTEM.KArmHigh));
		addSequential(new MoveLiftToPosition(Robot.LIFT_SUBSYSTEM.KLiftShip));
		addSequential(new Collect(Robot.oi.btn6Stick));
	}
}