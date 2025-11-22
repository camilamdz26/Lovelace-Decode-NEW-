package org.firstinspires.ftc.teamcode.auton;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantFunction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import  org.firstinspires.ftc.teamcode.movement.MOV_cam;
import static org.firstinspires.ftc.teamcode.movement.MOV_cam.tag_ID;

import org.firstinspires.ftc.teamcode.movement.SampleOpMode;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

import java.util.Objects;

@Autonomous(name = "Blue April Tag Far- RR")
public class LL_auto_boilerplate extends LinearOpMode {
    CRServo intake;
    CRServo transfer;
    DcMotor outake;

    class OT implements InstantFunction {
        //OS stands for outake speed. It is the variable for the speed we want to set the outake at.
        //We use it in the class so we can adjust the speed in the actual path, rather than makng a seperate code for every speed we need.
        float OS;
        public OT(float OS){
            this.OS = OS;
        }
        @Override
        public void run(){
            outake.setPower(OS);
        }
    }

    class GATE implements InstantFunction{
        float TS;
        public GATE(float TS){
            this.TS = TS;
        }
        @Override
        public void run(){
            transfer.setPower(TS);
        }
    }


    class IT implements InstantFunction {
        float IS;
        public IT(float IS){
            this.IS = IS;
        }
        @Override
        public void run(){
            intake.setPower(IS);
        }
    }

    @Override
    public void runOpMode() throws InterruptedException{
        intake = hardwareMap.get(CRServo.class, "intake");
        transfer = hardwareMap.get(CRServo.class, "transfer");
        outake = hardwareMap.get(DcMotor.class, "outake");
        //create starting pos
        Pose2d beginPose = new Pose2d(-58,45,Math.toRadians(305));
        Pose2d camPose = new Pose2d(0,10,Math.toRadians(180));
        Pose2d prePose = new Pose2d(-34, 30, Math.toRadians(180));

        //create rr drive obj
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        //create path
        Action toCam = drive.actionBuilder(beginPose)
                .splineTo(new Vector2d(0, 10), Math.toRadians(180))
                .build();
        Action GPP = drive.actionBuilder(prePose) //closest
                
                //
                .build();
        Action PPG = drive.actionBuilder(prePose) //farthest

                //
                .build();
        Action PGP = drive.actionBuilder(prePose)
                //
                .build();
        Action preload = drive.actionBuilder(camPose)
                .splineTo(new Vector2d(-34, 35), Math.toRadians(135))
                //launch
                .stopAndAdd(new OT(1))
                .stopAndAdd(new GATE(1))
                //.sleep(2000)
                .stopAndAdd(new OT(0))
                .stopAndAdd(new GATE(0))
                //
                .lineToY(30)
                .turn(Math.toRadians(45))

                .build();

        //this calls the function called vid, whcih initializes the camera
        waitForStart();
        Actions.runBlocking(new SequentialAction(toCam));

        MOV_cam.vid(hardwareMap);
        MOV_cam cam = new MOV_cam();
        if (MOV_cam.is_ID()) {
            String tagName = tag_ID();
            telemetry.addData("Detected Tag:", tagName); //print
        } else {
            telemetry.addLine("No tag detected"); //if there is no april tag
            //add how to get to the april tag, do later.
        }
        telemetry.update();
        sleep(2000);

        String APRT = tag_ID(); //assign string aprt to the tag id that you sense

        cam.stopCamera();
        Actions.runBlocking(new SequentialAction(preload));
        if (Objects.equals(APRT, "GPP")) {
            Actions.runBlocking(new SequentialAction(GPP));
        } else if (Objects.equals(APRT, "PPG")) {
            Actions.runBlocking(new SequentialAction(PPG));
        } else if (Objects.equals(APRT, "PGP")) {
            Actions.runBlocking(new SequentialAction(PGP));
        }
    }

}
