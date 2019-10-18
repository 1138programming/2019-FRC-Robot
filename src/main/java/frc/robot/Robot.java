package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.Lift.LiftStop;
import frc.subsystems.ArmSubsystem;
import frc.subsystems.Camera;
import frc.subsystems.CarriageSubsystem;
import frc.subsystems.CollectorSubsystem;
import frc.subsystems.DriveSubsystem;
import frc.subsystems.HatchSubsystem;
import frc.subsystems.LiftSubsystem;
import frc.subsystems.PDP;
import frc.subsystems.PneumaticsSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static DriveSubsystem DRIVE_SUBSYSTEM = new DriveSubsystem();
  public static ArmSubsystem ARM_SUBSYSTEM = new ArmSubsystem();
  public static CarriageSubsystem CARRIAGE_SUBSYSTEM = new CarriageSubsystem();
  public static LiftSubsystem LIFT_SUBSYSTEM = new LiftSubsystem();
  public static CollectorSubsystem COLLECTOR_SUBSYSTEM = new CollectorSubsystem();
  public static PneumaticsSubsystem PNEMATICSSUBSYSTEM = new PneumaticsSubsystem();
  public static HatchSubsystem HATCH_SUBSYSTEM = new HatchSubsystem(); 
  public static OI oi;
  public static PDP pdp;
  public static Camera CAMERA = new Camera();

  public static final boolean useDualArmPID = false;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    oi = new OI();
    pdp = new PDP();
    Robot.LIFT_SUBSYSTEM.setLiftEncoder(0);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    LIFT_SUBSYSTEM.setLiftEncoder(0);
    ARM_SUBSYSTEM.setLeftArmEncoder(0);
    ARM_SUBSYSTEM.setRightArmEncoder(0);
    if (Robot.ARM_SUBSYSTEM.inStartPos()) {
      SmartDashboard.putString("Robot Encoders", "FULLY ALIGNED - READY TO GO");
    }
    pdp.voltageSpikeOccured = false;
    ARM_SUBSYSTEM.lock();      
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    if (Robot.ARM_SUBSYSTEM.inStartPos()) {
      SmartDashboard.putString("Robot Encoders", "FULLY ALIGNED - READY TO GO");
    }
    Scheduler.getInstance().run();    
  }

  @Override
  public void testPeriodic() {
  }
}
