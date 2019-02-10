package frc.commands.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.subsystems.DriveSubsystem;

public class ArcadeDriveWithJoy extends Command {
  public ArcadeDriveWithJoy() {
    requires(Robot.DRIVE_SUBSYSTEM);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.DRIVE_SUBSYSTEM.baseDrive((Robot.oi.getLeftAxis() - Robot.oi.getArcadeRightAxis()), (Robot.oi.getLeftAxis() + Robot.oi.getArcadeRightAxis()));
    SmartDashboard.putNumber("Left Logitech Axis", Robot.oi.getLeftAxis());
    SmartDashboard.putNumber("Right Logitech Axis", Robot.oi.getArcadeRightAxis());
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
