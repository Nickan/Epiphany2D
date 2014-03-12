package com.nickan.epiphany.view.subview;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

/**
 * All renderers should inherit from this base class. This class is the result of my redundant code
 * because I want to separate every part of game to make it manageable.
 * @author Kandie
 *
 */
public abstract class BaseRenderer {
	protected void drawFromTexture(SpriteBatch batch, Texture texture, Rectangle srcRect, 
			float x, float y, float width, float height) {

		// Draws the texture in the center of the position given
		batch.draw(texture, x - (width - 1f) / 2, y - (height - 1f) / 2, 
				width, height, (int) srcRect.x, (int) srcRect.y, 
				(int) srcRect.width, (int) srcRect.height, false, false);
	}
	
	protected void drawBoundingRect(ShapeRenderer sr, Rectangle rect, Color color) {
		sr.begin(ShapeType.Line);
		sr.setColor(color);
		sr.rect(rect.x, rect.y, rect.width, rect.height);
		sr.end();
	}
	
	protected void drawString(SpriteBatch batch, String string, float x, float y, 
			BitmapFont font, Color color, float scale) {
		font.setColor(color);
		font.setScale(scale);
		font.draw(batch, string, x, y);
	}
	
	
}
