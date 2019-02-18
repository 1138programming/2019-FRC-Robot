package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Robot;

import frc.subsystems.ArmSubsystem;
import frc.subsystems.LiftSubsystem;

import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Lift.MoveLiftToPosition;
import frc.commands.Lift.ResetLift;
import frc.commands.Arm.ResetArm;

public class SetCargoToDrive extends CommandGroup {
	public SetCargoToDrive() {
        requires(Robot.ARM_SUBSYSTEM);
		requires(Robot.LIFT_SUBSYSTEM);
		
		addSequential(new ResetLift());
		addSequential(new ResetArm());
	}
}