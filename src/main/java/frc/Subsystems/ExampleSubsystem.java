package frc.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.Commands.ExampleCommand;
import frc.robot.OI;
import frc.robot.Robot;

/*
    Imports go here. See OI.java to find descriptions for all of these. 
*/

public class ExampleSubsystem extends Subsystem {

/*
    Declarations go here. 
*/

public ExampleSubsystem()
{
    /*
        Instantiations go here.
    */
}

@Override
public void initDefaultCommand() {
    setDefaultCommand(new ExampleCommand());
}

/*
    Functions go here. Functions are called by commands to perform actions, based upon what occurs inside the function. 
*/

}