/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.subsystems.LiftSubsystem;
import frc.subsystems.LiftSubsystem.LiftPosition;
import frc.subsystems.ArmSubsystem.ArmPosition;

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

		// if (((Robot.ARM_SUBSYSTEM.getRightArmPosition() == ArmPosition.FULLUP) || 
        // (Robot.ARM_SUBSYSTEM.getLeftArmPosition() == ArmPosition.FULLUP)) && 
        //  ((Robot.LIFT_SUBSYSTEM.getLiftPosition() == LiftPosition.FULLUP) || 
        //  (Robot.LIFT_SUBSYSTEM.getLiftPosition() == LiftPosition.SHIP) || 
		//  (Robot.LIFT_SUBSYSTEM.getLiftPosition() == LiftPosition.CARGO) || 
		//  Robot.LIFT_SUBSYSTEM.getLiftPosition() == LiftPosition.UNKNOWN)) {
		// 		joystickValue = 0;
		//  }

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
