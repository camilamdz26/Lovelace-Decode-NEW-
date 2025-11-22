package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(190), Math.toRadians(190), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-34,30,Math.toRadians(180)))
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
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}