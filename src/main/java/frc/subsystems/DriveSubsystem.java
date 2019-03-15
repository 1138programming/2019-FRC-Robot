package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.Drive.DriveWithJoysticks;

public class DriveSubsystem extends Subsystem {
  /**
   * public static final int KDriveLeftFrontTalon = 0; public static final int
   * KDriveRightFrontTalon = 1; public static final int KDriveLeftRearTalon = 2;
   * public static final int KDriveRightRearTalon = 3;
   * 
   * public static final int KShifterSolenoid1 = 1; public static final int
   * KShifterSolenoid2 = 2;
   * 
   * private TalonSRX driveRightFront; private TalonSRX driveLeftFront; private
   * VictorSPX driveRightRear; private VictorSPX driveLeftRear; private
   * DoubleSolenoid shifterSolenoid;
   */

  private static Boolean isReversed = false;

  private final TalonSRX driveLeftFront, driveRightFront; 
  public static final int KDriveLeftFrontTalon = 0;
  public static final int KDriveRightFrontTalon = 1;  

  private final VictorSPX driveLeftRear, driveRightRear; 
  public static final int KDriveLeftRearVictor = 2; 
  public static final int KDriveRightRearVictor = 3;

  private final DoubleSolenoid shifterSolenoid;
  public static final int KShifterSolenoid1 = 0;
  public static final int KShifterSolenoid2 = 1;

  public final AnalogAccelerometer Accel; 
  public static final int KAccelerometer = 0;

  public static final double KDriveSpeed = .75;
  public static final double KP = .001;

  public DriveSubsystem()
  {
    driveLeftFront = new TalonSRX(KDriveLeftFrontTalon);
    driveRightFront = new TalonSRX(KDriveRightFrontTalon);
    driveLeftRear = new VictorSPX(KDriveLeftRearVictor);
    driveRightRear = new VictorSPX(KDriveRightRearVictor);

    driveLeftRear.follow(driveLeftFront);
    driveRightRear.follow(driveRightFront);

    driveLeftFront.setInverted(false);
    driveLeftRear.setInverted(false);
    driveRightFront.setInverted(true);
    driveRightRear.setInverted(true);

    // driveLeftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    // driveLeftFront.setSelectedSensorPosition(0);
    // driveRightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    // driveRightFront.setSelectedSensorPosition(0);

    shifterSolenoid = new DoubleSolenoid(KShifterSolenoid1, KShifterSolenoid2);

    Accel = new AnalogAccelerometer(KAccelerometer);
  }

  @Override
  public void initDefaultCommand() {
   setDefaultCommand(new DriveWithJoysticks()/*ArcadeDriveWithJoy()*/); //Arcade Drive is for Gio, pretty much everyone else uses Tank (DriveWithJoysticks)
  }

  public void baseDrive(double leftSpeed, double rightSpeed) {
    if (isReversed) {
      leftSpeed = -leftSpeed;
      rightSpeed = -rightSpeed;
    }
    
    SmartDashboard.putNumber("Left Base Input", leftSpeed);
		SmartDashboard.putNumber("Right Base Input", rightSpeed);
    
    driveRightFront.set(ControlMode.PercentOutput, rightSpeed);
    driveLeftFront.set(ControlMode.PercentOutput, leftSpeed);
  }

  // public double driveBaseToPosition(double position)
  // {
  //   double error = position - (getRightFrontEncoder() - getLeftFrontEncoder())/2;
  //   double speed = KDriveSpeed * error * KP;

  //   if (speed > KDriveSpeed)
  //     speed = KDriveSpeed;
  //   else if (speed < -KDriveSpeed)
  //     speed = -KDriveSpeed;
    
  //   baseDrive(speed, speed);

  //   return error;
  // }

  public void driveBaseInSandstorm()
  {
    double speed;
    if (isReversed)
       speed = -KDriveSpeed;
    else
        speed = KDriveSpeed;

    baseDrive(KDriveSpeed, KDriveSpeed);
  }

  public void switchDriveBase(boolean switchButton) {
      isReversed = switchButton;
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

	public void zeroEncoders()
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

  // Only used by motion profiling
	public TalonSRX getBaseLeftFront() {
		return this.driveLeftFront;
  }
  
	public TalonSRX getBaseRightFront() {
		return this.driveRightFront;
  }

  public double getLeftFrontEncoder() {
    return driveLeftFront.getSelectedSensorPosition();
  }

  public double getRightFrontEncoder() {
    return driveRightFront.getSelectedSensorPosition();
  }
}