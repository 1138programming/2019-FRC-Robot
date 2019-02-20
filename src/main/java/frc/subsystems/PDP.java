/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.RegulateVoltageSpike;
import edu.wpi.first.hal.PDPJNI;


/**
 * Add your docs here.
 */
public class PDP extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static final int KPDP = 0;
  private final PowerDistributionPanel PDPInstance;
  public static boolean voltageSpikeOccured = false;
  public static final double voltageThreshold = 8;

  public PDP() 
  {
    PDPInstance = new PowerDistributionPanel(KPDP);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new RegulateVoltageSpike());
  }

  public void voltageSpikeRegulation() { //ASK PAULINE OR BASI 
    if(PDPInstance.getVoltage() <= voltageThreshold)
      voltageSpikeOccured = true;
  }
}
