package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ArcadeDriveWithJoy extends Command {
  public ArcadeDriveWithJoy() {
    requires(Robot.DRIVE_SUBSYSTEM);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.DRIVE_SUBSYSTEM.baseDrive(-(Robot.oi.getLeftAxis() - Robot.oi.getArcadeRightAxis()), -(Robot.oi.getLeftAxis() + Robot.oi.getArcadeRightAxis()));
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
