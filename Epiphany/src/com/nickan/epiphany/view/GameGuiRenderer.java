package com.nickan.epiphany.view;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.nickan.epiphany.model.Character;
import com.nickan.epiphany.model.Enemy;
import com.nickan.epiphany.model.items.ItemBounds.ButtonBounds;
import com.nickan.framework1_0.pathfinder1_0.Node;

public class GameGuiRenderer {
	private GameGuiHandler guiSlot;
	private static final float barWidth = 100 / 32f;
	private static final float barHeight = 8 / 32f;

	Texture pauseTextures;

	Rectangle equipmentBounds = new Rectangle(0, 64, 64, 64);

	private GameGuiRenderer(GameGuiHandler guiSlot) {
		this.guiSlot = guiSlot;
	}

	// Thinking of making this section manageable...
	// Those that uses spriteBatch
	public void drawUpdate(SpriteBatch batch) {
		drawPotionGui(batch);
		drawSkillGui(batch);

		// Attack icon
		drawFromTexture(batch, pauseTextures, ButtonBounds.atk, guiSlot.atkButton.x, guiSlot.atkButton.y, 1.5f, 1.5f);
	}

	private void drawPotionGui(SpriteBatch batch) {
		List<Rectangle> potSlots = guiSlot.potSlots;
		for (int i = 0; i < potSlots.size(); ++i) {
			drawGrid(batch, potSlots.get(i).x,
					potSlots.get(i).y);
		}
	}

	private void drawSkillGui(SpriteBatch batch) {
		List<Rectangle> skillSlots = guiSlot.skillSlots;
		for (int i = 0; i < skillSlots.size(); ++i) {
			drawGrid(batch, skillSlots.get(i).x, skillSlots.get(i).y);
		}

		// Controls
		for (int i = 0; i < guiSlot.moveButtons.size(); ++i) {
			drawArrow(batch, guiSlot.moveButtons.get(i), i * 90);
		}

		for (int i = 0; i < guiSlot.moveButtons.size(); ++i) {
			drawArrow(batch, guiSlot.targetButtons.get(i), i * 90);
		}

		drawGrid(batch, guiSlot.atkButton.x, guiSlot.atkButton.y);
	}


	// Uses ShapeRenderer
	public void drawPlayerStatus(OrthographicCamera camera, ShapeRenderer sr) {
		sr.setProjectionMatrix(camera.combined);
		
		// Destination node
		Node node = guiSlot.world.player.destinationNode;
		drawBoundingRect(sr, node.x, node.y, 1f, 1f, Color.LIGHT_GRAY);
		
		// Target attack node
		node = guiSlot.world.player.attackTargetNode;
		drawBoundingRect(sr, node.x, node.y, 1f, 1f, Color.RED);
		

		// Full hp
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(guiSlot.position.x + guiSlot.hpBarX, guiSlot.position.y + guiSlot.hpBarY, barWidth, barHeight);
		
		float hp = guiSlot.world.player.statsHandler.hp;
		float fullHp = guiSlot.world.player.statsHandler.totalFullHp;
		
		// Current hp
		sr.setColor(Color.YELLOW);
		sr.rect(guiSlot.position.x + guiSlot.hpBarX, guiSlot.position.y + guiSlot.hpBarY, 
				barWidth * ( (hp / 32) / (fullHp / 32) ), barHeight);
		
		// Full mp
		sr.setColor(Color.BLACK);
		sr.rect(guiSlot.position.x + guiSlot.hpBarX, guiSlot.position.y + guiSlot.hpBarY - 0.3f, barWidth, barHeight);

		float mp = guiSlot.world.player.statsHandler.mp;
		float fullMp = guiSlot.world.player.statsHandler.totalFullMp;
		
		// Current mp
		sr.setColor(Color.BLUE);
		sr.rect(guiSlot.position.x + guiSlot.hpBarX, guiSlot.position.y + guiSlot.hpBarY - 0.3f, 
				barWidth * ( (mp / 32) / (fullMp / 32) ), barHeight);
		sr.end();
	}

	public void drawEnemyHealthBar(OrthographicCamera camera, ShapeRenderer sr, Enemy enemy) {
		sr.setProjectionMatrix(camera.combined);

		// Full hp
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(guiSlot.position.x - guiSlot.enemyNameX - 2f, guiSlot.position.y + guiSlot.enemyNameY - 0.63f, 5, 0.7f);
		
		// Current hp
		sr.setColor(Color.GRAY);
		sr.rect(guiSlot.position.x - guiSlot.enemyNameX - 2f, guiSlot.position.y + guiSlot.enemyNameY - 0.63f, 
				5 * (enemy.statsHandler.hp / enemy.statsHandler.totalFullHp), 0.7f);
		sr.end();
	}


	// Every letter on the screen
	public void drawLetters(SpriteBatch batch, BitmapFont font, Character ch) {
		/*
		Vector2 position = ch.getPosition();

		// For hp
		font.draw(batch, Integer.toString(((int) ch.hp)) + " / ", 
				position.x + guiSlot.hpBarX, position.y + guiSlot.hpBarY + 0.8f);
		font.draw(batch, Integer.toString( (int) ch.totalFullHp ), 
				position.x + guiSlot.hpBarX + 1.5f, position.y + guiSlot.hpBarY + 0.8f);

		// For mp
		font.draw(batch, Integer.toString(((int) ch.mp)) + " / ", 
				position.x + guiSlot.hpBarX, position.y + guiSlot.hpBarY - 0.2f);
		font.draw(batch, Integer.toString( (int) ch.totalFullMp ), 
				position.x + guiSlot.hpBarX + 1.5f, position.y + guiSlot.hpBarY - 0.2f);
		 */
	}

	public void drawName(SpriteBatch batch, BitmapFont font, Enemy enemy) {
		TextBounds bounds = font.getBounds(enemy.getName());
		font.drawMultiLine(batch, enemy.getName(), guiSlot.position.x + guiSlot.enemyNameX, guiSlot.position.y + guiSlot.enemyNameY, 
				bounds.width / 2, BitmapFont.HAlignment.CENTER);
	}


	// Drawing for icons
	private void drawGrid(SpriteBatch batch, float x, float y) {
		drawFromTexture(batch, pauseTextures, ButtonBounds.slotBg, x, y, 1.5f, 1.5f);
	}

	private void drawArrow(SpriteBatch batch, Rectangle bounds, float rotation) {
		drawFromTexture(batch, pauseTextures, ButtonBounds.arrow, bounds.x, bounds.y, 1.5f, 1.5f, rotation);
	}

	private void drawFromTexture(SpriteBatch batch, Texture texture, Rectangle srcRect, 
			float x, float y, float width, float height, float rotation) {

		// Draws the texture in the center of the position given	
		batch.draw(pauseTextures, x - (width - 1f) / 2, y - (height - 1f) / 2, 
				width / 2, height / 2, width, height, 1, 1, rotation, 
				(int) srcRect.x, (int) srcRect.y, (int) srcRect.width, (int) srcRect.height, false, false);
	}
	
	// All helper methods
	private void drawFromTexture(SpriteBatch batch, Texture texture, Rectangle srcRect, 
			float x, float y, float width, float height) {

		// Draws the texture in the center of the position given
		batch.draw(texture, x - (width - 1f) / 2, y - (height - 1f) / 2, 
				width, height, (int) srcRect.x, (int) srcRect.y, 
				(int) srcRect.width, (int) srcRect.height, false, false);
	}

	// All Debugging methods
	public void drawPauseDebug(ShapeRenderer sr) {
		/*
		
		for (int y = 0; y < guiSlot.itemSlotHeight; ++y) {
			for (int x = 0; x < guiSlot.itemSlotWidth; ++x) {
				drawBoundingRect(sr, guiSlot.itemSlots[y][x]);
			}
		}

		drawEquipmentRectangle(sr);
		drawAddStatus(sr);
		*/
	}
	

	public void drawUpdateDebug(ShapeRenderer sr) {
		drawPotSlotsRectangle(sr);
		drawSkillSlotsRectangle(sr);
		drawBoundingRect(sr, guiSlot.atkButton, Color.CYAN);
		drawMoveRectangle(sr);
		drawTargetRectangle(sr);
	}

	private void drawMoveRectangle(ShapeRenderer sr) {
		for (Rectangle rect: guiSlot.moveButtons) {
			drawBoundingRect(sr, rect, Color.CYAN);
		}
	}

	private void drawTargetRectangle(ShapeRenderer sr) {
		for (Rectangle rect: guiSlot.targetButtons) {
			drawBoundingRect(sr, rect, Color.CYAN);
		}
	}

	private void drawPotSlotsRectangle(ShapeRenderer sr) {
		for (int i = 0; i < guiSlot.potSlots.size(); ++i) {
			drawBoundingRect(sr, guiSlot.potSlots.get(i), Color.CYAN);
		}
	}

	private void drawSkillSlotsRectangle(ShapeRenderer sr) {
		for (int i = 0; i < guiSlot.skillSlots.size(); ++i) {
			drawBoundingRect(sr, guiSlot.skillSlots.get(i), Color.CYAN);
		}
	}
	
	
	

	private void drawBoundingRect(ShapeRenderer sr, Rectangle rect, Color color) {
		sr.begin(ShapeType.Line);
		sr.setColor(color);
		sr.rect(rect.x, rect.y, rect.width, rect.height);
		sr.end();
	}
	
	private void drawBoundingRect(ShapeRenderer sr, float x, float y, float width, float height, Color color) {
		sr.begin(ShapeType.Line);
		sr.setColor(color);
		sr.rect(x, y, width, height);
		sr.end();
	}
	
	
	public static GameGuiRenderer newGuiSprite(GameGuiHandler guiSlot) {
		GameGuiRenderer guiSprite = new GameGuiRenderer(guiSlot);
		return guiSprite;
	}

}
