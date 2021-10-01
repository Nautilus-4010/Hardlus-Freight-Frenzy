package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.robot.FTCRobot;
import org.firstinspires.ftc.teamcode.utils.FPSCounter;

@TeleOp(name="Teleoperado")
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
        double drive = -gamepad1.left_stick_x;
        double lateral = gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        robot.chasis.move(drive, lateral, turn);
        telemetry.addData("FPS", fps.getUpdatedFPS());
        telemetry.update();
    }
    
    @Override
    public void stop(){}
}