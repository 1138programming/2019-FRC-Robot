package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.Drive.ArcadeDriveWithJoy;
import frc.commands.Drive.DriveWithJoysticks;
import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class DriveSubsystem extends Subsystem {
  /**
   * public static final int KDriveLeftFrontTalon = 0;
   * public static final int KDriveRightFrontTalon = 1;  
   * public static final int KDriveLeftRearTalon = 2; 
   * public static final int KDriveRightRearTalon = 3;
   * 
   * public static final int KShifterSolenoid1 = 1;
   * public static final int KShifterSolenoid2 = 2;
   * 
   * private TalonSRX driveRightFront;
   * private TalonSRX driveLeftFront;
   * private VictorSPX driveRightRear; 
   * private VictorSPX driveLeftRear;
   * private DoubleSolenoid shifterSolenoid;
   */
  public static final int KDriveLeftFrontTalon = 0;
  public static final int KDriveRightFrontTalon = 1;  
  public static final int KDriveLeftRearTalon = 2; 
  public static final int KDriveRightRearTalon = 3;
  public static final int KAccelerometer = 0;

  public static final int KShifterSolenoid1 = 1;
  public static final int KShifterSolenoid2 = 2;

  public AnalogAccelerometer Accel; 
  
  private Boolean isReversed = false;

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

    Accel = new AnalogAccelerometer(KAccelerometer);

    driveRightFront.setInverted(true);
    driveRightRear.setInverted(true);

    shifterSolenoid = new DoubleSolenoid(KShifterSolenoid1, KShifterSolenoid2);

    driveRightRear.follow(driveRightFront);
    driveLeftRear.follow(driveLeftFront);
  }

  @Override
  public void initDefaultCommand() {
   setDefaultCommand(new /*DriveWithJoysticks()*/ArcadeDriveWithJoy()); //Arcade Drive is for Gio, pretty much everyone else uses Tank (DriveWithJoysticks)
  }

  public void baseDrive(double leftSpeed, double rightSpeed) {
    if (isReversed) {
      double tempLeftSpeed = leftSpeed;
      double tempRightSpeed = rightSpeed;
      leftSpeed = -tempRightSpeed;
      rightSpeed = -tempLeftSpeed;
    }
    
    SmartDashboard.putNumber("Left Base Input", leftSpeed);
		SmartDashboard.putNumber("Right Base Input", rightSpeed);
    
    driveRightFront.set(ControlMode.PercentOutput, rightSpeed);
    driveLeftFront.set(ControlMode.PercentOutput, leftSpeed);
  }

  public void switchDriveBase(boolean Switch) {
      isReversed = Switch;
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

  public void accelDrive () {
    if (Accel.getAcceleration() < .1)
    {

    }
  }

   // Resets both of the base encoders
	public void resetEncoders()
	{
		driveLeftFront.getSensorCollection().setQuadraturePosition(0, 0);
		driveRightFront.getSensorCollection().setQuadraturePosition(0, 0);
	}

  // This sets the motion control mode for the left side of the base
	public void setLeftMotionControl(ControlMode mode, double value) {
		this.driveLeftFront.set(mode, value);
	}
	
	// This sets the motion control mode for the right side of the base
	public void setRightMotionControl(ControlMode mode, double value) {
		this.driveRightFront.set(mode, value);
	}
  
  public void driveClearSticky() {
    driveRightFront.clearStickyFaults(1000);
    driveLeftFront.clearStickyFaults(1000);
  }

  // These methods just return the base talons if we need to access them somewhere else
	public TalonSRX getBaseLeftFront() {
		return this.driveLeftFront;
  }
  
	public TalonSRX getBaseRightFront() {
		return this.driveRightFront;
  }
}