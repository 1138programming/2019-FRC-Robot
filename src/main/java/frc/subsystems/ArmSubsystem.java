package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.Arm.ArmWithJoysticks;
import frc.robot.Robot;

public class ArmSubsystem extends Subsystem {

  /**
   * public static final int KArmLeft = 4; ArmLeft is Right Arm public static
   * final int KArmRight = 5; ArmRight is Left Arm public static final double
   * KArmSpeed = 1.0;
   * 
   * private TalonSRX ArmLeft; private VictorSPX ArmRight;
   */

  private static final double KArmSpeed = .75; // Max speed of arm when controlled by autonomous functions/macros=
  public static final double KArmIsBelowLift = 965;

  public static enum ArmPosition {
    UNKNOWN, FULLDOWN, LOW, MIDDLE, HIGH, FULLUP
  }

  // Encoder positions for arm in relationship to 0 (full up)
  private static final double KArmFullDown = 1795; // 650, 1035, 1650, 1910
  private static final double KArmLow = 1525;
  private static final double KArmMiddle = 900;
  private static final double KArmHigh = 475;
  private static final double KArmFullUp = 0;

  public double armInPosition = 0;

  // Encoder position for when the arm should slow down
  private static final double KArmBottomLimitHuntRange = 1625;
  private static final double KArmTopLimitHuntRange = 400;
  private static final double KArmHuntSpeed = .5; // Speed when approaching a limit (as a percentage)

  public static final double KClimbArmRatioForFull = 0.3239294403;

  // Encoder position for when the arm is considered at it's location
  private static final double KArmBottomRange = 1575; // over this number means we are at the bottom
  private static final double KArmTopRange = 300; // under this number mean we are at the top

  // PI(D) tuning for arm
  private static final double KP = 0.005;
  private static final double KPClimb = 0.0045;

  // Talon config
  private final TalonSRX ArmLeft, ArmRight;
  private static final int KArmLeft = 4;
  private static final int KArmRight = 5;

  // Limit config
  private final DigitalInput LeftLimit, RightLimit;
  private static final int KLeftLimit = 6;
  private static final int KRightLimit = 7;

  public ArmSubsystem() {
    ArmLeft = new TalonSRX(KArmLeft);
    ArmRight = new TalonSRX(KArmRight);

    LeftLimit = new DigitalInput(KLeftLimit);
    RightLimit = new DigitalInput(KRightLimit);

    // Always configure BOTH talons
    ArmLeft.setInverted(true);
    ArmRight.setInverted(false);

    // Configure encoders
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

  /**
   * Sets the left arm encoder to the desired value.
   * @param position - The value we want the encoder to have.
   */
  public void setLeftArmEncoder(int position) {
    ArmLeft.getSensorCollection().setQuadraturePosition(position, 0);
  }

  public void zeroLeftArmEncoder() {
    setLeftArmEncoder(0);
  }

  public int getRightArmEncoder() {
    return ArmRight.getSelectedSensorPosition();
  }

  /**
   * Sets the right arm encoder to the desired value.
   * @param position - The value we want the encoder to have.
   */
  public void setRightArmEncoder(int position) {
    ArmRight.getSensorCollection().setQuadraturePosition(position, 0);
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
    if (getLeftArmEncoder() <= KArmTopRange)
      return ArmPosition.FULLUP;
    else if (getLeftArmEncoder() >= KArmBottomRange)
      return ArmPosition.FULLDOWN;
    else
      return ArmPosition.UNKNOWN;
  }

  public ArmPosition getRightArmPosition() {
    if (getRightArmEncoder() <= KArmTopRange)
      return ArmPosition.FULLUP;
    else if (getRightArmEncoder() >= KArmBottomRange)
      return ArmPosition.FULLDOWN;
    else
      return ArmPosition.UNKNOWN;
  }

  // Identifies which limit switch has been activated and resets the encoder
  // appropriatly
  private void identifyLeftLimitandResetEncoder() {
    switch (getLeftArmPosition()) {
    case FULLUP:
      setLeftArmEncoder((int) KArmFullUp);
      break;
    case FULLDOWN:
      setLeftArmEncoder((int) KArmFullDown);
      break;
    default:
      break;
    }
  }

  // Identifies which limit switch has been activated and resets the encoder
  // appropriatly
  private void identifyRightLimitandResetEncoder() {
    switch (getRightArmPosition()) {
      case FULLUP:
        setRightArmEncoder((int) KArmFullUp);
        break;
      case FULLDOWN:
        setRightArmEncoder((int) KArmFullDown);
        break;
      default:
        break;
    }
  }

  private double enforceLeftArmLimits(double targetSpeed) {
    /*if (leftLimitClosed() && getLeftArmPosition() == ArmPosition.FULLUP) {
      if (targetSpeed < 0)
        targetSpeed = 0;
      identifyLeftLimitandResetEncoder();
    }
    else if (leftLimitClosed() && getLeftArmPosition() == ArmPosition.FULLDOWN) {
      if (targetSpeed > 0)
        targetSpeed = 0;
      identifyLeftLimitandResetEncoder();
    }*/

    double leftPos = getLeftArmEncoder();

    if (leftLimitClosed()) {
      double fullUpDist = Math.abs(leftPos - KArmFullUp);
      double fullDownDist = Math.abs(KArmFullDown - leftPos);
      boolean closerToUp = fullUpDist < fullDownDist;

      if (closerToUp) {
        setLeftArmEncoder((int)KArmFullUp);
        if (targetSpeed < 0)
          targetSpeed = 0;
      } 
      else {
        setLeftArmEncoder((int)KArmFullDown);
        if (targetSpeed > 0)
          targetSpeed = 0;
      }
    } 
    // else if (leftPos < 0) {
    //     if (targetSpeed < 0)
    //         targetSpeed = 0;
    // }

    return targetSpeed;
  }

  private double enforceRightArmLimits(double targetSpeed) {
    /*if (rightLimitClosed() && getRightArmPosition() == ArmPosition.FULLUP) {
      if (targetSpeed < 0)
        targetSpeed = 0;
      identifyRightLimitandResetEncoder();
    }
    else if (rightLimitClosed() && getRightArmPosition() == ArmPosition.FULLDOWN) {
      if (targetSpeed > 0)
        targetSpeed = 0;
      identifyRightLimitandResetEncoder();
    }*/

    double rightPos = getRightArmEncoder();

    if (rightLimitClosed()) {
      double fullUpDist = Math.abs(rightPos - KArmFullUp); //500
      double fullDownDist = Math.abs(KArmFullDown - rightPos); //2295
      boolean closerToUp = fullUpDist < fullDownDist;

      if (closerToUp) {
        setRightArmEncoder((int)KArmFullUp);
        if (targetSpeed < 0)
          targetSpeed = 0;
      } else {
        setRightArmEncoder((int)KArmFullDown);
        if (targetSpeed > 0)
          targetSpeed = 0;
      }
    } 
    // else if (rightPos < 0) {
    //   if (targetSpeed < 0)
    //       targetSpeed = 0;
    // }

    return targetSpeed;
  }

  public void moveArm(double desiredSpeed) {
    if (desiredSpeed != 0)
    {
      // What if one of the enforceArmLimits returns zero? E.g. one of them has reached zero
      // It'd still run, right?
      desiredSpeed = (enforceLeftArmLimits(desiredSpeed) + enforceRightArmLimits(desiredSpeed)) / 2;

      if (isInHuntRange()) {
        desiredSpeed = desiredSpeed * KArmHuntSpeed;
      }

      if ((!leftLimitClosed() || getLeftArmEncoder() != 0 || 
        !rightLimitClosed() || getRightArmEncoder() != 0) && 
        Robot.armHasBeenReset == false && Robot.oi.getRightXbox() >= 0) {
          desiredSpeed = 0;
      }
      else if ((leftLimitClosed() && getLeftArmEncoder() == 0 && 
                rightLimitClosed() && getRightArmEncoder() == 0)) {
          Robot.armHasBeenReset = true;
     }
    }

    ArmLeft.set(ControlMode.PercentOutput, desiredSpeed);
    ArmRight.set(ControlMode.PercentOutput, desiredSpeed);
  }

  private boolean isInHuntRange() {
    return ((getLeftArmEncoder() <= KArmTopLimitHuntRange) || (getLeftArmEncoder() >= KArmBottomLimitHuntRange)
        || (getRightArmEncoder() <= KArmTopLimitHuntRange) || (getLeftArmEncoder() >= KArmBottomLimitHuntRange));
  }

  // Returns how far off we are from the desired position
  private double moveArmWithEncoders(double positionInTicks) {
    double error = positionInTicks - (getRightArmEncoder() + getLeftArmEncoder()) / 2;
    double speed = error * KP; //Speed * KP;

    if (speed > KArmSpeed)
      speed = KArmSpeed;
    else if (speed < -KArmSpeed)
      speed = -KArmSpeed;

    // if (leftLimitClosed() || rightLimitClosed())
    //   speed = 0;

    moveArm(speed);

    return error;
  }

  // Returns how far off we are from the desired position
  public double moveArmToPosition(ArmPosition position) {
    if (Robot.useDualArmPID) {
      switch (position) {
      case FULLDOWN:
        return combinedControl(KArmFullDown);
      case LOW:
        return combinedControl(KArmLow);
      case MIDDLE:
        return combinedControl(KArmMiddle);
      case HIGH:
        return combinedControl(KArmHigh);
      case FULLUP:
        return combinedControl(KArmFullUp);
      default:
        // Should really throw an exception;
        return 1 / 0;
      }
    } else {
      switch (position) {
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
          // Should really throw an exception;
          return 1 / 0;
        }
    }
  }

  public double moveArmWithClimb() {
    double[] adjustmentValue = Robot.CLIMB_SUBSYSTEM.getGyroValues();
    double error = -adjustmentValue[0];
    SmartDashboard.putNumber("Yaw", adjustmentValue[0]);
    SmartDashboard.putNumber("Pitch", adjustmentValue[1]);
    SmartDashboard.putNumber("Roll", adjustmentValue[2]);
    double climbSpeed = error * KPClimb * KClimbArmRatioForFull;

    if (climbSpeed > KClimbArmRatioForFull)
      climbSpeed = KClimbArmRatioForFull;
    else if (climbSpeed < -KClimbArmRatioForFull)
      climbSpeed = -KClimbArmRatioForFull;


    if (error <= 5 && error >= -5)
      climbSpeed = 0;

    moveArm(climbSpeed);

    return error;
  }

  private boolean inStartingPosition() {
    return leftLimitClosed() && rightLimitClosed() && (getLeftArmEncoder() == 0) && (getRightArmEncoder() == 0);
  }

  private double combinedControl(double tPos) {
    if (!inStartingPosition() && !Robot.armHasBeenReset)
      return 0;

    double KPvel = 0.0043, KPpos = 0;
    double rightPos = getRightArmEncoder(), leftPos = getLeftArmEncoder();

    // Regular PID control for initial speeds
    double leftError = tPos - leftPos, rightError = tPos - rightPos;
    double leftSpeed = KPvel * leftError, rightSpeed = KPvel * rightError;

    // Add or subtract difference petween positions to correct for differing speeds
    leftSpeed += KPpos * (rightPos - leftPos);
    rightSpeed += KPpos * (leftPos - rightPos);

    // Lowers speed if the arm is close to the limits
    if (isInHuntRange()) {
      leftSpeed *= KArmHuntSpeed;
      rightSpeed *= KArmHuntSpeed;
    }

    // Makes sure the speed
    leftSpeed = enforceLeftArmLimits(leftSpeed);
    rightSpeed = enforceRightArmLimits(rightSpeed);

    // Confines to range of -1 to 1
    if (leftSpeed > KArmSpeed)
      leftSpeed = KArmSpeed;
    else if (leftSpeed < -KArmSpeed)
      leftSpeed = -KArmSpeed;

    if (rightSpeed > KArmSpeed)
      rightSpeed = KArmSpeed;
    else if (rightSpeed < -KArmSpeed)
      rightSpeed = -KArmSpeed;

    ArmLeft.set(ControlMode.PercentOutput, leftSpeed);
    ArmRight.set(ControlMode.PercentOutput, rightSpeed);

    if (Math.abs(leftError) > Math.abs(rightError))
      return leftError;
    else
      return rightError;
  }
}
