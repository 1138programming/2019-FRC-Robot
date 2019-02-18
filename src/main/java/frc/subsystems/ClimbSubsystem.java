package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.Climb.ClimbReset;

public class ClimbSubsystem extends Subsystem {
  /**
   * public static final int KClimb = 6; public static final double KClimbSpeed =
   * 1.0;
   * 
   * private TalonSRX climb;
   */

   public static final double KClimbSpeed = 1.0;

  private final TalonSRX ClimbTalon; 
  public static final int KClimbTalon = 6;

  public ClimbSubsystem() {
    ClimbTalon = new TalonSRX(KClimbTalon);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ClimbReset());
  }
  
  public void moveClimb(double speed) {
    ClimbTalon.set(ControlMode.PercentOutput, speed);
  }
}