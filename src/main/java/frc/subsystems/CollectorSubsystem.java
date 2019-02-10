package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.commands.Collector.CollectorReset;


public class CollectorSubsystem extends Subsystem {
  /**
   * public static final int KCollectorVictor = 8; 
   * public static final double KCollecterSpeed = 1.0; 
   * private VictorSPX collectorVictor;
   */
  public static final int KCollectorVictor = 9; 
  public static final double KCollecterSpeed = 1.0; 

  private VictorSPX collectorVictor;

  public CollectorSubsystem() {
    collectorVictor = new VictorSPX(KCollectorVictor);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new CollectorReset());
  }

  
  public void moveCollector(double speed) {
    collectorVictor.set(ControlMode.PercentOutput, speed);
  }
}
