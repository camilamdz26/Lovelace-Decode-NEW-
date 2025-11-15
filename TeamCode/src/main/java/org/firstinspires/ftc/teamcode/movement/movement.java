package org.firstinspires.ftc.teamcode.movement;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class movement {

    static double rotations;
    static double ticks;
    public static volatile boolean on = false;

    public static void forward(double distance, Telemetry telemetry, DcMotor BL, DcMotor BR, DcMotor FL, DcMotor FR) {
        reset_encoders(BL, BR, FL, FR);
        rotations = distance * 21.74;
        // SET TARGET POSITION
        FR.setTargetPosition((int) rotations);
        FL.setTargetPosition((int) rotations); //was neg
        BL.setTargetPosition((int) rotations);
        BR.setTargetPosition((int) rotations); //was neg, switch back to neg if this doesn't work, for last year and test bot
        // RUN TO POSITION
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // POWER (left slide)
        FR.setPower(.85);
        FL.setPower(.85);
        BL.setPower(.85);
        BR.setPower(.85);
        while (BL.isBusy() && BR.isBusy() && FL.isBusy() && FR.isBusy()) {
            telemetry.addData("function", FL.getCurrentPosition());
            motor_telemetry(telemetry, BL, BR, FL, FR);
            telemetry.update();
        }
        stopMotors(BL, BR, FL, FR);
        reset_encoders(BL, BR, FL, FR);
    }

    public static void left(int distance, Telemetry telemetry, DcMotor BL, DcMotor BR, DcMotor FL, DcMotor FR) {
        reset_encoders(BL, BR, FL, FR);
        rotations = distance * 21.74;
        // SET TARGET POSITION
        FR.setTargetPosition((int) rotations); //last year, all were positive, same for test bot
        FL.setTargetPosition((int) -rotations);
        BL.setTargetPosition((int) rotations);
        BR.setTargetPosition((int) -rotations);
        // RUN TO POSITION
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // POWER (left slide)
        FR.setPower(.85);
        FL.setPower(.85);
        BL.setPower(.85);
        BR.setPower(.85);
        while (BL.isBusy() && BR.isBusy() && FL.isBusy() && FR.isBusy()) {
            telemetry.addData("function", "forward...");
            motor_telemetry(telemetry, BL, BR, FL, FR);
            telemetry.update();
        }
        stopMotors(BL, BR, FL, FR);
        reset_encoders(BL, BR, FL, FR);
    }

    public static void right(double distance, Telemetry telemetry, DcMotor BL, DcMotor BR, DcMotor FL, DcMotor FR) {
        reset_encoders(BL, BR, FL, FR);
        rotations = distance * 21.74;
        // SET TARGET POSITION
        FR.setTargetPosition((int) -rotations);
        FL.setTargetPosition((int) rotations);
        BL.setTargetPosition((int) -rotations);
        BR.setTargetPosition((int) rotations);
        // RUN TO POSITION
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // POWER (left slide)
        FR.setPower(-.85);
        FL.setPower(.85);
        BL.setPower(-.85);
        BR.setPower(-.85);
        while (BL.isBusy() && BR.isBusy() && FL.isBusy() && FR.isBusy()) {
            telemetry.addData("function", "backward");
            motor_telemetry(telemetry, BL, BR, FL, FR);
            telemetry.update();
        }
        stopMotors(BL, BR, FL, FR);
        reset_encoders(BL, BR, FL, FR);
    }

    public static void backward(double distance, Telemetry telemetry, DcMotor BL, DcMotor BR, DcMotor FL, DcMotor FR) {
        reset_encoders(BL, BR, FL, FR);
        rotations = distance * 21.74;
        // SET TARGET POSITION
        FR.setTargetPosition((int) -rotations);
        FL.setTargetPosition((int) -rotations);
        BL.setTargetPosition((int) -rotations);
        BR.setTargetPosition((int) -rotations); //was not negative for test bot
        // RUN TO POSITION
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // POWER (left slide)
        FR.setPower(-.85);
        FL.setPower(.85);
        BL.setPower(.85);
        BR.setPower(.85);
        while (BL.isBusy() && BR.isBusy() && FL.isBusy() && FR.isBusy()) {
            telemetry.addData("going backwards: bl", BL.getCurrentPosition());
            motor_telemetry(telemetry, BL, BR, FL, FR);
            telemetry.update();
        }
        stopMotors(BL, BR, FL, FR);
        reset_encoders(BL, BR, FL, FR);
    }


    //rotation right
    // Rotation Left (Counterclockwise)
    public static void rotateRight(double degrees, Telemetry telemetry, DcMotor BL, DcMotor BR, DcMotor FL, DcMotor FR) {
        reset_encoders(BL, BR, FL, FR);

        // Calculate the number of encoder ticks for the given degree of rotation
        rotations = degrees * 14;  // Adjust this constant based on your robot's configuration

        // Set target positions for a counterclockwise turn
        FR.setTargetPosition((int) -rotations);  // Right motors move forward (counterclockwise)
        FL.setTargetPosition((int) rotations); // Left motors move backward (counterclockwise)
        BL.setTargetPosition((int) rotations); // Left motors move backward (counterclockwise)
        BR.setTargetPosition((int) rotations);  // Right motors move forward (counterclockwise)

        // Run to position mode
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set velocity for a smooth turn
        FR.setPower(.85);
        FL.setPower(.85);
        BL.setPower(.85);
        BR.setPower(.85);

        while (BL.isBusy() && BR.isBusy() && FL.isBusy() && FR.isBusy()) {
            telemetry.addData("Rotating Left", "In Progress");
            motor_telemetry(telemetry, BL, BR, FL, FR);
            telemetry.update();
        }
        stopMotors(BL, BR, FL, FR);
        reset_encoders(BL, BR, FL, FR);
    }



    // Rotation Right (Clockwise)
    public static void rotateLeft(double degrees, Telemetry telemetry, DcMotor BL, DcMotor BR, DcMotor FL, DcMotor FR) {
        reset_encoders(BL, BR, FL, FR);

        // Calculate number of encoder ticks for the given degree of rotation
        rotations = degrees * 14;  // Adjust this constant based on your robot's configuration

        // Set target positions for a turn (opposite directions for left and right motors)
        FR.setTargetPosition((int) rotations);
        FL.setTargetPosition((int) -rotations);
        BL.setTargetPosition((int) -rotations);
        BR.setTargetPosition((int) -rotations);

        // Run to position mode
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set velocity for a smooth turn
        FR.setPower(.85);
        FL.setPower(.85);
        BL.setPower(.85);
        BR.setPower(.85);

        // Wait until the robot reaches the target position
        while (BL.isBusy() && BR.isBusy() && FL.isBusy() && FR.isBusy()) {
            telemetry.addData("Rotating Right", "In Progress");
            motor_telemetry(telemetry, BL, BR, FL, FR);
            telemetry.update();
        }

        // Stop motors after rotation is complete
        stopMotors(BL, BR, FL, FR);
        reset_encoders(BL, BR, FL, FR);
    }

    //SMALL FUNCTIONS BELOW:

    public static void reset_encoders(DcMotor BL, DcMotor BR, DcMotor FL, DcMotor FR) {
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        BR.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        FL.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        FR.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
    }

    public static void reset_slides(DcMotor LSL, DcMotor LSR) {
        LSL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LSR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public static void reset_arm(DcMotor AM) {
        AM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public static void motor_telemetry(Telemetry telemetry, DcMotor BL, DcMotor BR, DcMotor FL, DcMotor FR) {
        telemetry.addData("back left pos", BL.getCurrentPosition());
        telemetry.addData("back right pos", BR.getCurrentPosition());
        telemetry.addData("front left pos", FL.getCurrentPosition());
        telemetry.addData("front right pos", FR.getCurrentPosition());
        telemetry.addData("back left pow", BL.getPower());
        telemetry.addData("back right pow", BR.getPower());
        telemetry.addData("front left pow", FL.getPower());
        telemetry.addData("front right pow", FR.getPower());
        telemetry.update();
    }

    public static void stopMotors(DcMotor BL, DcMotor BR, DcMotor FL, DcMotor FR) {
        BL.setPower(0);
        BR.setPower(0);
        FL.setPower(0);
        FR.setPower(0);
    }

    public static void position(Telemetry telemetry, DcMotor BL, DcMotor BR, DcMotor FL, DcMotor FR){
        int pos1 = abs(BR.getCurrentPosition()) + abs(BL.getCurrentPosition()) + abs(FL.getCurrentPosition()) + abs(FR.getCurrentPosition());
        int pos = pos1/4;
    }

}