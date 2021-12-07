package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;

public class InTake implements Mechanism{
    
    private DcMotor motorIntake;
    
    public InTake(){}

    public void initializeHardware(HardwareMap hardwareMap){
        motorIntake = hardwareMap.get(DcMotor.class, "intakeMotor");
        motorIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        
    }
    
    public void setPower(Gamepad gamepad){
        double intakePower = 0;
        if(gamepad.a){
            intakePower = -0.9;
        } else if (gamepad.b){
            intakePower = 0.9;
        }
        motorIntake.setPower(intakePower);
    }
}
