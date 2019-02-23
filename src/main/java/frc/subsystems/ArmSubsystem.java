package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.commands.Arm.ArmWithJoysticks;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import frc.commands.Arm.ArmStop;



public class ArmSubsystem extends Subsystem {
  /**
   * public static final int KArmLeft = 4;    ArmLeft is Right Arm
   * public static final int KArmRight = 5;     ArmRight is Left Arm
   * public static final double KArmSpeed = 1.0; 
   * 
   * private TalonSRX ArmLeft;
   * private VictorSPX ArmRight;
  */
  public static final int KArmLeft = 4; 
  public static final int KArmRight = 5;  
  public static final double KArmSpeed = .5; 
  public static final double KArmDeadZone = 1;

  public static final double KArmFullDown = 1795; //650, 1035, 1650, 1910
  public static final double KArmLow = 1525;
  public static final double KArmMiddle = 985;
  public static final double KArmHigh = 425;
  public static final double KArmFullUp = 0; 
  public static final int KArmTopReset = 500;
  public static final int KArmBottomReset = 1400;

  private static final double KP = 0.008;

  private TalonSRX ArmLeft;
  private TalonSRX ArmRight;

  private DigitalInput rightLimit, leftLimit;

  public ArmSubsystem() {
    ArmLeft = new TalonSRX(KArmLeft); 
    ArmRight = new TalonSRX(KArmRight);

    //ArmRight.follow(ArmLeft);
    ArmLeft.setInverted(true);

    ArmLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    ArmLeft.getSensorCollection().setQuadraturePosition(0, 0);
    ArmRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    ArmRight.getSensorCollection().setQuadraturePosition(0, 0);
    ArmRight.setSensorPhase(true);
    ArmLeft.setSensorPhase(true);
    
    rightLimit = new DigitalInput(7);
    leftLimit = new DigitalInput(6);

    SmartDashboard.putBoolean("activated", false);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArmWithJoysticks());
  }
  
  public void moveArm(double speed) {
    ArmLeft.set(ControlMode.PercentOutput, speed);
    ArmRight.set(ControlMode.PercentOutput, speed);
  }

  public double moveArmWithEncoders(double position) {
    double error = position - getRightArmEncoder();
    double speed = error * KArmSpeed * KP;

    if (speed > KArmSpeed)
      speed = KArmSpeed;
    else if (speed < -KArmSpeed)
      speed = -KArmSpeed;

    ArmLeft.set(ControlMode.PercentOutput, -speed);
    ArmRight.set(ControlMode.PercentOutput, -speed);

    return error;
  }

  public double resetArm() {
    return moveArmWithEncoders(KArmFullUp);
  }

  public int getRightArmEncoder() {
    //return liftMotor.getSelectedSensorPosition(0);
    return ArmRight.getSelectedSensorPosition();
  }

  public void resetRightArmEncoder() {
    ArmRight.getSensorCollection().setQuadraturePosition(0,0);
  }

  public int getLeftArmEncoder() {
    //return liftMotor.getSelectedSensorPosition(0);
    return ArmLeft.getSelectedSensorPosition();
  }

  public void resetLeftArmEncoder() {
    ArmLeft.getSensorCollection().setQuadraturePosition(0,0);
  }

  public boolean leftLimitClosed() {
    return !leftLimit.get();
  }

  public boolean rightLimitClosed() {
    return !rightLimit.get();
  }

  public void rightLimitReset() {
    if(ArmRight.getSensorCollection().getQuadraturePosition() <= KArmTopReset)
      ArmRight.getSensorCollection().setQuadraturePosition((int)KArmFullUp, 0);
    else if(ArmRight.getSensorCollection().getQuadraturePosition() >= KArmBottomReset)
      ArmRight.getSensorCollection().setQuadraturePosition((int)KArmFullDown, 0);
  }

  public void leftLimitReset() {
    if(ArmLeft.getSensorCollection().getQuadraturePosition() <= KArmTopReset)
      ArmLeft.getSensorCollection().setQuadraturePosition((int)KArmFullUp, 0);
    else if(ArmLeft.getSensorCollection().getQuadraturePosition() >= KArmBottomReset)
      ArmLeft.getSensorCollection().setQuadraturePosition((int)KArmFullDown, 0);
      SmartDashboard.putBoolean("activated", true);
  }
}
