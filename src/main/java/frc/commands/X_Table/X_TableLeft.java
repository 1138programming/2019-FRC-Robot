package frc.commands.X_Table;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.XTableSubsystem;

public class X_TableLeft extends Command
{
	public X_TableLeft()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.X_TABLE_SUBSYSTEM);
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
		Robot.X_TABLE_SUBSYSTEM.moveX_Table(180);
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
