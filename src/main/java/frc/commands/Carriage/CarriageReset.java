package frc.commands.Carriage;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.subsystems.CarriageSubsystem;

public class CarriageReset extends Command {
	public CarriageReset() {
		requires(Robot.CARRIAGE_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.CARRIAGE_SUBSYSTEM.moveCarriage(0);
		SmartDashboard.putBoolean("Ball in carraige: ", Robot.CARRIAGE_SUBSYSTEM.bumpSwitchClosed());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
