package frc.robot;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;

/**
    Imports go here. Imports are how we are able to call all of these different commands. 
    These commands are stored in libraries, which are added to the code by importing them. 
    No library, no knowledge of what it is supposed to do. 
*/

public class OI { 
// DON'T CHANGE ANY CONSTANTS OR DECLARATIONS UNLESS SPECIFICALLY STATED

/**
    Declarations! Declarations are where you, well, declare things! If something exists, then it needs to be declared 
    in the code. We give these things their identities through numbers, which are (usually) preset based on what it is. 
    For example, the Logitech Joysticks have to be declared like this, but another controller could be laid out 
    differently, like the Xbox Controller.
*/

//Controller Constants 
public static final int KLogitechController = 0;
public static final int KXboxController = 1;

//Dead Zones (adjust to what feels necessary, but this is the standard setting)
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

//Declarations
public Joystick logitech, xbox; //Controllers
public JoystickButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8; //Logitech Buttons
public JoystickButton btnA, btnB, btnX, btnY, btnLB, btnRB, btnStrt, btnLT, btnRT; //Xbox Buttons

public OI(){
    //Instantiating Time!
    /** 
        This is where you instantiate the buttons. Instantiation is where you tell these newly-introduced things 
        what they are going to be doing, usually by assigning them to create a new "instance" of themselves. 
        In this case, an instance is just a single occurence of whatever the thing is assigned to, so that when 
        it is plotted to a command, the command is run as an instance in the scheduler, instead of as a conglomerate 
        mess of commands.  
    */
    }

//Getters and Setters
/**
    This is where you create your Getters and Setters. As a policy, we declare all of our things to be private, 
    not public, meaning only this file can access and change them. Because of this, if we want another file to 
    be able to grab these values or change them, we have to make a getter that returns the value of the thing 
    and a setter that sets the value of the thing. We normally only use getters, but setters might also come up, 
    so it is worth noting. 
*/
}