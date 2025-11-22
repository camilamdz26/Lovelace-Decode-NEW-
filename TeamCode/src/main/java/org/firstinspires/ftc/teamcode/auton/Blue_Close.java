package org.firstinspires.ftc.teamcode.auton;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import  org.firstinspires.ftc.teamcode.movement.MOV_cam;

import org.firstinspires.ftc.teamcode.movement.MOV_cam2;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

import java.util.Objects;

@Autonomous(name = "Blue April Tag Close- RR")
public class Blue_Close extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        Limelight3A LL = hardwareMap.get(Limelight3A.class, "LL");

        //create starting pos
        Pose2d beginPose = new Pose2d(60,-34,Math.toRadians(180));
        Pose2d camPose = new Pose2d(0,-10,Math.toRadians(180));
        //this might be wrong...

        //create rr drive obj
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Action toCam = drive.actionBuilder(beginPose)
                .splineTo(new Vector2d(0, -10), Math.toRadians(180))
                .build();

        Action GPP = drive.actionBuilder(camPose) //closest
                .lineToX(35)
                .turn(Math.toRadians(90))
                .lineToY(-42)
                //intake
                .lineToY(-35)
                .turn(Math.toRadians(-90))
                .lineToX(-34)
                .turn(Math.toRadians(45))
                //launch
                .turn(Math.toRadians(-45))
                .lineToX(-12)
                .turn(Math.toRadians(90))
                .lineToY(-42)
                //intake
                .turn(Math.toRadians(-360)) //placehodler for intake
                .lineToY(-35)
                .turn(Math.toRadians(-90))
                .lineToX(-34)
                .turn(Math.toRadians(45))
                //launch
                .build();
        Action PPG = drive.actionBuilder(camPose) //farthest
                .lineToX(-12)
                .turn(Math.toRadians(90))
                .lineToY(-42)
                //intake
                .lineToY(-35)
                .turn(Math.toRadians(-90))
                .lineToX(-34)
                .turn(Math.toRadians(45))
                //launch
                .turn(Math.toRadians(-45))
                .lineToX(12)
                .turn(Math.toRadians(90))
                .lineToY(-42)
                //intake
                .turn(Math.toRadians(360)) //placehodler for intake
                .lineToY(-35)
                .turn(Math.toRadians(-90))
                .lineToX(-34)
                .turn(Math.toRadians(45))
                //launch
                .build();
        Action PGP = drive.actionBuilder(camPose) //middle
                .lineToX(12)
                .turn(Math.toRadians(90))
                .lineToY(-42)
                //intake
                .lineToY(-35)
                .turn(Math.toRadians(-90))
                .lineToX(-34)
                .turn(Math.toRadians(45))
                //launch
                .turn(Math.toRadians(-45))
                .lineToX(-12)
                .turn(Math.toRadians(90))
                .lineToY(-42)
                //intake
                .turn(Math.toRadians(-360)) //placehodler for intake
                .lineToY(-35)
                .turn(Math.toRadians(-90))
                .lineToX(-34)
                .turn(Math.toRadians(45))
                //launch
                .build();

        waitForStart();

        Actions.runBlocking(new SequentialAction(toCam));
        MOV_cam2.video(hardwareMap, LL, telemetry);
        sleep(1000);
        MOV_cam2.LL_stats(telemetry, LL);

        String APRT = MOV_cam2.tag_ID(LL, telemetry); //assign string aprt to the tag id that you sense

        if (Objects.equals(APRT, "GPP")) {
            Actions.runBlocking(new SequentialAction(GPP));
        } else if (Objects.equals(APRT, "PPG")) {
            Actions.runBlocking(new SequentialAction(PPG));
        } else if (Objects.equals(APRT, "PGP")) {
            Actions.runBlocking(new SequentialAction(PGP));
        }
    }

}
