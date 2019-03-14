/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.Arm.MoveArmToPosition;
import frc.commands.Lift.MoveLiftToPosition;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem.ArmPosition;
import frc.subsystems.LiftSubsystem.LiftPosition;

public class CollectingPositionPostMortem extends Command {
  
  MoveArmToPosition moveArmToMiddle;
  MoveLiftToPosition moveLiftToCargoPos;
  MoveArmToPosition moveArmFullUp;

  int stage = 0;
  boolean canRun = false;

  public CollectingPositionPostMortem() {
    requires(Robot.ARM_SUBSYSTEM);
    requires(Robot.LIFT_SUBSYSTEM);

    moveArmToMiddle = new MoveArmToPosition(ArmPosition.MIDDLE);
    moveLiftToCargoPos = new MoveLiftToPosition(LiftPosition.CARGO);
    moveArmFullUp = new MoveArmToPosition(ArmPosition.FULLUP);

    moveArmToMiddle.removeRequirements();
    moveLiftToCargoPos.removeRequirements();
    moveArmFullUp.removeRequirements();
  }

  @Override
  protected void initialize() {
    stage = 0;
    canRun = Robot.CARRIAGE_SUBSYSTEM.BumpSwitch.get();
    SmartDashboard.putNumber("postmortem stage", stage);
  }

  @Override
  protected void execute() {
    if (!canRun) 
      return;
    
    switch (stage) {
      case 0:
        moveArmToMiddle.start();
        stage++;
        break;
      case 1:
        if (!moveArmToMiddle.isRunning()) {
          moveLiftToCargoPos.start();
          stage++;
        }
        break;
      case 2:
        if (!moveLiftToCargoPos.isRunning()) {
          moveArmFullUp.start();
          stage++;
        }
        break;
      case 3:
        if (!moveArmFullUp.isRunning()) {
          stage++; // Once stage is 4, and not here, then we're done
        }
        break;
    }
  }

  @Override
  protected boolean isFinished() {
    return !canRun || (stage >= 4); // If we can't run, we're finished
  }

  @Override
  protected void end() {

  }

  @Override
  protected void interrupted() {

  }
}
