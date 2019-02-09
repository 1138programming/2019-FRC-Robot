package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.ArmSubsystem;
import frc.commands.CarriageIntake;
import frc.robot.OI;
import edu.wpi.first.wpilibj.GenericHID;

public class CollectCargo extends Command {
	public CollectCargo() {
        requires(Robot.ARM_SUBSYSTEM);
        requires(Robot.COLLECTOR_SUBSYSTEM);
        requires(Robot.CARRIAGE_SUBSYSTEM);
        requires(Robot.LIFT_SUBSYSTEM);
    }

	@Override
	protected void initialize() {
        Robot.ARM_SUBSYSTEM.moveArmWithEncoders(Robot.ARM_SUBSYSTEM.KArmMiddle);
        Robot.LIFT_SUBSYSTEM.moveLiftWithEncoders(Robot.LIFT_SUBSYSTEM.KLiftFullDown);
	}

	@Override
	protected void execute() {
        Robot.COLLECTOR_SUBSYSTEM.moveCollector(-Robot.COLLECTOR_SUBSYSTEM.KCollecterSpeed);
        Robot.CARRIAGE_SUBSYSTEM.moveCarriage(-Robot.CARRIAGE_SUBSYSTEM.KCarriageSpeed);
	}

	@Override
	protected boolean isFinished() {
        return !Robot.oi.btn5Stick.get();
	}

	@Override
	protected void end() {
        Robot.LIFT_SUBSYSTEM.moveLiftWithEncoders(Robot.LIFT_SUBSYSTEM.KLiftCargo);
        Robot.ARM_SUBSYSTEM.moveArmWithEncoders(Robot.ARM_SUBSYSTEM.KArmHigh);
	}

	@Override
	protected void interrupted() {
	}
}