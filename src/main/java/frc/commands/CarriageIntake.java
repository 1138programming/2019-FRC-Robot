/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.CarriageSubsystem;

public class CarriageIntake extends Command {
  public CarriageIntake() {
    requires(Robot.CARRIAGE_SUBSYSTEM);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.CARRIAGE_SUBSYSTEM.moveCarriage(-CarriageSubsystem.KCarriageSpeed); 
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
