package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;


public class Elevator implements Mechanism {
    private DcMotor elevador;
    
    public void initializeHardware(HardwareMap hardwareMap){
        elevador = hardwareMap.get(DcMotor.class, "motor 5");
    }
}
