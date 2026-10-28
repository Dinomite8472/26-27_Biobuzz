package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "TeleOp (Simple Mecanum2")
public class TeleOpSimpleMecanum extends LinearOpMode {

    private DcMotorEx  FrontRight;
    private DcMotorEx  BackRight;
    private DcMotorEx  FrontLeft;
    private DcMotorEx  BackLeft;


    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        double D_Speed = 0.5;
        double halfSpeed = 0.5;
        double wheel_Speed = 1;

        float verticalGp1_left;
        float horizontalGp1_left;
        float verticalGp1_right;
        float horizontalGp1_right;

        float verticalGp2_left;
        float horizontalGp2_left;
        float verticalGp2_right;
        float horizontalGp2_right;

        boolean debugInfo = true;

        BackLeft = hardwareMap.get(DcMotorEx .class, "BackLeft");
        BackRight = hardwareMap.get(DcMotorEx .class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotorEx .class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotorEx .class, "FrontRight");

        waitForStart();
        if (opModeIsActive()) {


            BackLeft.setDirection(DcMotor.Direction.REVERSE);
            BackRight.setDirection(DcMotor.Direction.REVERSE);
            FrontLeft.setDirection(DcMotor.Direction.REVERSE);
            FrontRight.setDirection(DcMotor.Direction.FORWARD);


            /*
            BackLeft.setDirection(DcMotor.Direction.FORWARD);
            BackRight.setDirection(DcMotor.Direction.FORWARD);
            FrontLeft.setDirection(DcMotor.Direction.REVERSE);
            FrontRight.setDirection(DcMotor.Direction.REVERSE);
            */


            BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

            BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            while (opModeIsActive()) {
                telemetry.update();
                //
                // Used for stopping motor and servo movement
                //
                testMode = debugInfo;
                //
                // Used for arm movement
                //
                verticalGp1_left = gamepad1.left_stick_y;
                //
                // Used for wheel movement
                //
                horizontalGp1_left = -gamepad1.left_stick_x;
                //
                // Used for wheel movement
                //
                verticalGp1_right = gamepad1.right_stick_y;
                //
                // Used for wheel movement
                //
                horizontalGp1_right = -gamepad1.right_stick_x;
                //
                // Used for wheel movement
                //
                verticalGp2_left = gamepad2.left_stick_y;
                //
                // Used for wheel movement
                //
                horizontalGp2_left = -gamepad2.left_stick_x;
                //
                // Used for wheel movement
                //
                verticalGp2_right = gamepad2.right_stick_y;
                //
                // Used for wheel movement
                //
                horizontalGp2_right = -gamepad2.right_stick_x;
                //
                // Used for wheel movement
                //
                pivot = gamepad1.left_stick_x;
                //
                // Gamepad 1 commands start
                //
                // Used for Mecanum wheel movement
                //




                BackLeft.setPower((horizontalGp1_left - verticalGp1_left) * wheel_Speed);
                BackRight.setPower((horizontalGp1_left + verticalGp1_left) * wheel_Speed);
                FrontLeft.setPower((horizontalGp1_left + verticalGp1_left) * wheel_Speed);
                FrontRight.setPower((horizontalGp1_left - verticalGp1_left) * wheel_Speed);


                if (gamepad1.dpad_up) {
                    // Makes the robot go forward (Hopefully)
                    BackLeft.setPower(D_Speed);
                    BackRight.setPower(-D_Speed);
                    FrontLeft.setPower(-D_Speed);
                    FrontRight.setPower(D_Speed);
                } else if (gamepad1.dpad_down) {
                    // Makes the rbot go backwards (Hopefully)
                    BackLeft.setPower(-D_Speed);
                    BackRight.setPower(D_Speed);
                    FrontLeft.setPower(D_Speed);
                    FrontRight.setPower(-D_Speed);
                } else if (gamepad1.dpad_right) {
                    // Turns the robot right (Hopefully)
                    BackLeft.setPower(D_Speed);
                    BackRight.setPower(D_Speed);
                    FrontLeft.setPower(-D_Speed);
                    FrontRight.setPower(-D_Speed);
                } else if (gamepad1.dpad_left) {
                    // Turns the robot left (Hopefully)
                    BackLeft.setPower(-D_Speed);
                    BackRight.setPower(-D_Speed);
                    FrontLeft.setPower(D_Speed);
                    FrontRight.setPower(D_Speed);
                } else if(gamepad1.ps) {
                    BackLeft.setPower(1);
                    BackRight.setPower(-1);
                    FrontLeft.setPower(-1);
                    FrontRight.setPower(1);
                }

                if (gamepad1.y)
                {
                    FrontLeft.setPower(halfSpeed);
                } else if (gamepad1.b)
                {
                    FrontRight.setPower(halfSpeed);
                } else if (gamepad1.a)
                {
                    BackRight.setPower(halfSpeed);
                } else if (gamepad1.x)
                {
                    BackLeft.setPower(halfSpeed);
                }

                telemetry.addLine("Wheels:");
                telemetry.addData("P BackLeft", BackLeft.getPower());
                telemetry.addData("P BackRight", BackRight.getPower());
                telemetry.addData("P FrontLeft", FrontLeft.getPower());
                telemetry.addData("P FrontRight", FrontRight.getPower());
                telemetry.addData("V BackLeft", BackLeft.getVelocity());
                telemetry.addData("V BackRight", BackRight.getVelocity());
                telemetry.addData("V FrontLeft", FrontLeft.getVelocity());
                telemetry.addData("V FrontRight", FrontRight.getVelocity());
                telemetry.addData("A BackLeft", BackLeft.getCurrent(CurrentUnit.AMPS));
                telemetry.addData("A BackRight", BackRight.getCurrent(CurrentUnit.AMPS));
                telemetry.addData("A FrontLeft", FrontLeft.getCurrent(CurrentUnit.AMPS));
                telemetry.addData("A FrontRight", FrontRight.getCurrent(CurrentUnit.AMPS));

                telemetry.addLine("\nGamePad1:");
                telemetry.addData("VerticalGp1_Left", verticalGp1_left);
                telemetry.addData("HorizontalGp1_left", horizontalGp1_left);
                telemetry.addData("VerticalGp1_right", verticalGp1_right);
                telemetry.addData("HorizontalGp1_right", horizontalGp1_right);
                telemetry.addData("Dpad_up", gamepad1.dpad_up);
                telemetry.addData("Dpad_down", gamepad1.dpad_down);
                telemetry.addData("Dpad_left", gamepad1.dpad_left);
                telemetry.addData("Dpad_right", gamepad1.dpad_right);
                telemetry.addData("A", gamepad1.a);
                telemetry.addData("X", gamepad1.x);
                telemetry.addData("Y", gamepad1.y);
                telemetry.addData("B", gamepad1.b);
                telemetry.addData("Left_bumper", gamepad1.left_bumper);
                telemetry.addData("Right_bumper", gamepad1.right_bumper);

                telemetry.addLine("\nGamePad2:");
                telemetry.addData("VerticalGp2_Left", verticalGp2_left);
                telemetry.addData("HorizontalGp2_left", horizontalGp2_left);
                telemetry.addData("VerticalGp2_right", verticalGp2_right);
                telemetry.addData("HorizontalGp2_right", horizontalGp2_right);
                telemetry.addData("Dpad_up", gamepad2.dpad_up);
                telemetry.addData("Dpad_down", gamepad2.dpad_down);
                telemetry.addData("Dpad_left", gamepad2.dpad_left);
                telemetry.addData("Dpad_right", gamepad2.dpad_right);
                telemetry.addData("A", gamepad2.a);
                telemetry.addData("X", gamepad2.x);
                telemetry.addData("Y", gamepad2.y);
                telemetry.addData("B", gamepad2.b);
                telemetry.addData("Left_bumper", gamepad2.left_bumper);
                telemetry.addData("Right_bumper", gamepad2.right_bumper);

                telemetry.addLine("\n Ports:");
                telemetry.addData("BackLeft", BackLeft.getPortNumber());
                telemetry.addData("BackRight", BackRight.getPortNumber());
                telemetry.addData("FrontLeft", FrontLeft.getPortNumber());
                telemetry.addData("FrontRight", FrontRight.getPortNumber());
            }
        }
    }
}