package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@Autonomous(name= "Autónomo Azul Estacionarse Cuadrado", group = "Blue Alliance")
public class AutonomoBlue2 extends LinearOpMode{
    private Hardbot robot;
    @Override
    public void runOpMode() {
        robot = new Hardbot(this);
        robot.initializeHardware();
        telemetry.update();
        waitForStart();
        robot.moveForward(5);
        robot.turnRight(85);
        robot.moveForward(42);
        robot.lateralMove(7);
        robot.carouselMove(1.0);
        sleep(2500);
        robot.carouselMove(0.0);
        robot.lateralMove(-50);
        robot.moveForward(3);
    }
}