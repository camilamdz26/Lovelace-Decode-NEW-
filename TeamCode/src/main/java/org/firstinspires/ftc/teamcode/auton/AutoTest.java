package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Autonomous(name = "auto w/o cameras")
public class AutoTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException{

        //create starting pos
        Pose2d beginPose = new Pose2d(-58,45,Math.toRadians(305));
        Pose2d camPose = new Pose2d(0,10,Math.toRadians(180));
        //create rr drive obj
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        waitForStart();

        //create path
        Action toCam = drive.actionBuilder(beginPose)
                .lineToY(10)
                .lineToX(0)
                //.lineToY(0)
                .build();
        Action GPP = drive.actionBuilder(camPose)
                .lineToX(35)
                .turn(Math.toRadians(-90))
                .lineToY(42)
                //intake
                .lineToY(35)
                .turn(Math.toRadians(90))
                .lineToX(-34)
                .turn(Math.toRadians(-45))
                //launch
                .turn(Math.toRadians(45))
                .lineToX(-12)
                .turn(Math.toRadians(-90))
                .lineToY(42)
                //intake
                .turn(Math.toRadians(360)) //placehodler for intake
                .lineToY(35)
                .turn(Math.toRadians(90))
                .lineToX(-34)
                .turn(Math.toRadians(-45))
                //launch
                .build();
        Action PPG = drive.actionBuilder(camPose)
                .lineToX(-12)
                .turn(Math.toRadians(-90))
                .lineToY(42)
                //intake
                .lineToY(35)
                .turn(Math.toRadians(90))
                .lineToX(-34)
                .turn(Math.toRadians(-45))
                //launch
                .turn(Math.toRadians(45))
                .lineToX(12)
                .turn(Math.toRadians(-90))
                .lineToY(42)
                //intake
                .turn(Math.toRadians(360)) //placehodler for intake
                .lineToY(35)
                .turn(Math.toRadians(90))
                .lineToX(-34)
                .turn(Math.toRadians(-45))
                //launch
                .build();
        Action PGP = drive.actionBuilder(camPose)
                .lineToX(12)
                .turn(Math.toRadians(-90))
                .lineToY(42)
                //intake
                .lineToY(35)
                .turn(Math.toRadians(90))
                .lineToX(-34)
                .turn(Math.toRadians(-45))
                //launch
                .turn(Math.toRadians(45))
                .lineToX(12)
                .turn(Math.toRadians(-90))
                .lineToY(42)
                //intake
                .turn(Math.toRadians(360)) //placehodler for intake
                .lineToY(35)
                .turn(Math.toRadians(90))
                .lineToX(-34)
                .turn(Math.toRadians(-45))
                //launch
                .build();

        Actions.runBlocking(new SequentialAction(toCam));
        Actions.runBlocking(new SequentialAction(PGP));
    }

}
