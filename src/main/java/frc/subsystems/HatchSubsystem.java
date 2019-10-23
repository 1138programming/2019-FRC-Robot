/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.Hatch.HatchOff;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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
	public static boolean isSame = false; 

	public static final double KHatchSpeed = 1.0; 

	public static final int KSolenoid3 = 3;
	public static final int KSolenoid6 = 6;
	public static final int KSolenoid4 = 4;
	public static final int KSolenoid5 = 5;

	public static final int KSparkMax1 = 10; 
	//public static final int KSparkMax2 = 2; 

	private DoubleSolenoid HatchSolenoid;
	// //private DoubleSolenoid YMechanism;

	private CANSparkMax HatchMotor1; 
	//private CANSparkMax HatchMotor2; 
	
	public HatchSubsystem() {
		HatchSolenoid = new DoubleSolenoid(KSolenoid3, KSolenoid6);
		HatchMotor1 = new CANSparkMax(KSparkMax1, CANSparkMaxLowLevel.MotorType.kBrushless);
		// HatchMotor2 = new CANSparkMax(KSparkMax2, MotorType.kBrushless);
		// YMechanism = new DoubleSolenoid(KSolenoid4, KSolenoid5);
	}

	@Override
  	public void initDefaultCommand() {
		setDefaultCommand(new HatchOff());
	}

	public void moveHatchMechanismToAttain() {
		HatchSolenoid.set(Value.kReverse);
		HatchMotor1.set(KHatchSpeed);
		//HatchMotor2.set(-desiredSpeed);
	}

	public void moveHatchMechanismToEject() {
		HatchSolenoid.set(Value.kForward);
		HatchMotor1.set(-KHatchSpeed);
		//HatchMotor2.set(-desiredSpeed);
	}

	public void HatchSolenoidForward() {
		HatchSolenoid.set(Value.kForward);
	}

	public void HatchSolenoidBackward() {
		HatchSolenoid.set(Value.kReverse);
	}

	public void HatchSolenoidOff() {
		HatchSolenoid.set(Value.kOff);
	}

	public void HatchMotorMove(double speed) {
		HatchMotor1.set(speed);
	}

	public void HatchMotorStop() {
		HatchMotor1.set(0);
	}
}