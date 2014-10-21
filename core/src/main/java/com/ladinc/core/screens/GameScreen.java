package com.ladinc.core.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.core.BelfastGC;
import com.ladinc.core.assets.Art;
import com.ladinc.core.collision.CollisionHelper;
import com.ladinc.core.contorllers.GamePadControls;
import com.ladinc.core.contorllers.KeyboardAndMouseControls;
import com.ladinc.core.contorllers.listeners.MCPListenerClient;
import com.ladinc.core.objects.FloorTileSensor;
import com.ladinc.core.objects.Postman;
import com.ladinc.core.objects.Robot;
import com.ladinc.core.screens.layouts.PainterLayout;

public class GameScreen implements Screen {

	public static Vector2 center = new Vector2();
	private static final int NUMBER_OF_PLAYERS = 1; // TODO 4
	private static int PIXELS_PER_METER = 10;
	private final OrthographicCamera camera;
	private final Box2DDebugRenderer debugRenderer;
	private final BelfastGC game;
	private PainterLayout layout;
	private Postman postman;
	private Map<Integer, Vector2> robotPositions;
	private List<Robot> robots;
	private final int screenHeight;
	private final int screenWidth;
	private final SpriteBatch spriteBatch;
	private World world;
	public static int lettersDelivered = 0;
	private final int worldHeight;
	private final int worldWidth;
	public static boolean GAME_OVER = false;
	public static boolean INTRO_SCREEN = true;
	
	private Texture postmanTexture;
	
	private BitmapFont font;
	private Texture gameOverTexture;
	//private Sound wavSound = Gdx.audio.newSound(Gdx.files.internal("Futuristic music for game.wav"));
	private Texture tgtHouseTexture;
	private Texture normalHouseTexture;
	private Texture robotTexture;
	
	private Sprite bgImage;

	private Texture splashScreenTexture;
	
	public GameScreen(BelfastGC game) {
		this.game = game;

		this.screenWidth = this.game.screenWidth;
		this.screenHeight = this.game.screenHeight;

		this.worldHeight = screenHeight / PIXELS_PER_METER;
		this.worldWidth = this.screenWidth / PIXELS_PER_METER;

		center = new Vector2(worldWidth / 2, worldHeight / 2);

		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.screenWidth, screenHeight);

		this.spriteBatch = new SpriteBatch();

		this.debugRenderer = new Box2DDebugRenderer();
		
		this.gameOverTexture = new Texture(Gdx.files.internal("gameOverImg.png"));
		
		this.splashScreenTexture = new Texture(Gdx.files.internal("postmanPanic.png"));
		
		font = new BitmapFont(Gdx.files.internal("Swis-721-50.fnt"), Gdx.files.internal("Swis-721-50.png"), false);
		postmanTexture = new Texture(Gdx.files.internal("postman.png"));
		
		tgtHouseTexture  = new Texture(Gdx.files.internal("house_target.png"));
		normalHouseTexture  = new Texture(Gdx.files.internal("house_normal.png"));
		robotTexture  = new Texture(Gdx.files.internal("robot.png"));
		
		bgImage = Art.getSprite(Art.PAINTER_BACKGROUND);
		bgImage.setPosition(0, 0);
		
		this.font.setColor(Color.WHITE);
	}
	
	private void getPostmanPositionIPad(PainterLayout painterLayout) {
		JSONObject obj = new JSONObject();
		obj.put("postx", painterLayout.postman.body.getWorldCenter().x / this.worldWidth);
		obj.put("posty", painterLayout.postman.body.getWorldCenter().y / this.worldHeight);
		obj.put("mailboxx", painterLayout.mailboxTile.body.getWorldCenter().x / this.worldWidth);
		obj.put("mailboxy", painterLayout.mailboxTile.body.getWorldCenter().y / this.worldHeight);
		this.game.mcm.moreControllers.hearbeatResponses.put("1", obj);
	}

	private void createLayout() {
		this.layout = new PainterLayout(world, worldWidth, worldHeight, center,
				0, postman);
	}
	
	private void buildRobotPositionsMap() {
		robotPositions = new HashMap<Integer, Vector2>();

		Vector2 robot1Pos = new Vector2(70, 80);
		Vector2 robot2Pos = new Vector2(20, 10);
		Vector2 robot3Pos = new Vector2(60, 30);

		robotPositions.put(0, robot1Pos);
		robotPositions.put(1, robot2Pos);
		robotPositions.put(2, robot3Pos);
	}

	// this will add a predefined number of robots
	private void createAndAssignControls() {
		robots = new ArrayList<Robot>();

		// Loop until all players have joined the game
		while (this.game.mcm.inActiveControls.size() < NUMBER_OF_PLAYERS) {

			// TODO: waiting for all players to join message
			System.out.println("Waiting for players to join!");
		}

		// All players have joined!
		for (int i = 0; i < this.game.mcm.inActiveControls.size(); i++) {
			if(this.game.mcm.inActiveControls.get(i).getClass() == KeyboardAndMouseControls.class)
			{
//				postman = new Postman(world, center, 0,
//						this.game.mcm.inActiveControls.get(i), false);
				
				postman = new Postman(world, center, 0,
				MCPListenerClient.gpc, false);
			}
			else if (this.game.mcm.inActiveControls.get(i).getClass() == GamePadControls.class) {
				// assign all the players using controllers to robots, the
				// person using the ipad will be the postman

				// Get a robot position from our starting positions map
				Vector2 robotPosition = robotPositions.get(i);
				Robot robot = new Robot(world, robotPosition, i, camera,
						this.game.mcm.inActiveControls.get(i));
				robots.add(robot);
			}
		}
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.spriteBatch.begin();
		
		

		if(INTRO_SCREEN){
			spriteBatch.draw(splashScreenTexture, 0, 0);
			
			if(Gdx.input.isButtonPressed(0)){ 
				INTRO_SCREEN = false;
			}
		}
		//check for Game Over, if set, play game over sound, reset values 
		else {
			if(GAME_OVER){
		
			displayGameOverImage();
			
			if(Gdx.input.isButtonPressed(0)){ 
				System.out.println("reset Game button pressed");
					GameScreen.GAME_OVER = false;
					GameScreen.lettersDelivered = 0;
					this.game.setScreen(new GameScreen(game));
			}
				
			//resetValues(); //TODO New instance of Game screen
		}
		else{
			
		this.bgImage.draw(spriteBatch);
		// camera.zoom = 2f;
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		world.step(Gdx.app.getGraphics().getDeltaTime(), 10, 10);
		// world.clearForces();
		// world.step(1/60f, 3, 3);
		world.clearForces();

		postman.updateMovement(delta);

		updatePostmanSprite();

		updateTileSprites();
		
		updateRobotSprites();
		
		for(Robot robot : robots){
			if(robot!=null){
				robot.updateMovement(delta);
			}
		}
		postman.canRobotsSeeMe(robots, this.layout);

		//layout.drawSpritesForTiles(spriteBatch, PIXELS_PER_METER);
		
		String scoreText = "Mail Delivered: " + lettersDelivered;
		this.font.setColor(Color.BLACK);
		this.font.draw(spriteBatch, scoreText, this.screenWidth/2 - this.font.getBounds(scoreText).width/2, 1050);
	}

		getPostmanPositionIPad(this.layout);
		
		//wavSound.loop();
		
//		if(!GAME_OVER){
//		debugRenderer.render(world, camera.combined.scale(PIXELS_PER_METER,
//				PIXELS_PER_METER, PIXELS_PER_METER));
//		}
	}
		this.spriteBatch.end();
	}
	private void updateRobotSprites() {
		for(Robot robot : robots){
			updateSprite(new Sprite(new Sprite(robotTexture)), spriteBatch, PIXELS_PER_METER, robot.body);
		}
	}

	private void updateTileSprites() {
		for(FloorTileSensor floorTile : PainterLayout.floorSensors){
			if(floorTile.isBlock){
				if(floorTile.ismailbox){
					//updateSprite(new Sprite(tgtHouseTexture), spriteBatch, PIXELS_PER_METER, floorTile.body);
					updateSprite(new Sprite(normalHouseTexture), spriteBatch, PIXELS_PER_METER, floorTile.body);
				}
				else{
					updateSprite(new Sprite(normalHouseTexture), spriteBatch, PIXELS_PER_METER, floorTile.body);
				}
			}
		}
	}

	private void displayGameOverImage() {
		spriteBatch.draw(gameOverTexture, 0, 0);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		world = new World(new Vector2(0.0f, 0.0f), true);
		
		buildRobotPositionsMap();
		createAndAssignControls();
				
		createLayout();
		world.setContactListener(new CollisionHelper(this.layout));
	}

		private void updatePostmanSprite() {
			//TODO Move this into a map
			if(postman.isVisible()){ //only draw the postman if he's close to robots
				updateSprite(new Sprite(postmanTexture), spriteBatch, PIXELS_PER_METER, postman.body);
			}
	}

		public static void updateSprite(Sprite sprite, SpriteBatch spriteBatch,
				int PIXELS_PER_METER, Body body) {
			if (sprite != null && spriteBatch != null && body != null) {
				setSpritePosition(sprite, PIXELS_PER_METER, body);
				sprite.draw(spriteBatch);
			}
		}
		
		public static void setSpritePosition(Sprite sprite, int PIXELS_PER_METER,
				Body body) {

			sprite.setPosition(
					PIXELS_PER_METER * body.getWorldCenter().x - sprite.getWidth()
							/ 2, PIXELS_PER_METER * body.getWorldCenter().y
							- sprite.getHeight() / 2);

			sprite.setRotation((MathUtils.radiansToDegrees * body.getAngle()));
		}	
}
