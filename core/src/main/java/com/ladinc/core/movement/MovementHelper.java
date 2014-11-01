package com.ladinc.core.movement;

import com.ladinc.core.objects.GameCharacter;

public class MovementHelper {

	private Directions currentDirection = Directions.Up; //default to any direction 

	public void calculateDirection(float xMovement, float yMovement, GameCharacter character)
	{
		getDirection(xMovement, yMovement);
		setDirection(character);
	}

	private void setDirection(GameCharacter character) {
		character.setCurrentDirection(currentDirection);	
	}

	private void getDirection(float xMovement, float yMovement) {
		float xPower = xMovement;
		float yPower = yMovement;
		
		if (xMovement < 0)
			xPower = xMovement*(-1);
		
		if (yMovement < 0)
			yPower = yMovement*(-1); 
		
		if(xPower == 0 && yPower == 0)
		{
			//Dont change direction
			return;
		}
		

		//going right
		if(xPower/3 > yPower)
		{
			//X movement out weighs y over 3 to 1, ignoring y influences
			if(xMovement > 0)
			{
				this.currentDirection = Directions.Right;
			}
			else
			{
				this.currentDirection = Directions.Left;
			}
			
			return;
		}
		else if (yPower/3 > xPower)
		{
			//Y movement out weighs x over 3 to 1, ignoring x influences
			if(yMovement < 0)
			{
				this.currentDirection = Directions.Down;
			}
			else
			{
				this.currentDirection = Directions.Up;
			}
			
			return;
		}
		else
		{
			//its somewhere in the middle
			if(xMovement > 0)
			{
				//Right
				if(yMovement < 0)
				{
					this.currentDirection = Directions.DiagDownRight;
				}
				else
				{
					this.currentDirection = Directions.DiagUpRight;
				}
			}
			else
			{
				//Left
				if(yMovement < 0)
				{
					this.currentDirection = Directions.DiagDownLeft;
				}
				else
				{
					this.currentDirection = Directions.DiagUpLeft;
				}
			}
		}
	}
	
	public static enum Directions{Down, Up, Left, Right, DiagUpRight, DiagUpLeft, DiagDownLeft, DiagDownRight}
	
}
