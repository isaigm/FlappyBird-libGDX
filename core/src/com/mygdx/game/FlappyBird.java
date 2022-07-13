package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class FlappyBird extends ApplicationAdapter {
	private Texture background;
	private SpriteBatch batch;
	private MovingFloor movingFloor;
	private OrthographicCamera camera;
	private Player player;
	private Pipe pipe;
	private int scrHeight;
	private int scrWidth;
	@Override
	public void create () {
		movingFloor = new MovingFloor();
		batch = new SpriteBatch();
		background = new Texture("background-day.png");
		camera = new OrthographicCamera();
		player = new Player();
		scrWidth = Gdx.graphics.getWidth() / 3;
		scrHeight = Gdx.graphics.getHeight() / 3;
		camera.setToOrtho(false, scrWidth, scrHeight);
		pipe = new Pipe();
		pipe.setHeight(movingFloor.getHeight(), scrHeight, 100, 100);
		pipe.setPos(400);
	}
	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		if(!movingFloor.collides(player.getBounds()))
		{
			player.update(dt);
		}
		if(pipe.outOfScene(camera.position.x - camera.viewportWidth / 2))
		{
			pipe.setPos(camera.position.x + camera.viewportWidth / 2);
		}
		float cameraSpeed = 50.0f;
		camera.position.x += cameraSpeed * dt;
		movingFloor.move(camera);
		camera.update();
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, camera.position.x - camera.viewportWidth / 2, 0);
		pipe.render(batch);
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
		pipe.dispose();
	}
}
