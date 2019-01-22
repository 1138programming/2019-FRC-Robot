package frc.subsystems;

// import frc.robot.OI;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;

//import static org.junit.Assume.assumeNoException;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.commands.ArmWithJoysticks;



public class ArmSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  /**
   *public static final int KArmMaster = 4; 
    public static final int KArmSlave = 5;  
    public static final double KArmSpeed = 1.0; 
   */
 
  public static final int KArmMaster = 4; 
  public static final int KArmSlave = 5;  
  public static final double KArmSpeed = 1.0; 

  private TalonSRX armMaster;
  private VictorSPX armSlave;

  public ArmSubsystem()
  {
    armMaster = new TalonSRX(KArmMaster); 
    armSlave = new VictorSPX(KArmSlave);
    
    armSlave.follow(armMaster);
  }

  @Override
  public void initDefaultCommand() {
    //default command for a subsystem here.
    setDefaultCommand(new ArmWithJoysticks());
  }

  
  public void moveArm(double speed) {
    armMaster.set(ControlMode.PercentOutput, speed);
    //if (speed != 0) System.out.println(1 / 0);
  }
}
