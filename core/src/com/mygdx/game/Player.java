package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Locale;

public class Player {
    private final Texture[]textures = new Texture[3];
    private Animation<Texture> anim;
    private float gravity = -400.0f;
    private float yspeed = -50.0f;
    private float xspeed = 50.0f;
    private float x = 100;
    private float y = 350;
    private float timer = 0;
    private Rectangle bounds;
    public Player()
    {
        for(int i = 0; i < 3; i++)
        {
            String path = String.format(Locale.ENGLISH, "sprite%d.png", i + 1);
            textures[i] = new Texture(path);
        }
        anim = new Animation<>(0.1f, textures);
        anim.setPlayMode(Animation.PlayMode.LOOP);
        bounds = new Rectangle();
        bounds.x = x;
        bounds.y = y;
        bounds.width = textures[0].getWidth();
        bounds.height = textures[0].getHeight();
    }
    public void update(float dt){
        if(Gdx.input.justTouched())
        {
            yspeed = 200;
        }
        y += dt * yspeed;
        x += dt * xspeed;
        bounds.x = x;
        bounds.y = y;
        yspeed += dt * gravity;
        timer += dt;
    }
    public Rectangle getBounds()
    {
        return bounds;
    }
    public void render(SpriteBatch batch)
    {
        batch.draw(anim.getKeyFrame(timer, true), x, y);
    }
    public void dispose()
    {
        for(int i = 0; i < 3; i++)
        {
            textures[i].dispose();
        }
    }
}
