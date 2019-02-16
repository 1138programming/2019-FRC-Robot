//TBC

package frc.commands.Collector;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.CollectorSubsystem;
import frc.subsystems.CarriageSubsystem;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Collect extends Command {
  JoystickButton button;
  public Collect(JoystickButton button) {
    this.button = button;

    // Use requires() here to declare subsystem dependencies
    requires(Robot.COLLECTOR_SUBSYSTEM);
    requires(Robot.CARRIAGE_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(button == Robot.oi.btnX)
    {
      Robot.COLLECTOR_SUBSYSTEM.moveCollector(-Robot.COLLECTOR_SUBSYSTEM.KCollecterSpeed);
      Robot.CARRIAGE_SUBSYSTEM.moveCarriage(-Robot.CARRIAGE_SUBSYSTEM.KCarriageSpeed);
    }
    else if(button == Robot.oi.btnY)
    {
      Robot.COLLECTOR_SUBSYSTEM.moveCollector(Robot.COLLECTOR_SUBSYSTEM.KCollecterSpeed);
      Robot.CARRIAGE_SUBSYSTEM.moveCarriage(Robot.CARRIAGE_SUBSYSTEM.KCarriageSpeed);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !button.get();
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
