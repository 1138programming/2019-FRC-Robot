package frc.subsystems;

import edu.wpi.first.wpilibj.Talon;

// import frc.robot.OI;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;


//import static org.junit.Assume.assumeNoException;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;;

public class ClimbSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  /**
   *  public static final int KClimb = 6;
      public static final double KClimbSpeed = 1.0;
   */
  public static final int KClimb = 6;
  public static final double KClimbSpeed = 1.0;

  private TalonSRX climb; 

  public ClimbSubsystem() {
    climb = new TalonSRX(KClimb);
  }
  @Override
  public void initDefaultCommand() {
    //default command for a subsystem here.
   //setDefaultCommand(new ());
  }
  
  public void moveClimb(double speed) {
    climb.set(ControlMode.PercentOutput, speed);
  }
}