package org.firstinspires.ftc.teamcode.TeleOp;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.OpModeStorage.*;

import org.firstinspires.ftc.teamcode.OpModeStorage;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "(Use this) Bio-Pedro Teleop")
public class BioPedroTeleop extends OpMode {
    private Follower follower;

    Pose startPose;

    int scenario = 0;

    DcMotorEx Intake;
    DcMotorEx Launcher;

    Servo Stopper;

    ServoController ControlHub_ServoController;
    ServoController ExpansionHub_ServoController;

    double wheel_Speed = 0.75;
    double launcher_Speed = defLauncher;
    double intake_Speed = defIntake;

    boolean doIntake = false;
    boolean doLauncher = false;
    boolean stopperUp = false;

    long timer1 = 0;
    long timer2 = 0;
    boolean condition1 = false;
    boolean condition2 = false;
    int timesRun = 0;

    boolean autoDrive = false;
    boolean runningRoutineNoDrive = false;
    boolean runningRoutineYesDrive = false;

    String routine = "None";
    String routineLog = "";

    boolean launch1 = false;
    boolean launchAll = false;

    public void init() {
        follower = Constants.create(hardwareMap);

        Intake = hardwareMap.get(DcMotorEx .class, "Intake");
        Launcher = hardwareMap.get(DcMotorEx .class, "Launcher");
        Stopper = hardwareMap.get(Servo.class, "Stopper");

        ControlHub_ServoController = hardwareMap.get(ServoController.class, "Control Hub");
        ExpansionHub_ServoController = hardwareMap.get(ServoController.class, "Expansion Hub");

        ControlHub_ServoController.pwmDisable();
        ExpansionHub_ServoController.pwmDisable();

        Intake.setDirection(DcMotorSimple.Direction.REVERSE);
        Launcher.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void init_loop() { // only so I can use gamepad :p
        if(startPose == null) {

            if (OpModeStorage.autonomousEndPose != null) {
                telemetry.addLine("Saved pose found: Use it? \n[Y] yes, [A] no (discard)");

                if(gamepad1.yWasPressed() || gamepad2.yWasPressed()) {
                    startPose = OpModeStorage.autonomousEndPose;
                    scenario = 1;
                }

                if(gamepad1.aWasPressed() || gamepad2.aWasPressed()) {
                    OpModeStorage.autonomousEndPose = null;
                }
            } else {
                telemetry.addLine("No auto pose found: Please select start position (relative to you) \n[DLeft] Left, [DRight] Right");

                if (gamepad1.dpadLeftWasPressed() || gamepad2.dpadLeftWasPressed()) {
                    startPose = parkPoseBtm;
                    scenario = 2;
                }
                if (gamepad1.dpadRightWasPressed() || gamepad2.dpadRightWasPressed()) {
                    startPose = parkPoseTop;
                    scenario = 3;
                }
                telemetry.update();
            }

            if(startPose != null) {
                if (scenario == 0) {
                    telemetry.addLine("*** No start pose selected!!! ***");
                    telemetry.update();
                    return;
                } else if (scenario == 1) {
                    telemetry.addLine("Found saved auto pose, starting from: " + startPose.x() + ", " + startPose.y() + " @" + startPose.heading());
                } else if (scenario == 2) {
                    telemetry.addLine("Left selected, starting from: " + startPose.x() + ", " + startPose.y() + " @" + startPose.heading());
                } else if (scenario == 3) {
                    telemetry.addLine("Right selected, starting from: " + startPose.x() + ", " + startPose.y() + " @" + startPose.heading());
                }

                // is this efficient? Nope! Does it work? Heck yeah! - pa173178

                telemetry.addLine("\n***You may now safely start opmode***");

                telemetry.update();
            }
        }
    }

    public void start(){
        if(startPose == null) {
            telemetry.addLine("No start pose selected, terminating to prevent crash");
            telemetry.update();
            opMode.requestOpModeStop();
        } else {
            follower.setPose(startPose);
            follower.update();
        }
    }

    public void loop() {
        if(!autoDrive) {
            ManualDrive.driveOrHold(
                    follower,
                    -gamepad1.left_stick_y * wheel_Speed,
                    -gamepad1.left_stick_x * wheel_Speed,
                    (-gamepad1.right_stick_x * wheel_Speed) / 2
            );
        }

        // relocalise button
        if (gamepad1.startWasPressed()) {
            autoDrive = false;
        }

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

        if (gamepad1.dpadRightWasPressed() && intake_Speed < 1) {
            intake_Speed += 0.125;
        }

        //Optional way to change slow mode strength
        if (gamepad1.dpadLeftWasPressed() && intake_Speed > 0) {
            intake_Speed -= 0.125;
        }

        if (gamepad1.yWasPressed() && !runningRoutineYesDrive) {
            doLauncher = !doLauncher;
        }

        if (gamepad1.bWasPressed()) {
            doIntake = !doIntake;
        }

        if (gamepad1.xWasPressed() && !runningRoutineYesDrive) {
            stopperUp = !stopperUp;
        }

        if (gamepad2.xWasPressed() && !runningRoutineYesDrive) { // launch 1
            launch1 = true;
            runningRoutineYesDrive = true;
            stopperUp = false;
            routine = "Launch 1";
            Stopper.setPosition(StopperServo_Normal);
            timer1 = System.currentTimeMillis();
        }

        if(gamepad2.yWasPressed() && !runningRoutineYesDrive) { // launch all
            launchAll = true;
            runningRoutineYesDrive = true;
            stopperUp = false;
            routine = "Launch All";
            timesRun = 0;
            Stopper.setPosition(StopperServo_Normal);
            timer1 = System.currentTimeMillis();
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


        // * Routines *

        if(runningRoutineYesDrive) {
            if (launch1) {
                Launcher.setPower(launcher_Speed);
                if(!condition1 && System.currentTimeMillis()-timer1 >= tLaunchDelay)
                {
                    condition1 = true;
                    timer2 = System.currentTimeMillis();
                } else if(condition1) {
                    Stopper.setPosition(StopperServo_Up);
                    if(System.currentTimeMillis()-timer2 >= tStopperUp) {
                        Stopper.setPosition(StopperServo_Normal);
                        Launcher.setPower(0);

                        condition1 = false;
                        runningRoutineYesDrive = false;
                        launch1 = false;
                    } else {
                        routineLog = "Waiting for pollen to be launched";
                    }
                } else {
                    routineLog = "Waiting for launcher to get to target speed";
                }
            }

            if(launchAll) {
                if(timesRun < 1) {
                    Launcher.setPower(launcher_Speed);
                    if (!condition1 && System.currentTimeMillis() - timer1 >= tLaunchDelay) {
                        condition1 = true;
                        timer2 = System.currentTimeMillis();
                    } else if (condition1) {
                        Stopper.setPosition(StopperServo_Up);
                        if (System.currentTimeMillis() - timer2 >= tStopperUp) {
                            Stopper.setPosition(StopperServo_Normal);

                            condition1 = false;
                            timer1 = System.currentTimeMillis();
                            timesRun++;
                        } else {
                            routineLog = "Waiting for pollen to be launched";
                        }
                    } else {
                        routineLog = "Waiting for launcher to get to target speed";
                    }
                } else {
                    Launcher.setPower(0);

                    runningRoutineYesDrive = false;
                    launch1 = false;
                }
            }
        }

        follower.update();
        Pose robotPose = follower.pose(); // returns a Pose object
        if(!runningRoutineNoDrive) {
            telemetry.addLine("Robot Position:");
            telemetry.addData("Robot X", robotPose.x());
            telemetry.addData("Robot Y", robotPose.y());
            telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));

            telemetry.addLine("\nWhat is the robot currently doing:");
            telemetry.addData("Follower Mode", follower.mode());
            if (runningRoutineYesDrive) {
                telemetry.addData("***Running routine***", routine);
                telemetry.addData("Log", routineLog);
                telemetry.addData("Times Run", timesRun);
                telemetry.addData("Condition 1", condition1);
                telemetry.addData("Condition 2", condition2);
                telemetry.addData("Timer 1", System.currentTimeMillis()-timer1);
                telemetry.addData("Timer 2", System.currentTimeMillis()-timer2);


            }

            if (follower.mode() != Follower.Mode.MANUAL) {
                telemetry.addData("Current Path", follower.currentPath());
            }

            telemetry.addLine("\nUseful info:");
            telemetry.addData("Autodrive", autoDrive);
            telemetry.addData("Drivechain speed", wheel_Speed);
            telemetry.addData("Launcher speed", launcher_Speed);
            telemetry.addData("Intake speed", intake_Speed);
            telemetry.addData("Launching", doLauncher);
            telemetry.addData("Intaking", doIntake);
            telemetry.addData("Stopper up", stopperUp);

            telemetry.addLine("\nOther info:");
            telemetry.addData("VLauncher", Launcher.getVelocity());
            telemetry.addData("PStopper", Stopper.getPosition());

        } else {
            telemetry.addData("***Running routine***", routine);

            telemetry.addLine("\nRobot Position:");
            telemetry.addData("Robot X", robotPose.x());
            telemetry.addData("Robot Y", robotPose.y());
            telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));

            telemetry.addLine("\nWhat is the robot currently doing:");
            telemetry.addData("Follower Mode", follower.mode());
            telemetry.addLine(routineLog);

            telemetry.addLine("\nUseful info:");
            telemetry.addData("Launching", doLauncher);
            telemetry.addData("Intaking", doIntake);
            telemetry.addData("Stopper up", stopperUp);

        }
        telemetry.update();
    }

    public void stop() {
        OpModeStorage.autonomousEndPose = follower.pose(); //saves your position in that file
    }
}
