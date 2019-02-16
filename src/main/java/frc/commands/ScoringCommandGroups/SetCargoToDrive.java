package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Robot;

import frc.subsystems.ArmSubsystem;
import frc.subsystems.LiftSubsystem;

import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Lift.MoveLiftToPosition;

public class SetCargoToDrive extends CommandGroup {
	public SetCargoToDrive() {
        requires(Robot.ARM_SUBSYSTEM);
		requires(Robot.LIFT_SUBSYSTEM);
		
		addSequential(new MoveArmToPosition(Robot.ARM_SUBSYSTEM.KArmMiddle));
        addSequential(new MoveLiftToPosition(Robot.LIFT_SUBSYSTEM.KLiftShip, false));
	}
}