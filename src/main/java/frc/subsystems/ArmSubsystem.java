package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.commands.Arm.ArmWithJoysticks;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import frc.commands.Arm.ArmStop;



public class ArmSubsystem extends Subsystem {
  /**
   * public static final int KArmMaster = 4;    ArmMaster is Right Arm
   * public static final int KArmSlave = 5;     ArmSlave is Left Arm
   * public static final double KArmSpeed = 1.0; 
   * 
   * private TalonSRX armMaster;
   * private VictorSPX armSlave;
  */
  public static final int KArmMaster = 4; 
  public static final int KArmSlave = 5;  
  public static final double KArmSpeed = 1; 
  public static final double KArmDeadZone = 1;

  public static final double KArmFullDown = 0; //650, 1035, 1650, 1910
  public static final double KArmLow = 1000;
  public static final double KArmMiddle = 1500;
  public static final double KArmHigh = 2000;
  public static final double KArmFullUp = 3000; 

  private static final double KP = 0.01;

  private TalonSRX armMaster;
  private TalonSRX armSlave;

  private DigitalInput rightLimit, leftLimit;

  public ArmSubsystem() {
    armMaster = new TalonSRX(KArmMaster); 
    armSlave = new TalonSRX(KArmSlave);

    armSlave.follow(armMaster);
    armSlave.setInverted(true);

    armMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    armMaster.getSensorCollection().setQuadraturePosition(0, 0);
    armSlave.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    armSlave.getSensorCollection().setQuadraturePosition(0, 0);
    
    rightLimit = new DigitalInput(7);
    leftLimit = new DigitalInput(6);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArmWithJoysticks());
  }
  
  public void moveArm(double speed) {
    armMaster.set(ControlMode.PercentOutput, speed);
    //armSlave.set(ControlMode.PercentOutput, speed);
  }

  public double moveArmWithEncoders(double position) {
    double error = position - armMaster.getSelectedSensorPosition();
    double speed = error * KArmSpeed * KP;

    if (speed > KArmSpeed)
      speed = KArmSpeed;
    else if (speed < -KArmSpeed)
      speed = -KArmSpeed;

    armMaster.set(ControlMode.PercentOutput, speed);

    return error;
  }

  public double resetArm() {
    return moveArmWithEncoders(KArmMiddle);
  }

  public int getRightArmEncoder() {
    //return liftMotor.getSelectedSensorPosition(0);
    return armMaster.getSelectedSensorPosition();
  }

  public void resetRightArmEncoder() {
    armMaster.getSensorCollection().setQuadraturePosition(0,0);
  }

  public int getLeftArmEncoder() {
    //return liftMotor.getSelectedSensorPosition(0);
    return armSlave.getSelectedSensorPosition();
  }

  public void resetLeftArmEncoder() {
    armSlave.getSensorCollection().setQuadraturePosition(0,0);
  }

  public boolean leftLimitClosed() {
    return !leftLimit.get();
  }

  public boolean rightLimitClosed() {
    return !rightLimit.get();
  }

  public void leftLimitReset() {
    if(armSlave.getSensorCollection().getQuadraturePosition() <= 500)
      armSlave.getSensorCollection().setQuadraturePosition(0, 0);
    else if(armSlave.getSensorCollection().getQuadraturePosition() >= 18500)
      armSlave.getSensorCollection().setQuadraturePosition(19000, 0);
  }

  public void rightLimitReset() {
    if(armMaster.getSensorCollection().getQuadraturePosition() <= 500)
      armMaster.getSensorCollection().setQuadraturePosition(0, 0);
    if(armMaster.getSensorCollection().getQuadraturePosition() >= 18500)
      armMaster.getSensorCollection().setQuadraturePosition(19000, 0);
  }
}
