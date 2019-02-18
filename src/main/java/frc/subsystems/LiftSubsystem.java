package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.Lift.LiftWithJoysticks;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.commands.Lift.LiftStop;

public class LiftSubsystem extends Subsystem {
  /** 
   * public static final int KLiftTalon = 7; 
   * private TalonSRX liftMotor;
   */
  public static final double KLiftSpeed = .75; 

  public static enum LiftPosition { UNKNOWN, FULLDOWN, CARGO, SHIP, FULLUP }

  private DigitalInput topLimit, bottomLimit;

  private static final double KLiftFullDown = 0; 
  private static final double KLiftShip = 14950; //Haven't checked this one yet
  private static final double KLiftCargo = 23500;
  private static final double KLiftFullUp = 23500;
  private static final int KLiftTopEncoderPosition = 23500;
  private static final int KLiftBottomEncoderPosition = 0;
  private static final double KLiftTopLimitHuntRange = 23000; //??
  private static final double KLiftBottomLimitHuntRange = 500;

  public static final double KMotorOffset = 0.05;

  private static final double KP = 0.13;

  private TalonSRX liftMotor;
  private static final int KLiftTalon = 7; 
  private static final int KTopLimit = 9;
  private static final int KBottomLimit = 8;

  public LiftSubsystem() {
    liftMotor = new TalonSRX(KLiftTalon); 
    topLimit = new DigitalInput(KTopLimit);
    bottomLimit = new DigitalInput(KBottomLimit);

    liftMotor.setSensorPhase(true);
    liftMotor.setInverted(true);
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    liftMotor.getSensorCollection().setQuadraturePosition(0, 0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LiftWithJoysticks());
  }

  public int getLiftEncoder() {
    //return liftMotor.getSelectedSensorPosition(0);
    return liftMotor.getSelectedSensorPosition();
  }

  public LiftPosition getLiftPosition() {
    if(getLiftEncoder() <= KLiftTopLimitHuntRange) {
      return LiftPosition.FULLUP;
    }

    if(getLiftEncoder() >= KLiftBottomLimitHuntRange) {
      return LiftPosition.FULLDOWN;
    }
    else {
      return LiftPosition.UNKNOWN;
    }
  }

  public void setLiftEncoder(int position) {
    liftMotor.getSensorCollection().setQuadraturePosition(position, 0);
  }

  public void zeroLiftEncoder() {
    liftMotor.getSensorCollection().setQuadraturePosition(0,0);
  }

  public boolean topLimitClosed() {
    return !topLimit.get();
  }

  public boolean bottomLimitClosed() {
    return !bottomLimit.get();
  }

  public void resetTopLimit() {
    setLiftEncoder((int)KLiftTopEncoderPosition);
  }

  public void resetBottomLimit() {
    setLiftEncoder((int)KLiftBottomEncoderPosition);
  }

  public double checkLiftLimits(double targetSpeed) {
    if(topLimitClosed()) {
			if (targetSpeed > 0) 
				targetSpeed = 0;
			resetTopLimit();
		} 
		else if(bottomLimitClosed()) {
			if (targetSpeed < 0)
				targetSpeed = 0;
			resetBottomLimit();
    }

    return targetSpeed;
  }

  public void moveLift(double speed) {
    liftMotor.set(ControlMode.PercentOutput, speed);
  }

  private double moveLiftWithEncoders(double position) {
    double error = position - getLiftEncoder();
    double speed = error * KLiftSpeed * KP;
    if (speed > KLiftSpeed)
      speed = KLiftSpeed;
    else if (speed < -KLiftSpeed)
      speed = -KLiftSpeed;

    speed = checkLiftLimits(speed);
    liftMotor.set(ControlMode.PercentOutput, speed);

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
        return 0;
    }
  }
}
