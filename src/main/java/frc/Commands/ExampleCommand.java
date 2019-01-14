package frc.Commands;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/*
    Imports go here. Refer to OI.java for explanations.
*/

public class ExampleCommand extends Command {
    public ExampleCommand() {
        requires(Robot.EXAMPLE_SUBSYSTEM); 
        /**
        Requires is a system we use to prevent clashing of subsystems. What this means is that if you call 
        two commands, for example Arm Up and Arm Down, normally the robot would attempt to do both, which 
        would break the robot. Instead, we use a requires statement, which means that it can only be called 
        when this subsystem is free. If another command is using the subsystem, the scheduler interrupts the 
        actions of the initial command, sending it into the "interrupted" section, and the new command begins 
        to run. This way, we can never break the robot by calling contradictory commands. 
        */
    }

 // Called just before this Command runs the first time
 @Override
 protected void initialize() {
 }

 // Called repeatedly when this Command is scheduled to run
 @Override
 protected void execute() {
 }

 // Make this return true when you want to exit the loop, and make it return false if you don't want it to exit.
 @Override
 protected boolean isFinished() {
   return false;
 }

 // Called once after isFinished returns true
 @Override
 protected void end() {
 }

 // Called when another command which requires one or more of the same
 // subsystems is scheduled to run
 @Override
 protected void interrupted() {
 }
}