package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.utils.FPSCounter;

@TeleOp(name="Teleoperado Hardlus Freight Frenzy")
public class Teleoperado extends OpMode{
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
        double drive = -gamepad1.left_stick_y;
        double lateral = gamepad1.right_stick_x;
        double turn = gamepad1.left_stick_x;
        double powerMultiplier = getPowerMultiplier();

        robot.move(drive, lateral, turn, powerMultiplier);
        controlIntake();
        controlElevator();
        if (lastChange + 30 < runtime.milliseconds()){
            controlElevatorServo();
            lastChange = runtime.milliseconds();
        }
        controlCarousel();
        telemetry.addData("FPS", fps.getUpdatedFPS());
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
    
    private void controlCarousel(){
        double carouselPower = 0;
        if (gamepad2.right_stick_y < 0) {
            carouselPower = -1;
        }
        else if (gamepad2.right_stick_y > 0) {
            carouselPower = 1;
        }
        else {
            carouselPower = 0;
        }
        robot.carouselServomotor.setPower(carouselPower);
    }
    
    private void controlElevator(){
        double elevatorPower = 0;
        if(gamepad2.right_trigger > 0.00001){
            elevatorPower = Hardbot.ELEVATOR_POWER;
        } else if (gamepad2.left_trigger > 0.00001){
            elevatorPower = -Hardbot.ELEVATOR_POWER;
        } else {
            elevatorPower = 0.0;
        }
        robot.motorElevator.setPower(elevatorPower);
    }
    
    private void controlElevatorServo(){
        double servoElevatorPower = 0;
        if (gamepad2.left_stick_x > 0) {
            servoElevatorPower = 1;
        } else if (gamepad2.left_stick_x < 0) {
            servoElevatorPower = -1;
        }
        else{
           servoElevatorPower = 0; 
        }
    }
    
    private void controlIntake(){
        double intakePower = 0;
        if(gamepad2.a){
            intakePower = -Hardbot.INTAKE_POWER;
        } else if (gamepad2.b){
            intakePower = Hardbot.INTAKE_POWER;
        }
        robot.motorIntake.setPower(intakePower);
    }
}
