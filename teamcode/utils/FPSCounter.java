package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.util.ElapsedTime;


public class FPSCounter {
    private ElapsedTime runtime;
    private int ciclos;
    private int fps;
    private double lastFPSUpdate;

    public FPSCounter(){
        this.runtime = new ElapsedTime();
    }

    public void startTimer(){
        this.runtime.reset();
        this.lastFPSUpdate = runtime.seconds();
        this.ciclos = 0;
        this.fps = ciclos;
    }

    public String getUpdatedFPS(){
        this.updateFPS();
        return Integer.toString(this.fps);
    }

    private void updateFPS(){
        double currentTime = runtime.seconds();
        double secondsSinceLastUpdate = lastFPSUpdate - currentTime;
        if(secondsSinceLastUpdate > 1){
            fps = ciclos;
            ciclos = 0;
            lastFPSUpdate = currentTime;
        }
        ciclos++;
    }
}