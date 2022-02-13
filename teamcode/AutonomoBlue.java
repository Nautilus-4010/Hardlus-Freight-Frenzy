package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.robot.FTCRobot;

@Autonomous(name="Autónomo Azul Estacionarse", group="Blue Alliance")

public class AutonomoBlue extends LinearOpMode{
    private FTCRobot robot;
    @Override
    public void runOpMode() {
        robot = new FTCRobot(this);
        robot.initializeMechanisms();
        telemetry.update();
        waitForStart();
        robot.chasis.moveForward(5);
        robot.chasis.turnRight(82);
        robot.chasis.lateralMove(-10);
        robot.chasis.lateralMove(-4);
        robot.chasis.moveForward(45);
        robot.chasis.moveForward(30);
    }
}