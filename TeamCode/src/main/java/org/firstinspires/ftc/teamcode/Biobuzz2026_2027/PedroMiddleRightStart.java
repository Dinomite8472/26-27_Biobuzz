package org.firstinspires.ftc.teamcode.Biobuzz2026_2027;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import static org.firstinspires.ftc.teamcode.OpModeStorage.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.OpModeStorage;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "PedroMiddleRightStart")
    public class PedroMiddleRightStart extends OpMode { //  aka Example Auto
        private Follower follower;
        private final PoseFactory p = PoseFactory.degrees();

        // start2 to park path
        private Path start2ToPark(){
            return line(start2Pose, parkPoseBtm).linear(start2Pose, parkPoseBtm);
        }
        // routine that will execute the path(s)
        private Command autoRoutine(){
            return sequential(
                follow(follower,start2ToPark())

            );
        }

        @Override
        public void init() {
            Scheduler.reset();

            follower = Constants.create(hardwareMap);
            follower.setPose(start2Pose);
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
