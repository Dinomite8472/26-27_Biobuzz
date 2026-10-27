package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "Biobuzz Teleop (Use this)")
public class BiobuzzTeleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotorEx FrontLeft = hardwareMap.get(DcMotorEx .class, "FrontLeft");
        DcMotorEx BackLeft = hardwareMap.get(DcMotorEx .class, "BackLeft");
        DcMotorEx FrontRight = hardwareMap.get(DcMotorEx .class, "FrontRight");
        DcMotorEx BackRight = hardwareMap.get(DcMotorEx .class, "BackRight");

        DcMotorEx Intake = hardwareMap.get(DcMotorEx .class, "Intake");

        DcMotorEx Launcher = hardwareMap.get(DcMotorEx .class, "Launcher");

        Servo Stopper = hardwareMap.get(Servo.class, "Stopper");

        double StopperServo_Normal = 0.5;
        double StopperServo_Up = 0.75;

        double wheel_Speed = 0.75;
        double D_Speed = 0.5;
        double launcher_Speed = 0.7812;
        double intake_Speed = 1;

        boolean doIntake = false;
        boolean doLauncher = false;
        boolean stopperUp = false;

        ServoController ControlHub_ServoController = hardwareMap.get(ServoController.class, "Control Hub");
        ServoController ExpansionHub_ServoController = hardwareMap.get(ServoController.class, "Expansion Hub");
        ControlHub_ServoController.pwmDisable();
        ExpansionHub_ServoController.pwmDisable();

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //BackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        Intake.setDirection(DcMotorSimple.Direction.REVERSE);
        Launcher.setDirection(DcMotorSimple.Direction.REVERSE);

        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x/2;


            BackLeft.setPower((x - y) * wheel_Speed);
            BackRight.setPower((x + y) * wheel_Speed);
            FrontLeft.setPower((x + y) * wheel_Speed);
            FrontRight.setPower((x - y) * wheel_Speed);

            if (gamepad1.dpad_right) {
                // Turns the robot right (Hopefully)
                BackLeft.setPower(D_Speed * wheel_Speed);
                BackRight.setPower(D_Speed * wheel_Speed);
                FrontLeft.setPower(-D_Speed * wheel_Speed);
                FrontRight.setPower(-D_Speed * wheel_Speed);
            } else if (gamepad1.dpad_left) {
                // Turns the robot left (Hopefully)
                BackLeft.setPower(-D_Speed * wheel_Speed);
                BackRight.setPower(-D_Speed * wheel_Speed);
                FrontLeft.setPower(D_Speed * wheel_Speed);
                FrontRight.setPower(D_Speed * wheel_Speed);
            }


            //Optional way to change slow mode strength
            if (gamepad1.rightBumperWasPressed() && wheel_Speed < 1) {
                wheel_Speed += 0.125;
            }

            //Optional way to change slow mode strength
            if (gamepad1.leftBumperWasPressed() && wheel_Speed > 0) {
                wheel_Speed -= 0.125;
            }

            if (gamepad1.dpadUpWasPressed() && launcher_Speed < 1) {
                launcher_Speed += 0.03125;
            }

            //Optional way to change slow mode strength
            if (gamepad1.dpadDownWasPressed() && launcher_Speed > 0) {
                launcher_Speed -= 0.03125;
            }

            if (gamepad1.rightTriggerWasPressed() && intake_Speed < 1) {
                intake_Speed += 0.125;
            }

            //Optional way to change slow mode strength
            if (gamepad1.leftTriggerWasPressed() && intake_Speed > 0) {
                intake_Speed -= 0.125;
            }

            if (gamepad1.yWasPressed()) {
                doLauncher = !doLauncher;
            }

            if (gamepad1.bWasPressed()) {
                doIntake = !doIntake;
            }

            if (gamepad1.xWasPressed()) {
                stopperUp = !stopperUp;
            }

            if(doLauncher) {
                Launcher.setPower(launcher_Speed);
            } else {
                Launcher.setPower(0);
            }

            if(doIntake) {
                Intake.setPower(intake_Speed);
            } else {
                Intake.setPower(0);
            }

            if(stopperUp) {
                Stopper.setPosition(StopperServo_Up);
            } else {
                Stopper.setPosition(StopperServo_Normal);
            }

            telemetry.addLine("Useful info:");
            telemetry.addData("Launching", doLauncher);
            telemetry.addData("Intaking", doIntake);
            telemetry.addData("Stopper up", stopperUp);
            telemetry.addData("Launcher speed", launcher_Speed);
            telemetry.addData("Intake speed", intake_Speed);
            telemetry.addData("Drivechain speed", wheel_Speed);


            telemetry.addLine("Wheels:");
            telemetry.addData("P BackLeft", BackLeft.getPower());
            telemetry.addData("P BackRight", BackRight.getPower());
            telemetry.addData("P FrontLeft", FrontLeft.getPower());
            telemetry.addData("P FrontRight", FrontRight.getPower());
            telemetry.addData("V BackLeft", BackLeft.getVelocity());
            telemetry.addData("V BackRight", BackRight.getVelocity());
            telemetry.addData("V FrontLeft", FrontLeft.getVelocity());
            telemetry.addData("V FrontRight", FrontRight.getVelocity());

            /*
            telemetry.addData("A BackLeft", BackLeft.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("A BackRight", BackRight.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("A FrontLeft", FrontLeft.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("A FrontRight", FrontRight.getCurrent(CurrentUnit.AMPS));
            */
            telemetry.addLine("\nGamePad1:");
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

            telemetry.update();
        }
    }
}