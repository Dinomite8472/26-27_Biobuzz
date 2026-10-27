package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class Functions {

    public static void pause(double waitTime, com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode) {
        ElapsedTime elapsedTime = new ElapsedTime();

        while (elapsedTime.seconds() < waitTime) {
            if (!opMode.opModeIsActive()) {
                return;
            }
            //STOP, wait a minute
        }
    }

    public static String formatSeconds(double inputSeconds) {
        double fixedValue = Math.floor(inputSeconds * 10) / 10;
        return String.valueOf(fixedValue);
    }

    public static void drive(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, double BackLeft_target, double BackRight_target, double Speed, double FrontLeft_target, double FrontRight_target, boolean testMode) {
        if (!opMode.opModeIsActive()) {
            return;
        }

        // 10in = 600
        // 1in = 60

        final double unitsPerInch = 60;

        DcMotor BackLeft;
        DcMotor BackRight;
        DcMotor FrontLeft;
        DcMotor FrontRight;

        boolean BLPassed = false;
        boolean FLPassed = false;
        boolean BRPassed = false;
        boolean FRPassed = false;

        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");

        BackLeft.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.REVERSE);


        //0 Bl
        //1 Fl
        //2 Br
        //3 Fr

        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        BackLeft.setTargetPosition((int) (BackLeft_target * unitsPerInch));
        BackRight.setTargetPosition((int) (-BackRight_target * unitsPerInch));
        FrontLeft.setTargetPosition((int) (-FrontLeft_target * unitsPerInch));
        FrontRight.setTargetPosition((int) (FrontRight_target * unitsPerInch));

        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BackLeft.setPower(Speed);
        BackRight.setPower(Speed);
        FrontLeft.setPower(Speed);
        FrontRight.setPower(Speed);

        while (opMode.opModeIsActive() && BackLeft.isBusy() && BackRight.isBusy() && FrontLeft.isBusy() && FrontRight.isBusy()) {
            if (testMode) {
                telemetry.addData("bk-left-end", BackLeft.getCurrentPosition() + "," + BackLeft.getPower());
                telemetry.addData("bk-right-end", BackRight.getCurrentPosition() + "," + BackRight.getPower());
                telemetry.addData("fwd-left-end", FrontLeft.getCurrentPosition() + "," + FrontLeft.getPower());
                telemetry.addData("fwd-right-end", FrontRight.getCurrentPosition() + "," + FrontRight.getPower());
                telemetry.addData("Speed", BackLeft_target);
                telemetry.addData("Avg", BackLeft.getCurrentPosition());
                telemetry.addData("Target", BackLeft_target);
                telemetry.addData("%", (Math.abs(BackLeft.getCurrentPosition()) / Math.abs(BackLeft_target * unitsPerInch)));
                telemetry.addData("bk-left-endBusy", BackLeft.isBusy());
                telemetry.addData("bk-right-endBusy", BackRight.isBusy());
                telemetry.addData("fwd-left-endBusy", FrontLeft.isBusy());
                telemetry.addData("fwd-right-endBusy", FrontRight.isBusy());
                telemetry.update();
            }

            if (!opMode.opModeIsActive()) {
                BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                BackLeft.setPower(0);
                BackRight.setPower(0);
                FrontLeft.setPower(0);
                FrontRight.setPower(0);

                return;
            }

            opMode.idle();
        }

        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        BackLeft.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);

    }
/*
    public static void driveDirect(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, double wheel_target, double target_Speed, boolean testMode) {
        if(!opMode.opModeIsActive())
        {
            return;
        }

        // 10in = 600
        // 1in = 60

        final double unitsPerInch = 60;

        DcMotor BackLeft;
        DcMotor BackRight;
        DcMotor FrontLeft;
        DcMotor FrontRight;

        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");

        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);

        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        BackLeft.setTargetPosition((int)(wheel_target * unitsPerInch));
        BackRight.setTargetPosition((int)(wheel_target * unitsPerInch));
        FrontLeft.setTargetPosition((int)(wheel_target * unitsPerInch));
        FrontRight.setTargetPosition((int)(wheel_target * unitsPerInch));

        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BackLeft.setPower(target_Speed);
        BackRight.setPower(target_Speed);
        FrontLeft.setPower(target_Speed);
        FrontRight.setPower(target_Speed);

        boolean threshold1Passed = false;
        boolean threshold2Passed = false;
        boolean threshold3Passed = false;
        boolean threshold4Passed = false;

        double threshold1 = 0.05;
        double threshold2 = 0.1;
        double threshold3 = 0.75;
        double threshold4 = 0.90;

        double speed1 = 1;
        double speed2 = 0.75;
        double speed3 = 0.5;
        double speed4 = 0.25;


        double currentSpeed = 0;
        double newSpeed = speed4;

        while (opMode.opModeIsActive() && BackLeft.isBusy() && BackRight.isBusy() && FrontLeft.isBusy() && FrontRight.isBusy())
        {
            double avgPos = ((BackLeft.getCurrentPosition() + BackRight.getCurrentPosition() + FrontLeft.getCurrentPosition() + FrontRight.getCurrentPosition())/4f);

            if(!threshold1Passed)
            {
                if((avgPos/(wheel_target * unitsPerInch)) >= threshold1)
                {
                    threshold1Passed = true;
                    newSpeed = target_Speed * speed3;
                }
            }
            if(!threshold2Passed)
            {
                if((avgPos/(wheel_target * unitsPerInch)) >= threshold2)
                {
                    threshold2Passed = true;
                    newSpeed = target_Speed * speed1;
                }
            }
            if(!threshold3Passed)
            {
                if((avgPos/(wheel_target * unitsPerInch)) >= threshold3)
                {
                    threshold3Passed = true;
                    newSpeed = target_Speed * speed2;
                }
            }
            if(!threshold4Passed)
            {
                if((avgPos/(wheel_target * unitsPerInch)) >= threshold4)
                {
                    threshold4Passed = true;
                    newSpeed = target_Speed * speed4;
                }
            }

            if(newSpeed != currentSpeed) {
                currentSpeed = newSpeed;
                BackLeft.setPower(currentSpeed);
                BackRight.setPower(currentSpeed);
                FrontLeft.setPower(currentSpeed);
                FrontRight.setPower(currentSpeed);
            }
            if (testMode) {
                telemetry.addData("bk-left-end", BackLeft.getCurrentPosition() + "," + BackLeft.getPower());
                telemetry.addData("bk-right-end", BackRight.getCurrentPosition() + "," + BackRight.getPower());
                telemetry.addData("fwd-left-end", FrontLeft.getCurrentPosition() + "," + FrontLeft.getPower());
                telemetry.addData("fwd-right-end", FrontRight.getCurrentPosition() + "," + FrontRight.getPower());
                telemetry.addData("Speed", currentSpeed);
                telemetry.addData("Avg", avgPos);
                telemetry.addData("Target", wheel_target);
                telemetry.addData("%", (avgPos / (wheel_target * unitsPerInch)));
                telemetry.addData("bk-left-endBusy", BackLeft.isBusy());
                telemetry.addData("bk-right-endBusy", BackRight.isBusy());
                telemetry.addData("fwd-left-endBusy", FrontLeft.isBusy());
                telemetry.addData("fwd-right-endBusy", FrontRight.isBusy());
                telemetry.addData("Threshold 1 Passed", threshold1Passed);
                telemetry.addData("Threshold 2 Passed", threshold2Passed);
                telemetry.addData("Threshold 3 Passed", threshold3Passed);
                telemetry.addData("Threshold 4 Passed", threshold4Passed);
                telemetry.update();
            }

            if(!opMode.opModeIsActive())
            {
                return;
            }

            opMode.idle();
        }

        BackLeft.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
    }

    /*
    public static void viperSlideMove(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, double Arm_target, double Speed, boolean testMode) {
        if(!opMode.opModeIsActive())
        {
            return;
        }

        // 10in = 600
        // 1in = 60

        final double unitsPerInch = 60;

        DcMotor ViperSlideMotorLeft;
        DcMotor ViperSlideMotorRight;

        ViperSlideMotorLeft = hardwareMap.get(DcMotor.class, "ViperSlideMotorLeft");
        ViperSlideMotorRight = hardwareMap.get(DcMotor.class, "ViperSlideMotorRight");

        ViperSlideMotorLeft.setDirection(DcMotor.Direction.FORWARD);
        ViperSlideMotorRight.setDirection(DcMotor.Direction.FORWARD);

        ViperSlideMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ViperSlideMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ViperSlideMotorLeft.setTargetPosition((int)(Arm_target * unitsPerInch));
        ViperSlideMotorRight.setTargetPosition((int)(Arm_target * unitsPerInch));

        ViperSlideMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ViperSlideMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ViperSlideMotorLeft.setPower(0);
        ViperSlideMotorRight.setPower(Speed);

        while (opMode.opModeIsActive() && ViperSlideMotorLeft.isBusy() && ViperSlideMotorRight.isBusy()) {
            if (testMode)
            {
                telemetry.addLine("LeftArm");
                telemetry.addData("ArmMotor", ViperSlideMotorLeft.getCurrentPosition() + "," + ViperSlideMotorLeft.getPower());
                telemetry.addData("Speed", ViperSlideMotorLeft.getPower());
                telemetry.addData("Avg", ViperSlideMotorLeft.getCurrentPosition());
                telemetry.addData("Target", Arm_target);
                telemetry.addData("%", (ViperSlideMotorLeft.getCurrentPosition() / (Arm_target * unitsPerInch)));
                telemetry.addData("ArmMotorBusy", ViperSlideMotorLeft.isBusy());

                telemetry.addLine("RightArm");
                telemetry.addData("RightArmMotor", ViperSlideMotorRight.getCurrentPosition() + "," + ViperSlideMotorRight.getPower());
                telemetry.addData("Speed", ViperSlideMotorRight.getPower());
                telemetry.addData("Avg", ViperSlideMotorRight.getCurrentPosition());
                telemetry.addData("Target", Arm_target);
                telemetry.addData("%", (ViperSlideMotorRight.getCurrentPosition() / (Arm_target * unitsPerInch)));
                telemetry.addData("ArmMotorBusy", ViperSlideMotorRight.isBusy());
                telemetry.update();
            }

            if(!opMode.opModeIsActive())
            {
                return;
            }

            opMode.idle();
        }

        ViperSlideMotorLeft.setPower(0);
        ViperSlideMotorRight.setPower(0);
        return;
    }
     */


    public static void frontArmMove(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, double Arm_target, double Speed, double time, boolean testMode) {
        if (!opMode.opModeIsActive()) {
            return;
        }

        // 10in = 600
        // 1in = 60

        final double unitsPerInch = 60;

        DcMotor FrontArmMotor;

        FrontArmMotor = hardwareMap.get(DcMotor.class, "FrontArmMotor");

        FrontArmMotor.setDirection(DcMotor.Direction.FORWARD);

        FrontArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontArmMotor.setTargetPosition((int) (Arm_target * unitsPerInch));

        FrontArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontArmMotor.setPower(Speed);

        ElapsedTime elapsedTime = new ElapsedTime();

        while (opMode.opModeIsActive() && FrontArmMotor.isBusy() && elapsedTime.seconds() < time) {
            if (testMode) {
                telemetry.addData("ArmMotor", FrontArmMotor.getCurrentPosition() + "," + FrontArmMotor.getPower());
                telemetry.addData("Speed", FrontArmMotor.getPower());
                telemetry.addData("Pos", FrontArmMotor.getCurrentPosition());
                telemetry.addData("Target", Arm_target);
                telemetry.addData("%", (FrontArmMotor.getCurrentPosition() / (Arm_target * unitsPerInch)));
                telemetry.addData("ArmMotorBusy", FrontArmMotor.isBusy());
                telemetry.update();
            }

            if (!opMode.opModeIsActive()) {
                return;
            }

            opMode.idle();
        }

        //FrontArmMotor.setPower(0);
        return;
    }

    public static void frontArmStop(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, boolean testMode) {
        if (!opMode.opModeIsActive()) {
            return;
        }

        // 10in = 600
        // 1in = 60

        final double unitsPerInch = 60;

        DcMotor FrontArmMotor;

        FrontArmMotor = hardwareMap.get(DcMotor.class, "FrontArmMotor");

        FrontArmMotor.setDirection(DcMotor.Direction.FORWARD);

        FrontArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontArmMotor.setTargetPosition((int) (1));

        FrontArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontArmMotor.setPower(0);
        return;
    }


    public static void dropArtifacts(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, com.qualcomm.robotcore.hardware.ServoController ControlHub_ServoController, com.qualcomm.robotcore.hardware.ServoController ExpansionHub2_ServoController, double speed, double defualtPos, double leftPos, double rightPos, boolean testMode) {

        //Define Objects
        Servo DropperServo = null;
        DcMotor WeeeMotor = null;

        //Set objects
        DropperServo = hardwareMap.get(Servo.class, "DropperServo");
        WeeeMotor = hardwareMap.get(DcMotor.class, "WeeeMotor");

        WeeeMotor.setDirection(DcMotor.Direction.REVERSE);

        WeeeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Disable pwm
        ControlHub_ServoController.pwmDisable();

        // Start spinning Motor
        Functions.addComment("Starting up Motor...", opMode, telemetry, testMode);

        DropperServo.setPosition(defualtPos);
        WeeeMotor.setPower(speed);

        Functions.pause(3, opMode);

        // Drop first Artifact
        Functions.addComment("Dropping Artifact #1", opMode, telemetry, testMode);

        DropperServo.setPosition(leftPos);
        Functions.pause(0.375, opMode);
        DropperServo.setPosition(defualtPos);

        // Wait
        Functions.addComment("Waiting...", opMode, telemetry, testMode);
        Functions.pause(3, opMode);

        // Drop second Artifact
        Functions.addComment("Dropping Artifact #2", opMode, telemetry, testMode);

        DropperServo.setPosition(rightPos);
        Functions.pause(2, opMode);
        DropperServo.setPosition(defualtPos);
    }


    public static void launchArtifacts(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, com.qualcomm.robotcore.hardware.ServoController ControlHub_ServoController, com.qualcomm.robotcore.hardware.ServoController ExpansionHub2_ServoController, double speed, double KServo_Stop, double KServo_UpPos, float cDistance_Full, float carousel_Speed, boolean testMode) {

        //Define Objects
        Servo KickerServo = null;
        DcMotor LauncherMotor = null;

        //Set objects
        KickerServo = hardwareMap.get(Servo.class, "KickerServo");
        LauncherMotor = hardwareMap.get(DcMotor.class, "LauncherMotor");

        LauncherMotor.setDirection(DcMotor.Direction.REVERSE);

        LauncherMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Disable pwm
        ControlHub_ServoController.pwmDisable();

        // Start spinning Motor
        Functions.addComment("Starting up Motor...", opMode, telemetry, testMode);

        KickerServo.setPosition(KServo_Stop);
        LauncherMotor.setPower(speed);

        Functions.pause(3, opMode);

        // Drop first Artifact
        Functions.addComment("Dropping Artifact #1", opMode, telemetry, testMode);

        KickerServo.setPosition(KServo_UpPos);
        Functions.pause(1, opMode);
        KickerServo.setPosition(KServo_Stop);

        // Wait
        Functions.addComment("Waiting...", opMode, telemetry, testMode);
        Functions.moveCarousel(opMode, hardwareMap, telemetry, cDistance_Full, carousel_Speed, testMode);

        // Drop second Artifact
        Functions.addComment("Dropping Artifact #2", opMode, telemetry, testMode);

        KickerServo.setPosition(KServo_UpPos);
        Functions.pause(1, opMode);
        KickerServo.setPosition(KServo_Stop);

        // Wait
        Functions.addComment("Waiting...", opMode, telemetry, testMode);
        Functions.moveCarousel(opMode, hardwareMap, telemetry, cDistance_Full, carousel_Speed, testMode);

        // Drop third Artifact
        Functions.addComment("Dropping Artifact #3", opMode, telemetry, testMode);

        KickerServo.setPosition(KServo_UpPos);
        Functions.pause(1, opMode);
        KickerServo.setPosition(KServo_Stop);

        // Stop

        LauncherMotor.setPower(0);

    }

    public static void moveCarousel(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, double cDistance_Full, double carousel_Speed, boolean testMode) {

        DcMotor CarouselMotor = null;

        CarouselMotor = hardwareMap.get(DcMotor.class, "CarouselMotor");

        CarouselMotor.setDirection(DcMotor.Direction.FORWARD);

        CarouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        CarouselMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        CarouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        CarouselMotor.setTargetPosition((int)(cDistance_Full));

        CarouselMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        CarouselMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        CarouselMotor.setPower(carousel_Speed);

        while (opMode.opModeIsActive() && CarouselMotor.isBusy())
        {
            telemetry.addData("Motor:", CarouselMotor.getDeviceName());
            telemetry.addData("Speedy:", CarouselMotor.getPower() + String.valueOf(carousel_Speed));
            telemetry.addData("Distance:", CarouselMotor.getCurrentPosition());
            telemetry.addData("Target:", CarouselMotor.getTargetPosition());
            telemetry.addData("%:", (CarouselMotor.getCurrentPosition()/(CarouselMotor.getTargetPosition()+0.0000001)));
            telemetry.addData("IsBusy:", CarouselMotor.isBusy());
            telemetry.update();
        }

        CarouselMotor.setPower(0);
        CarouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public static void turn(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, String direction, double Speed, boolean testMode) {
        if(!opMode.opModeIsActive())
        {
            return;
        }

        DcMotor BackLeft;
        DcMotor FrontLeft;
        DcMotor FrontRight;
        DcMotor BackRight;

        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");

        BackLeft.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.REVERSE);

        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int distance = 1300;

        if(direction == "Half-Right") {
            distance /= 2;
            BackLeft.setTargetPosition(distance);
            BackRight.setTargetPosition(distance);
            FrontLeft.setTargetPosition(-distance);
            FrontRight.setTargetPosition(-distance);
        } else if (direction == "Half-Left") {
            distance /= 2;
            BackLeft.setTargetPosition(-distance);
            BackRight.setTargetPosition(-distance);
            FrontLeft.setTargetPosition(distance);
            FrontRight.setTargetPosition(distance);
        } else if (direction == "Right") {
            BackLeft.setTargetPosition(distance);
            BackRight.setTargetPosition(distance);
            FrontLeft.setTargetPosition(-distance);
            FrontRight.setTargetPosition(-distance);
        } else if (direction == "Slight-Left") {
            distance /= 2;
            distance = distance - 50;
            BackLeft.setTargetPosition(-distance);
            BackRight.setTargetPosition(-distance);
            FrontLeft.setTargetPosition(distance);
            FrontRight.setTargetPosition(distance);
        } else if (direction == "Slight-Right") {
            distance /= 2;
            distance = distance + 100;
            BackLeft.setTargetPosition(distance);
            BackRight.setTargetPosition(distance);
            FrontLeft.setTargetPosition(-distance);
            FrontRight.setTargetPosition(-distance);            
        } else if (direction == "Left") {
            distance = distance - 200;
            BackLeft.setTargetPosition(-distance);
            BackRight.setTargetPosition(-distance);
            FrontLeft.setTargetPosition(distance);
            FrontRight.setTargetPosition(distance);
        } else if (direction == "Rightx4") {
            distance = distance*4;
            BackLeft.setTargetPosition(distance);
            BackRight.setTargetPosition(distance);
            FrontLeft.setTargetPosition(-distance);
            FrontRight.setTargetPosition(-distance);
        } else if (direction == "Leftx4") {
            distance = distance*4;
            BackLeft.setTargetPosition(-distance);
            BackRight.setTargetPosition(-distance);
            FrontLeft.setTargetPosition(distance);
            FrontRight.setTargetPosition(distance);
        }   else if (direction == "Rightx2") {
            distance = distance * 2;
            BackLeft.setTargetPosition(distance);
            BackRight.setTargetPosition(distance);
            FrontLeft.setTargetPosition(-distance);
            FrontRight.setTargetPosition(-distance);
        }

        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BackLeft.setPower(Speed);
        BackRight.setPower(Speed);
        FrontLeft.setPower(Speed);
        FrontRight.setPower(Speed);

        while (opMode.opModeIsActive() && BackLeft.isBusy() && BackRight.isBusy() && FrontLeft.isBusy() && FrontRight.isBusy())
        {
            if (testMode) {
                telemetry.addData("bk-left-end", BackLeft.getCurrentPosition() + "," + BackLeft.getPower());
                telemetry.addData("bk-right-end", BackRight.getCurrentPosition() + "," + BackRight.getPower());
                telemetry.addData("fwd-left-end", FrontLeft.getCurrentPosition() + "," + FrontLeft.getPower());
                telemetry.addData("fwd-right-end", FrontRight.getCurrentPosition() + "," + FrontRight.getPower());
                telemetry.addData("bk-left-endBusy", BackLeft.isBusy());
                telemetry.addData("bk-right-endBusy", BackRight.isBusy());
                telemetry.addData("fwd-left-endBusy", FrontLeft.isBusy());
                telemetry.addData("fwd-right-endBusy", FrontRight.isBusy());
                telemetry.update();
            }

            if(!opMode.opModeIsActive())
            {
                return;
            }

            opMode.idle();
        }

        BackLeft.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);

    }

    public static void turn2(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, Integer distance, String direction, double Speed, boolean testMode) {
        if(!opMode.opModeIsActive())
        {
            return;
        }

        DcMotor BackLeft;
        DcMotor FrontLeft;
        DcMotor FrontRight;
        DcMotor BackRight;

        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");

        BackLeft.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.REVERSE);

        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BackLeft.setPower(Speed);
        BackRight.setPower(Speed);
        FrontLeft.setPower(Speed);
        FrontRight.setPower(Speed);

        if (direction.equals("Left")) {
            BackLeft.setTargetPosition(-distance);
            BackRight.setTargetPosition(-distance);
            FrontLeft.setTargetPosition(distance);
            FrontRight.setTargetPosition(distance);
        }
        else {
            BackLeft.setTargetPosition(distance);
            BackRight.setTargetPosition(distance);
            FrontLeft.setTargetPosition(-distance);
            FrontRight.setTargetPosition(-distance);
        }

        while (opMode.opModeIsActive() && BackLeft.isBusy() && BackRight.isBusy() && FrontLeft.isBusy() && FrontRight.isBusy())
        {
            if (testMode) {
                telemetry.addData("bk-left-end", BackLeft.getCurrentPosition() + "," + BackLeft.getPower());
                telemetry.addData("bk-right-end", BackRight.getCurrentPosition() + "," + BackRight.getPower());
                telemetry.addData("fwd-left-end", FrontLeft.getCurrentPosition() + "," + FrontLeft.getPower());
                telemetry.addData("fwd-right-end", FrontRight.getCurrentPosition() + "," + FrontRight.getPower());
                telemetry.addData("bk-left-endBusy", BackLeft.isBusy());
                telemetry.addData("bk-right-endBusy", BackRight.isBusy());
                telemetry.addData("fwd-left-endBusy", FrontLeft.isBusy());
                telemetry.addData("fwd-right-endBusy", FrontRight.isBusy());
                telemetry.update();
            }

            if(!opMode.opModeIsActive())
            {
                return;
            }

            opMode.idle();
        }

        BackLeft.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);

    }

    public static void driveUntilDistance(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, double distance, double maxDistance, double Speed, boolean testMode) {
        if(!opMode.opModeIsActive())
        {
            return;
        }

        // 10in = 600
        // 1in = 60

        final double unitsPerInch = 60;

        final double tolerance = 1.5;
        double distanceLeft = 0; //Distance.GetDistanceLeft(opMode, hardwareMap, telemetry);
        double distanceCenter = maxDistance;

        DcMotor BackLeft;
        DcMotor BackRight;
        DcMotor FrontLeft;
        DcMotor FrontRight;

        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");

        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);

        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /*
        BackLeft.setTargetPosition((int)((distanceLeft - distance) * unitsPerInch));
        BackRight.setTargetPosition((int)((distanceCenter - distance) * unitsPerInch));
        FrontLeft.setTargetPosition((int)((distanceLeft - distance) * unitsPerInch));
        FrontRight.setTargetPosition((int)((distanceCenter - distance) * unitsPerInch));
        */

        BackLeft.setTargetPosition((int)(maxDistance * unitsPerInch));
        BackRight.setTargetPosition((int)(maxDistance * unitsPerInch));
        FrontLeft.setTargetPosition((int)(maxDistance * unitsPerInch));
        FrontRight.setTargetPosition((int)(maxDistance * unitsPerInch));


        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BackLeft.setPower(Speed);
        BackRight.setPower(Speed);
        FrontLeft.setPower(Speed);
        FrontRight.setPower(Speed);

        DcMotor[] WHEELS = new DcMotor[4];
        WHEELS[0] = BackLeft;
        WHEELS[1] = BackRight;
        WHEELS[2] = FrontLeft;
        WHEELS[3] = FrontRight;

        while (opMode.opModeIsActive() && (distanceCenter > distance)) {

            distanceCenter = 0;//Distance.GetDistanceRight(opMode, hardwareMap, telemetry);

            BackLeft.setPower(Speed);
            FrontLeft.setPower(Speed);
            BackRight.setPower(Speed);
            FrontRight.setPower(Speed);

            if (testMode)
            {
                telemetry.addLine("Wheels:");
                telemetry.addData("bk-left-end", BackLeft.getCurrentPosition() + "," + BackLeft.getPower());
                telemetry.addData("bk-right-end", BackRight.getCurrentPosition() + "," + BackRight.getPower());
                telemetry.addData("fwd-left-end", FrontLeft.getCurrentPosition() + "," + FrontLeft.getPower());
                telemetry.addData("fwd-right-end", FrontRight.getCurrentPosition() + "," + FrontRight.getPower());
                telemetry.addData("bk-left-endBusy", BackLeft.isBusy());
                telemetry.addData("bk-right-endBusy", BackRight.isBusy());
                telemetry.addData("fwd-left-endBusy", FrontLeft.isBusy());
                telemetry.addData("fwd-right-endBusy", FrontRight.isBusy());

                telemetry.addLine("\nDistanceSensor:");
                telemetry.addData("DistanceSensor", distanceCenter);
                telemetry.addData("Distance", distance);
                telemetry.addData("Is there", distanceCenter > distance);

                telemetry.update();
            }


            if(!opMode.opModeIsActive())
            {
                return;
            }

            opMode.idle();
        }

        BackLeft.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);

    }

    public static void hangSpec1(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, double Speed, boolean testMode){
        if(!opMode.opModeIsActive())
        {
            return;
        }

        CRServo ClawArmServo;
        ServoController ControlHub_ServoController;

        ControlHub_ServoController = hardwareMap.get(ServoController.class, "Control Hub");
        ClawArmServo = hardwareMap.get(CRServo.class, "ClawArmServo");

        //Disable pwm
        ControlHub_ServoController.pwmDisable();

        ClawArmServo.setPower(1);

//10.78
        Functions.drive(opMode, hardwareMap, telemetry, 13.5, 13.5, Speed, 13.5, 13.5, testMode);

        //Functions.driveUntilDistance(opMode, hardwareMap, telemetry, 11.1, 47, Speed, testMode);

        Functions.frontArmMove(opMode, hardwareMap, telemetry, 16, 0.65, 1.25, testMode);
        Functions.drive(opMode, hardwareMap, telemetry, -4.5, -4.5, 0.135, -4.5, -4.5, testMode);
        Functions.frontArmMove(opMode, hardwareMap, telemetry, 16, 0.65, 0.25, testMode);
        ClawArmServo.setPower(-1);
        Functions.pause(0.25, opMode);
        Functions.frontArmStop(opMode, hardwareMap, telemetry, testMode);

        Functions.frontArmMove(opMode, hardwareMap, telemetry, -12, 0.3, 1.2, testMode);
        Functions.frontArmStop(opMode, hardwareMap, telemetry, testMode);
        Functions.reset(opMode, hardwareMap, telemetry, testMode);
        return;
    }

    public static void hangSpec2(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, double Speed, boolean testMode){
        if(!opMode.opModeIsActive())
        {
            return;
        }

        CRServo ClawArmServo;
        ServoController ControlHub_ServoController;

        ControlHub_ServoController = hardwareMap.get(ServoController.class, "Control Hub");
        ClawArmServo = hardwareMap.get(CRServo.class, "ClawArmServo");

        //Disable pwm
        ControlHub_ServoController.pwmDisable();

        ClawArmServo.setPower(0);

//10.78
        Functions.driveUntilDistance(opMode, hardwareMap, telemetry, 10.8, 47, Speed, testMode);
        Functions.pause(0.25, opMode);

        Functions.frontArmMove(opMode, hardwareMap, telemetry, 7, 0.1, 1, testMode);
        Functions.pause(2, opMode);
        ClawArmServo.setPower(-1);
        Functions.drive(opMode, hardwareMap, telemetry, -7.4, -7.4, 0.15, -7.4, -7.4, testMode);
        Functions.frontArmStop(opMode, hardwareMap, telemetry, testMode);
        Functions.pause(0.25, opMode);

        Functions.frontArmStop(opMode, hardwareMap, telemetry, testMode);
        ClawArmServo.setPower(-1);
        Functions.frontArmMove(opMode, hardwareMap, telemetry, -15, 0.25, 1, testMode);
        Functions.pause(2, opMode);
        Functions.frontArmStop(opMode, hardwareMap, telemetry, testMode);
        Functions.reset(opMode, hardwareMap, telemetry, testMode);
        return;
    }

    public static void specDistance(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, double Speed, boolean testMode){
        if(!opMode.opModeIsActive())
        {
            return;
        }

        CRServo ClawArmServo;
        ServoController ControlHub_ServoController;

        ControlHub_ServoController = hardwareMap.get(ServoController.class, "Control Hub");
        ClawArmServo = hardwareMap.get(CRServo.class, "ClawArmServo");

        //Disable pwm
        ControlHub_ServoController.pwmDisable();

        ClawArmServo.setPower(0);

//10.78
        Functions.driveUntilDistance(opMode, hardwareMap, telemetry, 11, 47, Speed, testMode);
        Functions.pause(0.25, opMode);

        Functions.reset(opMode, hardwareMap, telemetry, testMode);
        return;
    }

    public static String GetColor(LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, boolean testMode) {

        try {
            ColorSensor colorSensor;
            colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

            int redC = colorSensor.red();
            int greenC = colorSensor.green();
            int blueC = colorSensor.blue();

            if (testMode) {
                telemetry.addData("Red", redC);
                telemetry.addData("Green", greenC);
                telemetry.addData("Blue", blueC);
            }

            if (redC == greenC && greenC == blueC) {
                return null;
            }

            if (redC > greenC) {
                if (redC > blueC) {
                    return "Red";
                } else if (redC == blueC) {
                    return "Red & Blue";
                }
            }

            if (greenC > blueC) {
                if (greenC > redC) {
                    return "Green";
                } else if (greenC == redC) {
                    return "Red & Green";
                }
            }

            if (blueC > redC) {
                if (blueC > greenC) {
                    return "Blue";
                } else if (blueC == greenC) {
                    return "Green & Blue";
                }
            }
        }
        catch (Error error) {
            Functions.addComment(error.toString() ,opMode, telemetry, testMode);
        }

        return null;
    }

    public static String CheckArtifactColor(LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, boolean testMode) {

        try {
            ColorSensor colorSensor;
            colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

            int redC = colorSensor.red();
            int greenC = colorSensor.green();
            int blueC = colorSensor.blue();

            if (testMode) {
                telemetry.addData("Red", redC);
                telemetry.addData("Green", greenC);
                telemetry.addData("Blue", blueC);
            }

            if (redC == greenC && greenC == blueC) {
                return null;
            }

            if (redC > greenC) {
                if (redC > blueC) {
                    return "Red";
                } else if (redC == blueC) {
                    return "Red & Blue";
                }
            }

            if (greenC > blueC) {
                if (greenC > redC) {
                    return "Green";
                } else if (greenC == redC) {
                    return "Red & Green";
                }
            }

            if (blueC > redC) {
                if (blueC > greenC) {
                    return "Blue";
                } else if (blueC == greenC) {
                    return "Green & Blue";
                }
            }
        }
        catch (Error error) {
            Functions.addComment(error.toString() ,opMode, telemetry, testMode);
        }

        return null;
    }

    public static void reset(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, boolean testMode) {
        if (!opMode.opModeIsActive()) {
            return;
        }

        DcMotor BackLeft;
        DcMotor BackRight;
        DcMotor FrontLeft;
        DcMotor FrontRight;

        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");

        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
    }

    /*
    public static void driveUntilDistanceKiro(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, double distance, double Speed, boolean testMode) {
                if(!opMode.opModeIsActive())
        {
            return;
        }

        // 10in = 600

        // 1in = 60

        final double unitsPerInch = 60;

        final double tolerance = 1.5;

        double distanceLeft = Distance.GetDistanceLeft(opMode, hardwareMap, telemetry);
        double distanceCenter = Distance.GetDistanceRight(opMode, hardwareMap, telemetry);

        DcMotor BackLeft;
        DcMotor BackRight;
        DcMotor FrontLeft;
        DcMotor FrontRight;

        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");

        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);

        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        BackLeft.setTargetPosition((int) ((distanceLeft - distance) * unitsPerInch));
        BackRight.setTargetPosition((int) ((distanceCenter - distance) * unitsPerInch));
        FrontLeft.setTargetPosition((int) ((distanceLeft - distance) * unitsPerInch));
        FrontRight.setTargetPosition((int) ((distanceCenter - distance) * unitsPerInch));

        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        DcMotor[] WHEELS = new DcMotor[4];
        WHEELS[0] = BackLeft;
        WHEELS[1] = BackRight;
        WHEELS[2] = FrontLeft;
        WHEELS[3] = FrontRight;

        while (opMode.opModeIsActive() && (BackLeft.isBusy() || BackRight.isBusy() || FrontLeft.isBusy() || FrontRight.isBusy())) {

            // If either left wheels aren't busy then stop left wheels
            if (!(BackLeft.isBusy() && FrontLeft.isBusy())) {
                BackLeft.setPower(0);
                FrontLeft.setPower(0);
            }

            // If either right wheels aren't busy then stop right wheels
            if (!(BackRight.isBusy() && FrontRight.isBusy())) {
                BackRight.setPower(0);
                FrontRight.setPower(0);
            }

            if (testMode) {
                distanceLeft = Distance.GetDistanceLeft(opMode, hardwareMap, telemetry);
                distanceCenter = Distance.GetDistanceRight(opMode, hardwareMap, telemetry);
                ;

                telemetry.addLine("Wheels:");
                telemetry.addData("bk-left-end", BackLeft.getCurrentPosition() + "," + BackLeft.getPower());
                telemetry.addData("bk-right-end", BackRight.getCurrentPosition() + "," + BackRight.getPower());
                telemetry.addData("fwd-left-end", FrontLeft.getCurrentPosition() + "," + FrontLeft.getPower());
                telemetry.addData("fwd-right-end", FrontRight.getCurrentPosition() + "," + FrontRight.getPower());
                telemetry.addData("bk-left-endBusy", BackLeft.isBusy());
                telemetry.addData("bk-right-endBusy", BackRight.isBusy());
                telemetry.addData("fwd-left-endBusy", FrontLeft.isBusy());
                telemetry.addData("fwd-right-endBusy", FrontRight.isBusy());

                telemetry.addLine("\nDistanceSensor:");
                telemetry.addData("LeftDistanceSensor", distanceLeft);
                telemetry.addData("RightDistanceSensor", distanceCenter);

                telemetry.addLine("\nDistanceLeftToGo:");
                telemetry.addData("LeftWheels", distanceLeft - ((BackLeft.getCurrentPosition() + FrontLeft.getCurrentPosition()) / 2));
                telemetry.addData("RightWheels", distanceCenter - ((BackRight.getCurrentPosition() + FrontRight.getCurrentPosition()) / 2));

                while (distanceCenter > distance) {
                    BackLeft.setPower(1.0);
                    FrontLeft.setPower(1.0);
                    BackRight.setPower(1.0);
                    FrontRight.setPower(1.0);

                }


                telemetry.update();
            }

            if (!opMode.opModeIsActive()) {
                return;
            }

            opMode.idle();
        }

        BackLeft.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);

    }


/*
        public static void dropYellow(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, String Direction, double speed, double time, com.qualcomm.robotcore.hardware.ServoController ControlHub_ServoController, com.qualcomm.robotcore.hardware.ServoController ExpansionHub2_ServoController) {

            //Define CRServos
            CRServo FrontArmServo = null;

            //Set servos
            FrontArmServo = hardwareMap.get(CRServo.class, "FrontArmServo");

            //Disable pwm
            ControlHub_ServoController.pwmDisable();

            // Drop Pixel
            telemetry.addData("Currently going: ", "Up");
            telemetry.update();

            FrontArmServo.setPower(0.7);
            Functions.pause(1.5);
            FrontArmServo.setPower(0);

            // Wait
            Functions.pause(0.6);


            telemetry.addData("Currently going: ", "Down");
            telemetry.update();

            // Bring down Dropigimigigy6
            FrontArmServo.setPower(-0.7);
            Functions.pause(1);
            FrontArmServo.setPower(0);

            telemetry.addData("Currently going: ", "to");
            telemetry.update();
        }   ````````````````````````````````````````````````````````````````````````````````````````````````
/*
        public static void slideUp(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, com.qualcomm.robotcore.hardware.ServoController ControlHub_ServoController, com.qualcomm.robotcore.hardware.ServoController ExpansionHub2_ServoController) {
/*

            //Define CRServos
            CRServo ExpandControl = null;

            //Set servos
            ExpandControl = hardwareMap.get(CRServo.class, "ExpandControl");

            //Disable pwm
            ControlHub_ServoController.pwmDisable();
            ExpansionHub2_ServoController.pwmDisable();

            //Slide to the Up
            ExpandControl.setPower(1);

            telemetry.addData("ExpandControl power: ", ExpandControl.getPower());
            telemetry.update();

        }

        public static void slideStop(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, com.qualcomm.robotcore.hardware.ServoController ControlHub_ServoController, com.qualcomm.robotcore.hardware.ServoController ExpansionHub2_ServoController) {
/*
            //Define CRServos
            CRServo ExpandControl = null;

            //Set servos
            ExpandControl = hardwareMap.get(CRServo.class, "ExpandControl");

            //Disable pwm
            ControlHub_ServoController.pwmDisable();
            ExpansionHub2_ServoController.pwmDisable();

            //Slide to the Up
            //ExpandControl.setPower(0);

            telemetry.addData("ExpandControl power: ", ExpandControl.getPower());
            telemetry.update();


        }

        public static void straighten(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, double baseLine) {

            DcMotor BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
            DcMotor FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
            DcMotor FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
            DcMotor BackRight = hardwareMap.get(DcMotor.class, "BackRight");


            FrontLeft.setDirection(DcMotor.Direction.REVERSE);
            FrontRight.setDirection(DcMotor.Direction.FORWARD);
            BackLeft.setDirection(DcMotor.Direction.REVERSE);
            BackRight.setDirection(DcMotor.Direction.FORWARD);

            BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            IMU imu = hardwareMap.get(IMU.class, "imu");

            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();

            double newangle = orientation.getYaw(AngleUnit.DEGREES);

            double difference = baseLine - newangle;
            difference = difference;

            if(difference > 0) {
                FrontLeft.setPower(-0.3);
                FrontRight.setPower(0.3);
                BackLeft.setPower(-0.3);
                BackRight.setPower(0.3);
            } else { //if(difference < 0)
                FrontLeft.setPower(0.3);
                FrontRight.setPower(-0.3);
                BackLeft.setPower(0.3);
                BackRight.setPower(-0.3);
            }

            telemetry.addData("Difference", difference);
            telemetry.update();


            while (opMode.opModeIsActive() && Math.abs(difference) > 1) {

                orientation = imu.getRobotYawPitchRollAngles();

                newangle = orientation.getYaw(AngleUnit.DEGREES);

                difference = baseLine - newangle;

                if(difference > 90)
                {
                    difference = difference - 90;
                }
                else if(difference < -90)
                {
                    difference = difference + 90;
                }


                telemetry.addData("Baseline", baseLine);
                telemetry.addData("Difference", difference);
                telemetry.addData("Newangle", newangle);
                telemetry.update();
            }

            telemetry.addData("Finish", "Fish ish");
            telemetry.update();

            // turn the motors off.
            FrontLeft.setPower(0);
            FrontRight.setPower(0);
            BackLeft.setPower(0);
            BackRight.setPower(0);
        }

        public static double getBaseLine(com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, org.firstinspires.ftc.robotcore.external.Telemetry telemetry) {

            IMU imu = hardwareMap.get(IMU.class, "imu");

            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();

            return orientation.getYaw(AngleUnit.DEGREES);
        }

 */
    public static void addComment( String comment, com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, org.firstinspires.ftc.robotcore.external.Telemetry telemetry, boolean testMode) {
        if(!testMode) {
            return;
        }

        telemetry.addLine(comment);
        telemetry.update();
    }

    public static List<LLResultTypes.FiducialResult> getLLAprilTags(Limelight3A limelight) {
        LLResult limelightResult = limelight.getLatestResult();
        if (limelightResult != null && limelightResult.isValid()) {
            return limelightResult.getFiducialResults();
        }
        return null;
    }


    public static void outputLLAprilTags (Limelight3A limelight, Telemetry telemetry) {
        LLResult limelightResult = limelight.getLatestResult();

        telemetry.addLine("\nLimelight Data:");

        if (limelightResult != null && limelightResult.isValid()) {
            List<LLResultTypes.FiducialResult> fiducials = limelightResult.getFiducialResults();

            for (LLResultTypes.FiducialResult fiducial : fiducials) {
                int id = fiducial.getFiducialId(); // The ID number of the AprilTag
                double tx = fiducial.getTargetXDegrees(); // X offset in degrees
                double ty = fiducial.getTargetYDegrees(); // Y offset in degrees
                double ta = fiducial.getTargetArea(); // Area percentage of the tag

                telemetry.addData("ID", id);
                telemetry.addData("tx", tx);
                telemetry.addData("ty", ty);
                telemetry.addData("ta", ta);
            }
            return;
        }

        telemetry.addLine("N/A");
    }

    public static int LimeLightPipelines(String pipeline) {
        switch (pipeline) {
            case "LLAprilTags":
                return 0;
            case "LLAprilTags2":
                return 1;
            case "LLBlobDetection(Yellow)":
                return 2;
            default:
                return -1;
        }
    }
}