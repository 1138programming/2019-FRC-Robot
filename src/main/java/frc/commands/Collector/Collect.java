//TBC

package frc.commands.Collector;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.CollectorSubsystem;
import frc.subsystems.LiftSubsystem;
import frc.subsystems.CarriageSubsystem;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Collect extends Command {
  JoystickButton button;
  boolean reversed;

  public Collect(JoystickButton button) {
    this.button = button;
    this.reversed = false;

    // Use requires() here to declare subsystem dependencies
    requires(Robot.COLLECTOR_SUBSYSTEM);
    requires(Robot.CARRIAGE_SUBSYSTEM);
  }

  public Collect(JoystickButton button, boolean reversed) {
    this.button = button;
    this.reversed = reversed;

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
    if(reversed)
    {
      Robot.COLLECTOR_SUBSYSTEM.moveCollector(-Robot.COLLECTOR_SUBSYSTEM.KCollecterSpeed);
      Robot.CARRIAGE_SUBSYSTEM.moveCarriage(-Robot.CARRIAGE_SUBSYSTEM.KCarriageSpeed);
      Robot.LIFT_SUBSYSTEM.moveLift(LiftSubsystem.KMotorOffset);
    }
    else
    {
      Robot.COLLECTOR_SUBSYSTEM.moveCollector(Robot.COLLECTOR_SUBSYSTEM.KCollecterSpeed);
      Robot.CARRIAGE_SUBSYSTEM.moveCarriage(Robot.CARRIAGE_SUBSYSTEM.KCarriageSpeed);
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
