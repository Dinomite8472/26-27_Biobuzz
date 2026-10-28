package org.firstinspires.ftc.teamcode.Testing;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OpModeStorage;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name = "Get Pedro Positions", group = "Testing")
public class GetPedroPose extends OpMode {
    private final PoseFactory p = PoseFactory.degrees();

    private Follower follower;

    Pose parkedPose = p.of(132, 25, 90);
    Pose Pose0 = p.of(72, 72, 90);
    Pose startPose;

    int scenario = 0;

    boolean holdP = false;

    double wheel_Speed = 0.25;

    public void init() {
        follower = Constants.create(hardwareMap);
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
                telemetry.addLine("No auto pose found: Please select start position \n[X] Parked, [B] 0,0 @90");

                if (gamepad1.xWasPressed() || gamepad2.xWasPressed()) {
                    startPose = parkedPose;
                    scenario = 2;
                }
                if (gamepad1.bWasPressed() || gamepad2.bWasPressed()) {
                    startPose = Pose0;
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
                    telemetry.addLine("Parked selected, starting from: " + startPose.x() + ", " + startPose.y() + " @" + startPose.heading());
                } else if (scenario == 3) {
                    telemetry.addLine("0,0 @90 selected, starting from: " + startPose.x() + ", " + startPose.y() + " @" + startPose.heading());
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

        if(gamepad1.aWasPressed()) {
            holdP = !holdP;
        }

        if(holdP) {
            ManualDrive.driveOrHold(
                    follower,
                    -gamepad1.left_stick_y * wheel_Speed,
                    -gamepad1.left_stick_x * wheel_Speed,
                    (-gamepad1.right_stick_x * wheel_Speed) / 2
            );
        } else {
            follower.manual(
                    -gamepad1.left_stick_y * wheel_Speed,
                    -gamepad1.left_stick_x * wheel_Speed,
                    (-gamepad1.right_stick_x * wheel_Speed) / 2
            );
        }


        // relocalise button
        if (gamepad1.startWasPressed()) {
            Pose cornerPose = p.of(10.5, 10.5, 90);
            // On the fly Pose creation, we dont recommend this for Autonomous. Only accepts radians for heading
            follower.setPose(cornerPose); // overrides our pose
        }

        if (gamepad1.rightBumperWasPressed() && wheel_Speed < 1) {
            wheel_Speed += 0.125;
        }

        //Optional way to change slow mode strength
        if (gamepad1.leftBumperWasPressed() && wheel_Speed > 0) {
            wheel_Speed -= 0.125;
        }

        follower.update();
        Pose robotPose = follower.pose(); // returns a Pose object
        telemetry.addLine("Robot Position:");
        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));

        telemetry.addLine("\nUseful info:");
        telemetry.addData("Drivechain speed", wheel_Speed);
        telemetry.addData("Holding", holdP);

        telemetry.update();
    }

    public void stop() {
        OpModeStorage.autonomousEndPose = follower.pose(); //saves your position in that file
    }
}
