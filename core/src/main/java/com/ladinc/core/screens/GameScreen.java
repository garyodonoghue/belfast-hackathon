package com.ladinc.core.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.core.BelfastGC;
import com.ladinc.core.collision.CollisionHelper;
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
	private List<Robot> robots;
	private final int screenHeight;
	private final int screenWidth;
	private final SpriteBatch spriteBatch;
	private World world;

	private final int worldHeight;

	private final int worldWidth;

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
	}

	private void createLayout() {
		this.layout = new PainterLayout(world, worldWidth, worldHeight, center,
				0);

	}

	private void createPostman() {
		postman = new Postman(world, center, 0,
				this.game.mcm.inActiveControls.get(0));
	}

	// this will add a predefined number of robots
	private void createRobots() {
		robots = new ArrayList<Robot>();
		for (int i = 0; i < NUMBER_OF_ROBOTS; i++) {
			// TODO generate a random starting position for the robot
			Vector2 robotPos = new Vector2(50, 50);
			Robot robot = new Robot(world, robotPos, i, camera,
					this.game.mcm.inActiveControls.get(0)); // TODO Controller
															// management needs
															// work
			robots.add(robot);
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

		// camera.zoom = 2f;
		camera.update();
		// TODO: spriteBatch.setProjectionMatrix(camera.combined);

		world.step(Gdx.app.getGraphics().getDeltaTime(), 10, 10);
		// world.clearForces();
		// world.step(1/60f, 3, 3);
		world.clearForces();

		postman.updateMovement(delta);

		this.spriteBatch.begin();
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
		createRobots();
	}

}