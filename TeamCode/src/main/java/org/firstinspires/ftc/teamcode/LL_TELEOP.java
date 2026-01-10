package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.List;

public class LL_TELEOP extends LinearOpMode {

    Limelight3A LL;
    DcMotorEx outake2;
    DcMotorEx outake;
    CRServo transferR;
    CRServo transferL;

    final double h = 18.25 ; //inch
    // height between tag and limelight 0.26 meters
    final double theta = 35; //degrees//angle of camera ( 35 degrees
    double d = 14.5; //at ty = 0
    double velocity = //avg

    public void runOpMode() {
        LL = hardwareMap.get(Limelight3A.class, "LL");
        telemetry.setMsTransmissionInterval(11);
        LL.pipelineSwitch(0);

        waitForStart();
        LL.start();

        while (opModeIsActive()) {

            LLResult result = LL.getLatestResult();
            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fr : fiducialResults) {
                telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
                double id = fr.getFiducialId();
                if (id == 20) {
                    double ty20 = fr.getTargetYDegrees();
                    double m = Math.tan(Math.toRadians(ty20 + theta));
                    double d = h / m;
                } else if (id == 24) {
                    double ty24 = fr.getTargetYDegrees();
                    double m = Math.tan(Math.toRadians(ty24 + theta));
                    double d = h / m;
                }
                double velocity = (4.86726 * d) + 776.32743;
            }

            if (gamepad2.left_bumper){
               outake.setVelocity(velocity);
               outake2.setVelocity(velocity);
            }

        }
        }
    }
