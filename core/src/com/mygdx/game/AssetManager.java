package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import java.util.Locale;

public class AssetManager {
    private Texture[] redSkin = new Texture[3];
    private Texture[] yellowSkin = new Texture[3];
    private Texture[] blueSkin = new Texture[3];
    private Texture greenPipe;
    private Texture redPipe;
    private Texture dayBg;
    private Texture nightBg;
    private static AssetManager instance = null;
    public static AssetManager getInstance() {
        if(instance == null)
        {
            instance = new AssetManager();
        }
        return instance;
    }
    private AssetManager()
    {
        greenPipe = new Texture("pipe.png");
        redPipe = new Texture("pipe-red.png");
        nightBg = new Texture("background-night.png");
        dayBg = new Texture("background-day.png");
        for(int i = 0; i < 3; i++)
        {
            String redPath = String.format(Locale.ENGLISH, "red%d.png", i + 1);
            String yellowPath = String.format(Locale.ENGLISH, "yellow%d.png", i + 1);
            String bluePath = String.format(Locale.ENGLISH, "blue%d.png", i + 1);
            redSkin[i] = new Texture(redPath);
            yellowSkin[i] = new Texture(yellowPath);
            blueSkin[i] = new Texture(bluePath);
        }
    }
    public void dispose()
    {
        for(int i = 0; i < 3; i++)
        {
            redSkin[i].dispose();
            blueSkin[i].dispose();
            yellowSkin[i].dispose();
        }
        redPipe.dispose();
        greenPipe.dispose();
        dayBg.dispose();
        nightBg.dispose();
    }
    public Texture getGreenPipe() {
        return greenPipe;
    }
    public Texture getRedPipe(){
        return redPipe;
    }
    public Texture getNightBg() {
        return nightBg;
    }
    public Texture getDayBg() {
        return dayBg;
    }
    public Texture[] getRedSkin() {
        return redSkin;
    }
    public Texture[] getYellowSkin() {
        return yellowSkin;
    }
    public Texture[] getBlueSkin() {
        return blueSkin;
    }
}
