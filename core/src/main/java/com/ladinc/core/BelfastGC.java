package com.ladinc.core;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ladinc.core.assets.Assets;
import com.ladinc.core.contorllers.MyControllerManager;
import com.ladinc.core.screens.GameScreen;
import com.ladinc.mcp.MCP;
import com.ladinc.mcp.RedirectOption;

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
		Assets.load();
		this.mcm = new MyControllerManager();
		
		this.mcm.moreControllers.customLinks = new ArrayList<String>();
		this.mcm.moreControllers.customLinks.add("Background.png");
		this.mcm.moreControllers.customLinks.add("post.png");
		this.mcm.moreControllers.customLinks.add("target.png");
		this.mcm.moreControllers.customLinks.add("transStick.png");
		this.mcm.moreControllers.customLinks.add("postman.html");
		
		this.mcm.moreControllers.redirectOptions = new ArrayList<RedirectOption>();
		this.mcm.moreControllers.redirectOptions.add(new RedirectOption("postman.html", "Postman"));
		
		MCP.SHOW_DEBUG_LOGGING = false;
		
		
		createScreens();
		setScreen(gameScreen);
	}
	
	private void createScreens() 
	{
		this.gameScreen = new GameScreen(this);

	}

}
