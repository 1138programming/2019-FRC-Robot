package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
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

    shifterSolenoid = new DoubleSolenoid(KShifterSolenoid1, KShifterSolenoid2);

    Accel = new AnalogAccelerometer(KAccelerometer);
  }

  @Override
  public void initDefaultCommand() {
   setDefaultCommand(new DriveWithJoysticks()); //Arcade Drive is for Gio, pretty much everyone else uses Tank (DriveWithJoysticks)
  }

  public void baseDrive(double leftSpeed, double rightSpeed) {
    SmartDashboard.putBoolean("Reversed?", isReversed);
    if (isReversed) {
      leftSpeed = -leftSpeed;
      rightSpeed = -rightSpeed;
    }
    
    SmartDashboard.putNumber("Left Base Input", leftSpeed);
		SmartDashboard.putNumber("Right Base Input", rightSpeed);
    
    driveRightFront.set(ControlMode.PercentOutput, rightSpeed);
    driveLeftFront.set(ControlMode.PercentOutput, leftSpeed);
  }

  public void driveBaseInSandstorm()
  {
    baseDrive(-KDriveSpeed, -KDriveSpeed);
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