package frc.subsystems;

// import frc.robot.OI;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;

//import static org.junit.Assume.assumeNoException;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.commands.CollectorStop;


public class CollectorSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  /**
   *  public static final int KCollectorVictor = 8; 
      public static final double KCollecterSpeed = 1.0;
   */
 
  public static final int KCollectorVictor = 8; 
  public static final double KCollecterSpeed = 1.0; 

  private VictorSPX collectorVictor;

  public CollectorSubsystem()
  {
    collectorVictor = new VictorSPX(KCollectorVictor);
  }

  @Override
  public void initDefaultCommand() {
    //default command for a subsystem here.
    setDefaultCommand(new CollectorStop());
  }

  
  public void moveCollector(double speed) {
    collectorVictor.set(ControlMode.PercentOutput, speed);
  }
}
