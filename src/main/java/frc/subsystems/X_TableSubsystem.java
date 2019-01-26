package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.commands.X_TableCenter;

public class X_TableSubsystem extends Subsystem {
  /**
   * public static final int KX_Table = 10;
   * public static final double KX_TableSpeed = .8;
   * private Spark X_Table; 
   */
  public static final int KX_Table = 10;
  public static final double KX_TableSpeed = .8;

  private TalonSRX X_Table; 

  public X_TableSubsystem() {
    X_Table = new TalonSRX(KX_Table);
  } 

  public void moveX_Table(double speed) {
    X_Table.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new X_TableCenter());
  }
}