
package org.firstinspires.ftc.teamcode.movement;

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

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "SampleAuto")
public class SampleOpMode extends LinearOpMode {

    CRServo intake;
    CRServo transfer;
    DcMotor outake;

    //These are the roadrunner classes!
    public class OT implements InstantFunction{
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


    public class GATE implements InstantFunction{
        float TS;
        public GATE(float TS){
            this.TS = TS;
        }
        @Override
        public void run(){
            transfer.setPower(TS);
        }
    }


    public class IT implements InstantFunction {
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

        //create starting pos
        Pose2d beginPose = new Pose2d(0,0,Math.toRadians(180));

        //create rr drive obj
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        waitForStart();

        //create path
        Action path = drive.actionBuilder(beginPose)
                .lineToX(-24)
                .stopAndAdd(new intake(1))
                .strafeTo(new Vector2d(40, 40))
                .build();
        Actions.runBlocking(new SequentialAction(path));
    }

}