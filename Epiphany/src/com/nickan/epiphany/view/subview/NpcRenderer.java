package com.nickan.epiphany.view.subview;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class NpcRenderer extends ItemRenderer {
	public static NpcRenderer renderer= null;
	NpcHandler handler;
	public Texture pauseTexture;
	public Texture itemTexture;
	
	private final float gridX = -11.9f;
	private final float gridY = -4.7f;
	
	private float gridHeight = 1.74f;
	private float gridWidth = 1.85f;
	
	private NpcRenderer(NpcHandler handler) {
		this.handler = handler;
	}
	
	public void draw(SpriteBatch batch, BitmapFont courier, BitmapFont arial) {
		drawGrids(batch);
		drawBlackSmithItems(batch);
	}
	
	private void drawGrids(SpriteBatch batch) {
		Vector2 position = handler.player.getPosition();
		
		for (int y = 0; y < handler.buySlots.length; ++y) {
			for (int x = 0; x < handler.buySlots[y].length; ++x) {
				batch.draw(pauseTexture, position.x + (gridX + gridWidth * x), 
						position.y - (gridY + gridHeight * y), 
						gridWidth, gridHeight, 544, 0, 38, 60, false, false);
			}
		}
	}
	
	private void drawBlackSmithItems(SpriteBatch batch) {
		for (int y = 0; y < handler.buySlotRows; ++y) {
			for (int x = 0; x < handler.buySlotColumns; ++x) {
				drawItem(batch, itemTexture, handler.blackSmithInventory.items[y][x], handler.buySlots[y][x], 1.5f);
			}
		}
	}
	
	// For debugging
	public void debug(ShapeRenderer sr) {
		for (int y = 0; y < handler.buySlotRows; ++y) {
			for (int x = 0; x < handler.buySlotColumns; ++x) {
				drawBoundingRect(sr, handler.buySlots[y][x], Color.WHITE);
			}
		}
	}
	
	public static NpcRenderer getInstance(NpcHandler handler) {
		if (renderer == null) {
			renderer = new NpcRenderer(handler);
		}
		return renderer;
	}
}
