//TBC

package frc.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.HatchSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EjectHatch extends Command {
  // private static final int msDelay = 1000;
  // private int c = 0;
  public EjectHatch() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.HATCH_SUBSYSTEM);
  }

  @Override 
  protected void initialize() {
  }

  @Override
  protected void execute() {
      Robot.HATCH_SUBSYSTEM.moveHatchMechanismToEject();
  }

  @Override
  protected boolean isFinished() {
    return true; 
  }

  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
