package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.Arm.ArmWithJoysticks;

public class ArmSubsystem extends Subsystem {

  /**
   * public static final int KArmLeft = 4; ArmLeft is Right Arm public static
   * final int KArmRight = 5; ArmRight is Left Arm public static final double
   * KArmSpeed = 1.0;
   * 
   * private TalonSRX ArmLeft; private VictorSPX ArmRight;
   */

  //Max speed of arm when controlled by autonomous functions/macros
  public static final double KArmSpeed = .75; 

  public static enum ArmPosition { UNKNOWN, FULLDOWN, LOW, MIDDLE, HIGH, FULLUP }

  //Encoder positions for arm in relationship to 0 (full up)
  private static final double KArmFullDown = 1795; //650, 1035, 1650, 1910
  private static final double KArmLow = 1525;
  private static final double KArmMiddle = 985;
  private static final double KArmHigh = 445;
  private static final double KArmFullUp = 0;

  private static final double KArmBottomLimitHuntRange = 1575; //??
  private static final double KArmTopLimitHuntRange = 300;
    
  //PI(D) tuning for arm
  private static final double KP = 0.0075;

  //Talon config
  private final TalonSRX ArmLeft, ArmRight;
  private static final int KArmLeft = 4; 
  private static final int KArmRight = 5;

  //Limit config
  private final DigitalInput LeftLimit, RightLimit;
  private static final int KLeftLimit = 6;
  private static final int KRightLimit = 7;

  public ArmSubsystem() {
    ArmLeft = new TalonSRX(KArmLeft); 
    ArmRight = new TalonSRX(KArmRight);

    LeftLimit = new DigitalInput(KLeftLimit);
    RightLimit = new DigitalInput(KRightLimit);

    //Always configure BOTH talons
    ArmLeft.setInverted(true);
    ArmRight.setInverted(false);

    //Configure encoders
    ArmLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    zeroLeftArmEncoder();
    ArmRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    zeroRightArmEncoder();

    ArmLeft.setSensorPhase(true);
    ArmRight.setSensorPhase(true);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArmWithJoysticks());
  }

  public int getLeftArmEncoder() {
    return ArmLeft.getSelectedSensorPosition();
  }

  public void setLeftArmEncoder(int position) {
    ArmLeft.getSensorCollection().setQuadraturePosition(position,0);
  }

  public void zeroLeftArmEncoder() {
    setLeftArmEncoder(0);
  }

  public int getRightArmEncoder() {
    return ArmRight.getSelectedSensorPosition();
  }

  public void setRightArmEncoder(int position) {
    ArmRight.getSensorCollection().setQuadraturePosition(position,0);
  }

  public void zeroRightArmEncoder() {
    setRightArmEncoder(0);
  }

  public boolean leftLimitClosed() {
    return !LeftLimit.get();
  }

  public boolean rightLimitClosed() {
    return !RightLimit.get();
  }

  public ArmPosition getLeftArmPosition() {
    if(getLeftArmEncoder() <= KArmTopLimitHuntRange)
      return ArmPosition.FULLUP;
    else if(getLeftArmEncoder() >= KArmBottomLimitHuntRange)
      return ArmPosition.FULLDOWN;
    else
      return ArmPosition.UNKNOWN;
  }

  public ArmPosition getRightArmPosition() {
    if(getRightArmEncoder() <= KArmTopLimitHuntRange)
      return ArmPosition.FULLUP;
    else if(getRightArmEncoder() >= KArmBottomLimitHuntRange)
      return ArmPosition.FULLDOWN;
    else
      return ArmPosition.UNKNOWN;
  }

  //Identifies which limit switch has been activated and resets the encoder appropriatly
  public void identifyLeftLimitandResetEncoder() {
    if(getLeftArmEncoder() <= KArmTopLimitHuntRange)
      setLeftArmEncoder((int)KArmFullUp);
    else if(getLeftArmEncoder() >= KArmBottomLimitHuntRange)
      setLeftArmEncoder((int)KArmFullDown);
  }
  
  //Identifies which limit switch has been activated and resets the encoder appropriatly
  public void identifyRightLimitandResetEncoder() {
    if(getRightArmEncoder() <= KArmTopLimitHuntRange)
      setRightArmEncoder((int)KArmFullUp);
    else if(getRightArmEncoder() >= KArmBottomLimitHuntRange)
      setRightArmEncoder((int)KArmFullDown);
  }

  //FLAG: Refactor eventually
  public double checkLeftArmLimits(double targetSpeed) {
    if(leftLimitClosed() && getLeftArmPosition() == ArmPosition.FULLUP) {
      if(targetSpeed > 0)
        targetSpeed = 0;
      identifyLeftLimitandResetEncoder();
    }
    if(leftLimitClosed() && getLeftArmPosition() == ArmPosition.FULLDOWN) {
      if(targetSpeed < 0)
        targetSpeed = 0;
      identifyLeftLimitandResetEncoder();
    }

    return targetSpeed;
  }

  //FLAG: Refactor eventually
  public double checkRightArmLimits(double targetSpeed) {
    if(rightLimitClosed() && getRightArmPosition() == ArmPosition.FULLUP) {
      if(targetSpeed > 0)
        targetSpeed = 0;
      identifyRightLimitandResetEncoder();
    }
    if(rightLimitClosed() && getRightArmPosition() == ArmPosition.FULLDOWN) {
      if(targetSpeed < 0)
        targetSpeed = 0;
      identifyRightLimitandResetEncoder();
    }

    return targetSpeed;
  }

  public void moveArm(double speed) {
    ArmLeft.set(ControlMode.PercentOutput, speed);
    ArmRight.set(ControlMode.PercentOutput, speed);
  }

  private double moveArmWithEncoders(double position) {
    double error = position - (getRightArmEncoder() + getLeftArmEncoder())/2;
    double speed = error * KArmSpeed * KP;

    if (speed > KArmSpeed)
      speed = KArmSpeed;
    else if (speed < -KArmSpeed)
      speed = -KArmSpeed;

    moveArm(speed);

    return error;
  }

  public double moveArmToPosition(ArmPosition position)
  {
    switch (position)
    {
      case FULLDOWN:
        return moveArmWithEncoders(KArmFullDown);
      case LOW:
        return moveArmWithEncoders(KArmLow);
      case MIDDLE:
        return moveArmWithEncoders(KArmMiddle);
      case HIGH:
        return moveArmWithEncoders(KArmHigh);
      case FULLUP:
        return moveArmWithEncoders(KArmFullUp);
      default:
        //Should really throw an exception;
        return 1/0;
    }
  }
}
