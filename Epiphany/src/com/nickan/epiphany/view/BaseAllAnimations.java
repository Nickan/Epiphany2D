package com.nickan.epiphany.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nickan.epiphany.model.Character;


/**
 * This class is just created for correct draw priority of all the characters' animation(s)
 * @author Nickan
 */
public abstract class BaseAllAnimations {
	Character character;
	int width;
	int height;
	int columns;
	int totalFrames;
	float animationDelay;
	
	public BaseAllAnimations(Character character, int width, int height, int columns, int totalFrames, 
			float animationDelay) {
		this.character = character;
		this.width = width;
		this.height = height;
		this.columns = columns;
		this.totalFrames = totalFrames;
		this.animationDelay = animationDelay;
	}
	
	public abstract void draw(SpriteBatch batch);
}
