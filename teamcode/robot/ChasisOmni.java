package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class ChasisOmni implements Mechanism{

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    
    public ChasisOmni(){}
    
    public void initializeHardware(HardwareMap hardwareMap){
        frontLeft = hardwareMap.get(DcMotor.class, "Motor_frontal_izquierdo");
        frontRight = hardwareMap.get(DcMotor.class, "Motor_frontal_derecho");
        backLeft = hardwareMap.get(DcMotor.class, "Motor_trasero_izquierdo");
        backRight = hardwareMap.get(DcMotor.class, "Motor_trasero_derecho");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
    }
    
    public void move(double drive, double lateral, double turn, double multiplier){
        // TODO: Include multiplier somewhere to increase or decrease the speed of the robot
        double frontLeftPower = drive + lateral + turn;
        double frontRightPower = drive - lateral - turn;
        double backLeftPower = drive - lateral + turn;
        double backRightPower = drive + lateral - turn;
        frontLeft.setPower(Range.clip(frontLeftPower, -1, 1));
        frontRight.setPower(Range.clip(frontRightPower, -1, 1));
        backLeft.setPower(Range.clip(backLeftPower, -1, 1));
        backRight.setPower(Range.clip(backRightPower, -1, 1));
    }
}
