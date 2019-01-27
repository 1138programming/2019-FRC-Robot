/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.HatchDisengage;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class HatchSubsystem extends Subsystem {
	/**
	 * public static final int KSolenoid1 = 3;
	 * public static final int KSolenoid2 = 4;
	 * 
	 * private DoubleSolenoid Hatch;
	*/
	public static final int KSolenoid3 = 3;
  	public static final int KSolenoid4 = 4;

	private DoubleSolenoid Hatch;
	 
	public HatchSubsystem() {
		Hatch = new DoubleSolenoid(KSolenoid3, KSolenoid4);
	 }
	@Override
  	public void initDefaultCommand() {
		setDefaultCommand(new HatchDisengage());
	}
	public void moveHatch(boolean on) {
		
	}
}
