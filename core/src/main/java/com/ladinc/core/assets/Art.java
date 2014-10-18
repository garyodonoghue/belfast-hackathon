package com.ladinc.core.assets;

import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public class Art {
	public static HashMap<String, Texture> textureTable = new HashMap<String, Texture>();
	
//	public static final String GAME_DESCRIPTION_WINDOW = "GAME_DESCRIPTION_WINDOW";
//	
//	public static final String CROWN = "CROWN";
//	
//	public static final String WHEELS = "WHEELS";
//	public static final String IDENTIFIER = "IDENTIFIER";
//	
//	public static final String SOCCER_PITCH = "SOCCER_PITCH";
//	public static final String GOAL_OVERLAY = "GOAL_OVERLAY";
//	
//	
//	public static final String TOUCH_OVERLAY = "TOUCH_OVERLAY";
//	public static final String FINISHED_OVERLAY= "FINISHED_OVERLAY";
//	public static final String DEMO_MESSAGE= "DEMO_MESSAGE";
//	
//	//Team Select
//	public static final String TEAM_SELECT_AREA = "TEAM_SELECT_AREA";
//	public static final String START_GAME_CONFIRM = "START_GAME_CONFIRM";
//	public static final String START_GAME_CONFIRM_TOUCH = "START_GAME_CONFIRM_TOUCH";
//	public static final String INSTRUCTIONS_CONTROLLER = "INSTRUCTIONS_CONTROLLER";
//	public static final String INSTRUCTIONS_TOUCH = "INSTRUCTIONS_TOUCH";
//	
//	
//	public static final String RACE_BACKGROUND_1 = "RACE_BACKGROUND_1";
//	
//	
//	//Pong
//	public static final String PONG_BACKGROUND = "PONG_BACKGROUND";
//	public static final String PONG_BALL = "PONG_BALL";
	
	//Painter
	public static final String PAINTER_BACKGROUND = "PAINTER_BACKGROUND";
	public static final String PAINT_TILES = "PAINT_TILES";
	
//	//Mower
//	public static final String MOWER_BACKGROUND = "MOWER_BACKGROUND";
//	
//	//Car Pool
//	
//	public static final String POOL_TABLE = "POOL_TABLE";
	
	public static HashMap<String, Sprite> spriteTable = new HashMap<String, Sprite>();
	
	public static void load()
	{
		loadTextures();
	}
	
	private static void loadTextures()
	{

		
		textureTable.put(PAINTER_BACKGROUND, new Texture(Gdx.files.internal("Painter/PainterBackground.png")));
		textureTable.put(PAINT_TILES, new Texture(Gdx.files.internal("Painter/PaintTiles.png")));
		
		
	}
	
	public static void updateSprite(Sprite sprite, SpriteBatch spriteBatch, int PIXELS_PER_METER, Body body)
	{
		if(sprite != null && spriteBatch != null && body != null)
		{
			setSpritePosition(sprite, PIXELS_PER_METER, body);
	
			sprite.draw(spriteBatch);
		}
	}
	
	public static void setSpritePosition(Sprite sprite, int PIXELS_PER_METER, Body body)
	{
		
		sprite.setPosition(PIXELS_PER_METER * body.getPosition().x - sprite.getWidth()/2,
				PIXELS_PER_METER * body.getPosition().y  - sprite.getHeight()/2);
		sprite.setRotation((MathUtils.radiansToDegrees * body.getAngle()));
	}
	
	public static Sprite getSprite(String str)
	{
		if(!spriteTable.containsKey(str))
		{
			spriteTable.put(str, new Sprite(Art.textureTable.get(str)));
		}
		
		return spriteTable.get(str);
	}
	
}