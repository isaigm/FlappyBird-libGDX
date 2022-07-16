package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.Locale;

public class Player {
    private final Texture[]textures = new Texture[3];
    private final Animation<Texture> anim;
    private float gravity = -600.0f;
    private float yspeed = -50.0f;
    private float xspeed = 120.0f;
    private float timer = 0;
    private final Rectangle bounds;
    private Sprite sprite;
    private Sound wingSound;
    private Sound hitSound;
    private float rotSpeed = 100.0f;
    private boolean isDead = false;
    private boolean hitPipeorTop = false;
    private boolean hitFloor = false;
    public Player()
    {
        for(int i = 0; i < 3; i++)
        {
            String path = String.format(Locale.ENGLISH, "sprite%d.png", i + 1);
            textures[i] = new Texture(path);
        }
        sprite = new Sprite(textures[0]);
        wingSound = Gdx.audio.newSound(Gdx.files.internal("wing.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
        anim = new Animation<>(0.1f, textures);
        anim.setPlayMode(Animation.PlayMode.LOOP);
        bounds = new Rectangle();
        bounds.x = 100;
        bounds.y = 350;
        bounds.width = textures[0].getWidth();
        bounds.height = textures[0].getHeight();
    }
    public void update(float dt, Pipe pipe, MovingFloor floor, int scrHeight){
        if(!isDead)
        {
            if(pipe.collides(this) || bounds.y > scrHeight)
            {
                isDead = true;
                hitPipeorTop = true;
                hitSound.play();
                rotSpeed = 150.0f;
                gravity = -900;
            }
            if(!hitPipeorTop && floor.collides(this))
            {
                hitSound.play();
            }
            if(Gdx.input.justTouched())
            {
                yspeed = 200;
                wingSound.play();
                sprite.rotate(45 - sprite.getRotation());
            }
            bounds.x += dt * xspeed;
            timer += dt;
        }
        if(!hitFloor)
        {
            if(floor.collides(this))
            {
                hitFloor = true;
                isDead = true;
                return;
            }
            bounds.y += dt * yspeed;
            sprite.setPosition(bounds.x, bounds.y);
            if(sprite.getRotation() > -90)
            {
                sprite.rotate(-rotSpeed * dt);
            }else
            {
                sprite.rotate(-90 - sprite.getRotation());
            }
            yspeed += dt * gravity;
        }
    }
    public boolean dead()
    {
        return isDead;
    }
    public Rectangle getBounds()
    {
        return bounds;
    }
    public void render(SpriteBatch batch)
    {
        sprite.setTexture(anim.getKeyFrame(timer, true));
        sprite.draw(batch);
    }
    public void dispose()
    {
        for(int i = 0; i < 3; i++)
        {
            textures[i].dispose();
        }
        wingSound.dispose();
        hitSound.dispose();
    }
}
