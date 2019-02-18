package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Robot;

import frc.subsystems.ArmSubsystem;
import frc.subsystems.CollectorSubsystem;
import frc.subsystems.CarriageSubsystem;
import frc.subsystems.LiftSubsystem;
import frc.subsystems.ArmSubsystem.ArmPosition;
import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Lift.MoveLiftToPosition;

public class ScoreCargoInShip extends CommandGroup {
	public ScoreCargoInShip() {
		requires(Robot.ARM_SUBSYSTEM);
		requires(Robot.COLLECTOR_SUBSYSTEM);
		requires(Robot.CARRIAGE_SUBSYSTEM);
		requires(Robot.LIFT_SUBSYSTEM);

		addSequential(new MoveArmToPosition(ArmPosition.HIGH));
		addSequential(new MoveLiftToPosition(Robot.LIFT_SUBSYSTEM.KLiftShip));
	}
}