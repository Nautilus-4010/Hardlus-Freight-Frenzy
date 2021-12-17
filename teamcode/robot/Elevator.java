package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Elevator implements Mechanism {
    private OpMode programa;
    
    private DcMotor motorElevator;
    private CRServo servoElevator;
    public long NIVEL_UNO = 400L, 
    NIVEL_DOS = 800L,
    NIVEL_TRES = 1200L;
    
    public Elevator() {}
    
    public Elevator(OpMode programa){
        this.programa = programa;
    }
    
    private final double INCREMENTO = 0.06;
    
    private final double LIMITE = 20; // En cm
    
    public void initializeHardware(HardwareMap hardwareMap){
        motorElevator = hardwareMap.get(DcMotor.class, "elevatorMotor");
        motorElevator.setDirection(DcMotorSimple.Direction.FORWARD);
        
        //distanceSensor = hardwareMap.get(LynxI2cColorRangeSensor.class, "sensor_distancia");
        
        //crServo = hardwareMap.get(CRServo.class, "elevatorServomotor");
        servoElevator = hardwareMap.get(CRServo.class, "elevatorServomotor");
    }
    
    /**
     * return: verdadero si la posicion es menor al limite
    */
    /*public boolean getValue(){
        // Pongan el comportamiento del límite
        return distanceSensor.getDistance(DistanceUnit.CM) < LIMITE;
    } */
    
    /**
     * Getter para el sensor de distancia
    */
    //public LynxI2cColorRangeSensor getDistanceSensor(){
    //    return this.distanceSensor;
    //}
    
    public void setPower(Gamepad gamepad){
        double elevatorPower = 0;
        
        if(gamepad.right_trigger > 0.00001){
            elevatorPower = 0.6*gamepad.right_trigger;
        } else if (gamepad.left_trigger > 0.00001){
            elevatorPower = -0.6*gamepad.left_trigger;
        }
        else {
            elevatorPower = 0.0;
        }
        motorElevator.setPower(elevatorPower);
    }
    
    public void setServoPower(Gamepad gamepad) {
        //double servoElevatorPosition = servoElevator.getPosition();
        double servoElevatorPower = 0;
        if (gamepad.left_stick_x > 0.01) {
            //servoElevatorPosition += INCREMENTO;
            servoElevatorPower = 1;
        }
        else if (gamepad.left_stick_x < -0.01) {
            //servoElevatorPosition -= INCREMENTO;
            servoElevatorPower = -1;
        }
        else{
           servoElevatorPower = 0; 
        }
        //servoElevator.setPosition(Range.clip(servoElevatorPosition, -1, 1));
        servoElevator.setPower(servoElevatorPower);
    }
    public void setLevel(int nivel){
        LinearOpMode aux = (LinearOpMode) programa;
        double power = 0.6;
        switch(nivel){
            case 1:
                motorElevator.setPower(power);
                aux.sleep(NIVEL_UNO);
                break;
            case 2:
                motorElevator.setPower(power);
                aux.sleep(NIVEL_DOS);
                break;
            case 3:
                motorElevator.setPower(power);
                aux.sleep(NIVEL_TRES);
                break;
            default: 
                aux.sleep(100L);
                break;
        }
        motorElevator.setPower(0);
    }
   /* public double getAngle(){
        return servoElevator.getPosition();
    } */
    /*
    public void setServoPosition(Gamepad gamepad){
        double power = 0;
        if (gamepad.y) {
            power = -0.8;
        }
        else if (gamepad.x) {
            power = 0.8;
        }
        else {
            power = 0;
        }
        servoElevator.setPower(power);
    }
    */
}
