package org.firstinspires.ftc.teamcode.robot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Carousel implements Mechanism{
    private CRServo carouselServomotor;
    public Carousel() {}
    public void initializeHardware(HardwareMap hardwareMap) {
        carouselServomotor = hardwareMap.get(CRServo.class, "servoCarrusel");
    }
    public void setServoPosition(Gamepad gamepad) {
        double carouselPower = 0;
        if (gamepad.left_stick_x < -0.01) {
            carouselPower = -1;
        }
        else if (gamepad.left_stick_x > 0.01) {
            carouselPower = 1;
        }
        else {
            carouselPower = 0;
        }
        carouselServomotor.setPower(carouselPower);
    }
}
