package org.firstinspires.ftc.teamcode.teleop;

import static java.lang.Math.log;
import static java.lang.Math.tan;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp

public class sensing_teleop extends LinearOpMode {
    DcMotor BR;
    DcMotor BL;
    DcMotor FL;
    DcMotor FR;
    DcMotor intake;
    DcMotorEx outake;
    DcMotorEx outake2;
    CRServo transferR;
    CRServo transferL;
    double TPS = 0;

    Limelight3A LL;

    double r;

    double tagHeight = 28;
    double cameraHeight = 10.2;
    double cameraAngle = 20;

    public void runOpMode() {


        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        FL = hardwareMap.get(DcMotor.class, "FL");
        FR = hardwareMap.get(DcMotor.class, "FR");

        LL = hardwareMap.get(Limelight3A.class, "LL");


        outake = hardwareMap.get(DcMotorEx.class, "outake");
        outake2 = hardwareMap.get(DcMotorEx.class, "outake2");

        intake = hardwareMap.get(DcMotor.class, "intake");
        transferR = hardwareMap.get(CRServo.class, "transferR"); //the robots right
        transferL = hardwareMap.get(CRServo.class, "transferL");


        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);



        waitForStart();

        LL.start();
        LL.pipelineSwitch(0);   // Pipeline 0 (usually AprilTags)

        while (opModeIsActive()) {

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
            telemetry.update();




            //everything else in if statements here

            outake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            outake2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            if(gamepad1.right_bumper){ //intake
                intake.setPower(-1);
            }
            if(gamepad1.left_bumper) { //stop intake
                intake.setPower(0);
            }
            if(gamepad1.x){ //reverse intake
                intake.setPower(1);
            }

            LLResult result = LL.getLatestResult();
            double Tx = result.getTx();
            double Ty = result.getTy();

            double distance = (tagHeight - cameraHeight) / tan(cameraAngle + Ty);


            TPS = mx+b;
            /*
            m = average slope, find between several points
            b = idk
            x = distance
             */


            if(gamepad2.left_bumper){ //outake far
                outake.setVelocity(TPS);
                outake2.setVelocity(TPS);
            }
            if(gamepad2.right_bumper){ //outake close
                outake.setVelocity(TPS);
                outake2.setVelocity(TPS);
            }
            if(gamepad2.x){ //outake off
                outake.setVelocity(0);
                outake2.setVelocity(0);
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
        }



    }


}