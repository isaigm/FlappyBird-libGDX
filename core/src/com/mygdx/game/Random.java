package com.mygdx.game;

public class Random {
    public static int getRandomIn(int min, int max)
    {
        double rng = Math.random() * (max - min + 1) + min;
        return (int) rng;
    }
}
