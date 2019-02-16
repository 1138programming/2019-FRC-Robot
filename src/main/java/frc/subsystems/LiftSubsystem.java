package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.Lift.LiftWithJoysticks;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class LiftSubsystem extends Subsystem {
  /**
   * public static final int KLiftTalon = 7; 
   * private TalonSRX liftMotor;
   */
  public static final int KLiftTalon = 7; 
  public static final double KLiftSpeed = 1.0; 

  public static final double KLiftFullDown = 0; //These numbers are arbitrary rn, we need to calc this
  public static final double KLiftCargo = 10000;
  public static final double KLiftShip = 12500; 

  private static final double KP = 0.00017;

  private TalonSRX liftMotor;

  public LiftSubsystem() {
    liftMotor = new TalonSRX(KLiftTalon); 
    liftMotor.setSensorPhase(true);
    liftMotor.setInverted(true);
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    liftMotor.getSensorCollection().setQuadraturePosition(0, 0);
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

    if (speed > 1.0)
      speed = 1.0;
    else if (speed < -1.0)
      speed = -1.0;

    SmartDashboard.putNumber("speed 2", speed);

    liftMotor.set(ControlMode.PercentOutput, speed);

    return error;
  }
}
