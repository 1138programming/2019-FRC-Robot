package frc.subsystems;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Camera extends Subsystem
{
    //private static CameraServer cam;
    private final UsbCamera camera1;
    private final UsbCamera camera2;

    //private MjpegServer server;
    private final MjpegServer server1;
	
	@Override
	protected void initDefaultCommand() 
	{
	}

	public Camera() {
        camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera2 = CameraServer.getInstance().startAutomaticCapture(1);

        server1 = new MjpegServer("Server 1", 1181);
        server1.setSource(camera1);  //Default to camera 1
        
        camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
    }

    public void switchToCamera1() {
        SmartDashboard.putString("Setting camera", "1");
        server1.setSource(camera1);
    }
    
    public void switchToCamera2() {
        SmartDashboard.putString("Setting camera", "2");
        server1.setSource(camera2);
    }
}