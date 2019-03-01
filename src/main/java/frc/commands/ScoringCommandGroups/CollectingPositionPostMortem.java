/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Lift.MoveLiftToPosition;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem.ArmPosition;
import frc.subsystems.LiftSubsystem.LiftPosition;

public class CollectingPositionPostMortem extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CollectingPositionPostMortem() {
    if(Robot.CARRIAGE_SUBSYSTEM.BumpSwitch.get())
    {
      addSequential(new MoveArmToPosition(ArmPosition.MIDDLE));
      addSequential(new MoveLiftToPosition(LiftPosition.CARGO));
      addSequential(new MoveArmToPosition(ArmPosition.FULLUP));
      SmartDashboard.putNumber("amount cycled entered", 1);
    }
  }
}
