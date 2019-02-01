//@TODO Figure out XBox triggers, get Sparks MAX working, make Pneumatics and Camera subsystems, vision integration into 
//the hatches, the hatch commands themselves (with x_table), and all of the encoder-based stuff for the arm and lift positions 
//(and maybe climb but that's lower priority)

package frc.robot;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import frc.commands.ShiftDrive;
import frc.commands.X_TableCenter;
import frc.commands.ArmWithJoysticks;
import frc.commands.AttainHatch;
import frc.commands.CarriageIntake;
import frc.commands.CarriageOuttake;
import frc.commands.ClimbUp;
import frc.commands.ClimbDown;
import frc.commands.CollectorBackwards;
import frc.commands.CollectorForward;
import frc.commands.Diagnostic;
import frc.commands.ScoreHatch;
import frc.commands.AttainHatch;

public class OI {
  /**
   * Controller Constants 
   * public static final int KLogitechDrive = 0;
   * public static final int KXboxArms = 1;
   * 
   * DeadZone 
   * public static final double KDeadZone = 0.2; 
   * 
   * Logitech Button Constants 
   * public static final int KButton1 = 1;
   * public static final int KButton2 = 2;
   * public static final int KButton3 = 3;
   * public static final int KButton4 = 4;
   * public static final int KButton5 = 5;
   * public static final int KButton6 = 6;
   * public static final int KButton7 = 7;
   * public static final int KButton8 = 8;
   * 
   * Xbox Button Constants 
   * public static final int KButtonA = 1;
   * public static final int KButtonB = 2;
   * public static final int KButtonX = 3;
   * public static final int KButtonY = 4;
   * public static final int KLeftBumper = 5;
   * public static final int KRightBumper = 6;
   * public static final int KStartButton = 8;
   * public static final int KLeftTrigger = 9;
   * public static final int KRightTrigger = 10;
   * 
   * public Joystick logitech;
   * public XboxController xbox;
   * public JoystickButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8; // Logitech Button
   * public JoystickButton btnA, btnB, btnX, btnY, btnLB, btnRB, btnStrt, btnLT, btnRT; // Xbox Buttons
   */
  
  //Controller Constants 
  public static final int KLogitechDrive = 0;
  public static final int KXboxArms = 1;

  //DeadZone 
  public static final double KDeadZone = 0.2; 

  //Logitech Button Constants 
  public static final int KButton1 = 1;
	public static final int KButton2 = 2;
	public static final int KButton3 = 3;
	public static final int KButton4 = 4;
	public static final int KButton5 = 5;
	public static final int KButton6 = 6;
	public static final int KButton7 = 7;
	public static final int KButton8 = 8;

  //Xbox Button Constants 
  public static final int KButtonA = 1;
	public static final int KButtonB = 2;
	public static final int KButtonX = 3;
	public static final int KButtonY = 4;
	public static final int KLeftBumper = 5;
	public static final int KRightBumper = 6;
	public static final int KStartButton = 8;
	public static final int KLeftTrigger = 9;
  public static final int KRightTrigger = 10;

  public Joystick logitech;
  public XboxController xbox;
	public JoystickButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8; // Logitech Button
	public JoystickButton btnA, btnB, btnX, btnY, btnLB, btnRB, btnStrt, btnLT, btnRT; // Xbox Buttons

  public OI() {
    //Controllers 
    logitech = new Joystick(KLogitechDrive);
    xbox = new XboxController(KXboxArms);

    //Logitech Buttons
		btn1 = new JoystickButton(logitech, KButton1);
		btn2 = new JoystickButton(logitech, KButton2);
		btn3 = new JoystickButton(logitech, KButton3);
		btn4 = new JoystickButton(logitech, KButton4);
		btn5 = new JoystickButton(logitech, KButton5);
		btn6 = new JoystickButton(logitech, KButton6);
		btn7 = new JoystickButton(logitech, KButton7);
		btn8 = new JoystickButton(logitech, KButton8);

		//XBox Buttons
		btnA = new JoystickButton(xbox, KButtonA);
		btnB = new JoystickButton(xbox, KButtonB);
		btnX = new JoystickButton(xbox, KButtonX);
		btnY = new JoystickButton(xbox, KButtonY);
		btnLB = new JoystickButton(xbox, KLeftBumper);
		btnRB = new JoystickButton(xbox, KRightBumper);
		btnStrt = new JoystickButton(xbox, KStartButton);
		btnLT = new JoystickButton(xbox, KLeftTrigger);
    btnRT = new JoystickButton(xbox, KRightTrigger);
    
    //Button Assigned Commands 
    btn2.whenPressed(new Diagnostic());
    btn5.whenPressed(new ShiftDrive());
    btn7.whenPressed(new X_TableCenter());  //1. Sparks MAX not working right now. 2. Picking up hatches and scoring will be on btns 6 & 8. 3. Triggers on XBox aren't working
    btn6.whenPressed(new AttainHatch());
    btn8.whenPressed(new ScoreHatch());

    btnLB.whileHeld(new CollectorBackwards()); 
    btnRB.whileHeld(new CollectorForward()); 
    btnA.whileHeld(new CarriageIntake());
    btnB.whileHeld(new CarriageOuttake());
    btnX.whileHeld(new ClimbDown()); 
    btnY.whileHeld(new ClimbUp());
  }

  public double getRightAxis() {
    if(logitech.getThrottle() > KDeadZone || logitech.getThrottle() < -KDeadZone)
      return -logitech.getThrottle(); 
    else 
      return 0; 
  }

  public double getArcadeRightAxis() {
    if(logitech.getTwist() > KDeadZone || logitech.getTwist() < -KDeadZone)
      return -logitech.getTwist();
    else
      return 0;
  }

  public double getLeftAxis() {
    if(logitech.getY() > KDeadZone || logitech.getY() < -KDeadZone)
      return -logitech.getY(); 
    else 
      return 0; 
  }

  public double getRightXbox() {
    if(xbox.getY(Hand.kRight) > KDeadZone || xbox.getY(Hand.kRight) < -KDeadZone) 
      return -xbox.getY(Hand.kRight);
    else 
      return 0;
  }

  public double getLeftXbox() {
    if(xbox.getY(Hand.kLeft) > KDeadZone || xbox.getY(Hand.kLeft) < -KDeadZone) 
      return -xbox.getY(Hand.kLeft);
    else 
      return 0;
  }

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}