package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Elevator implements Mechanism {
    private DcMotor motorElevator;
    private Servo servoElevator;
    //private CRServo crServo;
    //private LynxI2cColorRangeSensor distanceSensor;
    public Elevator() {}
    
    private final double LIMITE = 20; // En cm
    
    public void initializeHardware(HardwareMap hardwareMap){
        motorElevator = hardwareMap.get(DcMotor.class, "elevatorMotor");
        motorElevator.setDirection(DcMotorSimple.Direction.FORWARD);
        
        //servo = hardwareMap.get(Servo.class, "elevatorServomotor");
        //distanceSensor = hardwareMap.get(LynxI2cColorRangeSensor.class, "sensor_distancia");
        
        //crServo = hardwareMap.get(CRServo.class, "elevatorServomotor");
        servoElevator = hardwareMap.get(Servo.class, "elevatorServomotor");
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
    
    public void setServoPosition(Gamepad gamepad) {
        double servoElevatorPosition = 0.5;
        if (gamepad.x) {
            servoElevatorPosition = 1.0;
        }
        else if (gamepad.y) {
            servoElevatorPosition = 0.0;
        }
        else {
            servoElevatorPosition = 0.5;
        }
        servoElevator.setPosition(servoElevatorPosition);
    }
}
