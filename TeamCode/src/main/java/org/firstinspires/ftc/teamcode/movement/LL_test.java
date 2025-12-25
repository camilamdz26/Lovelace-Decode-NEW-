package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

@Autonomous
public class LL_test extends LinearOpMode {
    Limelight3A LL;

    @Override
    public void runOpMode() {
        LL = hardwareMap.get(Limelight3A.class, "LL");
        telemetry.setMsTransmissionInterval(11);


       while (opModeIsActive()){
        LL.pipelineSwitch(0);
        LL.start();
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
    }
}
