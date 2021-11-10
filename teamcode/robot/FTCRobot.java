package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.utils.TargetInfo;
import java.util.List;


public class FTCRobot {
    
    protected OpMode programa;

    public ChasisOmni chasis;
    public Elevator elevator;
    public RobotVision vision;
    
    public FTCRobot(OpMode programa){
        this.programa = programa;
        this.chasis = new ChasisOmni();
        this.elevator = new Elevator();
        this.vision = new RobotVision();
    }
    
    public void initializeMechanisms(){
        HardwareMap hwMap = programa.hardwareMap;
        chasis.initializeHardware(hwMap);
        //elevator.initializeHardware(hwMap);
        vision.initializeHardware(hwMap);
        programa.telemetry.addData("Status", "Ready to rumbleee!!!");
    }

    public void logMechanismStatus(){
        TargetInfo identifiedTarget = vision.getIdentifiedTarget();
        if(identifiedTarget != null){
            programa.telemetry.addData("Identified target", identifiedTarget.name);
            programa.telemetry.addData("Pos (mm)", "{X, Y, Z} = %.1f, %.1f, %.1f", identifiedTarget.x, identifiedTarget.y, identifiedTarget.z);
        }
        List<Recognition> identifiedObjects = vision.getRecognizedObjects();
        if(identifiedObjects != null){
            programa.telemetry.addData("Found objects", identifiedObjects.size());
            for(Recognition object: identifiedObjects)
                programa.telemetry.addData("Objeto", object.getLabel());
        }
        String[] chasisPowers = chasis.getChasisPowers();
        programa.telemetry.addData("Chasis", chasisPowers[0] + " | " + chasisPowers[1]);
        programa.telemetry.addData("Chasis", chasisPowers[2] + " | " + chasisPowers[3]);
    }
}