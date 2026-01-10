package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class LL_test extends LinearOpMode {
    Limelight3A LL;

    public void runOpMode() {
        LL = hardwareMap.get(Limelight3A.class, "LL");
        telemetry.setMsTransmissionInterval(11);
        LL.pipelineSwitch(0);

        waitForStart();
        LL.start();
        while (opModeIsActive()) {

            // test 1: is it working
            /*

            LLStatus status = LL.getStatus();
        telemetry.addData("Name", "%s",
                status.getName());
        telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(), (int) status.getFps());
        telemetry.addData("Pipeline", "Index: %d, Type: %s",
                status.getPipelineIndex(), status.getPipelineType());

             */

            // test 2: can it pick up april tag
            /*

            LLResult result = LL.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
        for (LLResultTypes.FiducialResult fr : fiducialResults){
            telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
        }
             */

            // test 3: can it sense distance

            /*

            LLResult result = LL.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
        for (LLResultTypes.FiducialResult fr : fiducialResults){
            telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
        }

        String APRT = "";

        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            double area = fr.getTargetArea();
             */

        }
    }
}
