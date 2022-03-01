package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


public class Hardbot {

    private final double LIMIT = 10;
    public static final int TICKS_PER_CM = 25;
    public static final double AUTONOMOUS_SPEED = 0.2;
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public DcMotor motorElevator;
    public Servo servoElevator;
    public long NIVEL_UNO = 400L;
    public long NIVEL_DOS = 800L;
    public long NIVEL_TRES = 1200L;
    public static final double ELEVATOR_POWER = 0.3;
    private final double INCREMENTO = 0.06;
    private final double LIMITE = 20; // En cm
    public static final double ELEVATOR_SERVO_TOP = 0;
    public static final double ELEVATOR_SERVO_MID = 0.67;
    public static final double ELEVATOR_SERVO_LOW = 0.7;

    private BNO055IMU imu;
    private Orientation angles;
    private Acceleration gravity;

    public CRServo carouselServomotor;

    public DcMotor motorIntake;
    public static final double INTAKE_POWER = 0.9;

    private OpMode program;

    private RobotVision vision;
    
    public Hardbot(OpMode program){
        this.program = program;
        this.vision = new RobotVision();
    }

    public void initializeHardware(){
        HardwareMap hardwareMap = this.program.hardwareMap;
        carouselServomotor = hardwareMap.get(CRServo.class, "servoCarrusel");
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        imu = hardwareMap.get(BNO055IMU.class, "imu2");
        initIMU();
        motorElevator = hardwareMap.get(DcMotor.class, "elevatorMotor");
        motorElevator.setDirection(DcMotorSimple.Direction.FORWARD);
        servoElevator = hardwareMap.get(Servo.class, "elevatorServomotor");
        motorIntake = hardwareMap.get(DcMotor.class, "intakeMotor");
        motorIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        servoElevator.setPosition(ELEVATOR_SERVO_LOW);
    }

    public void carouselMove(double lap) {
        double carouselPower = 0;
        if (lap >= 1) {
            carouselPower = lap;
        } else if (lap <= -1) {
            carouselPower = lap;
        } else {
            carouselPower = 0;
        }
        carouselServomotor.setPower(carouselPower);
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

    public double getAngle() {
        if(imu == null)
            return -1024;
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        gravity  = imu.getGravity();
        return angles.firstAngle;
    }
    
    public void moveForward(double distance){
        resetEncoders();
        int targetPosition = (int) Math.round(distance * TICKS_PER_CM);
        frontLeft.setTargetPosition(targetPosition);
        frontRight.setTargetPosition(targetPosition);
        backLeft.setTargetPosition(targetPosition);
        backRight.setTargetPosition(targetPosition);
        setChasisPowers(AUTONOMOUS_SPEED);
        initAutoDrive();
    }
    
    public void turnRight(double angleToRotate) {
        this.setChasisRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
        this.setChasisRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
        setChasisPowers(AUTONOMOUS_SPEED);
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
    
    public void bajarElevador() {
        this.motorElevator.setPower(-ELEVATOR_POWER);
    }
    
    public void subirElevador() {
        this.motorElevator.setPower(ELEVATOR_POWER);
    }

    public void setElevatorLevel(int nivel){
        LinearOpMode aux = (LinearOpMode) program;
        double power = 0.6;
        motorElevator.setPower(power);
        if(nivel == 1){
            aux.sleep(NIVEL_UNO);
        } else if(nivel == 2){
            aux.sleep(NIVEL_DOS);
        } else if(nivel == 3){
            aux.sleep(NIVEL_TRES);
        }
        motorElevator.setPower(0);
    }

    private void setChasisRunMode(DcMotor.RunMode runmode){
        frontLeft.setMode(runmode);
        frontRight.setMode(runmode);
        backLeft.setMode(runmode);
        backRight.setMode(runmode);
    }
    
    private void resetEncoders(){
        setChasisRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    
    private void setChasisPowers(double power){
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }
    
    private void initAutoDrive(){
        LinearOpMode aux = (LinearOpMode) program;
        this.setChasisRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(
            frontLeft.isBusy() &&
            frontRight.isBusy() &&
            backLeft.isBusy() &&
            backRight.isBusy()
        )
            aux.sleep(100L);
    }
}