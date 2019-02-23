package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.Carriage.CarriageReset;

public class CarriageSubsystem extends Subsystem {
  /**
   * public static final int KCarriage = 9; public static final double
   * KCarriageSpeedOut = 1.0;
   * 
   * private VictorSPX carriage;
   */

  public static final double KCarriageSpeedOut = 1.0;
  public static final double KCarriageSpeedIn = 0.5;

  //Victor Config
  private final VictorSPX Carriage; 
  private static final int KCarriage = 8;

  //Bump Switch config
  public final DigitalInput BumpSwitch;
  private static final int KBumpSwitchChannel = 5;

  public CarriageSubsystem() {
    Carriage = new VictorSPX(KCarriage);
    BumpSwitch = new DigitalInput(KBumpSwitchChannel);
  }
  
  @Override
  public void initDefaultCommand() {
   setDefaultCommand(new CarriageReset());
  }
  
  public void moveCarriage(double speed) {
    Carriage.set(ControlMode.PercentOutput, speed);
  }

  public boolean bumpSwitchClosed() {
    return !BumpSwitch.get();
  }
}