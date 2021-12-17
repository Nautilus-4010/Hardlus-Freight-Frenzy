package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import java.util.Locale;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;


public class ChasisOmni implements Mechanism, Sensor{
    private BNO055IMU imu;
    private Orientation angles;
    private Acceleration gravity;
    private final double LIMIT = 10;
    public static final int TICKS_PER_CM = 25;
    public static final double AUTONOMOUS_SPEED = 0.2;

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    
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
    
    public double getAngle() {
        if(imu == null)
            return -1024;
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        gravity  = imu.getGravity();
        return angles.firstAngle;
    }
    
    public void initializeHardware(HardwareMap hardwareMap){
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        imu = hardwareMap.get(BNO055IMU.class, "imu2");
        initIMU();
        distanceSensor = hardwareMap.get(RevColorSensorV3.class, "distance_sensor");
    }
    public void initIMU() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);
    }
    
    public void moveForward(double distance){
        resetEncoders();
        int targetPosition = (int) Math.round(distance * TICKS_PER_CM);
        frontLeft.setTargetPosition(targetPosition);
        frontRight.setTargetPosition(targetPosition);
        backLeft.setTargetPosition(targetPosition);
        backRight.setTargetPosition(targetPosition);
        setPowers(AUTONOMOUS_SPEED);
        initAutoDrive();
    }
    
    public void turnRight(double angleToRotate) {
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double currentAngle = getAngle();
        double targetAngle = currentAngle - angleToRotate;
        while (currentAngle > targetAngle) {
            frontLeft.setPower(AUTONOMOUS_SPEED);
            frontRight.setPower(-AUTONOMOUS_SPEED);
            backLeft.setPower(AUTONOMOUS_SPEED);
            backRight.setPower(-AUTONOMOUS_SPEED);
            currentAngle = getAngle();
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    public void turnLeft(double angleToRotate) {
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double currentAngle = getAngle();
        double startAngle = currentAngle; //borrar
        double targetAngle = angleToRotate + getAngle();
        while (currentAngle < targetAngle) {
            program.telemetry.addData("Start angle: ", startAngle);
            program.telemetry.addData("Target angle: ", targetAngle);
            program.telemetry.addData("Current angle: ", currentAngle);
            frontLeft.setPower(-AUTONOMOUS_SPEED);
            frontRight.setPower(AUTONOMOUS_SPEED); 
            backLeft.setPower(-AUTONOMOUS_SPEED);
            backRight.setPower(AUTONOMOUS_SPEED); 
            currentAngle = getAngle();
            program.telemetry.update();
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    public void lateralMove(double distance){
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
            String.format("FL: %.2f", frontLeft.getPower()),
            String.format("FR: %.2f", frontRight.getPower()),
            String.format("BL: %.2f", backLeft.getPower()),
            String.format("BR: %.2f", backRight.getPower())
        };
        return powers;
    }
    
    private void resetEncoders(){
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    
    private void setPowers(double power){
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }
    
    private void initAutoDrive(){
        LinearOpMode aux = (LinearOpMode) program;
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(
            frontLeft.isBusy() &&
            frontRight.isBusy() &&
            backLeft.isBusy() &&
            backRight.isBusy()
        )
            aux.sleep(100L);
    }
}
