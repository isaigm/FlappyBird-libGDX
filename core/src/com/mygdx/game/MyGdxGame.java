package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class MyGdxGame extends ApplicationAdapter {
	private Texture background;
	private SpriteBatch batch;
	private MovingFloor movingFloor;
	private OrthographicCamera camera;
	private Player player;
	@Override
	public void create () {

		movingFloor = new MovingFloor();
		batch = new SpriteBatch();
		background = new Texture("background-day.png");
		camera = new OrthographicCamera();
		player = new Player();
		int w = Gdx.graphics.getWidth() / 3;
		int h = Gdx.graphics.getHeight() / 3;
		camera.setToOrtho(false, w, h);

	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		if(!movingFloor.collides(player.getBounds()))
		{
			player.update(dt);
		}
		float cameraSpeed = 50.0f;
		camera.position.x += cameraSpeed * dt;
		movingFloor.move(camera);
		camera.update();
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, camera.position.x - camera.viewportWidth / 2, 0);
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
	}
}
