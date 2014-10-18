package com.ladinc.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.core.collision.CollisionInfo;
import com.ladinc.core.collision.CollisionInfo.CollisionObjectType;
import com.ladinc.core.contorllers.IControls;
import com.ladinc.core.screens.layouts.PainterLayout;

public class Robot extends GameCharacter {
	private final OrthographicCamera camera;
	private final int number;
	private final Object sprite;

	private final World world;
	public IControls controller;

	public static final int ROBOT_SPEED = 40;
	
	public Robot(World world, Vector2 startPos, int number,
			OrthographicCamera camera, IControls iControls) {

		this.world = world;
		this.camera = camera;
		this.number = number;

		createBody(startPos);

		this.controller  = iControls;
		
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

		this.body.setUserData(new CollisionInfo("robot",
				CollisionObjectType.AIPlayer, this));
	}

	public int getNumber() {
		return number;
	}

	public Object getSprite() {
		return sprite;
	}

	public void updateMovement(float delta) {
		// sticks direction
		Vector2 movement = this.controller.getMovementInput();
		Vector2 rotation = this.controller.getRotationInput();

		Gdx.app.debug(
				"Robot - updateMovement",
				"Movement: x=" + String.valueOf(movement.x) + " y="
						+ String.valueOf(movement.y));

		Vector2 position = this.body.getWorldCenter();

		this.body.setLinearVelocity(new Vector2((ROBOT_SPEED) * movement.x,
				(ROBOT_SPEED) * movement.y));

	}
	
	public void vision(Postman postman, PainterLayout painterLayout){
		if(painterLayout.getDistance(postman.body.getWorldCenter(), this.body.getWorldCenter()) < 25){
			//MAKE POSTMAN VISIBLE HERE
		}
		
	}

}