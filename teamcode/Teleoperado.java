package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.robot.FTCRobot;
import org.firstinspires.ftc.teamcode.utils.FPSCounter;

@TeleOp(name="Teleoperado Hardlus Freight Frenzy")
public class Teleoperado extends OpMode{
    private FTCRobot robot;
    private FPSCounter fps;

    @Override
    public void init(){
        robot = new FTCRobot(this);
        fps = new FPSCounter();
        robot.initializeMechanisms();
        telemetry.update();
    }
    
    @Override
    public void init_loop(){}
    
    @Override
    public void start(){
        fps.startTimer();
    }
    
    @Override
    public void loop(){
        double drive = -gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        double powerMultiplier = getPowerMultiplier();

        robot.chasis.move(drive, lateral, turn, powerMultiplier);
        telemetry.addData("FPS", fps.getUpdatedFPS());
        robot.logMechanismStatus();
        telemetry.update();
    }
    
    @Override
    public void stop(){}

    private double getPowerMultiplier(){
        if(gamepad1.right_bumper && gamepad1.left_bumper)
            return 0.4;
        if(gamepad1.right_bumper)
            return 0.8;
        if(gamepad1.left_bumper)
            return 0.6;
        else
            return 1;
    }
}