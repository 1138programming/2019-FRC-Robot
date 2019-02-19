/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.commands.Climb.ClimbDown;

public class ClimbToTopHab extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ClimbToTopHab() {
    requires(Robot.CLIMB_SUBSYSTEM);
    requires(Robot.ARM_SUBSYSTEM);

    addSequential(new ClimbDown());
  }
}
