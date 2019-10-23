//TBC

package frc.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class HatchOff extends Command {
  public HatchOff() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.HATCH_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    SmartDashboard.putBoolean("please work", true);
    Robot.HATCH_SUBSYSTEM.HatchSolenoidOff();
    Robot.HATCH_SUBSYSTEM.HatchMotorStop();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    SmartDashboard.putBoolean("please work plz", true);
  }
}
