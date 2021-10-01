package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class ChasisOmni implements Mechanism{

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    
    public ChasisOmni(){}
    
    public void initializeHardware(HardwareMap hardwareMap){
        frontLeft = hardwareMap.get(DcMotor.class, "motor 1");
        frontRight = hardwareMap.get(DcMotor.class, "");
        backLeft = hardwareMap.get(DcMotor.class, "");
        backRight = hardwareMap.get(DcMotor.class, "");
    }
    
    public void move(double drive, double lateral, double turn){
        // TODO: Add variables for the other motors using the correct arithmetic
        double frontLeftPower = drive + lateral + turn;
        // TODO: Assign the corresponding power to each motor
        frontLeft.setPower(frontLeftPower);
    }
    
    
}