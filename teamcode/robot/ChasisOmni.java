package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class ChasisOmni implements Mechanism, Sensor{
    private final double LIMIT = 10;
    // TODO: corregir numerito
    public static final int TICKS_PER_CM = 1440;
    public static final double AUTONOMOUS_SPEED = 0.6;

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    
    private OpMode program;
    
    private RevColorSensorV3 distanceSensor;
    
    public ChasisOmni(OpMode program){
        this.program = program;
    }
    
    public RevColorSensorV3 getSensor(){
        return this.distanceSensor;
    }
    public boolean getValue(){
        return distanceSensor.getDistance(DistanceUnit.CM) < LIMIT;
    }
    
    public void initializeHardware(HardwareMap hardwareMap){
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        
        distanceSensor = hardwareMap.get(RevColorSensorV3.class, "distance_sensor");
    }
    
    public void moveForward(double distance){}
    
    public void lateralMove(double distance){
        // TODO: Implement method
        resetEncoders();
        int targetPosition = (int) Math.round(distance * TICKS_PER_CM);
        frontLeft.setTargetPosition(targetPosition);
        frontRight.setTargetPosition(-targetPosition);
        backLeft.setTargetPosition(-targetPosition);
        backRight.setTargetPosition(targetPosition);
        setPowers(AUTONOMOUS_SPEED);
        initAutoDrive();
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

    public String[] getChasisPowers(){
        String[] powers = {
            "FL: " + String.valueOf(frontLeft.getPower()), "FR: " + String.valueOf(frontRight.getPower()), 
            "BL: " + String.valueOf(backLeft.getPower()), "BR: " + String.valueOf(backRight.getPower())
        };
        return powers;
    }
    
    private void resetEncoders(){
        // TODO: Implement method
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    
    private void setPowers(double power){
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }
    
    private void initAutoDrive(){
        // TODO: Implement method
        LinearOpMode aux = (LinearOpMode) program;
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(
            frontLeft.isBusy() &&
            frontRight.isBusy()
        )
            aux.sleep(100L);
    }
}
