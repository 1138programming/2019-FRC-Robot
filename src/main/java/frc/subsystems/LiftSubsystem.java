package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.LiftWithJoysticks;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class LiftSubsystem extends Subsystem {
  public static final int KLiftTalon = 7; 

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
}
