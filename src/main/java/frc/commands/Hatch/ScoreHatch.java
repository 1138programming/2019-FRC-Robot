//TBC

package frc.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ScoreHatch extends Command {
  private static final int msDelay = 1000;
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

    switch ((int)(Math.floor(timeSinceInitialized() * (1000 / msDelay)))) {
      case 1:
        if (c == 1) {
          Robot.HATCH_SUBSYSTEM.YMechanismForward();
          c++;
        }
        break;
      case 2:
        if (c == 2) {
          Robot.HATCH_SUBSYSTEM.moveHatchMechanism(false);
          c++;
        }
        break;
      case 3:
        if (c == 3) {
          Robot.HATCH_SUBSYSTEM.YMechanismBackward();
          c++;
        }
        break;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return c >= 4 || timeSinceInitialized() > 5;
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
