package com.ladinc.core.objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.core.movement.MovementHelper.Directions;

public abstract class GameCharacter {

	private static int PIXELS_PER_METER = 10;

	public static final double PLAYER_HEIGHT = 10;

	public static float PLAYER_SPEED = 10;

	public static Sprite getPlayerSprite() {
		Texture playerTexture;
		return null;
	}

	public Body body;
	protected OrthographicCamera camera;
	float playerSize = 3f;
	public Sprite sprite;
	public World world;

	private Directions currentDirection;

	public Directions getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(Directions currentDirection) {
		this.currentDirection = currentDirection;
	}

	public abstract void createBody(Vector2 startPos);

	// TODO Could potentially reuse this if we want to move the robots towards
	// the player
	public Vector2 getMovemenOfPlayerTowardsTargetDest(Vector2 aiLocation,
			Vector2 playerLocation) {

		Vector2 relativeVector = new Vector2();

		relativeVector.x = playerLocation.x - aiLocation.x;
		relativeVector.y = playerLocation.y - aiLocation.y;

		relativeVector.x = normalizeFloat(relativeVector.x, 1f);
		relativeVector.y = normalizeFloat(relativeVector.y, 1f);

		return relativeVector;
	}

	public float normalizeFloat(float value, float limit) {
		if (value < 0) {
			return Math.max(value, -limit);
		} else {
			return Math.min(value, limit);
		}

	}

	public void setSpritePosition(Sprite spr, int PIXELS_PER_METER,
			Body forLocation) {

		spr.setPosition(
				PIXELS_PER_METER * forLocation.getPosition().x - spr.getWidth()
						/ 2, PIXELS_PER_METER * forLocation.getPosition().y
						- spr.getHeight() / 2);
	}

	public void updateSprite(SpriteBatch spriteBatch) {
		setSpritePosition(sprite, PIXELS_PER_METER, body);
		sprite.draw(spriteBatch);
	}
}
