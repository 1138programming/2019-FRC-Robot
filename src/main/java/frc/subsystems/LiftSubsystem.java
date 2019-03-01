package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.Lift.LiftWithJoysticks;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
//import frc.commands.Lift.LiftStop;

public class LiftSubsystem extends Subsystem {
  /** 
   * public static final int KLiftTalon = 7; 
   * private TalonSRX liftMotor;
   */
  public static final int KLiftTalon = 7; 
  public static final double KLiftSpeed = .5; 

  private DigitalInput topLimit, bottomLimit;

  public static final double KLiftFullDown = 0; 
  public static final double KLiftCargo = 24000;
  public static final double KLiftShip = 12500; //Haven't checked this one yet
  public static final int KLiftTopReset = 23500;
  public static final int KLiftBottomReset = 0;
  public static final double KMotorOffset = .05;

  private static final double KP = 0.00025;

  private TalonSRX liftMotor;

  public LiftSubsystem() {
    liftMotor = new TalonSRX(KLiftTalon); 
    liftMotor.setSensorPhase(true);
    liftMotor.setInverted(true);
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    liftMotor.getSensorCollection().setQuadraturePosition(0, 0);

    topLimit = new DigitalInput(9);
    bottomLimit = new DigitalInput(8);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LiftWithJoysticks());
  }
  
  public void moveLift(double speed) {
    liftMotor.set(ControlMode.PercentOutput, speed);
  }

  public int getLiftEncoder() {
    //return liftMotor.getSelectedSensorPosition(0);
    return liftMotor.getSelectedSensorPosition();
  }

  public void resetLiftEncoder() {
    liftMotor.getSensorCollection().setQuadraturePosition(0,0);
  }

  public double moveLiftWithEncoders(double position) {
    double error = position - liftMotor.getSensorCollection().getQuadraturePosition();
    SmartDashboard.putNumber("error", error);
    double speed = error * KLiftSpeed * KP;
    SmartDashboard.putNumber("speed", speed);

    if (speed > KLiftSpeed)
      speed = KLiftSpeed;
    else if (speed < -KLiftSpeed)
      speed = -KLiftSpeed;

    SmartDashboard.putNumber("speed 2", speed);

    speed = checkLimits(speed);
    liftMotor.set(ControlMode.PercentOutput, speed);

    return error;
  }

  public boolean topLimitClosed() {
    return !topLimit.get();
  }

  public boolean bottomLimitClosed() {
    return !bottomLimit.get();
  }

  public void topLimitReset() {
    liftMotor.getSensorCollection().setQuadraturePosition(KLiftTopReset, 0);
  }

  public void bottomLimitReset() {
    liftMotor.getSensorCollection().setQuadraturePosition(KLiftBottomReset, 0);
  }

  public double checkLimits(double targetSpeed) {
    if(topLimitClosed() == true) {
			if (targetSpeed > 0) 
				targetSpeed = 0;
			topLimitReset();
		} 
		else if(bottomLimitClosed() == true) {
			if (targetSpeed < 0)
				targetSpeed = 0;
			bottomLimitReset();
    }
    return targetSpeed;
  }
}
