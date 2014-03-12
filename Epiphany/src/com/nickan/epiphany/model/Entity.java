package com.nickan.epiphany.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	float width;
	float height;
	Vector2 position;
	Rectangle bounds;
	
	public Entity(Vector2 position, float width, float height) {
		this.position = position;
		this.width = width;
		this.height = height;
		bounds = new Rectangle(position.x, position.y, width, height);
	}
	
	void updateBounds() {
		bounds.x = position.x;
		bounds.y = position.y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
