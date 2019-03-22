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

  private static final double KLiftFullDown = 0; 
  private static final double KLiftShip = 14950; //Haven't checked this one yet
  private static final double KLiftCargo = 23200;
  private static final double KLiftFullUp = 23200;
  
  private static final double KLiftTopLimitHuntRange = 22000; //??
  private static final double KLiftIsAboveArm = 15000; //check
  private static final double KLiftBottomLimitHuntRange = 700;

  public static final double KMotorOffset = 0.05;

  private static final double KP = 0.13;

  //Talon config
  private final TalonSRX LiftTalon;
  private static final int KLiftTalon = 7;

  //Limit config
  private final DigitalInput TopLimit, BottomLimit;
  private static final int KTopLimit = 9;
  private static final int KBottomLimit = 8;

  public LiftSubsystem() {
    LiftTalon = new TalonSRX(KLiftTalon); 

    TopLimit = new DigitalInput(KTopLimit);
    BottomLimit = new DigitalInput(KBottomLimit);

    LiftTalon.setInverted(true);

    LiftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    zeroLiftEncoder();

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

  public boolean topLimitClosed() {
    return !TopLimit.get();
  }

  public boolean bottomLimitClosed() {
    return !BottomLimit.get();
  }

  private double correctSpeed(double speed) {
    double newSpeed = speed;

    if ()

    if (newSpeed > KLiftSpeed)
      newSpeed = KLiftSpeed;
    else if (speed < -KLiftSpeed)
      newSpeed = -KLiftSpeed;

    return newSpeed;
  }

  public void moveLift(double speed) {    
    LiftTalon.set(ControlMode.PercentOutput, correctSpeed(speed));
  }

  public void move(double speed) {
    LiftTalon.set(ControlMode.PercentOutput, speed);
  }

  private double moveLiftWithEncoders(double position) {
    if (!Robot.ARM_SUBSYSTEM.armReset())
      return 0;
      
    double error = position - getLiftEncoder();
    double speed = error * KLiftSpeed * KP;

    moveLift(speed);

    return error;
  }
}
