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
  private static final double KArmSpeed = 1; // Max speed of arm when controlled by autonomous functions/macros=
  private static final double KArmNotReset = .25; // Max speed of arm when it has not yet been reset
  public static final double KArmIsBelowLift = 965;

  // Encoder positions for arm in relationship to 0 (full up)
  public static final int KArmFullDown = 1795; // Value of the encoder at the bottom limit
  public static final int KArmLow = 1525;
  public static final int KArmMiddle = 900;
  public static final int KArmHigh = 475;
  public static final int KArmFullUp = 0; // Value of the encoder at the top limit

  // Encoder position for when the arm should slow down
  private static final int KBottomHuntRange = 400;
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

  // Keeps track of the arm's state. Might help protect against encoder drift
  private static boolean pastUpLimit = false;
  private static boolean pastDownLimit = false;

  public static boolean armHasBeenReset = false;

  public ArmSubsystem() {
    // Initialize talons
    ArmLeft = new TalonSRX(KArmLeft);
    ArmRight = new TalonSRX(KArmRight);

    // Initialize limits
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
    ArmRight.setSensorPhase(true); //false on practice, true on comp
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArmWithJoysticks());
  }

  /**
   * @return - The value of the left arm encoder
   */
  public int getLeftArmEncoder() {
    //return ArmLeft.getSensorCollection().getQuadraturePosition();
    return ArmLeft.getSelectedSensorPosition();
  }

  /**
   * @return - The velocity of the left arm motor
   */
  public int getLeftVelocity() {
    //return ArmLeft.getSensorCollection().getQuadratureVelocity();
    return ArmLeft.getSelectedSensorVelocity();
  }

  /**
   * Sets the left arm encoder to the desired value.
   * @param position - The value we want the encoder to have.
   */
  public void setLeftArmEncoder(int position) {
    //ArmLeft.getSensorCollection().setQuadraturePosition(position, 0);
    ArmLeft.setSelectedSensorPosition(position);
  }

  /**
   * @return - The value of the right arm encoder
   */
  public int getRightArmEncoder() {
    //return ArmRight.getSensorCollection().getQuadraturePosition();
    return ArmRight.getSelectedSensorPosition();
  }

  /**
   * @return - The velocity of the right arm motor
   */
  public int getRightVelocity() {
    //return ArmRight.getSensorCollection().getQuadratureVelocity();
    return ArmRight.getSelectedSensorVelocity();
  }

  /**
   * Sets the right arm encoder to the desired value.
   * @param position - The value we want the encoder to have.
   */
  public void setRightArmEncoder(int position) {
    //ArmRight.getSensorCollection().setQuadraturePosition(position, 0);
    ArmRight.setSelectedSensorPosition(position);
  }

  /**
   * @return - The state of the left limit switch. True indicates it is close
   */
  public boolean getLeftLimit() {
    return !LeftLimit.get();
  }

  /**
   * @return - The state of the right limit switch. True indicates it is close
   */
  public boolean getRightLimit() {
    return !RightLimit.get();
  }

  public boolean armReset() {
    return armHasBeenReset;
  }

  /**
   * Moves the arm without caring about what speed it is given
   * @param leftSpeed - Speed to set the left motor to
   * @param rightSpeed - Speed to set the right motor to
   */
  private void move(double leftSpeed, double rightSpeed) {
    ArmLeft.set(ControlMode.PercentOutput, leftSpeed);
    ArmRight.set(ControlMode.PercentOutput, rightSpeed);
  }

  /**
   * Makes sure a given speed is valid and corrects it if necessary. 
   * Does not let the robot move in the direction past the limit switch if the switch has been passed
   * Resets the encoders to a known value when a limit switch is passed.
   * The speed is slowed down if the arm is approacing a limit switch.
   * If the arm has passed a limit switch, the speed will be set to try and move the arm to before the limit switch
   * @param speed - The speed to be corrected
   * @param left - The side with which to check the speed against. True is left and false is right
   * @return - The corrected speed
   */
  private double correctSpeed(double speed, boolean left) {
    double newSpeed = speed; // Stores the new speed
    double pos; // Store the position of the chosen side
    boolean limit; // Store the state of the given side's limit switch

    if (!armHasBeenReset) {
      return -Math.abs(speed) * KArmNotReset;
    }

    // Gets the position of the arm based on which side has been chosen
    if (left) {
      pos = getLeftArmEncoder();
    } else {
      pos = getRightArmEncoder();
    }

    // If the position of the given side is within the hunt range (a.k.a. close to the limit switch)
    // then the speed will be slowed proportionally to how close it is to the limit.
    if (pos < (KArmFullUp + KTopHuntRange) && speed < 0)
      newSpeed *= (pos - KArmFullUp) / (4 * KTopHuntRange) + 0.25;
    if (pos > (KArmFullDown - KBottomHuntRange) && speed > 0)
      newSpeed *= (KArmFullDown - pos) / (4 * KBottomHuntRange) + 0.25;

    double KOffFull = 100;
    if (left) {
      newSpeed += (getRightArmEncoder() - pos) * (1 / KOffFull);
    } else {
      newSpeed += (getLeftArmEncoder() - pos) * (1 / KOffFull);
    }

    // Gets the state of the arm's limit switch based on which side has been chosen
    if (left) {
      limit = getLeftLimit();
      SmartDashboard.putBoolean("Left limit is: ", limit);
    } else {
      limit = getRightLimit();
      SmartDashboard.putBoolean("Right limit is: ", limit);
    }

    // Resets encoders and limits speed if the limit switch is activates
    if (limit) {
      double upDist = Math.abs(pos - KArmFullUp); // Distance to the up limit switch
      double downDist = Math.abs(KArmFullDown - pos); // Distance to the down limit switch
      boolean closerToUp = upDist < downDist; // Decides if the arm is at te up or down limit switch based on the distances

      if (closerToUp) {
        // Resets the given side's encoder to the up limit
        if (left)
          setLeftArmEncoder(KArmFullUp);
        else
          setRightArmEncoder(KArmFullUp);

        // Does not allow the arm to move any further up
        if (newSpeed < 0)
          newSpeed = 0;

        // Records that the up limit switch has been passed
        pastUpLimit = true;
      } 
      else {
        // Resets the given side's encoder to the down limit
        if (left)
          setLeftArmEncoder(KArmFullDown);
        else
          setRightArmEncoder(KArmFullDown);

        // Does not allow the arm to move any further down
        if (newSpeed > 0)
          newSpeed = 0;

        // Records that the down limit switch has been passed
        pastDownLimit = true;
      }
    } 
    else {
      double KFullAt = 120; // When the difference between the arm's position and the limit position is equal to KFullAt, speed will be 100%

      // Moves arm back down if the up limit has been passed
      if (pos < KArmFullUp && pastUpLimit) {
        if (speed <= 0)
          newSpeed = (KArmFullUp - pos) * (1 / KFullAt); // Sets the speed proportionally to how far past the limit the arm is
      } else {
        pastUpLimit = false; // If the arm is not past the up limit (determined by the encoder's position), the boolean is set back to false
      }

      // Moves arm back up if the down limit has been passed
      if (pos > KArmFullDown && pastDownLimit) {
        if (speed >= 0)
          newSpeed = (KArmFullDown - pos) * (1 / KFullAt); // Sets the speed proportionally to how far past the limit the arm is
      } else {
        pastDownLimit = false; // If the arm is not past the down limit (determined by the encoder's position), the boolean is set back to false
      }
    }

    // Limits the speed to be between KArmSpeed and -KArmSpeed
    if (newSpeed > KArmSpeed)
      newSpeed = KArmSpeed;
    if (newSpeed < -KArmSpeed)
      newSpeed = -KArmSpeed;

    if (left) {
      SmartDashboard.putNumber("Corrected left side speed: ", newSpeed);
    } else {
      SmartDashboard.putNumber("Corrected right side speed: ", newSpeed);
    }

    SmartDashboard.putBoolean("Past up limit: ", pastUpLimit);
    SmartDashboard.putBoolean("Past down limit: ", pastDownLimit);

    //return speed;
    return newSpeed;
  }

  // /**
  //  * Moves both sides of the arm with one speed. Used with joysticks
  //  * @param speed - Speed to use for both sides of the arm
  //  */
  // public void moveArmForJoysticks(double speed) {
  //   // If the arm is in its starting position, it has been reset
  //   if (!armHasBeenReset && inStartPos())
  //     armHasBeenReset = true;

  //   move(correctSpeed(speed, true), correctSpeed(speed, false));
  // }

  /**
   * Moves each side of the arm with separate speeds
   * @param leftSpeed - The speed to drive the left side of the arm at
   * @param rightSpeed - The speed to drive the right side of the arm at
   */
  public void moveArm(double leftSpeed, double rightSpeed) {
    // If the arm is in its starting position, it has been reset
    if (!armHasBeenReset && inStartPos())
      armHasBeenReset = true; 
        
    move(correctSpeed(leftSpeed, true), correctSpeed(rightSpeed, false));
  }

  /**
   * Uses P loops to move the arm to a target encoder position
   * @param target - The target encoder position
   * @return - Returns the error between the target position and actual position
   */
  public int moveArmTo(int target) {
    // If the arm has not been reset, immediately return 0. This prevents macro control before the arm has safely been reset
    if (!armReset())
      return 0;

    double KPpos = 0.0035;

    int leftError = target - getLeftArmEncoder();
    int rightError = target - getRightArmEncoder();

    //double[] speeds = controlVel(new double[]{KPpos * leftError, KPpos * rightError});
    //moveArm(speeds[0], speeds[1]);
    moveArm(KPpos * leftError, KPpos * rightError);

    if (Math.abs(leftError) > Math.abs(rightError))
      return Math.abs(leftError);
    else
      return Math.abs(rightError);
  }

  /**
   * Uses a P loop to move the arm at a target velocity
   * @param targets - The target velocities to drive the arm at
   * @return - The motor outputs calculated by the P loop
   */
  private double[] controlVel(double[] targets) {
    double KPvel = 0.005;

    double leftError = targets[0] - getLeftVelocity();
    double rightError = targets[1] - getRightVelocity();

    return new double[]{KPvel * leftError, KPvel * rightError};
  }

  /**
   * Checks if the robot is in its starting position. This means the limits are closed and the encoders are at 0
   * @return - True if the robot is in starting positin, false otherwise
   */
  private boolean inStartPos() {
    return getLeftLimit() && getRightLimit() && (getLeftArmEncoder() <= 0) && (getRightArmEncoder() <= 0);
  }

  /**
   * Sets armHasBeenReset to false to prevent macro control of the arm
   */
  public void lock() {
    armHasBeenReset = false;
  }
}
