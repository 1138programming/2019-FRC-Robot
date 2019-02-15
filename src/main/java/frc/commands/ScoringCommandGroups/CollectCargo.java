package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Robot;

import frc.subsystems.ArmSubsystem;
import frc.subsystems.CollectorSubsystem;
import frc.subsystems.CarriageSubsystem;
import frc.subsystems.LiftSubsystem;

import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Lift.MoveLiftToPosition;
import frc.commands.Collector.Collect;

public class CollectCargo extends CommandGroup {
	public CollectCargo() {
		requires(Robot.ARM_SUBSYSTEM);
		requires(Robot.COLLECTOR_SUBSYSTEM);
		requires(Robot.CARRIAGE_SUBSYSTEM);
		requires(Robot.LIFT_SUBSYSTEM);
		
		// addSequential(new MoveArmToPosition(Robot.ARM_SUBSYSTEM.KArmMiddle, false));
		// addSequential(new MoveLiftToPosition(Robot.LIFT_SUBSYSTEM.KLiftFullDown, false));
		// addSequential(new Collect(Robot.oi.btn5Stick));
		// addSequential(new MoveLiftToPosition(Robot.LIFT_SUBSYSTEM.KLiftCargo, false));
		// addSequential(new MoveArmToPosition(Robot.ARM_SUBSYSTEM.KArmHigh, false));
	}
}