package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.utils.FPSCounter;

@TeleOp(name="Teleoperado Hardlus Freight Frenzy")
public class Teleoperado extends OpMode {
    private Hardbot robot;
    private FPSCounter fps;
    private double lastChange;
    private ElapsedTime runtime;
    @Override
    public void init(){
        robot = new Hardbot(this);
        fps = new FPSCounter();
        robot.initializeHardware();
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
        moveChasis(gamepad1);
        controlIntake(gamepad2);
        controlElevator(gamepad2);
        controlElevatorServo(gamepad2);
        controlCarousel(gamepad2);
        telemetry.addData("FPS", fps.getUpdatedFPS());
        telemetry.update();
    }
    
    @Override
    public void stop(){}

    private void moveChasis(Gamepad gamepad) {
        double drive = -gamepad.left_stick_y;
        double lateral = gamepad.right_stick_x;
        double turn = gamepad.left_stick_x;
        double powerMultiplier = getPowerMultiplier(gamepad);
        robot.move(drive, lateral, turn, powerMultiplier);
    }

    private double getPowerMultiplier(Gamepad gamepad){
        if(gamepad.right_bumper && gamepad.left_bumper)
            return 0.2;
        if(gamepad.right_bumper)
            return 0.6;
        if(gamepad.left_bumper)
            return 0.4;
        else
            return 0.8;
    }
    
    private void controlCarousel(Gamepad gamepad){
        double carouselPower = 0;
        if (gamepad.right_stick_y < 0) {
            carouselPower = -1;
        } else if (gamepad.right_stick_y > 0) {
            carouselPower = 1;
        } else {
            carouselPower = 0;
        }
        robot.carouselServomotor.setPower(carouselPower);
    }
    
    private void controlElevator(Gamepad gamepad){
        if(gamepad.right_trigger > 0){
            robot.bajarElevador();
        } else if (gamepad.left_trigger > 0){
            robot.subirElevador();
        } else {
            robot.motorElevator.setPower(0);
        }
    }
    
    private void controlElevatorServo(Gamepad gamepad){
        double servoElevatorPower = 0;
        if (gamepad.left_stick_x > 0) {
            servoElevatorPower = 1;
        } else if (gamepad.left_stick_x < 0) {
            servoElevatorPower = -1;
        } else {
           servoElevatorPower = 0; 
        }
        robot.servoElevator.setPower(servoElevatorPower);
    }
    
    private void controlIntake(Gamepad gamepad){
        double intakePower = 0;
        if(gamepad.a){
            intakePower = -Hardbot.INTAKE_POWER;
        } else if (gamepad.b){
            intakePower = Hardbot.INTAKE_POWER;
        }
        robot.motorIntake.setPower(intakePower);
    }
}
