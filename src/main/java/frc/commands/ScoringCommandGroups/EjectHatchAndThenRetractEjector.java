/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands.ScoringCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.commands.Hatch.ScoreHatch;
import frc.commands.Hatch.AttainHatch;
import frc.commands.Hatch.RetractEjector;
import frc.robot.Robot;
import frc.commands.Wait;

public class EjectHatchAndThenRetractEjector extends CommandGroup {
  /**
   * Add your docs here.
   */
  public EjectHatchAndThenRetractEjector() {
    addSequential(new ScoreHatch());
    addSequential(new Wait(.5));
    addSequential(new RetractEjector());
  }
}
