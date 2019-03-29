package frc.subsystems;

import frc.commands.CameraStart;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cameraserver.CameraServerSharedStore;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Camera extends Subsystem
{
    //private static CameraServer cam;
    private final UsbCamera camera1;
    private final UsbCamera camera2;

    // private MjpegServer server;
    private final CameraServer server1;
	
	@Override
	protected void initDefaultCommand() 
	{
        //setDefaultCommand(new CameraStart());
	}

	public Camera() {
        // camera1 = new UsbCamera("USB Camera 0", 0);
        // camera2 = new UsbCamera("Camera 2", 1);

        server1 = CameraServer.getInstance();
        
        camera1 = new UsbCamera("USB Camera 0", 0);
        camera2 = new UsbCamera("USB Camera 1", 1);
        server1.addCamera(camera2);
        MjpegServer server = server1.addServer("serve_ USB Camera 1");
        server.setSource(camera2);
        CameraServerSharedStore.getCameraServerShared().reportUsbCamera(camera2.getHandle());
        
        // camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        // camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
    }

    public void startCameraStream() {
        // server1.startAutomaticCapture();
    }

    public void switchToCamera1() {
        // SmartDashboard.putString("Setting camera", "1");
        //server.setSource(camera1);
        
    }
    
    public void switchToCamera2() {
        // SmartDashboard.putString("Setting camera", "2");
        //server.setSource(camera2);
    }
}