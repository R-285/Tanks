package com.tanks.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
    private static final String PATH = "res/sounds/";
    private Clip clip;
    private FloatControl volumeC;

    File f;

    public Audio(String track) {
        File f = new File(PATH + track);

        AudioInputStream tr;
        try {
            tr = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(tr);
            volumeC = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ext) {
            ext.printStackTrace();
        }


    }

    public void setVolume(double wt) {
        if (wt < 0) wt = 0;
        if (wt > 1) wt = 1;
        float min = volumeC.getMinimum();
        float max = volumeC.getMaximum();

        volumeC.setValue((max - min) * (float) wt + min);
    }

    public void sound() {
        if(clip.getMicrosecondPosition() == clip.getMicrosecondLength()) clip.setFramePosition(0);
        clip.start();
    }
    public void stop(){
        clip.stop();
    }


    private long timeNow = 0;
    public void setPosition(long position){
        clip.setMicrosecondPosition(position);
    }
    public void specialSound(){

        if (System.currentTimeMillis() - timeNow >  80){
            clip.setFramePosition(0);
            clip.start();
            timeNow = System.currentTimeMillis();
        }
    }

}
