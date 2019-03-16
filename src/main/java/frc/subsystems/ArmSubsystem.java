package frc.subsystems;

import javax.lang.model.util.ElementScanner6;

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

  // Encoder positions for arm in relationship to 0 (full up)
  public static final int KArmFullDown = 1795; // 650, 1035, 1650, 1910
  public static final int KArmLow = 1525;
  public static final int KArmMiddle = 900;
  public static final int KArmHigh = 475;
  public static final int KArmFullUp = 0;

  // Encoder position for when the arm should slow down
  private static final int KBottomHuntRange = 1625;
  private static final int KTopHuntRange = 400;

  public static final double KClimbArmRatioForFull = 0.3239294403;

  // Talon config
  private final TalonSRX ArmLeft, ArmRight;
  private static final int KArmLeft = 4;
  private static final int KArmRight = 5;

  // Limit config
  private final DigitalInput LeftLimit, RightLimit;
  private static final int KLeftLimit = 6;
  private static final int KRightLimit = 7;

  private static boolean armHasBeenReset = false;

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
    setLeftArmEncoder(0);

    ArmRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    setRightArmEncoder(0);

    ArmLeft.setSensorPhase(true);
    ArmRight.setSensorPhase(true);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArmWithJoysticks());
  }

  public int getLeftArmEncoder() {
    return ArmLeft.getSensorCollection().getQuadraturePosition();
  }

  public int getLeftVelocity() {
    return ArmLeft.getSensorCollection().getQuadratureVelocity();
  }

  /**
   * Sets the left arm encoder to the desired value.
   * @param position - The value we want the encoder to have.
   */
  public void setLeftArmEncoder(int position) {
    ArmLeft.getSensorCollection().setQuadraturePosition(position, 0);
  }

  public int getRightArmEncoder() {
    return ArmRight.getSensorCollection().getQuadraturePosition();
  }

  public int getRightVelocity() {
    return ArmRight.getSensorCollection().getQuadratureVelocity();
  }

  /**
   * Sets the right arm encoder to the desired value.
   * @param position - The value we want the encoder to have.
   */
  public void setRightArmEncoder(int position) {
    ArmRight.getSensorCollection().setQuadraturePosition(position, 0);
  }

  public boolean getLeftLimit() {
    return !LeftLimit.get();
  }

  public boolean getRightLimit() {
    return !RightLimit.get();
  }

  public void move(double leftSpeed, double rightSpeed) {
    if (leftSpeed > KArmSpeed)
      leftSpeed = KArmSpeed;
    if (rightSpeed > KArmSpeed)
      rightSpeed = KArmSpeed;

    ArmLeft.set(ControlMode.PercentOutput, leftSpeed);
    ArmRight.set(ControlMode.PercentOutput, rightSpeed);
  }

  public double correctSpeed(double speed, boolean left) {
    double pos;
    boolean limit;

    if (left) {
      pos = getLeftArmEncoder();
    } else {
      pos = getRightArmEncoder();
    }

    if (pos > KBottomHuntRange)
      speed *= (KArmFullDown - pos) / (KArmFullDown - KBottomHuntRange);
    if (pos < KTopHuntRange)
      speed *= (pos - KArmFullUp) / (KTopHuntRange - KArmFullUp);

    if (left) {
      limit = getLeftLimit();
    } else {
      limit = getRightLimit();
    }

    if (limit) {
      double upDist = Math.abs(pos - KArmFullUp);
      double downDist = Math.abs(KArmFullDown - pos);
      boolean closerToUp = upDist < downDist;

      if (closerToUp) {
        if (left)
          setLeftArmEncoder(KArmFullUp);
        else
          setRightArmEncoder(KArmFullUp);

        if (speed < 0)
          speed = 0;
      } else {
        if (left)
          setLeftArmEncoder(KArmFullDown);
        else
          setRightArmEncoder(KArmFullDown);

        if (speed > 0)
          speed = 0;
      }
    } else {
      double KMidpoint = 100; // When the difference between the actual position and the target position is equal to this midpoint, speed will be 0.5

      if (pos < KArmFullUp)
        speed = (KArmFullUp - pos) * (1 / (2 * KMidpoint));
      if (pos > KArmFullDown)
        speed = (KArmFullDown - pos) * (1 / (2 * KMidpoint));
    }

    if (speed > KArmSpeed)
      speed = KArmSpeed;
    if (speed < -KArmSpeed)
      speed = -KArmSpeed;

    return speed;
  }

  public void moveArm(double speed) {
    move(correctSpeed(speed, true), correctSpeed(speed, false));

    if (!armHasBeenReset && inStartPos())
      armHasBeenReset = true;
  }

  public void moveArm(double[] speeds) {
    move(correctSpeed(speeds[0], true), correctSpeed(speeds[1], false));
  }

  public int moveArmTo(int target) {
    if (!armHasBeenReset && !inStartPos())
      return 0;

    double KPpos = 0.005;

    int leftError = target - getLeftArmEncoder();
    int rightError = target - getRightArmEncoder();

    moveArm(controlVel(new double[]{KPpos * leftError, KPpos * rightError}));

    if (Math.abs(leftError) > Math.abs(rightError))
      return Math.abs(leftError);
    else
      return Math.abs(rightError);
  }

  private double[] controlVel(double[] targets) {
    double KPvel = 0.005;

    double leftError = targets[0] - getLeftVelocity();
    double rightError = targets[1] - getRightVelocity();

    return new double[]{KPvel * leftError, KPvel * rightError};
  }

  private boolean inStartPos() {
    return getLeftLimit() && getRightLimit() && (getLeftArmEncoder() == 0) && (getRightArmEncoder() == 0);
  }

  public void lock() {
    armHasBeenReset = false;
  }
}
