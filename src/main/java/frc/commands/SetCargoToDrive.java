package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;
import frc.commands.CarriageIntake;

public class SetCargoToDrive extends Command {
	public SetCargoToDrive() {
        requires(Robot.ARM_SUBSYSTEM);
        requires(Robot.COLLECTOR_SUBSYSTEM);
        requires(Robot.CARRIAGE_SUBSYSTEM);
        requires(Robot.LIFT_SUBSYSTEM);
    }

	@Override
	protected void initialize() {
        	Robot.ARM_SUBSYSTEM.moveArmWithEncoders(Robot.ARM_SUBSYSTEM.KArmMiddle);
        	Robot.LIFT_SUBSYSTEM.moveLiftWithEncoders(Robot.LIFT_SUBSYSTEM.KLiftShip);
	}

	@Override
	protected void execute() {
    }

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}