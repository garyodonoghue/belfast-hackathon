package com.ladinc.core.objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameCharacter {

	private static int PIXELS_PER_METER = 10;

	public static final double PLAYER_HEIGHT = 10;

	public static float PLAYER_SPEED = 10;

	public static Sprite getPlayerSprite() {
		Texture playerTexture;
		return null;
	}

	protected Vector2 attackingPos;
	public Body body;

	protected OrthographicCamera camera;

	protected Vector2 defendingPos;

	public double distFromBall;

	private boolean hasBall = false;

	private boolean isClosestPlayerToBall = false;

	private final Vector2 leftMovement = new Vector2(0, 0);
	private int playerNumber;

	float playerSize = 3f;

	public Sprite sprite;

	public World world;

	public abstract void createBody(Vector2 startPos);

	public Vector2 getAttackingPos() {
		return attackingPos;
	}

	public Vector2 getDefendingPos() {
		return defendingPos;
	}

	public double getDistFromBall() {
		return distFromBall;
	}

	public boolean getHasBall() {
		return hasBall;
	}

	// When attacking, this method will get the movement of the player towards
	// some target position on the pithc
	// When defending, this will either get the moveemnt of the player to the
	// ball if that player is the closest,
	// or it will get the movement towards the players target defending position
	public Vector2 getMovemenOfPlayerTowardsTargetDest(Vector2 aiLocation,
			Vector2 playerLocation) {

		Vector2 relativeVector = new Vector2();

		relativeVector.x = playerLocation.x - aiLocation.x;
		relativeVector.y = playerLocation.y - aiLocation.y;

		relativeVector.x = normalizeFloat(relativeVector.x, 1f);
		relativeVector.y = normalizeFloat(relativeVector.y, 1f);

		return relativeVector;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public boolean isClosestPlayerToBall() {
		return isClosestPlayerToBall;
	}

	public float normalizeFloat(float value, float limit) {
		if (value < 0) {
			return Math.max(value, -limit);
		} else {
			return Math.min(value, limit);
		}

	}

	public void setAttackingPos(Vector2 attackingPos) {
		this.attackingPos = attackingPos;
	}

	public void setClosestPlayerToBall(boolean isClosestPlayerToBall) {
		this.isClosestPlayerToBall = isClosestPlayerToBall;
	}

	public void setDefendingPos(Vector2 defendingPos) {
		this.defendingPos = defendingPos;
	}

	public void setDistFromBall(double d) {
		this.distFromBall = d;
	}

	public void setHasBall(boolean hasBall) {
		this.hasBall = hasBall;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
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
