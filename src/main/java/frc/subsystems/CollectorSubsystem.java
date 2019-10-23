package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.Collector.CollectorReset;

public class CollectorSubsystem extends Subsystem {

  public static final double KCollecterSpeed = 1.0; 

  private final VictorSPX CollectorVictor;
  public static final int KCollectorVictor = 9; 


  public CollectorSubsystem() {
    CollectorVictor = new VictorSPX(KCollectorVictor);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new CollectorReset());
  }

  public void moveCollector(double speed) {
    CollectorVictor.set(ControlMode.PercentOutput, speed);
  }
}
