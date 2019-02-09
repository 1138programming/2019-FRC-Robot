package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.commands.ArmWithJoysticks;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import frc.commands.ArmStop;



public class ArmSubsystem extends Subsystem {
  /**
   * public static final int KArmMaster = 4; 
   * public static final int KArmSlave = 5;  
   * public static final double KArmSpeed = 1.0; 
   * 
   * private TalonSRX armMaster;
   * private VictorSPX armSlave;
  */
  public static final int KArmMaster = 4; 
  public static final int KArmSlave = 5;  
  public static final double KArmSpeed = 1.0; 
  public static final double KArmDeadZone = 1;

  public static final double KArmFullDown = 0; //These numbers are arbitrary rn, we need to calc this
  public static final double KArmLow = 1000;
  public static final double KArmMiddle = 1500;
  public static final double KArmHigh = 2000;
  public static final double KArmFullUp = 3000; 

  private TalonSRX armMaster;
  private TalonSRX armSlave;

  public ArmSubsystem() {
    armMaster = new TalonSRX(KArmMaster); 
    armSlave = new TalonSRX(KArmSlave);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArmStop());
  }
  
  public void moveArm(double speed) {
    armMaster.set(ControlMode.PercentOutput, speed);
    armSlave.set(ControlMode.PercentOutput, speed);
  }

  public void moveArmWithEncoders(double position) {
    if(armMaster.getSensorCollection().getQuadraturePosition() < position) {
      armMaster.set(ControlMode.PercentOutput, KArmSpeed);
    }
    else if(armMaster.getSensorCollection().getQuadraturePosition() > position) {
      armMaster.set(ControlMode.PercentOutput, -KArmSpeed);
    }
    else {
      armMaster.set(ControlMode.PercentOutput, 0);
    }

    if(armSlave.getSensorCollection().getQuadraturePosition() < position) {
      armSlave.set(ControlMode.PercentOutput, -KArmSpeed);
    }
    else if(armSlave.getSensorCollection().getQuadraturePosition() > position) {
      armSlave.set(ControlMode.PercentOutput, KArmSpeed);
    }
    else {
      armSlave.set(ControlMode.PercentOutput, 0);
    }
  }

  public void resetArm() {
    if(armMaster.getSensorCollection().getQuadraturePosition() > KArmMiddle) {
      armMaster.set(ControlMode.PercentOutput, KArmSpeed);
    }
    else if(armMaster.getSensorCollection().getQuadraturePosition() <= KArmMiddle) {
      armMaster.set(ControlMode.PercentOutput, -KArmSpeed);
    }
    else {
      armMaster.set(ControlMode.PercentOutput, 0);
    }

    if(armSlave.getSensorCollection().getQuadraturePosition() > KArmMiddle) {
      armSlave.set(ControlMode.PercentOutput, KArmSpeed);
    }
    else if(armSlave.getSensorCollection().getQuadraturePosition() <= KArmMiddle) {
      armSlave.set(ControlMode.PercentOutput, -KArmSpeed);
    }
    else {
      armSlave.set(ControlMode.PercentOutput, 0);
    }
  }
/**
  public double getArm() {
    //return ArmSubsystem.armMaster;
  }
  */
}
