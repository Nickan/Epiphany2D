package com.nickan.epiphany.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nickan.epiphany.model.Character;
import com.nickan.framework1_0.Animation;

public class SingleAnimation extends BaseAllAnimations {
	Animation animation;
	Texture texture;
	Vector2 pos;
	float stateTime;
	
	public SingleAnimation(Character character, String filePath, int width,
			int height, int columns, int totalFrames, float animationDelay) {
		super(character, width, height, columns, totalFrames,
				animationDelay);
		texture = new Texture(filePath);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		animation = new Animation(texture, 0, 0, width, height, columns, totalFrames, animationDelay);
		pos = character.getPosition();
	}

	@Override
	public void draw(SpriteBatch batch) {
		stateTime += Gdx.graphics.getDeltaTime();
		
		// The heck, I am having problems with the image positioning
		batch.draw(animation.getKeyFrame(stateTime), pos.x - 1f, pos.y, 3, 3);
	}

}
