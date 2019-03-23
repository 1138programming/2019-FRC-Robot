package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.Lift.LiftWithJoysticks;
import frc.robot.Robot;

public class LiftSubsystem extends Subsystem {
  /**
   * public static final int KLiftTalon = 7; private TalonSRX liftMotor;
   */
  public static final double KLiftSpeed = .95;

  public static final int KLiftFullDown = 0; 
  public static final int KLiftShip = 14950; //Haven't checked this one yet
  public static final int KLiftCargo = 23200;
  public static final int KLiftFullUp = 23200;
  
  private static final int KLiftIsAboveArm = 15000; //check
  private static final int KTopHuntRange = 5000; //??
  private static final int KBottomHuntRange = 5500;
  private static final double KMinHuntSpeed = 0.25;

  public static final double KMotorOffset = 0.05;

  private static final double KP = 0.0005;
  private static final double KI = 0.0001;

  //Talon config
  private final TalonSRX LiftTalon;
  private static final int KLiftTalon = 7;

  //Limit config
  private final DigitalInput TopLimit, BottomLimit;
  private static final int KTopLimit = 9;
  private static final int KBottomLimit = 8;

  // Keeps track of the lift's state in addition to the limits. Might help protect against encoder drift
  public static boolean pastTopLimit = false;
  public static boolean pastBottomLimit = false;

  public LiftSubsystem() {
    LiftTalon = new TalonSRX(KLiftTalon); 

    TopLimit = new DigitalInput(KTopLimit);
    BottomLimit = new DigitalInput(KBottomLimit);

    LiftTalon.setInverted(true);

    LiftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    setLiftEncoder(0);

    LiftTalon.setSensorPhase(true);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LiftWithJoysticks());
  }

  public int getLiftEncoder() {
    return LiftTalon.getSelectedSensorPosition();
  }

  public void setLiftEncoder(int position) {
    LiftTalon.setSelectedSensorPosition(position);
  }

  public boolean getTopLimit() {
    return !TopLimit.get();
  }

  public boolean getBottomLimit() {
    return !BottomLimit.get();
  }

  private void move(double speed) {
    LiftTalon.set(ControlMode.PercentOutput, speed);
  }

  private double correctSpeed(double speed) {
    double newSpeed = speed;
    int pos = getLiftEncoder();

    if (newSpeed > KLiftSpeed)
      newSpeed = KLiftSpeed;
    else if (newSpeed < -KLiftSpeed)
      newSpeed = -KLiftSpeed;

    if (pos > (KLiftFullUp - KTopHuntRange) && speed > 0)
      newSpeed *= ((KLiftFullUp - pos) / (KTopHuntRange) * (1 - KMinHuntSpeed)) + KMinHuntSpeed;
    if (pos < (KLiftFullDown + KBottomHuntRange) && speed < 0)
      newSpeed *= ((pos - KLiftFullDown) / (KBottomHuntRange) * (1 - KMinHuntSpeed)) + KMinHuntSpeed;

    if (newSpeed >= 0 && newSpeed < KMotorOffset)
      newSpeed = KMotorOffset;

    // Resets encoders and limits speed if the limit switches are activated
    if (getTopLimit()) {
      setLiftEncoder(KLiftFullUp);

      if (newSpeed > 0)
        newSpeed = KMotorOffset;

      pastTopLimit = true;
    } else if (getBottomLimit()) {
      setLiftEncoder(KLiftFullDown);

      if (newSpeed < KMotorOffset)
        newSpeed = KMotorOffset;

      pastBottomLimit = true;
    } 
    // else {
    //   double KFullAt = 120;

    //   // Moves the lift back down if the up limit has been passed
    //   if (pos > KLiftFullUp && pastTopLimit) {
    //     if (newSpeed >= 0)
    //       newSpeed = (KLiftFullUp - pos) * (1 / KFullAt);
    //   } else {
    //     pastTopLimit = false;
    //   }

    //   // Moves the lift back up if the bottom limit has been passed
    //   if (pos < KLiftFullDown && pastBottomLimit) {
    //     if (newSpeed >= 0)
    //       newSpeed = (KLiftFullDown - pos) * (1 / KFullAt);
    //   } else {
    //     pastBottomLimit = false;
    //   }
    // }

    // Limits the speed to be between KLiftSpeed and -KLiftSpeed
    // if (newSpeed > KLiftSpeed)
    //   newSpeed = KLiftSpeed;
    // else if (newSpeed < -KLiftSpeed)
    //   newSpeed = -KLiftSpeed;

    SmartDashboard.putNumber("Lift corrected speed is: ", newSpeed);
    SmartDashboard.putNumber("Lift encoder value is: ", getLiftEncoder());

    return newSpeed;
    //return speed;
  }

  public void moveLift(double speed) {    
    move(correctSpeed(speed));
  }

  private static final int maxAdditions = 100;
  private double[] errors = new double[maxAdditions];
  private double integral = 0;
  private long lastTime = System.currentTimeMillis();

  public void initMoveTo() {
    errors = new double[maxAdditions];
    integral = 0;
  }

  public double moveLiftTo(int target) {
    if (!Robot.ARM_SUBSYSTEM.armReset())
      return 0;
    
    int error = target - getLiftEncoder();
    long time = System.currentTimeMillis();
    long dt = time - lastTime;

    integral += error * (dt / 1000);
    integral -= errors[0];

    double[] newErrors = new double[maxAdditions];
    System.arraycopy(errors, 1, newErrors, 0, 99);
    newErrors[maxAdditions - 1] = error * (dt / 1000);

    double speed = error * KLiftSpeed * KP + integral * KI;

    if (speed >= 0 && speed < KMotorOffset)
      speed += KMotorOffset;

    SmartDashboard.putNumber("Target speed is: ", speed);
    SmartDashboard.putNumber("Integral is: ", integral);

    moveLift(speed);

    lastTime = time;
    errors = newErrors;

    return error;
  }
}
