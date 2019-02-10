/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.Hatch.HatchOff;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

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
	public static final int KSolenoid5 = 5;

	private Solenoid HatchMechanism;
	private DoubleSolenoid YMechanism;
	 
	public HatchSubsystem() {
		HatchMechanism = new Solenoid(KSolenoid3);
		YMechanism = new DoubleSolenoid(KSolenoid4, KSolenoid5);
	}

	@Override
  	public void initDefaultCommand() {
		setDefaultCommand(new HatchOff());
	}

	public void moveHatchMechanism(boolean status) {
		if(status != HatchMechanism.get()) {
			HatchMechanism.set(status);
		}
		else {
		}
	}

	public void YMechanismForward() {
		YMechanism.set(Value.kForward);
	}

	public void YMechanismBackward() {
		YMechanism.set(Value.kReverse);
	}

	public void YMechanismOff() {
		YMechanism.set(Value.kOff);
	}
}
