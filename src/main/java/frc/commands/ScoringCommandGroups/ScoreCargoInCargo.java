package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Lift.MoveLiftToPosition;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;
import frc.subsystems.LiftSubsystem;

public class ScoreCargoInCargo extends CommandGroup {
	public ScoreCargoInCargo() {
        requires(Robot.ARM_SUBSYSTEM);
        requires(Robot.COLLECTOR_SUBSYSTEM);
        requires(Robot.CARRIAGE_SUBSYSTEM);
		requires(Robot.LIFT_SUBSYSTEM);

		addSequential(new MoveArmToPosition(ArmSubsystem.KArmMiddle));
		addSequential(new MoveLiftToPosition(LiftSubsystem.KLiftCargo));
		addSequential(new MoveArmToPosition(ArmSubsystem.KArmFullUp));
	}
}