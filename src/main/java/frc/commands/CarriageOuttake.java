package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.CarriageSubsystem;

public class CarriageOuttake extends Command {
  public CarriageOuttake() {
    requires(Robot.CARRIAGE_SUBSYSTEM);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.CARRIAGE_SUBSYSTEM.moveCarriage(CarriageSubsystem.KCarriageSpeed); 
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
