package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.X_TableStop;
import edu.wpi.first.wpilibj.Spark;

public class X_TableSubsystem extends Subsystem {
  public static final int KX_Table = 10;
  public static final double KX_TableSpeed = .8;

  private Spark X_Table; 

  public X_TableSubsystem() {
    X_Table = new Spark(KX_Table);
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new moveX_Table(0));
  }
  
  public void moveX_Table(double speed) {
    X_Table.set(speed);
  }
}