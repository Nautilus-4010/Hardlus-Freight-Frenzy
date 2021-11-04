package org.firstinspires.ftc.teamcode.utils;


public class TargetInfo {
    public String name;
    public double x, y, z;

    public TargetInfo(String name){
        this.name = name;
    }

    public void setPosition(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}