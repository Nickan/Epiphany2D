package com.nickan.epiphany.view.subview;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

/**
 * My first attempt to ease the rendering of basic graphics for game
 * @author Nickan
 *
 */
public class CommonGraphics {
	private static CommonGraphics graphics = null;
	private SpriteBatch batch;

	private CommonGraphics(SpriteBatch batch) {
		this.batch = batch;
	}

	public void begin() {
		batch.begin();
	}

	public void drawString(String string, float x, float y,
			BitmapFont font, Color color, float scale) {
		font.setColor(color);
		font.setScale(scale);
		font.draw(this.batch, string, x, y);
	}

	public void end() {
		batch.end();
	}

	/**
	 * Draws the specified source rectangle from texture and centers it
	 * @param texture
	 * @param srcRect
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawTextureRect(Texture texture, Rectangle srcRect,
			float x, float y, float width, float height) {

		// Draws the texture in the center of the position given
		this.batch.draw(texture, x - (width - 1f) / 2, y - (height - 1f) / 2,
				width, height, (int) srcRect.x, (int) srcRect.y,
				(int) srcRect.width, (int) srcRect.height, false, false);
	}

	/**
	 * Draws a 2D rectangle
	 * @param sr
	 * @param rect
	 * @param color
	 */
	public void drawBoundingRect(ShapeRenderer sr, Rectangle rect, Color color) {
		sr.begin(ShapeType.Line);
		sr.setColor(color);
		sr.rect(rect.x, rect.y, rect.width, rect.height);
		sr.end();
	}

	public static CommonGraphics getGraphics(SpriteBatch batch) {
		if (graphics == null)
			graphics = new CommonGraphics(batch);
		return graphics;
	}

}
