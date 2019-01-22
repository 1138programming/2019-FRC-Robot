package frc.subsystems;

// import frc.robot.OI;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.X_TableStop;
//import static org.junit.Assume.assumeNoException;
import edu.wpi.first.wpilibj.Spark;

public class X_TableSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  /**
   *  public static final int KX_Table = 10;
      public static final double KX_TableSpeed = 1.0;
   */
  public static final int KX_Table = 10;
  public static final double KX_TableSpeed = .8;

  private Spark X_Table; 

  public X_TableSubsystem() {
    X_Table = new Spark(KX_Table);
  }
  @Override
  public void initDefaultCommand() {
    //default command for a subsystem here.
    setDefaultCommand(new X_TableStop());
  }
  
  public void moveX_Table(/*double speed*/) {
    X_Table.set(KX_TableSpeed);
  }
}