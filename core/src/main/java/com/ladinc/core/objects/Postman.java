package com.ladinc.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.ladinc.core.collision.CollisionInfo;
import com.ladinc.core.collision.CollisionInfo.CollisionObjectType;
import com.ladinc.core.contorllers.IControls;

public class Postman extends GameCharacter {

	private final Object sprite;
	private final World world;
	
	public static final int USER_SPEED = 50;
	
	public IControls controller;

	public Postman(World world, Vector2 startPos, int number, IControls cont) {

		this.world = world;

		this.setPlayerNumber(number);

		createBody(startPos);
		
		this.controller = cont;

		this.sprite = Robot.getPlayerSprite();
	}

	@Override
	public void createBody(Vector2 startPos) {
		// Dynamic Body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody; // TODO Should this be dynamic so
												// that it can 'hit' the ball

		bodyDef.position.set(startPos.x, startPos.y);

		// This keeps it that the force up is applied relative to the screen,
		// rather than the direction that the player is facing
		bodyDef.fixedRotation = true;
		this.body = world.createBody(bodyDef);

		CircleShape dynamicCircle = new CircleShape();
		dynamicCircle.setRadius(playerSize);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 10.0f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.5f;
		fixtureDef.shape = dynamicCircle;

		this.body.createFixture(fixtureDef);

		this.body.setUserData(new CollisionInfo("aiplayer",
				CollisionObjectType.AIPlayer, this));
	}
	
	public void updateMovement(float delta) {
		// sticks direction
		Vector2 movement = this.controller.getMovementInput();
		Vector2 rotation = this.controller.getRotationInput();

		Gdx.app.debug(
				"HockeyPlayer - updateMovement",
				"Movement: x=" + String.valueOf(movement.x) + " y="
						+ String.valueOf(movement.y));

//		if(invertControlsTimer > 0f)
//		{
//			invertControlsTimer = invertControlsTimer - delta;
//			movement.x = movement.x * (-1);
//			movement.y = movement.y * (-1);
//		}

		Vector2 position = this.body.getWorldCenter();

		this.body.setLinearVelocity(new Vector2((USER_SPEED) * movement.x,
				(USER_SPEED) * movement.y));

	}

}