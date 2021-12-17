package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.robot.FTCRobot;
import org.firstinspires.ftc.teamcode.utils.FPSCounter;

@TeleOp(name="Teleoperado Hardlus Freight Frenzy")
public class Teleoperado extends OpMode{
    private FTCRobot robot;
    private FPSCounter fps;
    private double lastChange;
    private ElapsedTime runtime;
    @Override
    public void init(){
        robot = new FTCRobot(this);
        fps = new FPSCounter();
        robot.initializeMechanisms();
        telemetry.update();
        runtime = new ElapsedTime();
    }
    
    @Override
    public void init_loop(){}
    
    @Override
    public void start(){
        fps.startTimer();
        runtime.reset();
        lastChange = runtime.milliseconds();
    }
    
    @Override
    public void loop(){
        double drive = -gamepad1.left_stick_y;
        double lateral = gamepad1.right_stick_x;
        double turn = gamepad1.left_stick_x;
        double powerMultiplier = getPowerMultiplier();

        robot.chasis.move(drive, lateral, turn, powerMultiplier);
        robot.intake.setPower(gamepad2);
        robot.elevator.setPower(gamepad2);
        if (lastChange + 30 < runtime.milliseconds()){
            robot.elevator.setServoPower(gamepad2);
            lastChange = runtime.milliseconds();
        }
        robot.carousel.setServoPower(gamepad2);
        telemetry.addData("FPS", fps.getUpdatedFPS());
        robot.logMechanismStatus();
        telemetry.update();
    }
    
    @Override
    public void stop(){}

    private double getPowerMultiplier(){
        if(gamepad1.right_bumper && gamepad1.left_bumper)
            return 0.2;
        if(gamepad1.right_bumper)
            return 0.6;
        if(gamepad1.left_bumper)
            return 0.4;
        else
            return 0.8;
    }
}
