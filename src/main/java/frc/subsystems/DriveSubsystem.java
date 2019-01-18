package frc.subsystems;

import frc.robot.OI;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.commands.DriveWithJoysticks;

//import static org.junit.Assume.assumeNoException;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.VictorSP;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  // public static final int KDriveRightTopTalon = 4; 
  // public static final int KDriveLeftTopTalon = 9; 
  public static final int KDriveRightFrontTalon = 0; 
  public static final int KDriveLeftFrontTalon = 1; 
  public static final int KDriveRightRearTalon = 6; 
  public static final int KDriveLeftRearTalon = 5; 

  public static final int KShifterSolenoid1 = 1;
  public static final int KShifterSolenoid2 = 2; 

  // private TalonSRX driveRightTop;
  // private TalonSRX driveLeftTop;  
  private TalonSRX driveRightFront; 
  private TalonSRX driveLeftFront;
  private VictorSPX driveRightRear; 
  private VictorSPX driveLeftRear;

  private DoubleSolenoid shifterSolenoid;

  public DriveSubsystem()
  {
    // driveRightTop = new TalonSRX(KDriveRightTopTalon);
    // driveLeftTop = new TalonSRX(KDriveLeftTopTalon);
    driveRightFront = new TalonSRX(KDriveRightFrontTalon); 
    driveLeftFront = new TalonSRX(KDriveLeftFrontTalon);
    driveRightRear = new VictorSPX(KDriveRightRearTalon);
    driveLeftRear = new VictorSPX(KDriveLeftRearTalon);

    driveRightFront.setInverted(true);
    // driveRightTop.setInverted(true);
    driveRightRear.setInverted(true);

    shifterSolenoid = new DoubleSolenoid(KShifterSolenoid1, KShifterSolenoid2);

    // driveRightTop.set(ControlMode.Follower, driveRightFront.getDeviceID());
    driveRightRear.follow(driveRightFront);
    // driveLeftTop.set(ControlMode.Follower, driveLeftFront.getDeviceID());
    driveLeftRear.follow(driveLeftFront);

  }

  @Override
  public void initDefaultCommand() {
    //default command for a subsystem here.
   setDefaultCommand(new DriveWithJoysticks());
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
  public void driveClearSticky() {
    driveRightFront.clearStickyFaults(1000);
    driveLeftFront.clearStickyFaults(1000);
  }
}
