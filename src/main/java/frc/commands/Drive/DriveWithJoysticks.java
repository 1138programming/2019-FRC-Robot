package frc.commands.Drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.DriveSubsystem;
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
    Robot.DRIVE_SUBSYSTEM.voltageSpikeRegulation();
    if(DriveSubsystem.voltageSpikeOccured == false)
    {
      double leftSpeed = Robot.oi.getLeftAxis();
      double rightSpeed = Robot.oi.getRightAxis();
      Robot.DRIVE_SUBSYSTEM.baseDrive(leftSpeed, rightSpeed);
      SmartDashboard.putNumber("Left Logitech Axis", leftSpeed);
      SmartDashboard.putNumber("Right Logitech Axis", rightSpeed);
    }
    else if(DriveSubsystem.voltageSpikeOccured == true)
    {
      double leftSpeed = (Robot.oi.getLeftAxis() * 3)/4;
      double rightSpeed = (Robot.oi.getRightAxis() * 3)/4;
      Robot.DRIVE_SUBSYSTEM.baseDrive(leftSpeed, rightSpeed);
      SmartDashboard.putNumber("Left Logitech Axis", leftSpeed);
      SmartDashboard.putNumber("Right Logitech Axis", rightSpeed);
    }
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
