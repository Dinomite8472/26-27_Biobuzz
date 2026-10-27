package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.pedro.procedures.ForesightTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.MecanumTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.PinpointTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.Tests;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }

    @Tuner
    public static Procedure mecanumTuner() {
        return new MecanumTuner();
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("FrontLeft");
        c.frontRightName.set("FrontRight");
        c.backLeftName.set("BackLeft");
        c.backRightName.set("BackRight");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
    });

    @Tuner
    public static Procedure tests() {
        return new Tests(hardwareMap -> new Mecanum(hardwareMap, Constants.drivetrainConfig), (hardwareMap -> new PinpointLocalizer(hardwareMap, Constants.localizerConfig)), () -> new Foresight(Constants.foresightConfig));
    }

    @Tuner
    public static Procedure pinpointTuner() {
        return new PinpointTuner();
    }

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-8.048891082523376);
        c.yPodOffset.set(-1.2915013531061608);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    @Tuner
    public static Procedure foresightTuner() {
        return new ForesightTuner((hardwareMap) -> new PinpointLocalizer(hardwareMap, Constants.localizerConfig), (hardwareMap) -> new Mecanum(hardwareMap, Constants.drivetrainConfig));
    }

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.2637255925311894);
                Controller secondaryTranslationalForward = Controller.proportional(0.09743955956265783);
                Controller primaryTranslationalLateral = Controller.proportional(0.39074417840838255);
                Controller secondaryTranslationalLateral = Controller.proportional(0.14436953304515787);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.017847114267341934));
                c.brake.set(Controller.proportionalFeedforward(0.015170047127240644));

                c.headingFeedback.set(Controller.proportional(4.7843668598522076));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04772575372837674, 0.006852031544075388));

                c.linearBrakeCoefficients.set(Matrix.diag(0.05652385734693521, 0.0708815531987258));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0018128542319179486, 0.0011325518501350401));

                c.maxAchievableForwardVelocity.set(62.80493258517022);
                c.maxAchievableStrafeVelocity.set(50.79467782972232);
                c.naturalForwardDeceleration.set(28.705403764040383);
                c.naturalStrafeDeceleration.set(51.6753680759313);
            }
    );
}
