package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.Arm.ArmWithJoysticks;
import frc.Robot;

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
  private static final double KArmFullDown = 1795; // 650, 1035, 1650, 1910
  private static final double KArmLow = 1525;
  private static final double KArmMiddle = 900;
  private static final double KArmHigh = 475;
  private static final double KArmFullUp = 0;

  // Encoder position for when the arm should slow down
  private static final double KArmBottomLimitHuntRange = 1625;
  private static final double KArmTopLimitHuntRange = 400;

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

  /**
   * Sets the left arm encoder to the desired value.
   * @param position - The value we want the encoder to have.
   */
  public void setLeftArmEncoder(double position) {
    ArmLeft.getSensorCollection().setQuadraturePosition((int)position, 0);
  }

  public int getRightArmEncoder() {
    return ArmRight.getSensorCollection().getQuadraturePosition();
  }

  /**
   * Sets the right arm encoder to the desired value.
   * @param position - The value we want the encoder to have.
   */
  public void setRightArmEncoder(double position) {
    ArmRight.getSensorCollection().setQuadraturePosition((int)position, 0);
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

  private double getToPos(double target, double encoder, double KP = 0.005) {
    double error = target - encoder;

    return KP * error;
  }

  double lastSpeed = 0;
  double lastTime = 0;

  private double getToVel(double target, double encoder, double KP = 0.005) {
    long time = System.currentTimeMillis();
  }

  private boolean inStartPos() {
    return getLeftLimit() && getRightLimit() && (getLeftArmEncoder() == 0) && (getRightArmEncoder() == 0);
  }
}
