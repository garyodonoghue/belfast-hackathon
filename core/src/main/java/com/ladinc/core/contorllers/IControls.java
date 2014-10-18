package com.ladinc.core.contorllers;

import com.badlogic.gdx.math.Vector2;

public interface IControls 
{
	
	public Vector2 getMovementInput();
	
	public Vector2 getRotationInput();
	
	public boolean getDivePressed();
	
	public boolean isActive();
	
	public boolean isRotationRelative();
	
//	public void setIdentifier(Identifier identifier);
//	
//	public Identifier getIdentifier();
	
	public boolean getStartPressed();
	
	//TODO: Add start etc

}
