package org.firstinspires.ftc.teamcode.movement;

import static com.sun.tools.doclint.Entity.theta;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import static java.lang.Math.cos;
import static java.lang.Math.tan;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

public class motor_power {
    final double gravity = 9.81; //gravity
    final double angle = 20.0; // launch angle in degrees
    final double angle_rad = Math.toRadians(angle);
    final double shooterWheelRadius = 0.05; // meters
    final double yinit = 0.40; // y0, meters
    final double y = 0.90; // y, meters
    final double gearRatio = 1.0; // motor revs per wheel rev
    final double ticksPerRev = 42.0; // encoder ticks per motor rev (set to encoder)
    final double maxMotorRPM = 5000.0; // motor free speed or allowed RPM
    DcMotor outake;

    public void velocity(Limelight3A LL) {
        LLResult result = LL.getLatestResult(); // limelight is your Limelight3A object

        //we have to solve equation required launch speed = square root (gx^2/(2(costheta)^2(xtantheta+yinitial -y)
        /*
        when
        g = gravity
        theta = launch angle above horizontal
        x = horizontal distance to target
        yinitial = launch height, shooterEndHeiht
        y = target height, center of goal
         */
        if (result == null || !result.isValid()) {
            outake.setPower(0.35);        // fallback
            return;
        }

        double ty = result.getTy();   // vertical angle to tag (deg)
        double cameraAngle = 25;      // degrees, your camera tilt
        double h_cam = 0.40;          // meters, camera height
        double h_tag = 0.90;          // meters, center of goal tag height

        double angleRad = Math.toRadians(cameraAngle + ty);
        double x = (h_tag - h_cam) / Math.tan(angleRad);
        double numerator = gravity * x * x;
        double den1 = 2 * (cos(angle_rad)) * (cos(angle_rad));
        double den2 = (x * tan(angle_rad) + yinit - y);
        double denom = den1 * den2;

        if (denom <= 0) {
            outake.setPower(0.35);       // if the angle is unreachable, it just sets to thsi speed
            return;
        } else {
            double v = Math.sqrt(numerator / denom); // m/s

            // wheel angular velocity and RPM
            double omega = v / shooterWheelRadius; // rad/s
            double wheelRPM = omega * 60.0 / (2.0 * Math.PI);

            // motor RPM and ticks/sec
            double motorRPM = wheelRPM * gearRatio;
            if (motorRPM > maxMotorRPM) motorRPM = maxMotorRPM;

            double motorTicksPerSec = motorRPM / 60.0 * ticksPerRev;

            // set motor velocity (DcMot orEx)
            outake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            ((DcMotorEx) outake).setVelocity(motorTicksPerSec);

            telemetry.addData("distance (m)", x);
            telemetry.addData("wheelRPM", wheelRPM);
            telemetry.addData("motorRPM", motorRPM);
        }
    }
}


