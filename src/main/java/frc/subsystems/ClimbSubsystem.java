package frc.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.commands.ClimbReset;

public class ClimbSubsystem extends Subsystem {
  /**
   * public static final int KClimb = 6;
   * public static final double KClimbSpeed = 1.0;
   * 
   * private TalonSRX climb;
   */
  public static final int KClimb = 6;
  public static final double KClimbSpeed = 1.0;

  private TalonSRX climb; 

  public ClimbSubsystem() {
    climb = new TalonSRX(KClimb);
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ClimbReset());
  }
  
  public void moveClimb(double speed) {
    climb.set(ControlMode.PercentOutput, speed);
  }
}