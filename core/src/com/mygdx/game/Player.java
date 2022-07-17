package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

public class Player {
    private Texture[]textures = null;
    private final Animation<Texture> anim;
    private float gravity = -600.0f;
    private float yspeed = -50.0f;
    private float xspeed = 120.0f;
    private float timer = 0;
    private final Rectangle bounds;
    private final Sprite sprite;
    private final Sound wingSound;
    private final Sound hitSound;
    private final Sound pointSound;
    private float rotSpeed = 100.0f;
    private boolean collided = false;
    private boolean pipesSkipped[] = new boolean[GameWorldConstants.npipes];
    private State state = State.ALIVE;
    enum State{
        ALIVE,
        FALLING,
        ONGROUND
    }
    void restart()
    {
        collided = false;
        state = State.ALIVE;
        yspeed = -50.0f;
        gravity = -600.0f;
        bounds.x = (float) Gdx.graphics.getWidth() / 6 - bounds.width / 2;
        bounds.y = (float) Gdx.graphics.getHeight() / 6 - bounds.height / 2 + 50;
        sprite.setPosition(bounds.x, bounds.y);
        rotSpeed = 100.0f;
        timer = 0;
        sprite.setRotation(0);
        setRandomSkin();
        for(int i = 0; i < pipesSkipped.length; i++)
        {
            pipesSkipped[i] = false;
        }
    }
    private void setRandomSkin()
    {
        int skin = Random.getRandomIn(0, 2);
        switch (skin)
        {
            case 0:
                textures = AssetManager.getInstance().getRedSkin();
                break;
            case 1:
                textures = AssetManager.getInstance().getYellowSkin();
                break;
            case 2:
                textures = AssetManager.getInstance().getBlueSkin();
                break;
        }
    }
    public Player()
    {
        setRandomSkin();
        sprite = new Sprite(textures[0]);
        pointSound = Gdx.audio.newSound(Gdx.files.internal("audio_point.wav"));
        wingSound = Gdx.audio.newSound(Gdx.files.internal("wing.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
        anim = new Animation<>(0.1f, textures);
        anim.setPlayMode(Animation.PlayMode.LOOP);
        bounds = new Rectangle();
        bounds.x = (float) Gdx.graphics.getWidth() / 6 - bounds.width / 2;
        bounds.y = (float) Gdx.graphics.getHeight() / 6 - bounds.height / 2 + 50;
        bounds.width = textures[0].getWidth();
        bounds.height = textures[0].getHeight();
    }
    private void updateY(float dt)
    {
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
    private void checkScore(ArrayList<Pipe> pipes)
    {
        for(int i = 0; i < pipes.size(); i++)
        {
            Pipe pipe = pipes.get(i);
            if(bounds.x > pipe.getLeftPos()  && !pipesSkipped[i])
            {
                pipesSkipped[i] = true;
                pointSound.play();
            }
            if(bounds.x < pipe.getLeftPos())
            {
                pipesSkipped[i] = false;
            }
        }
    }
    public void update(float dt, ArrayList<Pipe> pipes, MovingFloor floor){
        switch (state)
        {
            case ALIVE:
                if(collidePipes(pipes) || bounds.y > (float) Gdx.graphics.getHeight() / 3)
                {
                    hitSound.play();
                    rotSpeed = 150.0f;
                    gravity = -900;
                    state = State.FALLING;
                    collided = true;
                    break;
                }
                if(floor.collides(this))
                {
                    hitSound.play();
                    state = State.ONGROUND;
                    collided = true;
                    break;
                }
                if(Gdx.input.justTouched())
                {
                    yspeed = 200;
                    wingSound.play();
                    sprite.rotate(45 - sprite.getRotation());
                }
                checkScore(pipes);
                bounds.x += dt * xspeed;
                timer += dt;
                updateY(dt);
                break;
            case FALLING:
                if(floor.collides(this))
                {
                    collided = true;
                    state = State.ONGROUND;
                    break;
                }
                updateY(dt);
                break;
            case ONGROUND:
                break;
        }
    }
    private boolean collidePipes(ArrayList<Pipe> pipes)
    {
        for(Pipe pipe: pipes)
        {
            if(pipe.collides(this)) return true;
        }
        return false;
    }
    public boolean canRestart(){return state == State.ONGROUND;}
    public boolean dead()
    {
        return collided;
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
        wingSound.dispose();
        hitSound.dispose();
        pointSound.dispose();
    }
}