package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.hardware.movement.backward;
import static org.firstinspires.ftc.teamcode.hardware.movement.forward;
import static org.firstinspires.ftc.teamcode.hardware.movement.rotateLeft;
import static org.firstinspires.ftc.teamcode.hardware.movement.rotateRight;
import static org.firstinspires.ftc.teamcode.movement.MOV_cam.tag_ID;
import static org.firstinspires.ftc.teamcode.movement.movement.backward;
import static org.firstinspires.ftc.teamcode.movement.movement.forward;
import static org.firstinspires.ftc.teamcode.movement.movement.left;
import static org.firstinspires.ftc.teamcode.movement.movement.right;
import static org.firstinspires.ftc.teamcode.movement.movement.rotateLeft;
import static org.firstinspires.ftc.teamcode.movement.movement.rotateRight;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.movement.MOV_cam;

import java.util.Objects;

@Autonomous
public class red_close_mov extends LinearOpMode {

    DcMotor FR;
    DcMotor FL;
    DcMotor BR;
    DcMotor BL;
    DcMotor intake;
    DcMotor outake;
    DcMotor outake2;
    CRServo transferR;
    CRServo transferL;

    double r;

    @Override
    public void runOpMode() {

        FL = hardwareMap.get(DcMotor.class, "FL");
        FR = hardwareMap.get(DcMotor.class, "FR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");

        outake = hardwareMap.get(DcMotor.class, "outake");
        outake2 = hardwareMap.get(DcMotor.class, "outake2");
        intake = hardwareMap.get(DcMotor.class, "intake");
        transferR = hardwareMap.get(CRServo.class, "transferR"); //the robots right
        transferL = hardwareMap.get(CRServo.class, "transferL");


        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();

        while (opModeIsActive()) {
            forward(84, telemetry, BL, BR, FL, FR); //forward to middle of field
            left(24, telemetry, BL, BR, FL, FR);
            rotateRight(45,telemetry, BL, BR, FL, FR);
            //outake
            outake.setPower(1);
            outake2.setPower(1);
            transferL.setPower(1);
            transferR.setPower(-1);
            sleep(4000);
            transferL.setPower(0);
            transferR.setPower(0);
            outake.setPower(0);
            outake2.setPower(0);
            outake.setPower(1);
            outake2.setPower(1);
            //
            rotateRight(45,telemetry, BL, BR, FL, FR);
            forward(36,telemetry, BL, BR, FL, FR);
            //intake
            intake.setPower(1);
            sleep(2000);
            intake.setPower(0);
            //
            backward(36, telemetry, BL, BR, FL, FR);
            rotateLeft(45, telemetry, BL, BR, FL, FR);
            //outtake
            outake.setPower(1);
            outake2.setPower(1);
            transferL.setPower(1);
            transferR.setPower(-1);
            sleep(4000);
            transferL.setPower(0);
            transferR.setPower(0);
            outake.setPower(0);
            outake2.setPower(0);
            //
        }
    }
}
}