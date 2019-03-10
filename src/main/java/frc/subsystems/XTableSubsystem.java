package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.commands.X_Table.X_TableCenter;

public class XTableSubsystem extends Subsystem {
  /**
   * public static final int KX_Table = 10;
   * public static final double KX_TableSpeed = .8;
   * private Spark X_Table; 
   */
  public static final int KX_Table = 10;
  public static final int KHatchServo = 0;
  public static final double KX_TableSpeed = .8;

  private TalonSRX X_Table; 
  private Servo HatchServo;

  public XTableSubsystem() {
    X_Table = new TalonSRX(KX_Table);
    HatchServo = new Servo(KHatchServo);
  } 

  public void moveX_Table(double angle) {
    //X_Table.set(ControlMode.PercentOutput, speed);
    HatchServo.setAngle(angle);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new X_TableCenter());
  }
}