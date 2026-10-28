package org.firstinspires.ftc.teamcode.Biobuzz2026_2027;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;

import static org.firstinspires.ftc.teamcode.OpModeStorage.*;

import org.firstinspires.ftc.teamcode.OpModeStorage;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.api.PoseFactory;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;

import static com.pedropathing.ivy.groups.Groups.sequential;

@Autonomous(name = "PedroBottomRightStart")
    public class PedroBottomRightStart extends OpMode { //  aka Example Auto
        private Follower follower;
        private final PoseFactory p = PoseFactory.degrees();

        // start1 to park path
        private Path start1ToPark(){
            return line(start1Pose, parkPoseTop).linear(start1Pose, parkPoseTop);
        }

        // routine that will execute the path(s)
        private Command autoRoutine(){
            return sequential(
                follow(follower,start1ToPark())

            );
        }

        @Override
        public void init() {
            Scheduler.reset();

            follower = Constants.create(hardwareMap);
            follower.setPose(start1Pose);
            follower.update();
        }
        @Override
        public void start() {
            schedule(autoRoutine());
        }
        @Override
        public void loop() {
            follower.update();
            Scheduler.execute();

            telemetry.addData("X", follower.pose().x());
            telemetry.addData("Y", follower.pose().y());
            telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
            telemetry.addData("Follower Mode", follower.mode());
            telemetry.update();
        }

    // in your autonomous


        @Override
        public void stop() {
            OpModeStorage.autonomousEndPose = follower.pose(); //saves your position in that file
        }
    }