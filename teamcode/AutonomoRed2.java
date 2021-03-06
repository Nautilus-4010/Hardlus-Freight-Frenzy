package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@Autonomous(name = "Autónomo Rojo Estacionarse Cuadrado", group = "Red Alliance")
public class AutonomoRed2 extends LinearOpMode {
    private Hardbot robot;

    @Override
    public void runOpMode() {
        robot = new Hardbot(this);
        robot.initializeHardware();
        telemetry.update();
        waitForStart();
        /*robot.moveForward(5);
        robot.turnLeft(85);
        robot.moveForward(42);
        robot.lateralMove(-7);
        robot.carouselMove(1.0);
        sleep(2500);
        robot.carouselMove(0.0);
        robot.lateralMove(50);
        robot.moveForward(3); */
        robot.lateralMove(3);
        robot.moveForward(28);
        robot.carouselMove(-1.0);
        sleep(3000);
        robot.carouselMove(0.0);
        robot.lateralMove(53);
        robot.moveForward(13);
    }
}