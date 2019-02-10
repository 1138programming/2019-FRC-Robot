package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.commands.Carriage.CarriageReset;

public class CarriageSubsystem extends Subsystem {
  /**
   * public static final int KCarriage = 9;
   * public static final double KCarriageSpeed = 1.0;
   * 
   * private VictorSPX carriage;
   */
  public static final int KCarriage = 8;
  public static final double KCarriageSpeed = 1.0;

  private VictorSPX carriage; 

  public CarriageSubsystem() {
    carriage = new VictorSPX(KCarriage);
  }
  @Override
  public void initDefaultCommand() {
   setDefaultCommand(new CarriageReset());
  }
  
  public void moveCarriage(double speed) {
    carriage.set(ControlMode.PercentOutput, speed);
  }
}