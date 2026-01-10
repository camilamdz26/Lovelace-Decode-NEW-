package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

@TeleOp (name="TELEOPV")
@Configurable
public class final_teleop extends LinearOpMode {
    DcMotor BR;
    DcMotor BL;
    DcMotor FL;
    DcMotor FR;
    DcMotor intake;
    DcMotorEx outake;
    DcMotorEx outake2;
    CRServo transferR;
    CRServo transferL;
    Limelight3A LL;
    final double h = 18.25 ; //inch
    // height between tag and limelight 0.26 meters
    final double theta = 35; //degrees//angle of camera ( 35 degrees
    double d = 26.02; //at ty = 0

    double r;

    public void runOpMode() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();

        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        FL = hardwareMap.get(DcMotor.class, "FL");
        FR = hardwareMap.get(DcMotor.class, "FR");

        outake = hardwareMap.get(DcMotorEx.class, "outake");
        outake2 = hardwareMap.get(DcMotorEx.class, "outake2");

        LL = hardwareMap.get(Limelight3A.class, "LL");

        telemetry.setMsTransmissionInterval(11);

        LL.pipelineSwitch(0);

        /*
         * Starts polling for data.  If you neglect to call start(), getLatestResult() will return null.
         */
        LL.start();
        LLResult result = null;
        //double limelightTa = 0;

        outake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outake2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        outake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        outake2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        outake.setDirection(DcMotorSimple.Direction.REVERSE);
        outake2.setDirection(DcMotorSimple.Direction.FORWARD);

        outake.setVelocityPIDFCoefficients(200, 0, 0, 14);
        outake2.setVelocityPIDFCoefficients(200, 0, 0, 14);

        intake = hardwareMap.get(DcMotor.class, "intake");
        transferR = hardwareMap.get(CRServo.class, "transferR"); //the robots right
        transferL = hardwareMap.get(CRServo.class, "transferL");

        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        while (opModeIsActive()) {

            result = LL.getLatestResult();
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
            }
            double velocity = (-0.0238008 * (d*d))+(9.37752 * d)+581.64844;

            r = Math.hypot(-gamepad1.left_stick_y, gamepad1.left_stick_x);
            double robotAngle = Math.atan2(gamepad1.left_stick_x, -gamepad1.left_stick_y) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            final double v4 = r * Math.cos(robotAngle) - rightX;
            final double v3 = r * Math.sin(robotAngle) - rightX; //might have to change based on which ones
            final double v2 = r * Math.sin(robotAngle) + rightX; //were forward/backward last year, which was fr
            final double v1 = r * Math.cos(robotAngle) + rightX;

            BR.setPower(Range.clip(-v4, -.85D, .85D));
            BL.setPower(Range.clip(-v3, -.85D, .85D));
            FR.setPower(Range.clip(-v2, -.85D, .85D));
            FL.setPower(Range.clip(-v1, -.85D, .85D));

            //everything else in if statements here

            if(gamepad1.right_bumper){ //intake
                intake.setPower(-1);
            }
            if(gamepad1.left_bumper) { //stop intake
                intake.setPower(0);
            }
            if(gamepad1.x){ //reverse intake
                intake.setPower(1);
            }

            if (gamepad2.left_bumper){
                outake.setVelocity(velocity);
                outake2.setVelocity(velocity);
            }
            if(gamepad2.right_bumper){ //outake close
                outake.setPower(.5);
                outake2.setPower(.5);

//                outake.setVelocity(TPS);
//                outake2.setVelocity(TPS);
            }
            if(gamepad2.x){ //outake off
                outake.setPower(0);
                outake2.setPower(0);
            }
            if(gamepad2.y){ //transfer
                transferL.setPower(1);
                transferR.setPower(-1);
            }
            if(gamepad2.b){ //transfer off
                transferL.setPower(0);
                transferR.setPower(0);
            }
            if(gamepad2.a){ //reverse transfer
                transferL.setPower(-1);
                transferR.setPower(1);
            }

            double outakeVelocity = outake.getVelocity();

            //telemetry.addData("Outake Pos", outake.getCurrentPosition());
            telemetry.addData("Outake Actual Vel", outakeVelocity);
            telemetry.addData("ty", result.getTy());
            telemetry.addData("distance",d);
            telemetry.addData("calculated velocity",velocity);

            telemetry.update();
        }
    }
}
