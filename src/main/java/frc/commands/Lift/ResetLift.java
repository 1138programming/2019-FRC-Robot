/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ResetLift extends Command {

  private static final double KResetLiftSpeed = .25;

  public ResetLift() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!Robot.LIFT_SUBSYSTEM.getBottomLimit())
    Robot.LIFT_SUBSYSTEM.moveLift(-KResetLiftSpeed);
  else
    Robot.LIFT_SUBSYSTEM.moveLift(0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.LIFT_SUBSYSTEM.getBottomLimit();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
