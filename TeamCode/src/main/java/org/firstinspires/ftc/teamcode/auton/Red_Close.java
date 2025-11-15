package org.firstinspires.ftc.teamcode.auton;



import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import  org.firstinspires.ftc.teamcode.movement.MOV_cam;
import static org.firstinspires.ftc.teamcode.movement.MOV_cam.tag_ID;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

import java.util.Objects;

@Autonomous(name = "Red April Tag Close- RR")
public class Red_Close extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException{

        //create starting pos
        Pose2d beginPose = new Pose2d(60,35,Math.toRadians(180));
        Pose2d camPose = new Pose2d(0,10,Math.toRadians(180));
        //this might be wrong...

        //create rr drive obj
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Action toCam = drive.actionBuilder(beginPose)
                .splineTo(new Vector2d(0, 10), Math.toRadians(180))
                .build();
        //create path
        Action GPP = drive.actionBuilder(camPose) //closest
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
        Action PPG = drive.actionBuilder(camPose) //farthest
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
        Action PGP = drive.actionBuilder(camPose) //middle
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
        if (Objects.equals(APRT, "GPP")) {
            Actions.runBlocking(new SequentialAction(GPP));
        } else if (Objects.equals(APRT, "PPG")) {
            Actions.runBlocking(new SequentialAction(PPG));
        } else if (Objects.equals(APRT, "PGP")) {
            Actions.runBlocking(new SequentialAction(PGP));
        }
    }

}
