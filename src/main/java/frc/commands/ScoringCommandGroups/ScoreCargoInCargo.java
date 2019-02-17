package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Robot;

import frc.subsystems.ArmSubsystem;
import frc.subsystems.CollectorSubsystem;
import frc.subsystems.CarriageSubsystem;
import frc.subsystems.LiftSubsystem;

import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Lift.MoveLiftToPosition;
import frc.commands.Lift.HoldLiftPosition;

public class ScoreCargoInCargo extends CommandGroup {
	public ScoreCargoInCargo() {
        requires(Robot.ARM_SUBSYSTEM);
        requires(Robot.COLLECTOR_SUBSYSTEM);
        requires(Robot.CARRIAGE_SUBSYSTEM);
		requires(Robot.LIFT_SUBSYSTEM);

		HoldLiftPosition holdLiftPosition = new HoldLiftPosition();

		addSequential(new MoveArmToPosition(Robot.ARM_SUBSYSTEM.KArmMiddle));
		addSequential(new MoveLiftToPosition(Robot.LIFT_SUBSYSTEM.KLiftCargo));
		addSequential(new MoveArmToPosition(Robot.ARM_SUBSYSTEM.KArmFullUp, holdLiftPosition));
		addParallel(holdLiftPosition);
	}
}