package org.firstinspires.ftc.teamcode;
//Make sure this matches your file location

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;

public class OpModeStorage {
    public static Pose autonomousEndPose;

    public static final double defLauncher = 0.7812;
    public static final double defIntake = 1;

    public static final double StopperServo_Normal = 0.5;
    public static final double StopperServo_Up = 0.75;

    public static final int tLaunchDelay = 1000;
    public static final int tStopperUp = 1000;

    public static final PoseFactory p = PoseFactory.degrees();
    // Park Pose
    public static final Pose parkPoseBtm = p.of(132.9, 21, 90);
    public static final Pose parkPoseTop = p.of(132.9, 47.5, 90);

    public static final Pose launchPoseLeft = p.of(83, 23, 90);
    public static final Pose launchPoseRight = p.of(87, 122, 270);
    // start1 to park poses
    public static final Pose start1Pose = p.of(132, 9, 90);
    //start2 to park poses
    public static final Pose start2Pose = p.of(134.7,78.2,89.7);
    //start3 to park poses
    public static final Pose start3Pose = p.of(90.5,135.5,270);
    public static final Pose intermediate1 = p.of(83,117,270);
    public static final Pose intermediate2 = p.of(118,117,270);
    public static final Pose intermediate3 = p.of(118,33,270);
}