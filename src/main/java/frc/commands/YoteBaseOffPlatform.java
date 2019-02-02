package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.DriveSubsystem;;

public class YoteBaseOffPlatform extends Command
{
	public YoteBaseOffPlatform()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.DRIVE_SUBSYSTEM);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
        Robot.DRIVE_SUBSYSTEM.moveWithEncoders(DriveSubsystem.KYoteDistance);   
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
        return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{

	}
}
