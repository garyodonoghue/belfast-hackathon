package com.ladinc.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ladinc.core.contorllers.MyControllerManager;
import com.ladinc.core.screens.GameScreen;

public class BelfastGC extends Game {
	public static float delta;
	SpriteBatch batch;
	float elapsed;

	private GameScreen gameScreen;

	public int screenHeight = 1080;

	public int screenWidth = 1920;
	Texture texture;
	
	public MyControllerManager mcm;

	@Override
	public void create() 
	{
		
		this.mcm = new MyControllerManager();
		createScreens();
		setScreen(gameScreen);
	}
	
	private void createScreens() 
	{
		this.gameScreen = new GameScreen(this);

	}

}
