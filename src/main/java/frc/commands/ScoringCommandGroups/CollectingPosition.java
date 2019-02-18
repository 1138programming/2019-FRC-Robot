package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Robot;

import frc.subsystems.ArmSubsystem;
import frc.subsystems.CollectorSubsystem;
import frc.subsystems.CarriageSubsystem;
import frc.subsystems.LiftSubsystem;
import frc.subsystems.ArmSubsystem.ArmPosition;
import frc.subsystems.LiftSubsystem.LiftPosition;
import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Lift.MoveLiftToPosition;
import frc.commands.Collector.CollectUntilBumpSwitch;
import frc.commands.Collector.CollectWithButtons;

public class CollectingPosition extends CommandGroup {
	public CollectingPosition() {
		requires(Robot.ARM_SUBSYSTEM);
		requires(Robot.COLLECTOR_SUBSYSTEM);
		requires(Robot.CARRIAGE_SUBSYSTEM);
		requires(Robot.LIFT_SUBSYSTEM);
		
		addSequential(new MoveArmToPosition(ArmPosition.MIDDLE));
		addSequential(new MoveLiftToPosition(LiftPosition.FULLDOWN));
		addSequential(new CollectWithButtons(Robot.oi.btnX));
	}
}