package frc.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.commands.ClimbReset;

public class ClimbSubsystem extends Subsystem {
  /**
   * public static final int KClimb = 6;
   * public static final double KClimbSpeed = 1.0;
   * 
   * private TalonSRX climb;
   */
  public static final int KClimbMaster = 6;
  // public static final int KClimbSlave = 7;
  public static final double KClimbSpeed = 1.0;

  private TalonSRX climbMaster; 
  // private VictorSPX climbSlave;

  public ClimbSubsystem() {
    climbMaster = new TalonSRX(KClimbMaster);
    // climbSlave = new VictorSPX(KClimbSlave);

    // climbSlave.follow(climbMaster);
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ClimbReset());
  }
  
  public void moveClimb(double speed) {
    climbMaster.set(ControlMode.PercentOutput, speed);
  }
}