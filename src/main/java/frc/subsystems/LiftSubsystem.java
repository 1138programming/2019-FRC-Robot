package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.Lift.LiftWithJoysticks;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class LiftSubsystem extends Subsystem {
  /**
   * public static final int KLiftTalon = 7; 
   * private TalonSRX liftMotor;
   */
  public static final int KLiftTalon = 7; 
  public static final double KLiftSpeed = 1.0; 

  public static final double KLiftFullDown = 0; //These numbers are arbitrary rn, we need to calc this
  public static final double KLiftCargo = 1500;
  public static final double KLiftShip = 3000; 

  private static final double KP = 0.01;

  private TalonSRX liftMotor;

  public LiftSubsystem() {
    liftMotor = new TalonSRX(KLiftTalon); 
    liftMotor.getSensorCollection().setQuadraturePosition(0,0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LiftWithJoysticks());
  }
  
  public void moveLift(double speed) {
    liftMotor.set(ControlMode.PercentOutput, speed);
  }

  public int getLiftEncoder() {
    return liftMotor.getSensorCollection().getQuadraturePosition();
  }

  public double moveLiftWithEncoders(double position) {
    double error = position - liftMotor.getSelectedSensorPosition();
    double speed = /*error */ KLiftSpeed /* KP*/;

    if (speed > 1.0)
      speed = 1.0;
    else if (speed < -1.0)
      speed = -1.0;

    liftMotor.set(ControlMode.PercentOutput, speed);

    return error;
  }
}
