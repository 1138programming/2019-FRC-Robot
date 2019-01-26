/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Compressor;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class PneumaticsSubsystem extends Subsystem {
  	private Compressor pCompressor;

	public PneumaticsSubsystem()
	{
		pCompressor = new Compressor(0);
	}

	public void initDefaultCommand()
	{
		pCompressor.start();
	}
}
