package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "TestAuto5", group = "Testing")
public class TestAuto5 extends LinearOpMode {

    private DcMotor WeeeMotor;

    private Servo servo;


    private ServoController ControlHub_ServoController;

    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        boolean testMode;
        double D_Speed = 0.5;
        double halfSpeed = 0.5;
        double arm_SpeedLeft = 0.5;
        double arm_SpeedRight = 0.3;
        double wheel_Speed = 1;

        double Servo_Stop;
        double Servo_Intake;
        double Servo_Outtake;

        float verticalGp1_left;
        float horizontalGp1_left;
        float verticalGp1_right;
        float horizontalGp1_right;

        float verticalGp2_left;
        float horizontalGp2_left;
        float verticalGp2_right;
        float horizontalGp2_right;
        float pivot;

        boolean debugInfo = true;


        WeeeMotor = hardwareMap.get(DcMotor.class, "WeeeMotor");



        ControlHub_ServoController = hardwareMap.get(ServoController.class, "Control Hub");

        //Disable pwm
        ControlHub_ServoController.pwmDisable();


        waitForStart();
        if (opModeIsActive()) {


            WeeeMotor.setDirection(DcMotor.Direction.FORWARD);


            WeeeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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

                WeeeMotor.setPower(verticalGp1_right);

                if (gamepad1.x) {
                    WeeeMotor.setPower(0);
                } else if (gamepad1.y) {
                    WeeeMotor.setPower(0.25);
                } else if (gamepad1.b) {
                    WeeeMotor.setPower(0.5);
                } else if (gamepad1.a) {
                    WeeeMotor.setPower(0.75);
                }

                if (debugInfo) {
                    telemetry.addLine("Wheels:");
                    telemetry.addData("WheelMotor", WeeeMotor.getPower());


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


                    telemetry.addLine("\n Ports:");
                    telemetry.addData("WheelMotor", WeeeMotor.getPortNumber());
                    //telemetry.addData("Color", Functions.GetColor(this, hardwareMap, telemetry, testMode));
                }

            }
        }
    }
}