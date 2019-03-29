//TBC

package frc.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ScoreHatch extends Command {
  private static final double[] KDelays = {1, 1.5};
  private double timeout = 0;
  private int c = 0;

  public ScoreHatch() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.HATCH_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    c = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.HATCH_SUBSYSTEM.GrabberReverse();
    Robot.HATCH_SUBSYSTEM.EjectorForward();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
