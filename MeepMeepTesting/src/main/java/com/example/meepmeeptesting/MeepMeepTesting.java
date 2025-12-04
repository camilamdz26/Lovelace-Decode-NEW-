package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Vector;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(190), Math.toRadians(190), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-58,45,Math.toRadians(305)))
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
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}