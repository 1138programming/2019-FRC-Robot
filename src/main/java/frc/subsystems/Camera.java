package frc.subsystems;

// import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.opencv.videoio.VideoCapture;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;

public class Camera extends Subsystem
{
    //private static CameraServer cam;
    private UsbCamera camera1;
    private UsbCamera camera2;
    private VideoSink server;
	
	@Override
	protected void initDefaultCommand() 
	{
	}

	public Camera() {
        camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        server = CameraServer.getInstance().addServer("Switched camera");
        camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
    }
    
    public void switchToCamera2() {
        SmartDashboard.putString("Setting camera", "1");
        server.setSource(camera2);
    }

    public void switchToCamera1() {
        SmartDashboard.putString("Setting camera", "2");
        server.setSource(camera1);
    }
}