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

  public static enum LiftPosition { UNKNOWN, FULLDOWN, CARGO, SHIP, FULLUP }


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

  public void zeroLiftEncoder() {
    setLiftEncoder(0);
  }

  private void setLiftEncoder(LiftPosition position)
  {
    switch (position)
    {
      case FULLDOWN:
        setLiftEncoder((int)KLiftFullDown);
        break;
      case SHIP:
        setLiftEncoder((int)KLiftShip);
        break;
      case CARGO:
        setLiftEncoder((int)KLiftCargo);
        break;
      case FULLUP:
        setLiftEncoder((int)KLiftFullUp);
        break;
      default:
        setLiftEncoder(1/0);
        break;
    }
  }

  public LiftPosition getLiftPosition() {
    if(getLiftEncoder() >= KLiftTopLimitHuntRange)
      return LiftPosition.FULLUP;
    else if(getLiftEncoder() <= KLiftBottomLimitHuntRange)
      return LiftPosition.FULLDOWN;
    else
      return LiftPosition.UNKNOWN;
  }

  public boolean topLimitClosed() {
    return !TopLimit.get();
  }

  public boolean bottomLimitClosed() {
    return !BottomLimit.get();
  }

  private double enforceLiftLimits(double targetSpeed) {
    if(topLimitClosed()) {
			if (targetSpeed > 0) 
				targetSpeed = 0;
		  setLiftEncoder(LiftPosition.FULLUP);
		} 
		else if(bottomLimitClosed()) {
			if (targetSpeed < 0)
				targetSpeed = 0;
      setLiftEncoder(LiftPosition.FULLDOWN);
    }
    
    return targetSpeed;
  }

  public void moveLift(double desiredSpeed) {
    if(desiredSpeed != 0) {
      SmartDashboard.putNumber("lift move lift", desiredSpeed);
      desiredSpeed = (enforceLiftLimits(desiredSpeed));
      SmartDashboard.putNumber("lift move lift", desiredSpeed);

      if(isInHuntRange()) {
        desiredSpeed = desiredSpeed/4;
        SmartDashboard.putString("lift", "hunting");
      }
      else
        SmartDashboard.putString("lift", "not hunting");

      // if (((Robot.ARM_SUBSYSTEM.getRightArmEncoder() + Robot.ARM_SUBSYSTEM.getLeftArmEncoder())/2 <= Robot.ARM_SUBSYSTEM.KArmIsBelowLift) && 
      //     (getLiftEncoder() >= KLiftIsAboveArm)) {
      //  desiredSpeed = 0;
      //}
      SmartDashboard.putNumber("Lift Encoder", getLiftEncoder());
    }
    
    LiftTalon.set(ControlMode.PercentOutput, desiredSpeed + KMotorOffset);
  }

  public void moveLiftUNSAFELY(double speed) {
    LiftTalon.set(ControlMode.PercentOutput, speed);
  }

  private boolean isInHuntRange() {
    return ((getLiftEncoder() >= KLiftTopLimitHuntRange) || (getLiftEncoder() <= KLiftBottomLimitHuntRange));
  }

  private double moveLiftWithEncoders(double position) {
    if (!Robot.ARM_SUBSYSTEM.armReset())
      return 0;
      
    double error = position - getLiftEncoder();
    double speed = error * KLiftSpeed * KP;

    if (speed > KLiftSpeed)
      speed = KLiftSpeed;
    else if (speed < -KLiftSpeed)
      speed = -KLiftSpeed;

    //speed = enforceLiftLimits(speed);
    //LiftTalon.set(ControlMode.PercentOutput, speed);
    moveLift(speed);

    return error;
  }

  public double moveLiftToPosition(LiftPosition position) {
    switch (position)
    {
      case FULLDOWN:
        return moveLiftWithEncoders(KLiftFullDown);
      case CARGO:
        return moveLiftWithEncoders(KLiftCargo);
      case SHIP:
        return moveLiftWithEncoders(KLiftShip);
      case FULLUP:
        return moveLiftWithEncoders(KLiftFullUp);
      default:
        //Should really throw an exception;
        return 1/0;
    }
  }
}
