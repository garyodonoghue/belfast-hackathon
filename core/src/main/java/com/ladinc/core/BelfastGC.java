package com.ladinc.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ladinc.core.screens.GameScreen;

public class BelfastGC implements ApplicationListener {
	public static float delta;
	SpriteBatch batch;
	float elapsed;

	private GameScreen gameScreen;

	public int screenHeight = 1080;

	public int screenWidth = 1920;
	Texture texture;

	@Override
	public void create() {
		texture = new Texture(Gdx.files.internal("libgdx-logo.png"));
		batch = new SpriteBatch();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {
		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 100 + 100 * (float) Math.cos(elapsed),
				100 + 25 * (float) Math.sin(elapsed));
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}
}
