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
        frontLeft = hardwareMap.get(DcMotor.class, "Motor frontal izquierdo");
        frontRight = hardwareMap.get(DcMotor.class, "Motor frontal derecho");
        backLeft = hardwareMap.get(DcMotor.class, "Motor trasero izquierdo");
        backRight = hardwareMap.get(DcMotor.class, "Motor trasero derecho");
    }
    
    public void move(double drive, double lateral, double turn, double multiplier){
        double frontLeftPower = (drive + lateral + turn) * multiplier;
        double frontRightPower = (drive - lateral - turn) * multiplier;
        double backLeftPower = (drive - lateral + turn) * multiplier;
        double backRightPower = (drive + lateral - turn) * multiplier;
        frontLeft.setPower(Range.clip(frontLeftPower, -1, 1));
        frontRight.setPower(Range.clip(frontRightPower, -1, 1));
        backLeft.setPower(Range.clip(backLeftPower, -1, 1));
        backRight.setPower(Range.clip(backRightPower, -1, 1));
    }
}