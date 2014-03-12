package com.nickan.epiphany.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.math.Rectangle;
import com.nickan.epiphany.model.Player;
import com.nickan.epiphany.model.StatisticsHandler;
import com.nickan.epiphany.model.items.ItemBounds.ButtonBounds;

public class StatusRenderer {
	private static StatusRenderer renderer = null;
	StatusHandler statusHandler;
	Texture pauseTexture;
	
	private StatusRenderer(StatusHandler handler) {
		this.statusHandler = handler;
	}
	
	public void draw(SpriteBatch batch, BitmapFont courier, BitmapFont arial) {
		// Add status icon
		if (statusHandler.player.remainingStatusPoints > 0) {
			drawFromTexture(batch, pauseTexture, ButtonBounds.addStatus, statusHandler.strSlot.x, statusHandler.strSlot.y, 1.5f, 1.5f);
			drawFromTexture(batch, pauseTexture, ButtonBounds.addStatus, statusHandler.agiSlot.x, statusHandler.agiSlot.y, 1.5f, 1.5f);
			drawFromTexture(batch, pauseTexture, ButtonBounds.addStatus, statusHandler.dexSlot.x, statusHandler.dexSlot.y, 1.5f, 1.5f);
			drawFromTexture(batch, pauseTexture, ButtonBounds.addStatus, statusHandler.vitSlot.x, statusHandler.vitSlot.y, 1.5f, 1.5f);
			drawFromTexture(batch, pauseTexture, ButtonBounds.addStatus, statusHandler.wisSlot.x, statusHandler.wisSlot.y, 1.5f, 1.5f);
		}

		// Bg box for status number
		drawFromTexture(batch, pauseTexture, ButtonBounds.statusBg, statusHandler.strSlot.x - 3.5f, statusHandler.strSlot.y, 
				1.5f, 1.5f);
		drawFromTexture(batch, pauseTexture, ButtonBounds.statusBg, statusHandler.agiSlot.x - 3.5f, statusHandler.agiSlot.y, 
				1.5f, 1.5f);
		drawFromTexture(batch, pauseTexture, ButtonBounds.statusBg, statusHandler.dexSlot.x - 3.5f, statusHandler.dexSlot.y, 
				1.5f, 1.5f);
		drawFromTexture(batch, pauseTexture, ButtonBounds.statusBg, statusHandler.vitSlot.x - 3.5f, statusHandler.vitSlot.y, 
				1.5f, 1.5f);
		drawFromTexture(batch, pauseTexture, ButtonBounds.statusBg, statusHandler.wisSlot.x - 3.5f, statusHandler.wisSlot.y, 
				1.5f, 1.5f);
		
		drawLetters(batch, courier, arial);
	}
	
	private void drawLetters(SpriteBatch batch, BitmapFont courier, BitmapFont arial) {
		Player player = statusHandler.player;
		
		courier.setScale(1f / 16f);
		courier.setColor(Color.BLACK);
		courier.draw(batch, "Knight  " + "Lv." + player.level, statusHandler.strSlot.x - 5.5f, statusHandler.strSlot.y + 4.5f);
		courier.setColor(Color.LIGHT_GRAY);
		courier.draw(batch, player.name, statusHandler.strSlot.x - 5.5f, statusHandler.strSlot.y + 3.5f);
		
		arial.setColor(Color.YELLOW);
		arial.setScale(1f / 45f);
		
		StatisticsHandler handler = player.statsHandler;
		arial.draw(batch, "Life:", statusHandler.strSlot.x - 5.5f, statusHandler.strSlot.y + 2.5f);
		drawLetterInCenter((int) handler.hp + "  /  " + (int) handler.totalFullHp, batch, arial, Color.YELLOW, 
				statusHandler.strSlot.x - 2.5f, statusHandler.strSlot.y + 2.5f);
		arial.setColor(Color.BLUE);
		arial.draw(batch, "Mana:", statusHandler.strSlot.x - 5.5f, statusHandler.strSlot.y + 1.8f);
		drawLetterInCenter((int) handler.mp + "  /  " + (int) handler.totalFullMp, batch, arial, Color.BLUE, 
				statusHandler.strSlot.x - 2.5f, statusHandler.strSlot.y + 1.8f);
		
		courier.setScale(1f / 16f);
		courier.setColor(Color.WHITE);
		courier.draw(batch, "STR", statusHandler.strSlot.x - 5.5f, statusHandler.strSlot.y + 0.8f);
		courier.draw(batch, "AGI", statusHandler.agiSlot.x - 5.5f, statusHandler.agiSlot.y + 0.8f);
		courier.draw(batch, "DEX", statusHandler.dexSlot.x - 5.5f, statusHandler.dexSlot.y + 0.8f);
		courier.draw(batch, "VIT", statusHandler.vitSlot.x - 5.5f, statusHandler.vitSlot.y + 0.8f);
		courier.draw(batch, "WIS", statusHandler.wisSlot.x - 5.5f, statusHandler.wisSlot.y + 0.8f);

		arial.setScale(1f / 40f);
		drawLetterInCenter(Integer.toString((int) handler.totalStr), batch, arial, Color.YELLOW, 
				statusHandler.strSlot.x - 3f, statusHandler.strSlot.y + 0.8f);
		drawLetterInCenter(Integer.toString((int) handler.totalAgi), batch, arial, Color.YELLOW, 
				statusHandler.agiSlot.x - 3f, statusHandler.agiSlot.y + 0.8f);
		drawLetterInCenter(Integer.toString((int) handler.totalDex), batch, arial, Color.YELLOW, 
				statusHandler.dexSlot.x - 3f, statusHandler.dexSlot.y + 0.8f);
		drawLetterInCenter(Integer.toString((int) handler.totalVit), batch, arial, Color.YELLOW, 
				statusHandler.vitSlot.x - 3f, statusHandler.vitSlot.y + 0.8f);
		drawLetterInCenter(Integer.toString((int) handler.totalWis), batch, arial, Color.YELLOW, 
				statusHandler.wisSlot.x - 3f, statusHandler.wisSlot.y + 0.8f);
		
		arial.draw(batch, Integer.toString((int) player.gold), statusHandler.position.x - 9.8f, statusHandler.position.y - 6.15f);

		
		courier.setColor(Color.DARK_GRAY);
		courier.setScale(1f / 25f);
		courier.draw(batch, "Atk", statusHandler.strSlot.x - 2.3f, statusHandler.strSlot.y + 0.65f);
		courier.draw(batch, "Spd", statusHandler.agiSlot.x - 2.3f, statusHandler.agiSlot.y + 1.1f);
		courier.draw(batch, "Avd", statusHandler.agiSlot.x - 2.3f, statusHandler.agiSlot.y + 0.4f);
		courier.draw(batch, "Hit", statusHandler.dexSlot.x - 2.3f, statusHandler.dexSlot.y + 1.1f);
		courier.draw(batch, "Crt", statusHandler.dexSlot.x - 2.3f, statusHandler.dexSlot.y + 0.4f);
		courier.draw(batch, "Def", statusHandler.vitSlot.x - 2.3f, statusHandler.vitSlot.y + 0.65f);
		
		arial.setScale(1f / 50f);
		drawLetterInCenter(Integer.toString((int) handler.totalAtkDmg), batch, arial, Color.CYAN,
				statusHandler.strSlot.x - 0.6f, statusHandler.strSlot.y + 0.7f);
		drawLetterInCenter(Integer.toString((int) handler.totalAtkSpd) + "%", batch, arial, Color.CYAN,
				statusHandler.agiSlot.x - 0.5f, statusHandler.agiSlot.y + 1.2f);
		drawLetterInCenter(Integer.toString((int) handler.totalAvoid) + "%", batch, arial, Color.CYAN,
				statusHandler.agiSlot.x - 0.5f, statusHandler.agiSlot.y + 0.5f);
		
		drawLetterInCenter(Integer.toString((int) handler.totalHit), batch, arial, Color.CYAN,
				statusHandler.dexSlot.x - 0.5f, statusHandler.dexSlot.y + 1.2f);
		drawLetterInCenter(Integer.toString((int) handler.totalCrit) + "%", batch, arial, Color.CYAN,
				statusHandler.dexSlot.x - 0.5f, statusHandler.dexSlot.y + 0.5f);
		
		drawLetterInCenter(Integer.toString((int) handler.totalDef), batch, arial, Color.CYAN,
				statusHandler.vitSlot.x - 0.6f, statusHandler.vitSlot.y + 0.7f);
	}
	
	// All helper methods
	private void drawFromTexture(SpriteBatch batch, Texture texture, Rectangle srcRect, 
			float x, float y, float width, float height) {

		// Draws the texture in the center of the position given
		batch.draw(texture, x - (width - 1f) / 2, y - (height - 1f) / 2, 
				width, height, (int) srcRect.x, (int) srcRect.y, 
				(int) srcRect.width, (int) srcRect.height, false, false);
	}

	// Auto center the letters being drawn
	private void drawLetterInCenter(String str, SpriteBatch batch, BitmapFont font, Color color, float posX, float posY) {
		font.setColor(color);
		TextBounds bounds = font.getBounds(str);
		font.draw(batch, str, posX - bounds.width / 2, posY);
	}
	
	/*
	private void drawAddStatus(ShapeRenderer sr) {
		drawBoundingRect(sr, statusHandler.strSlot, Color.CYAN);
		drawBoundingRect(sr, statusHandler.agiSlot, Color.CYAN);
		drawBoundingRect(sr, statusHandler.dexSlot, Color.CYAN);
		drawBoundingRect(sr, statusHandler.vitSlot, Color.CYAN);
		drawBoundingRect(sr, statusHandler.wisSlot, Color.CYAN);
	}
	
	
	private void drawBoundingRect(ShapeRenderer sr, Rectangle rect, Color color) {
		sr.begin(ShapeType.Line);
		sr.setColor(color);
		sr.rect(rect.x, rect.y, rect.width, rect.height);
		sr.end();
	}
	*/
	
	public static StatusRenderer getInstance(StatusHandler handler) {
		if (renderer == null) {
			renderer = new StatusRenderer(handler);
		}
		return renderer;
	}
}
