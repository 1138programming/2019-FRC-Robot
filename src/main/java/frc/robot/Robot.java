package frc.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.hal.PDPJNI;

import frc.subsystems.DriveSubsystem;
import frc.subsystems.LiftSubsystem;
import frc.subsystems.ArmSubsystem;
import frc.subsystems.CarriageSubsystem; 
import frc.subsystems.CollectorSubsystem;
//import frc.subsystems.X_TableSubsystem;
import frc.subsystems.ClimbSubsystem;
import frc.subsystems.PneumaticsSubsystem;
import frc.subsystems.HatchSubsystem;
import frc.subsystems.Camera;
import frc.subsystems.PDP;

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
  public static ClimbSubsystem CLIMB_SUBSYSTEM = new ClimbSubsystem(); 
  //public static X_TableSubsystem X_TABLE_SUBSYSTEM = new X_TableSubsystem(); 
  public static PneumaticsSubsystem PNEMATICSSUBSYSTEM = new PneumaticsSubsystem();
  public static HatchSubsystem HATCH_SUBSYSTEM = new HatchSubsystem(); 
  public static OI oi;
  public static PDP pdp;
  public static Camera CAMERA = new Camera();

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    oi = new OI();
    //m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
    Robot.LIFT_SUBSYSTEM.resetLiftEncoder();
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
    LIFT_SUBSYSTEM.resetLiftEncoder();
    ARM_SUBSYSTEM.zeroLeftArmEncoder();
    ARM_SUBSYSTEM.zeroRightArmEncoder();
    pdp.voltageSpikeOccured = false;
    SmartDashboard.putString("Robot Encoders", "NOT ALIGNED");
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    SmartDashboard.putNumber("Lift Encoder", Robot.LIFT_SUBSYSTEM.getLiftEncoder());
    if(Robot.ARM_SUBSYSTEM.leftLimitClosed() == true && Robot.ARM_SUBSYSTEM.getLeftArmEncoder() == 0 && Robot.ARM_SUBSYSTEM.rightLimitClosed() == true &&Robot.ARM_SUBSYSTEM.getRightArmEncoder() == 0) {
      SmartDashboard.putString("Robot Encoders", "FULLY ALIGNED");
    }
  }

  @Override
  public void testPeriodic() {
  }
}
