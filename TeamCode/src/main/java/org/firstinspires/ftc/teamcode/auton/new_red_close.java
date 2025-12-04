package org.firstinspires.ftc.teamcode.auton;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Autonomous
public class new_red_close extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        Limelight3A LL = hardwareMap.get(Limelight3A.class, "LL");

        //create starting pos
        Pose2d beginPose = new Pose2d(60,35,Math.toRadians(180));
        Pose2d preloadpose = new Pose2d(-16,-15,Math.toRadians(180));
        //this might be wrong...

        //create rr drive obj
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Action basic = drive.actionBuilder(beginPose)
                .strafeTo(new Vector2d(-16, 15))
                .turnTo(Math.toRadians(135))
                //outake
                .turnTo(Math.toRadians(180))
                .strafeTo(new Vector2d(-12, 30))
                //intake motor on
                .turnTo(Math.toRadians(90))
                .lineToY(42)
                //intake off
                .strafeTo(new Vector2d(-16, 15))
                .turnTo(Math.toRadians(135))
                //outake
                .build();

        Action preloaded = drive.actionBuilder(beginPose)
                .strafeTo(new Vector2d(-16, 15))
                .turnTo(Math.toRadians(135))
                //outake
                .turnTo(Math.toRadians(180))
                .build();

        Action GPP = drive.actionBuilder(preloadpose)
                //assume preloaded balls score gp, so we pick up and score pg
                .lineToX(12)
                .turn(Math.toRadians(-90))
                .lineToY(42)
                //intake
                .lineToY(15)
                .turn(Math.toRadians(90))
                .lineToX(-16)
                .turnTo(Math.toRadians(135))
                //outtake
                .build();
        //GRAB FROM 2ND
        //intake
        //then, in teleop, aim for balls pp

        Action PPG = drive.actionBuilder(preloadpose)
                //assume preloaded balls score pp, so we pick up and score gp//GRAP FROM CLOSEST
                .lineToX(35)
                .turn(Math.toRadians(-90))
                .lineToY(42)
                //intake
                .lineToY(15)
                .turn(Math.toRadians(90))
                .lineToX(-16)
                .turnTo(Math.toRadians(135))
                //outtake
                //then, in teleop, aim for balls pg
                .build();

        Action PGP = drive.actionBuilder(preloadpose)
                .lineToX(-12)
                .turn(Math.toRadians(-90))
                .lineToY(42)
                //intake
                .lineToY(15)
                .turn(Math.toRadians(90))
                .lineToX(-16)
                .turnTo(Math.toRadians(135))
                //outtake
                //assume preloaded balls score pg, so we pick up and score pp
                //intake
                //GRAB FROM FARTHEST
                //then, in teleop, aim for balls gp
                .build();


        waitForStart();
        //OPTION 1
        Actions.runBlocking(new SequentialAction(basic));


        //OPTION 2
        /*
        Actions.runBlocking(new SequentialAction(preloaded));
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
         */
    }

}
