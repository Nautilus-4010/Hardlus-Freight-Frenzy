package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.utils.TargetInfo;
import java.util.List;

public class RobotVision implements Mechanism {
    
    public final static String TARGET_LORRY = "Lorry";
    public final static String TARGET_LORRIES = "Lorries";
    public final static String TARGET_BOAT = "Boat";
    public final static String TARGET_AIRPLANE = "Airplane";

    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_DM.tflite";
    private static final String[] TFOD_LABELS = {"Duck", "Marker"};
    private static final float MIN_RECOGNITION_ACCURACY = 0.8f;

    private VuforiaLocalizer vuforia;
    private VuforiaTrackables targetsFreightFrenzy;
    private TFObjectDetector tfod;
    
    public RobotVision(){}
    
    public void initializeHardware(HardwareMap hardwareMap){
        initVuforia(hardwareMap);
        initTfod(hardwareMap);
        if(tfod != null){
            tfod.activate();
            tfod.setZoom(1, 16.0/9.0);
        }
    }

    private void initVuforia(HardwareMap hardwareMap){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
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

    private void initTfod(HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = MIN_RECOGNITION_ACCURACY;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, TFOD_LABELS);
    }
    
    public TargetInfo getIdentifiedTarget(){
        for (VuforiaTrackable trackable : targetsFreightFrenzy){
            VuforiaTrackableDefaultListener trackableListener = (VuforiaTrackableDefaultListener) trackable.getListener();
            if (trackableListener.isVisible())
                return extractTrackableInfo(trackable);
        }
        return null;
    }

    public List<Recognition> getRecognizedObjects(){
        return tfod != null ? tfod.getRecognitions(): null;
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
