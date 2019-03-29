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
	public static final int KSolenoid2 = 2;		//Grabber
	public static final int KSolenoid3 = 3;
	public static final int KSolenoid4 = 4;		//Ejector
	public static final int KSolenoid5 = 5;

	private DoubleSolenoid Grabber;
	private DoubleSolenoid Ejector;
	 
	public HatchSubsystem() {
		Grabber = new DoubleSolenoid(KSolenoid2, KSolenoid3);
		Ejector = new DoubleSolenoid(KSolenoid4, KSolenoid5);
	}

	@Override
  	public void initDefaultCommand() {
		setDefaultCommand(new HatchOff());
	}

	public void EjectorForward() {
		Ejector.set(Value.kForward);
	}

	public void EjectorReverse() {
		Ejector.set(Value.kReverse);
	}

	public void EjectorOff() {
		Ejector.set(Value.kOff);
	}

	public void GrabberForward() {
		Grabber.set(Value.kForward);
	}

	public void GrabberReverse() {
		Grabber.set(Value.kReverse);
	}

	public void GrabberOff() {
		Grabber.set(Value.kOff);
	}
}
