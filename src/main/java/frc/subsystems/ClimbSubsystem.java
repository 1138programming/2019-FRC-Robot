package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.Climb.ClimbReset;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.sensors.PigeonIMU;

public class ClimbSubsystem extends Subsystem {
  /**
   * public static final int KClimb = 6; public static final double KClimbSpeed =
   * 1.0;
   * 
   * Rate for arm in relation to climb: .3239294403
   */

  public static final double KClimbSpeed = 1.0;

  private final TalonSRX ClimbTalon; 
  private static final int KClimbTalon = 6;

  private final DigitalInput TopClimbLimit, BottomClimbLimit;
  private static final int KTopClimbLimit = 4;
  private static final int KBottomClimbLimit = 3;

   // Pigeon IMU config
   public final PigeonIMU Pigeon;
   private static final int KPigeon = 6;

  public ClimbSubsystem() {
    ClimbTalon = new TalonSRX(KClimbTalon);

    TopClimbLimit = new DigitalInput(KTopClimbLimit);
    BottomClimbLimit = new DigitalInput(KBottomClimbLimit);
    
    Pigeon = new PigeonIMU(KPigeon);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ClimbReset());
  }
  
  private boolean topLimitClosed() {
    return !TopClimbLimit.get();
  }

  private boolean bottomLimitClosed() {
    return !BottomClimbLimit.get();
  }

  private double enforceLimits(double desiredSpeed) {
    if(topLimitClosed() && desiredSpeed > 0) 
      desiredSpeed = 0;
    else if(bottomLimitClosed() && desiredSpeed < 0) 
      desiredSpeed = 0;

    return desiredSpeed;
  }

  public void moveClimb(double speed) {
    double desiredSpeed = speed;
    enforceLimits(desiredSpeed);
    ClimbTalon.set(ControlMode.PercentOutput, desiredSpeed);
  }

  public double[] getGyroValues() {
    double ypr_value[];
    ypr_value = new double[3];
    Pigeon.getYawPitchRoll(ypr_value);
    return ypr_value;
  }
}