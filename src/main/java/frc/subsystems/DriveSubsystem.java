package frc.subsystems;

import frc.robot.OI;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.ArcadeDriveWithJoy;
import frc.commands.DriveWithJoysticks;

//import static org.junit.Assume.assumeNoException;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.VictorSP;

public class DriveSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  /**
   *  public static final int KDriveLeftFrontTalon = 0;
      public static final int KDriveRightFrontTalon = 1;  
      public static final int KDriveLeftRearTalon = 2; 
      public static final int KDriveRightRearTalon = 3;

      public static final int KShifterSolenoid1 = 12;
      public static final int KShifterSolenoid2 = 13;
   */

  public static final int KDriveLeftFrontTalon = 0;
  public static final int KDriveRightFrontTalon = 1;  
  public static final int KDriveLeftRearTalon = 2; 
  public static final int KDriveRightRearTalon = 3;

  public static final int KShifterSolenoid1 = 0;
  public static final int KShifterSolenoid2 = 1;
  
  private TalonSRX driveRightFront; 
  private TalonSRX driveLeftFront;
  private VictorSPX driveRightRear; 
  private VictorSPX driveLeftRear;
  private DoubleSolenoid shifterSolenoid;

  public DriveSubsystem()
  {
    driveRightFront = new TalonSRX(KDriveRightFrontTalon); 
    driveLeftFront = new TalonSRX(KDriveLeftFrontTalon);
    driveRightRear = new VictorSPX(KDriveRightRearTalon);
    driveLeftRear = new VictorSPX(KDriveLeftRearTalon);

    driveRightFront.setInverted(true);
    driveRightRear.setInverted(true);

    shifterSolenoid = new DoubleSolenoid(KShifterSolenoid1, KShifterSolenoid2);

    driveRightRear.follow(driveRightFront);
    driveLeftRear.follow(driveLeftFront);
  }

  @Override
  public void initDefaultCommand() {
    //default command for a subsystem here.
   setDefaultCommand(new /*DriveWithJoysticks()*/ArcadeDriveWithJoy());
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    SmartDashboard.putNumber("Left Base Input", leftSpeed);
		SmartDashboard.putNumber("Right Base Input", rightSpeed);
    
    
    driveRightFront.set(ControlMode.PercentOutput, rightSpeed);
    driveLeftFront.set(ControlMode.PercentOutput, leftSpeed);
  }

	public void highShiftBase() {
    shifterSolenoid.set(DoubleSolenoid.Value.kReverse);
  }
  
	public void lowShiftBase() {
    shifterSolenoid.set(DoubleSolenoid.Value.kForward);
  }

	public void toggleShift() {
		if (shifterSolenoid.get() == DoubleSolenoid.Value.kForward) {
			highShiftBase();
		}
		else {
			lowShiftBase();
		}
  }
}
