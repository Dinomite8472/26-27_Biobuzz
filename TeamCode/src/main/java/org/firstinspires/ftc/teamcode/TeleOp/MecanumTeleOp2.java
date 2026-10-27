package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp
public class MecanumTeleOp2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotorEx FrontLeft = hardwareMap.get(DcMotorEx .class, "FrontLeft");
        DcMotorEx BackLeft = hardwareMap.get(DcMotorEx .class, "BackLeft");
        DcMotorEx FrontRight = hardwareMap.get(DcMotorEx .class, "FrontRight");
        DcMotorEx BackRight = hardwareMap.get(DcMotorEx .class, "BackRight");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;


            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            FrontLeft.setPower(frontLeftPower);
            BackLeft.setPower(backLeftPower);
            FrontRight.setPower(frontRightPower);
            BackRight.setPower(backRightPower);


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