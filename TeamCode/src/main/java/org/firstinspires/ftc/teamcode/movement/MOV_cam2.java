package org.firstinspires.ftc.teamcode.movement;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class MOV_cam2 {
    private static VisionPortal myVisionPortal;
    private static AprilTagProcessor myAprilTagProcessor;

    public static void video(HardwareMap hardwareMap, Limelight3A LL, Telemetry telemetry){
        telemetry.setMsTransmissionInterval(11);
        LL.pipelineSwitch(0);
        LL.start();
    }

    public static void LL_stats(Telemetry telemetry, Limelight3A LL) {
        LLStatus status = LL.getStatus();
        telemetry.addData("Name", "%s",
                status.getName());
        telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(), (int) status.getFps());
        telemetry.addData("Pipeline", "Index: %d, Type: %s",
                status.getPipelineIndex(), status.getPipelineType());

        LLResult result = LL.getLatestResult();
        if (result.isValid()) {
            // Access general information
            Pose3D botpose = result.getBotpose();
            double captureLatency = result.getCaptureLatency();
            double targetingLatency = result.getTargetingLatency();
            double parseLatency = result.getParseLatency();
            telemetry.addData("LL Latency", captureLatency + targetingLatency);
            telemetry.addData("Parse Latency", parseLatency);
            telemetry.addData("PythonOutput", java.util.Arrays.toString(result.getPythonOutput()));

            telemetry.addData("tx", result.getTx());
            telemetry.addData("txnc", result.getTxNC());
            telemetry.addData("ty", result.getTy());
            telemetry.addData("tync", result.getTyNC());

            telemetry.addData("Botpose", botpose.toString());

            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fr : fiducialResults) {
                telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
            }
        }
    }

    public static String tag_ID(Limelight3A LL,Telemetry telemetry) {
        LLResult result = LL.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fr : fiducialResults){
                telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
            }

        String APRT = "";

        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            telemetry.addData("Tag ID", fr.getFiducialId());
            telemetry.update();
            if (fr.getFiducialId() == 21) {
                APRT = "GPP";
            } else if (fr.getFiducialId() == 22) {
                APRT = "PGP";
            } else if (fr.getFiducialId() == 23) {
                APRT = "PPG";
            } else{
                //MOVE AND TRY AGAIN... WRITE CODE FOR THIS LATER....
            }
            telemetry.addData("Position (x,y,z)", "%.2f, %.2f, %.2f",
                    fr.getTargetXDegrees(), fr.getTargetYDegrees());
            telemetry.update();
        }
        return APRT;
    }

    public void stopCamera(Limelight3A LL) {
        LL.stop();
    }
}


