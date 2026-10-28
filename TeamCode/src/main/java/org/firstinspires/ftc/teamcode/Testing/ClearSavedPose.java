package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OpModeStorage;

@TeleOp(name = "Clear Saved Pose", group = "Testing")
public class ClearSavedPose extends OpMode {

    public void init()
    {

    }

    public void init_loop() { // only so I can use gamepad :p
        if (OpModeStorage.autonomousEndPose != null) {
            telemetry.addLine("Saved pose found: Keep it? \n[Y] yes, [A] no (discard)");
            telemetry.update();

            if(gamepad1.yWasPressed() || gamepad2.yWasPressed()) {
                telemetry.addLine("Kept saved position");
                telemetry.update();
                requestOpModeStop();
            }

            if(gamepad1.aWasPressed() || gamepad2.aWasPressed()) {
                OpModeStorage.autonomousEndPose = null;
                telemetry.addLine("Discarded saved position");
                telemetry.update();
                requestOpModeStop();
            }

        } else {
            telemetry.addLine("No saved pose found");
            requestOpModeStop();
        }
    }

    public void loop()
    {

    }
}
