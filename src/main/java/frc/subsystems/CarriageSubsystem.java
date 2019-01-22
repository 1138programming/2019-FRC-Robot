package frc.subsystems;

// import frc.robot.OI;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.CarriageStop;
import frc.commands.DriveWithJoysticks;

//import static org.junit.Assume.assumeNoException;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class CarriageSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  /**
   *public static final int KCarriage = 9;
    public static final double KCarriageSpeed = 1.0;
   */
  public static final int KCarriage = 9;
  public static final double KCarriageSpeed = 1.0;

  private VictorSPX carriage; 

  public CarriageSubsystem() {
    carriage = new VictorSPX(KCarriage);
  }
  @Override
  public void initDefaultCommand() {
    //default command for a subsystem here.
   setDefaultCommand(new CarriageStop());
  }
  
  public void moveCarriage(double speed) {
    carriage.set(ControlMode.PercentOutput, speed);
  }
}