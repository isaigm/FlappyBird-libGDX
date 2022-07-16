package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Pipe {
    private TextureRegion bottomPipe, topPipe;
    private Rectangle bottomPipeBounds, topPipeBounds;
    private final Texture texture;

    public Pipe(){
        texture = new Texture("pipe.png");
        bottomPipe = new TextureRegion(texture);
        topPipe = new TextureRegion(texture);
        bottomPipeBounds = new Rectangle();
        topPipeBounds = new Rectangle();
        bottomPipeBounds.width = texture.getWidth();
        topPipeBounds.width = texture.getWidth();
    }
    void setHeight(int baseHeight, int scrHeight, int bottomPipeHeight, int topPipeHeight){
        bottomPipeBounds.y = baseHeight;
        bottomPipe.setRegion(0, 0, texture.getWidth(), bottomPipeHeight);
        topPipe.setRegion(0, 0, texture.getWidth(), topPipeHeight);
        topPipe.flip(false, true);
        bottomPipeBounds.height = bottomPipeHeight;
        topPipeBounds.height = topPipeHeight;
        topPipeBounds.y = scrHeight - topPipeHeight;
    }
    boolean outOfScene(float cameraPos)
    {
        return cameraPos > bottomPipeBounds.x + bottomPipe.getRegionWidth();
    }
    void setPos(float pos)
    {
        bottomPipeBounds.x = pos;
        topPipeBounds.x = pos;
    }
    boolean collides(Player player)
    {
        return player.getBounds().overlaps(bottomPipeBounds) || player.getBounds().overlaps(topPipeBounds);
    }
    public void render(SpriteBatch batch)
    {
        batch.draw(bottomPipe, bottomPipeBounds.x, bottomPipeBounds.y);
        batch.draw(topPipe, topPipeBounds.x, topPipeBounds.y);
    }
    public void dispose()
    {
        texture.dispose();
    }
}
