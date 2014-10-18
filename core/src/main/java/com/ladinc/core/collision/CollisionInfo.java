package com.ladinc.core.collision;

public class CollisionInfo {

	public static enum CollisionObjectType {
		AIPlayer, Ball, UserPlayer, Wall
	}

	public Object object;
	public Object team;

	public String text;

	public CollisionObjectType type;

	public CollisionInfo(String text, CollisionObjectType type) {
		this.text = text;
		this.type = type;
	}

	public CollisionInfo(String text, CollisionObjectType type, Object object) {
		this.text = text;
		this.type = type;
		this.object = object;
	};

}
