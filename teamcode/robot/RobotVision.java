package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.utils.TargetInfo;

public class RobotVision implements Mechanism {
    
    public final static String TARGET_LORRY = "Lorry";
    public final static String TARGET_LORRIES = "Lorries";
    public final static String TARGET_BOAT = "Boat";
    public final static String TARGET_AIRPLANE = "Airplane";

    VuforiaLocalizer vuforia;
    VuforiaTrackables targetsFreightFrenzy;
    
    public RobotVision(){}
    
    public void initializeHardware(HardwareMap hardwareMap){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ATjrkEL/////AAABmfR2/BPftkOFvL9kl5ElbHswfU6Tuno4QSB4aHpVUmWaWqKdEUps2CsnGbmjoGqMAfOjyPlhrew8njlemEsarH9XKySF9i0egaUhOiT2fE0MivatYaT037ZwPe1bOkI1GGmd2CsWL8GeupcT91XQkGhRcMyTS3ZfmDYu1/HmcRxCy4zxwbiyPVcoHtsh+KPfjI29mv9YfMStiB4/o8FgefPbTGtX6L9zeoyUemNIMN1WcaMi6wSM7rB7kF3VnUJCrXAca6YmFNEr6GEdJX4G7JhO5EiD6K/e1+wZ0fLtWiQDWe09Bgxxpp2n+qHeccA06zA8nNTo2F07UORoM40ZK29vMj4eh0GjyNMAOmWcuQeI";
        parameters.useExtendedTracking = false;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        targetsFreightFrenzy = this.vuforia.loadTrackablesFromAsset("FreightFrenzy");
        targetsFreightFrenzy.get(0).setName(TARGET_BOAT);
        targetsFreightFrenzy.get(1).setName(TARGET_AIRPLANE);
        targetsFreightFrenzy.get(2).setName(TARGET_LORRIES);
        targetsFreightFrenzy.get(3).setName(TARGET_LORRY);
        targetsFreightFrenzy.activate();
    }
    
    public TargetInfo getIdentifiedTarget(){
        for (VuforiaTrackable trackable : targetsFreightFrenzy){
            VuforiaTrackableDefaultListener trackableListener = (VuforiaTrackableDefaultListener) trackable.getListener();
            if (trackableListener.isVisible())
                return extractTrackableInfo(trackable);
        }
        return null;
    }
    
    private TargetInfo extractTrackableInfo(VuforiaTrackable trackable){
        VuforiaTrackableDefaultListener trackableListener = (VuforiaTrackableDefaultListener) trackable.getListener();
        TargetInfo info = new TargetInfo(trackable.getName());
        OpenGLMatrix targetPose = trackableListener.getVuforiaCameraFromTarget();
        VectorF trans = targetPose.getTranslation();
        info.setPosition(trans.get(0), trans.get(1), trans.get(2));
        return info;
    }
}
