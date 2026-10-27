package org.firstinspires.ftc.teamcode.Biobuzz2026_2027;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "PedroTopMiddleStart")
    public class PedroTopMiddleStartTeleop extends OpMode { //  aka Example Auto
        private Follower follower;
        private final PoseFactory p = PoseFactory.degrees();
        // Park Pose
        private final Pose parkPose = p.of(132, 25, 90);
        // start1 to park poses
        private final Pose start1Pose = p.of(132, 9, 90);
        //start2 to park poses
        private final Pose start2Pose = p.of(132,92,270);
        //start3 to park poses
        private final Pose start3Pose = p.of(83,133,270);
        private final Pose intermediate1 = p.of(83,117,270);
        private final Pose intermediate2 = p.of(118,117,270);
        private final Pose intermediate3 = p.of(118,33,270);
        /* control pose for curved path
        private final Pose controlPose = p.of(36, 60, 45);
         */
        // start1 to park path
        private Path start1ToPark(){
            return line(start1Pose, parkPose).linear(start1Pose, parkPose);
        }
        // start2 to park path
        private Path start2ToPark(){
            return line(start2Pose, parkPose).linear(start2Pose, parkPose);
        }
        // start3 to park paths
        private Path start3ToInter1(){
            return line(start3Pose, intermediate1).linear(start3Pose, intermediate1);
        }
        private Path inter1ToInter2(){
            return line(intermediate1, intermediate2).linear(intermediate1, intermediate2);
        }
        private Path inter2ToInter3(){
            return line(intermediate2, intermediate3).linear(intermediate2, intermediate3);
        }
        private Path inter3ToPark(){
            return line(intermediate3, parkPose).linear(intermediate3, parkPose);
        }
        // routine that will execute the path(s)
        private Command autoRoutine(){
            return sequential(
                    follow(follower,start3ToInter1()),
                    follow(follower,inter1ToInter2()),
                    follow(follower,inter2ToInter3()),
                    follow(follower,inter3ToPark())

            );
        }

        @Override
        public void init() {
            Scheduler.reset();

            follower = Constants.create(hardwareMap);
            follower.setPose(start3Pose);
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
    }
