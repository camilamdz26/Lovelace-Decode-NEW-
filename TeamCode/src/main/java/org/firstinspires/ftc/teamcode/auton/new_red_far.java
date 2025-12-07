package org.firstinspires.ftc.teamcode.auton;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantFunction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Autonomous
public class new_red_far extends LinearOpMode {
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

    class OUTTAKE implements InstantFunction {
        @Override
        public void run(){
            outake.setPower(1);
            transfer.setPower(1);
            sleep(3000);
            outake.setPower(0);
            transfer.setPower(0);
        }
    }

    @Override
    public void runOpMode() throws InterruptedException{
        Limelight3A LL = hardwareMap.get(Limelight3A.class, "LL");

        //create starting pos
        Pose2d beginPose = new Pose2d(-58,45,Math.toRadians(305));
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
                //intake
                .stopAndAdd(new IT(1))
                //.sleep(1000)
                .turnTo(Math.toRadians(90))
                .lineToY(42)
                .stopAndAdd(new IT(0))
                //
                .strafeTo(new Vector2d(-16, 15))
                .turnTo(Math.toRadians(135))
                //outtake
                .stopAndAdd(new OT(1))
                .stopAndAdd(new GATE(1))
                //.sleep(2000)
                .stopAndAdd(new OT(0))
                .stopAndAdd(new GATE(0))
                .build();

        Action preloaded = drive.actionBuilder(beginPose)
                .strafeTo(new Vector2d(-16, 15))
                .turnTo(Math.toRadians(135))
                //outtake
                .stopAndAdd(new OT(1))
                .stopAndAdd(new GATE(1))
                //.sleep(2000)
                .stopAndAdd(new OT(0))
                .stopAndAdd(new GATE(0))
                .turnTo(Math.toRadians(180))
                .build();

        Action GPP = drive.actionBuilder(preloadpose)
                //assume preloaded balls score gp, so we pick up and score pg
                .lineToX(12)
                .turn(Math.toRadians(-90))
                .lineToY(39)
                .stopAndAdd(new IT(1))
                .lineToY(42) //go forward to intake
                .stopAndAdd(new IT(0))
                .lineToY(15)
                .turn(Math.toRadians(90))
                .lineToX(-16)
                .turnTo(Math.toRadians(135))
                //outtake
                .stopAndAdd(new OT(1))
                .stopAndAdd(new GATE(1))
                //.sleep(2000)
                .stopAndAdd(new OT(0))
                .stopAndAdd(new GATE(0))
                .build();
        //GRAB FROM 2ND
        //intake
        //then, in teleop, aim for balls pp

        Action PPG = drive.actionBuilder(preloadpose)
                //assume preloaded balls score pp, so we pick up and score gp//GRAP FROM CLOSEST
                .lineToX(35)
                .turn(Math.toRadians(-90))
                .lineToY(39)
                .stopAndAdd(new IT(1))
                .lineToY(42) //go forward to intake
                .stopAndAdd(new IT(0))
                .lineToY(15)
                .turn(Math.toRadians(90))
                .lineToX(-16)
                .turnTo(Math.toRadians(135))
                //outtake
                .stopAndAdd(new OT(1))
                .stopAndAdd(new GATE(1))
                //.sleep(2000)
                .stopAndAdd(new OT(0))
                .stopAndAdd(new GATE(0))
                //
                //then, in teleop, aim for balls pg
                .build();

        Action PGP = drive.actionBuilder(preloadpose)
                //assume preloaded balls score pg, so we pick up and score pp
                .lineToX(-12)
                .turn(Math.toRadians(-90))
                .lineToY(39)
                .stopAndAdd(new IT(1))
                .lineToY(42) //go forward to intake
                .stopAndAdd(new IT(0))
                .lineToY(15)
                .turn(Math.toRadians(90))
                .lineToX(-16)
                .turnTo(Math.toRadians(135))
                //outtake
                .stopAndAdd(new OT(1))
                .stopAndAdd(new GATE(1))
                //.sleep(2000)
                .stopAndAdd(new OT(0))
                .stopAndAdd(new GATE(0))
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
