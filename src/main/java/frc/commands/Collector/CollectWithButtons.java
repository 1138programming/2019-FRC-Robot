//TBC

package frc.commands.Collector;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.CollectorSubsystem;
import frc.subsystems.LiftSubsystem;
import frc.subsystems.CarriageSubsystem;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class CollectWithButtons extends Command {
  JoystickButton button;

  public CollectWithButtons(JoystickButton button) {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.COLLECTOR_SUBSYSTEM);
    requires(Robot.CARRIAGE_SUBSYSTEM);
    this.button = button;
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
      Robot.CARRIAGE_SUBSYSTEM.moveCarriage(-Robot.CARRIAGE_SUBSYSTEM.KCarriageSpeedIn);
      Robot.LIFT_SUBSYSTEM.moveLift(LiftSubsystem.KMotorOffset);
    }
    else if(button == Robot.oi.btnY)
    {
      Robot.COLLECTOR_SUBSYSTEM.moveCollector(Robot.COLLECTOR_SUBSYSTEM.KCollecterSpeed);
      Robot.CARRIAGE_SUBSYSTEM.moveCarriage(Robot.CARRIAGE_SUBSYSTEM.KCarriageSpeedOut);
      Robot.LIFT_SUBSYSTEM.moveLift(LiftSubsystem.KMotorOffset);
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
