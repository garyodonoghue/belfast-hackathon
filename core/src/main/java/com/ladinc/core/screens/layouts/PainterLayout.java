package com.ladinc.core.screens.layouts;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.core.objects.BoxProp;
import com.ladinc.core.objects.FloorTileSensor;
import com.ladinc.core.objects.Postman;
import com.ladinc.core.utilities.Enums.BoxType;

public class PainterLayout extends GenericLayout {
	
	private static final float GAP_BETWEEN_SIDEWALL_AND_EDGE = 10.5f;
	private static final float GAP_BETWEEN_TOPBOTTOMWALL_AND_EDGE = 8.5f;
	private static final float TILE_SIZE = 10f;
	
	private static final float PLAYER_GAP_X = 8f;
	
	private final Sprite houseSprite;
	private final Sprite mailBox;
	
	public int homeScore = 0;
	public int awayScore = 0;
	
	public ArrayList<FloorTileSensor> floorSensors;
	public ArrayList<FloorTileSensor> possibleMailBoxes;
	
	
	public PainterLayout(World world, float worldWidth, float worldHeight,
			Vector2 center, int numberOfInnerWalls, Postman postman) {
		super(world, worldWidth, worldHeight, center, numberOfInnerWalls, postman);
		
		houseSprite = FloorTileSensor.getSprite(BoxType.House);
		mailBox = FloorTileSensor.getSprite(BoxType.Mailbox);
	}
	
//	@Override
//	public StartingPosition getPlayerStartPoint(Team team, int playerTeamNumber)
//	{
//		float carPosX;
//		float carPosY = getWorldHeight() / 2;
//		
//		int direction;
//		
////		if (team == Team.Away)
////		{
////			direction = -1;
////		}
////		else
////		{
////			direction = 1;
////		}
//		
//		if (playerTeamNumber % 2 == 0)
//		{
//			carPosX = getWorldWidth() / 2 - (direction)
//					* (((playerTeamNumber) + 1) * PLAYER_GAP_X);
//		}
//		else
//		{
//			carPosX = getWorldWidth() / 2 + (direction)
//					* (((playerTeamNumber) + 1) * PLAYER_GAP_X);
//		}
//		
//		return new StartingPosition(new Vector2(carPosX, carPosY), 0);
//	}
	
	@Override
	public void createWorld(World world)
	{
		new BoxProp(world, getWorldWidth(), 1, new Vector2(getWorldWidth() / 2,
				GAP_BETWEEN_TOPBOTTOMWALL_AND_EDGE));// left
		new BoxProp(world, getWorldWidth(), 1, new Vector2(getWorldWidth() / 2,
				getWorldHeight() - GAP_BETWEEN_TOPBOTTOMWALL_AND_EDGE));
		
		new BoxProp(world, 1, getWorldHeight(), new Vector2(
				GAP_BETWEEN_SIDEWALL_AND_EDGE, getWorldHeight() / 2));// left
		new BoxProp(world, 1, getWorldHeight(), new Vector2(getWorldWidth()
				- GAP_BETWEEN_SIDEWALL_AND_EDGE, getWorldHeight() / 2));
		
		createTiles(world);
	}
	
	private void createTiles(World world)
	{
		float tempX;
		float tempY;
		
		if (floorSensors == null)
		{
			floorSensors = new ArrayList<FloorTileSensor>();
		}
		else
		{
			floorSensors.clear();
		}
		
		for (int i = 0; i < 17; i++)
		{
			tempX = i * TILE_SIZE + TILE_SIZE / 2
					+ GAP_BETWEEN_SIDEWALL_AND_EDGE + 0.5f;
			for (int j = 0; j < 9; j++) {
				tempY = j * TILE_SIZE + TILE_SIZE / 2
						+ GAP_BETWEEN_TOPBOTTOMWALL_AND_EDGE + 0.5f;
				if (i % 2 == 1 && j % 2 == 1) {
					floorSensors.add(new FloorTileSensor(world, TILE_SIZE,
							TILE_SIZE, new Vector2(tempX, tempY), false));
				} 
			}
		}
		
		possibleMailBoxes = new ArrayList<FloorTileSensor>();
		for (FloorTileSensor floorTileSensor : floorSensors) {
			if(floorTileSensor.isBlock()){
				possibleMailBoxes.add(floorTileSensor);
			}
		}
		
		determineMailbox();
	}

	public void determineMailbox() {
		//setting mailbox
		ArrayList<Double> listOfMailboxDistances = new ArrayList<Double>();
		double sum = 0;
		for (FloorTileSensor possibleMailBox : possibleMailBoxes) {
			Vector2 houseVector = possibleMailBox.body.getWorldCenter();
			Vector2 playerVector =  postman.body.getWorldCenter();
			double distance = getDistance(houseVector, playerVector);
			listOfMailboxDistances.add(distance);
			sum += distance;
		}
		
		double averageDistance  =  sum / listOfMailboxDistances.size();
		int f = 0;
		ArrayList<FloorTileSensor> furtherThanAverageMailBoxes= new ArrayList<FloorTileSensor>();
		for (Double mailDistance : listOfMailboxDistances) {
			if(mailDistance > averageDistance){
				furtherThanAverageMailBoxes.add(possibleMailBoxes.get(f));
			}
			f += 1;
		}
		
		Random r = new Random();
		int a = r.nextInt(furtherThanAverageMailBoxes.size());
		furtherThanAverageMailBoxes.get(a).setIsmailbox(true);//set mailbox
		
		
	}
	
	// Get distance of a player from the ball, can use this to determine if the
	// player has control of the ball
	// Use the formula 'root((x1-x2)2 + (y1-y2)2)' //TODO Confirm this!!
	public double getDistance(Vector2 house,Vector2 player) {
		double xDist = ((house.x - player.x) * (house.x - player.x));
		double yDist = ((house.y - player.y) * (house.y - player.y));
		double dist = Math.sqrt(xDist + yDist);
		return dist;
	}
	
	public void drawSpritesForTiles(SpriteBatch sp, int pixPerMeter)
	{
		for (FloorTileSensor fts : floorSensors)
		{
			if (fts.isBlock)
			{
				fts.updateSprite(houseSprite, sp, pixPerMeter);
				if(fts.ismailbox){
					fts.updateSprite(mailBox, sp, pixPerMeter);
				}
			}
		}
	}
	
	public void calculateScores()
	{
		for (FloorTileSensor fts : floorSensors)
		{
//			if (fts.assigned)
//			{
//				if (fts.getTeam() == Team.Home)
//				{
//					homeScore++;
//				}
//				else
//				{
//					awayScore++;
//				}
//			}
		}
	}
}