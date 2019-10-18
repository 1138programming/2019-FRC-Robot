package frc.subsystems;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem
{
    // private static CameraServer cam;
    private final UsbCamera camera1;
    private final UsbCamera camera2;


    private MjpegServer mjpegServer;
    private MjpegServer mjpegServer2;
    private final CameraServer server;
    private final CameraServer server2;
	
	@Override
	protected   void initDefaultCommand() {
	}

	public Camera() {
        server = CameraServer.getInstance();
        server2 = CameraServer.getInstance();
        mjpegServer = server.addSwitchedCamera("Camera");
        mjpegServer2 = server2.addSwitchedCamera("Camera 2");

        camera1 = new UsbCamera("USB Camera 0", 0);
        camera2 = new UsbCamera("USB Camera 1", 1);

        mjpegServer.setSource(camera1);
        mjpegServer2.setSource(camera2);
    }
}