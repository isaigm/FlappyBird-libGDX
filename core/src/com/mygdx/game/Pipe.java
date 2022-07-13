package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Pipe {
    private TextureRegion bottomPipe, topPipe;
    private Rectangle bottomPipeBounds, topPipeBounds;
    private final Texture texture;


    public Pipe( int bottomPipeHeight, int topPipeHeight){
        
        texture = new Texture("pipe.png");
        bottomPipe = new TextureRegion(texture);
        topPipe = new TextureRegion(texture);
        bottomPipe.setRegion(0, 0, texture.getWidth(), bottomPipeHeight);
        topPipe.setRegion(0, 0, texture.getWidth(), topPipeHeight);
        topPipe.flip(false, true);

    }
    public void render(int scrHeight, int baseHeight, SpriteBatch batch)
    {

        batch.draw(bottomPipe, 100, baseHeight);
        batch.draw(topPipe, 100, scrHeight - topPipe.getRegionHeight());
    }
    public void dispose()
    {
        texture.dispose();
    }
}
