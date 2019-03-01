/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LiftWithJoysticks extends Command {
	public LiftWithJoysticks() {
		requires(Robot.LIFT_SUBSYSTEM);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double joystickValue = Robot.oi.getLeftXbox();

		Robot.LIFT_SUBSYSTEM.moveLift(joystickValue);
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
