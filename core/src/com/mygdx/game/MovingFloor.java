package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MovingFloor {
    private final Texture base;
    private final Rectangle baseBounds;
    private final Rectangle auxBaseBounds;
    public MovingFloor()
    {
        base = new Texture("base.png");
        baseBounds = new Rectangle();
        auxBaseBounds = new Rectangle();
        baseBounds.x = 0;
        auxBaseBounds.x = base.getWidth();
        baseBounds.y = auxBaseBounds.y = 0;
        baseBounds.width = auxBaseBounds.width = base.getWidth();
        baseBounds.height = auxBaseBounds.height = base.getHeight();
    }
    public void move(Camera camera)
    {
        float camPos = camera.position.x - camera.viewportWidth / 2;
        if(camPos > baseBounds.x + base.getWidth())
        {
            baseBounds.x = auxBaseBounds.x + base.getWidth();
        }
        if(camPos > auxBaseBounds.x + base.getWidth())
        {
            auxBaseBounds.x = baseBounds.x + base.getWidth();
        }
    }
    public int getHeight()
    {
        return base.getHeight();
    }
    public boolean collides(Rectangle player)
    {
        return player.overlaps(baseBounds) || player.overlaps(auxBaseBounds);
    }
    public void render(SpriteBatch batch)
    {
        batch.draw(base, baseBounds.x, baseBounds.y);
        batch.draw(base, auxBaseBounds.x, auxBaseBounds.y);
    }
    public void dispose()
    {
        base.dispose();
    }
}
