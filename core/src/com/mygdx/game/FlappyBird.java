package com.mygdx.game;
import static com.mygdx.game.Random.getRandomIn;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;

public class FlappyBird extends ApplicationAdapter {
    private Texture background;
    private SpriteBatch batch;
    private MovingFloor movingFloor;
    private OrthographicCamera camera;
    private Player player;
    private int scrHeight;
    private int scrWidth;
    private ArrayList<Pipe> pipes;
    private final int npipes = 4;

    private void setRandomHeightPipe(Pipe pipe)
    {
        int freeSpace = getRandomIn(80, 85);
        if(getRandomIn(0, 1) == 1)
        {
            int topHeight = getRandomIn(90, 150);
            int bottomHeight = scrHeight - movingFloor.getHeight() - freeSpace - topHeight;
            pipe.setHeight(movingFloor.getHeight(), scrHeight, bottomHeight, topHeight);
        }else
        {
            int bottomHeight = getRandomIn(90, 150);
            int topHeight = scrHeight - movingFloor.getHeight() - freeSpace - bottomHeight;
            pipe.setHeight(movingFloor.getHeight(), scrHeight, bottomHeight, topHeight);
        }
    }
    private void spawnPipes()
    {
        int color = getRandomIn(0, 1);
        if(pipes.size() > 0)
        {
            pipes.clear();
        }
        int x = scrWidth + 100;
        for(int i = 0; i < npipes; i++)
        {
            int freeSpace = getRandomIn(150, 200);
            Pipe pipe = new Pipe(color);
            pipe.setPos(x);
            setRandomHeightPipe(pipe);
            pipes.add(pipe);
            x += freeSpace;
        }
    }
    private void setRandomBackground()
    {
        if(getRandomIn(0, 1) == 1)
        {
            background = new Texture("background-day.png");
        }else
        {
            background = new Texture("background-night.png");
        }
    }
    @Override
    public void create () {
        pipes = new ArrayList<>();
        movingFloor = new MovingFloor();
        batch = new SpriteBatch();
        setRandomBackground();
        camera = new OrthographicCamera();
        player = new Player();
        scrWidth = Gdx.graphics.getWidth() / 3;
        scrHeight = Gdx.graphics.getHeight() / 3;
        camera.setToOrtho(false, scrWidth, scrHeight);
        spawnPipes();
    }
    private void detectOutOfScenePipes()
    {
        Pipe pipe = pipes.get(0);
        if(pipe.outOfScene(camera))
        {
            setRandomHeightPipe(pipe);
            float nextPos = camera.position.x + camera.viewportWidth / 2;
            float lastPipePos = pipes.get(npipes - 1).getX();
            if(pipes.get(npipes - 1).getX()  < nextPos)
            {
                pipe.setPos(nextPos);
            }else
            {
                pipe.setPos(lastPipePos + getRandomIn(150, 200));
            }
            pipes.remove(0);
            pipes.add(pipe);
        }
    }
    @Override
    public void render () {
        float dt = Gdx.graphics.getDeltaTime();
        player.update(dt, pipes, movingFloor);
        if(!player.dead())
        {
            detectOutOfScenePipes();
            float cameraSpeed = 120.0f;
            camera.position.x += cameraSpeed * dt;
            movingFloor.move(camera);
        }else if(Gdx.input.justTouched() && player.canRestart())
        {
            player.restart();
            movingFloor.restart();
            camera.position.x = camera.viewportWidth / 2;
            setRandomBackground();
            spawnPipes();
        }
        camera.update();
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, camera.position.x - camera.viewportWidth / 2, 0);
        for(Pipe pipe: pipes)
        {
            pipe.render(batch);
        }
        player.render(batch);
        movingFloor.render(batch);
        batch.end();
    }
    @Override
    public void dispose () {
        batch.dispose();
        background.dispose();
        movingFloor.dispose();
        player.dispose();
        for(Pipe pipe: pipes)
        {
            pipe.dispose();
        }
    }
}
