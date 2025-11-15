package org.firstinspires.ftc.teamcode.movement;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class MOV_cam {
    private static VisionPortal myVisionPortal;
    private static AprilTagProcessor myAprilTagProcessor;
    private static Telemetry telemetry;

    public static void vid(HardwareMap hardwareMap){
        myAprilTagProcessor = new AprilTagProcessor.Builder().build();

        VisionPortal.Builder myVisionPortalBuilder = new VisionPortal.Builder();
        myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "CAM"));
        myVisionPortalBuilder.addProcessor(myAprilTagProcessor);
        myVisionPortal = myVisionPortalBuilder.build();

    }

    public static Boolean is_ID(){
        List<AprilTagDetection> currentDetections = myAprilTagProcessor.getDetections();
        return !currentDetections.isEmpty(); // true if a tag *is* detected
    }
    public static String tag_ID() {
        List<AprilTagDetection> currentDetections = myAprilTagProcessor.getDetections();

        String APRT = "";

        for (AprilTagDetection tag : currentDetections) {
            telemetry.addData("Tag ID", tag.id);
            telemetry.update();
            if (tag.id == 21) {
                APRT = "GPP";
            } else if (tag.id == 22) {
                APRT = "PGP";
            } else if (tag.id == 23) {
                APRT = "ppg";
            }
            telemetry.addData("Position (x,y,z)", "%.2f, %.2f, %.2f",
                    tag.ftcPose.x, tag.ftcPose.y, tag.ftcPose.z);
            telemetry.update();
        }
        return APRT;
    }

    public void stopCamera() {
        if (myVisionPortal != null) {
            myVisionPortal.close();
        }
    }
}


