package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveWithJoysticks extends Command {
  public DriveWithJoysticks() {
    requires(Robot.DRIVE_SUBSYSTEM);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.DRIVE_SUBSYSTEM.baseDrive(Robot.oi.getLeftAxis(), Robot.oi.getRightAxis());
    SmartDashboard.putNumber("Left Logitech Axis", Robot.oi.getLeftAxis());
    SmartDashboard.putNumber("Right Logitech Axis", Robot.oi.getRightAxis());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
