package com.ladinc.core.collision;

import java.awt.MultipleGradientPaint.ColorSpaceType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ladinc.core.collision.CollisionInfo.CollisionObjectType;
import com.ladinc.core.objects.FloorTileSensor;
import com.ladinc.core.objects.Postman;
import com.ladinc.core.objects.Robot;

public class CollisionHelper implements ContactListener{

//	private Sound warriorCollideSound;
//	private Sound fall;
//	
//	public CollisionHelper(Sound collide, Sound fall)
//	{
//		this.warriorCollideSound = collide;
//		this.fall = fall;
//	}
	
	//public Sound sound = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
	
	public CollisionHelper()
	{
	}
	
	public boolean newScore = false;
	
//	public Side getLastScored()
//	{
//		newScore = false;
//	}
	
	@Override
	public void beginContact(Contact contact) 
	{
		Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        
        CollisionInfo bodyAInfo = getCollisionInfoFromFixture(fixtureA);
    	CollisionInfo bodyBInfo = getCollisionInfoFromFixture(fixtureB);

        
        if(bodyAInfo != null && bodyBInfo != null)
        {
        	
        	Gdx.app.debug("beginContact", "between " + bodyAInfo.type.toString() + " and " + bodyBInfo.type.toString());
        	
        	if (checkIfCollisionIsOfCertainBodies(bodyAInfo, bodyBInfo, CollisionObjectType.Postman, CollisionObjectType.FloorSensor))
        	{
        		Postman postman;
        		FloorTileSensor fts;
        		//Enemy has hit a sword
        		if(bodyAInfo.type == CollisionObjectType.Postman)
        		{
        			postman = (Postman) bodyAInfo.object;
        			fts = (FloorTileSensor) bodyBInfo.object;
        		}
        		else
        		{
        			postman = (Postman) bodyBInfo.object;
        			fts = (FloorTileSensor) bodyAInfo.object;
        		}
        		
        		//if the hockey player's sword is disabled, dont kill the enemy
    			if(fts.isBlock)
    			{
    				//if(fts.ismailbox)
//    				{
//    					//mail delivered mthod
//    				}
    			}
        	}
        	else if(checkIfCollisionIsOfCertainBodies(bodyAInfo, bodyBInfo, CollisionObjectType.Postman, CollisionObjectType.Robot))
        	{
        		Postman postman;
        		Robot robot;
        		
        		//Enemy has hit a sword
        		if(bodyAInfo.type == CollisionObjectType.Postman)
        		{
        			postman = (Postman) bodyAInfo.object;
        			robot = (Robot) bodyBInfo.object;
        		}
        		else
        		{
        			postman = (Postman) bodyBInfo.object;
        			robot = (Robot) bodyAInfo.object;
        		}
        		
        		//Game OVER
        	}
        }
        
		
	}
	
	public static boolean checkIfCollisionIsOfCertainBodies(CollisionInfo bodyAInfo, CollisionInfo bodyBInfo, CollisionObjectType type1, CollisionObjectType type2)
	{
		return (bodyAInfo.type == type1 && bodyBInfo.type == type2) || (bodyAInfo.type == type2 && bodyBInfo.type == type1);
	}
		
	private void playerCollideIsDetected(CollisionInfo bodyA, CollisionInfo bodyB)
	{
//		if (this.warriorCollideSound != null)
//			this.warriorCollideSound.play(0.5f);
		
		Gdx.app.debug("Collision Helper", "warrior collide");
	}

	@Override
	public void endContact(Contact contact) 
	{
//		Fixture fixtureA = contact.getFixtureA();
//        Fixture fixtureB = contact.getFixtureB();
//		
//        CollisionInfo bodyAInfo = getCollisionInfoFromFixture(fixtureA);
//    	CollisionInfo bodyBInfo = getCollisionInfoFromFixture(fixtureB);
//    	
//    	if(bodyAInfo != null && bodyBInfo != null)
//        {
//    		if(bodyAInfo.type == CollisionObjectType.playerSensor)
//    		{
//    			if(bodyBInfo.type == CollisionObjectType.Arena)
//    			{
//    				handleFall(bodyAInfo.warrior);
//    				Gdx.app.log("endContact", bodyAInfo.text);
//    			}
//    		}
//    		if(bodyBInfo.type == CollisionObjectType.playerSensor)
//    		{
//    			if(bodyAInfo.type == CollisionObjectType.Arena)
//    			{
//    				handleFall(bodyBInfo.warrior);
//    				
//    				Gdx.app.log("endContact", bodyBInfo.text);
//    			}
//    		}
//        }
        
	}
	
//	private void handleFall(Warrior victim)
//	{
//		if (this.fall != null)
//			this.fall.play(0.9f);
//		
//		victim.inPlay = false;
//		
//		int score = victim.score / 2;
//		
//		if (score < 1)
//			score = 1;
//		
//		if (victim.lastPersonToTouch != null)
//		{		
//			victim.lastPersonToTouch.score = victim.lastPersonToTouch.score + score;
//			
//			Gdx.app.log("SCORE", "Warrior " + victim.lastPersonToTouch.warriorNumber + "Scored " + score + " points and now has " + victim.lastPersonToTouch.score);
//		}
//	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	private CollisionInfo getCollisionInfoFromFixture(Fixture fix)
	{	
		CollisionInfo colInfo = null;
		
		if(fix != null)
        {
			Body body = fix.getBody();
			
			if(body != null)
			{
				colInfo = (CollisionInfo) body.getUserData();
			}
        }
		
		return colInfo;
	}

}

