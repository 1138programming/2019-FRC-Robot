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

  private TalonSRX liftMotor;

  public LiftSubsystem() {
    liftMotor = new TalonSRX(KLiftTalon); 
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LiftWithJoysticks());
  }
  
  public void moveLift(double speed) {
    liftMotor.set(ControlMode.PercentOutput, speed);
  }

  public void moveLiftWithEncoders(double position) {
    if(liftMotor.getSensorCollection().getQuadraturePosition() < position) {
      liftMotor.set(ControlMode.PercentOutput, KLiftSpeed);
    }
    else if(liftMotor.getSensorCollection().getQuadraturePosition() > position) {
      liftMotor.set(ControlMode.PercentOutput, -KLiftSpeed);
    }
    else {
      liftMotor.set(ControlMode.PercentOutput, 0);
    }
  }
}
