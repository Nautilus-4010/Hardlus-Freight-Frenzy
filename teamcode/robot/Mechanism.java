package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;


public class Mechanism {

    // todo: write your code here
    public Elevator() {
        elevator = hardwareMap.get(DcMotor.class, "motor 5");
        //double elevatorPower = turn;
        //elevator.setPower(elevatorPower);
    }
}
