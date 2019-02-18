/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ResetArm extends Command {

  private static final double KResetArmSpeed = .25;

  public ResetArm()
  {
    //Default implementation
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!Robot.ARM_SUBSYSTEM.leftLimitClosed() && !Robot.ARM_SUBSYSTEM.rightLimitClosed())
      Robot.ARM_SUBSYSTEM.moveArm(-KResetArmSpeed);
    else
      Robot.ARM_SUBSYSTEM.moveArm(0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Robot.ARM_SUBSYSTEM.leftLimitClosed() || Robot.ARM_SUBSYSTEM.rightLimitClosed());
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
