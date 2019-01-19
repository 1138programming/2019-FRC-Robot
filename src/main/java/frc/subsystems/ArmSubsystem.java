package frc.subsystems;

// import frc.robot.OI;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.commands.DriveWithJoysticks;

//import static org.junit.Assume.assumeNoException;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;


public class ArmSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
 
  public static final int KArmTalon = 4; 
  public static final int KArmVictor = 5; 
  public static final int KCollectorVictor = 8; 
  public static final double KArmSpeed = 1.0; 

  private TalonSRX armLeft;
  private VictorSPX armRight;
  private VictorSPX collectorVictor;

  public ArmSubsystem()
  {
    armLeft = new TalonSRX(KArmTalon); 
    armRight = new VictorSPX(KArmVictor); 
    collectorVictor = new VictorSPX(KCollectorVictor);
    
    armLeft.follow(armRight);
  }

  @Override
  public void initDefaultCommand() {
    //default command for a subsystem here.
   //setDefaultCommand(new ArmStop());
  }

  
  public void Move(double speed) {
    armLeft.set(ControlMode.PercentOutput, speed);
    collectorVictor.set(ControlMode.PercentOutput, speed);
  }

  public void liftClearSticky() {
    armLeft.clearStickyFaults(1000);
  }
}
