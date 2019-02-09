package frc.motionProfiling;

import frc.motionProfiling.Constants;
import frc.motionProfiling.TrajectoryExecutor;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;


/**
 * @author Zheyuan Hu
 * @version 1.0.0 This Command requires Robot.SUB_DRIVE_SUBSYSTEM
 */
public class TrajectoryCommand extends Command
{
	private TrajectoryExecutor trajectoryExecutor;
	private Trajectory leftTrajectory, rightTrajectory;
	
	// PID constants
	private double kP = 0.05, kI = 0, kD = 0;

	// MP constants
	private double maxVel = 20, maxAccel = 5, maxJerk = 70, dt = 0.05, width = 2.2083;

	// F-gains
	private double kF_left = 2.753552972, kF_right = 2.756520802;

	public TrajectoryCommand(Waypoint[] points)
	{
		requires(Robot.DRIVE_SUBSYSTEM);
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, dt, maxVel, maxAccel, maxJerk);
		Trajectory trajectory = Pathfinder.generate(points, config);
		TankModifier modifier = new TankModifier(trajectory);
		modifier.modify(width);
		leftTrajectory = modifier.getLeftTrajectory();
		rightTrajectory = modifier.getRightTrajectory();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Robot.DRIVE_SUBSYSTEM.resetEncoders();
		trajectoryExecutor = new TrajectoryExecutor(Robot.DRIVE_SUBSYSTEM.getBaseLeftFront(), Robot.DRIVE_SUBSYSTEM.getBaseRightFront(), leftTrajectory, rightTrajectory);

		Robot.DRIVE_SUBSYSTEM.getBaseLeftFront().config_kP(0, kP, Constants.kTimeoutMs);
        Robot.DRIVE_SUBSYSTEM.getBaseLeftFront().config_kI(0, kI, Constants.kTimeoutMs);
		Robot.DRIVE_SUBSYSTEM.getBaseLeftFront().config_kD(0, kD, Constants.kTimeoutMs);
        Robot.DRIVE_SUBSYSTEM.getBaseLeftFront().config_kF(0, kF_left, Constants.kTimeoutMs);

		Robot.DRIVE_SUBSYSTEM.getBaseRightFront().config_kP(0, kP, Constants.kTimeoutMs);
        Robot.DRIVE_SUBSYSTEM.getBaseRightFront().config_kI(0, kI, Constants.kTimeoutMs);
        Robot.DRIVE_SUBSYSTEM.getBaseRightFront().config_kD(0, kD, Constants.kTimeoutMs);
		Robot.DRIVE_SUBSYSTEM.getBaseRightFront().config_kF(0, kF_right, Constants.kTimeoutMs);
		
		Robot.DRIVE_SUBSYSTEM.resetEncoders();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		trajectoryExecutor.control();
		trajectoryExecutor.startMotionProfile();
		
		SetValueMotionProfile leftOutput = trajectoryExecutor.getLeftValue();
		SetValueMotionProfile rightOutput = trajectoryExecutor.getRightValue();

		Robot.DRIVE_SUBSYSTEM.setRightMotionControl(ControlMode.MotionProfile, rightOutput.value);
		Robot.DRIVE_SUBSYSTEM.setLeftMotionControl(ControlMode.MotionProfile, leftOutput.value);
		
		SmartDashboard.putNumber("MP Left Motor Output", Robot.DRIVE_SUBSYSTEM.getBaseLeftFront().getMotorOutputPercent());
		SmartDashboard.putNumber("MP Right Motor Output", Robot.DRIVE_SUBSYSTEM.getBaseRightFront().getMotorOutputPercent());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return trajectoryExecutor.getLeftValue() == SetValueMotionProfile.Hold || trajectoryExecutor.getRightValue() == SetValueMotionProfile.Hold;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
        Robot.DRIVE_SUBSYSTEM.setLeftMotionControl(ControlMode.PercentOutput, 0);
		Robot.DRIVE_SUBSYSTEM.setRightMotionControl(ControlMode.PercentOutput, 0);
		trajectoryExecutor.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
        super.interrupted();
        end();
	}
}