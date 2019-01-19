package frc.subsystems;

// import frc.robot.OI;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.commands.DriveWithJoysticks;

//import static org.junit.Assume.assumeNoException;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class LiftSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
 
  public static final int KLiftTalon = 0; 

  private TalonSRX liftMotor;

  public LiftSubsystem()
  {
    liftMotor = new TalonSRX(KLiftTalon); 
  }

  @Override
  public void initDefaultCommand() {
    //default command for a subsystem here.
   setDefaultCommand(new DriveWithJoysticks());
  }
  
  public void move(double speed) {
    liftMotor.set(ControlMode.PercentOutput, speed);
  }

  public void liftClearSticky() {
    liftMotor.clearStickyFaults(1000);
  }
}
