package com.ladinc.core.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.core.BelfastGC;
import com.ladinc.core.collision.CollisionHelper;
import com.ladinc.core.contorllers.GamePadControls;
import com.ladinc.core.contorllers.listeners.MCPListenerClient;
import com.ladinc.core.objects.Postman;
import com.ladinc.core.objects.Robot;
import com.ladinc.core.screens.layouts.PainterLayout;

public class GameScreen implements Screen {

	public static Vector2 center = new Vector2();
	private static final int NUMBER_OF_ROBOTS = 3;
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
	
	private BitmapFont font;

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
		
		font = new BitmapFont(Gdx.files.internal("Swis-721-50.fnt"), Gdx.files.internal("Swis-721-50.png"), false);
		this.font.setColor(Color.WHITE);
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
	private void createAndAssignRobotControls() {
		robots = new ArrayList<Robot>();

		// Loop until all players have joined the game
		while (this.game.mcm.inActiveControls.size() < 2) { // TODO 4
			// TODO: waiting for all players to join message
		}

		// All players have joined!
		for (int i = 0; i < this.game.mcm.inActiveControls.size(); i++) {
			if (this.game.mcm.inActiveControls.get(i).getClass() == GamePadControls.class) {
				// assign all the players using controllers to robots, the
				// person using the ipad will be the postman

				// Get a robot position from our starting positions map
				Vector2 robotPosition = robotPositions.get(i);
				Robot robot = new Robot(world, robotPosition, i, camera,
						this.game.mcm.inActiveControls.get(i));
				robots.add(robot);
			}
		}

		// for (int i = 0; i < NUMBER_OF_ROBOTS; i++) { //TODO Can use this if
		// we want to dynamically generate robots
		// Vector2 robot1Pos = new Vector2(70, 80);
		// Robot robot1 = new Robot(world, robot1Pos, 1, camera,
		// this.game.mcm.inActiveControls.get(0));
		//
		// Vector2 robot2Pos = new Vector2(20, 10);
		// Robot robot2 = new Robot(world, robot2Pos, 2, camera,
		// this.game.mcm.inActiveControls.get(0));
		//
		// Vector2 robot3Pos = new Vector2(60, 30);
		// Robot robot3 = new Robot(world, robot3Pos, 3, camera,
		// this.game.mcm.inActiveControls.get(0));
		//
		// robots.add(robot1);
		// robots.add(robot2);
		// robots.add(robot3);

		// }
	}

	private void createLayout() {
		this.layout = new PainterLayout(world, worldWidth, worldHeight, center,
				0);

	}

	private void createPostman() {
		postman = new Postman(world, center, 0,
				this.game.mcm.inActiveControls.get(0));
//		postman = new Postman(world, center, 0,
//				MCPListenerClient.gpc);
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

		// camera.zoom = 2f;
		camera.update();
		// TODO: spriteBatch.setProjectionMatrix(camera.combined);

		world.step(Gdx.app.getGraphics().getDeltaTime(), 10, 10);
		// world.clearForces();
		// world.step(1/60f, 3, 3);
		world.clearForces();

		postman.updateMovement(delta);

		this.spriteBatch.begin();
		
		layout.drawSpritesForTiles(spriteBatch, PIXELS_PER_METER);
		
		String scoreText = "Mail Delivered: " + lettersDelivered;
		this.font.draw(spriteBatch, scoreText, this.screenWidth/2 - this.font.getBounds(scoreText).width/2, 1050);
		
		this.spriteBatch.end();

		debugRenderer.render(world, camera.combined.scale(PIXELS_PER_METER,
				PIXELS_PER_METER, PIXELS_PER_METER));
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
		world.setContactListener(new CollisionHelper());

		createLayout();

		createPostman();
		buildRobotPositionsMap();
		createAndAssignRobotControls();
	}

}
