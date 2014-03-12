package com.nickan.epiphany.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.nickan.epiphany.model.Player;
import com.nickan.epiphany.model.items.ItemBounds.ButtonBounds;
import com.nickan.epiphany.model.items.consumables.RegenPotion;
import com.nickan.epiphany.model.items.ItemEntity;
import com.nickan.epiphany.model.items.Wearable;
import com.nickan.epiphany.view.subview.ItemRenderer;

public class InventoryRenderer extends ItemRenderer {
	private static InventoryRenderer renderer = null;
	private InventoryHandler handler;
	private float scale = 1.5f;
	private float gridHeight = 1.74f;
	private float gridWidth = 1.85f;
	Texture pauseTexture;
	Texture itemTexture;
	
	private final float gridX = -2.9f;
	private final float gridY = 1.88f;
	
	private InventoryRenderer(InventoryHandler handler) {
		this.handler = handler;
	}
	
	public void draw(SpriteBatch batch, BitmapFont courier, BitmapFont arial) {
		drawInventoryGrids(batch);
		
		// Faded box for helm slot
		drawFromTexture(batch, pauseTexture, ButtonBounds.equipmentBg, handler.helmSlot.x, handler.helmSlot.y, scale, scale);
		drawFromTexture(batch, pauseTexture, ButtonBounds.equipmentBg, handler.armorSlot.x, handler.armorSlot.y, scale, scale);
		drawFromTexture(batch, pauseTexture, ButtonBounds.equipmentBg, handler.shieldSlot.x, handler.shieldSlot.y, scale, scale);
		drawFromTexture(batch, pauseTexture, ButtonBounds.equipmentBg, handler.weaponSlot.x, handler.weaponSlot.y, scale, scale);
		drawFromTexture(batch, pauseTexture, ButtonBounds.equipmentBg, handler.glovesSlot.x, handler.glovesSlot.y, scale, scale);
		drawFromTexture(batch, pauseTexture, ButtonBounds.equipmentBg, handler.bootsSlot.x, handler.bootsSlot.y, scale, scale);
		
		drawInventoryItems(batch);
		drawPlayerEquipment(batch);
		drawDescriptionWindow(batch, courier, arial);
	}
	
	private void drawDescriptionWindow(SpriteBatch batch, BitmapFont courier, BitmapFont arial) {
		ItemEntity item = handler.clickedItem;
		if (item == null) 
			return;
		Rectangle rectSlot = handler.clickedSlot;
		
		drawItem(batch, itemTexture, item, rectSlot, scale);
		
		// Prints out the item specs
		drawString(batch, item.desc, rectSlot.x - 0.3f, rectSlot.y - 3.5f, arial, Color.BLUE, 1f / 45f);
		drawString(batch, item.name, rectSlot.x - 0.3f, rectSlot.y - 2.8f, arial, Color.CYAN, 1f / 45f);
		
		switch (item.itemType) {
		case CONSUMABLE:
			RegenPotion potion = (RegenPotion) item;
			drawRegenPotionSpecs(batch, rectSlot, potion, arial, 1f / 45f);
			break;
		case WEARABLE:
			Wearable wearable = (Wearable) item;
			drawWearableSpecs(batch, rectSlot, wearable, arial, 1f / 45f);
			break;
		default:
			break;
		}
	}
	
	private void drawRegenPotionSpecs(SpriteBatch batch, Rectangle slot, RegenPotion potion, 
			BitmapFont font, float fontScale) {
		drawString(batch, "Heals: ", slot.x + 1.2f, slot.y + 1f, font, Color.WHITE, fontScale);
		drawString(batch, "Rate: ", slot.x + 1.2f, slot.y + 0.5f, font, Color.WHITE, fontScale);
		
		drawString(batch, Integer.toString((int) potion.regenPoints), 
				slot.x + 2.7f, slot.y + 1f, font, Color.WHITE, fontScale);
		
		drawString(batch, Integer.toString((int) potion.pointsPerSecond), 
				slot.x + 2.7f, slot.y + 0.5f, font, Color.WHITE, fontScale);
	}
	
	private void drawWearableSpecs(SpriteBatch batch, Rectangle slot, Wearable wearable, 
			BitmapFont font, float fontScale) {
		
		// Main status
		drawString(batch, "Str: ", slot.x + 1.2f, slot.y + 1f, font, Color.WHITE, fontScale);
		drawString(batch, "Dex: ", slot.x + 1.2f, slot.y + 0.5f, font, Color.WHITE, fontScale);
		drawString(batch, "Vit: ", slot.x + 1.2f, slot.y, font, Color.WHITE, fontScale);
		drawString(batch, "Agi: ", slot.x + 1.2f, slot.y - 0.5f, font, Color.WHITE, fontScale);
		drawString(batch, "Wiz: ", slot.x + 1.2f, slot.y - 1f, font, Color.WHITE, fontScale);
		
		drawString(batch, Integer.toString((int) wearable.strBonus), 
				slot.x + 2.7f, slot.y + 1f, font, Color.YELLOW, fontScale);
		drawString(batch, Integer.toString((int) wearable.dexBonus), 
				slot.x + 2.7f, slot.y + 0.5f, font, Color.YELLOW, fontScale);
		drawString(batch, Integer.toString((int) wearable.vitBonus), 
				slot.x + 2.7f, slot.y, font, Color.YELLOW, fontScale);
		drawString(batch, Integer.toString((int) wearable.agiBonus), 
				slot.x + 2.7f, slot.y - 0.5f, font, Color.YELLOW, fontScale);
		drawString(batch, Integer.toString((int) wearable.wizBonus), 
				slot.x + 2.7f, slot.y - 1f, font, Color.YELLOW, fontScale);
		
		// Sub status
		drawString(batch, "Atk: ", slot.x + 3.7f, slot.y + 1f, font, Color.WHITE, fontScale);
		drawString(batch, "Spd: ", slot.x + 3.7f, slot.y + 0.5f, font, Color.WHITE, fontScale);
		drawString(batch, "Avd: ", slot.x + 3.7f, slot.y, font, Color.WHITE, fontScale);
		drawString(batch, "Crt: ", slot.x + 3.7f, slot.y - 0.5f, font, Color.WHITE, fontScale);
		drawString(batch, "Def: ", slot.x + 3.7f, slot.y - 1f, font, Color.WHITE, fontScale);
		drawString(batch, "Hit: ", slot.x + 3.7f, slot.y - 1.5f, font, Color.WHITE, fontScale);
		drawString(batch, "Hp: ", slot.x + 3.7f, slot.y - 2f, font, Color.WHITE, fontScale);
		drawString(batch, "Mp: ", slot.x + 3.7f, slot.y - 2.5f, font, Color.WHITE, fontScale);
		
		drawString(batch, Integer.toString((int) wearable.atkDmgBonus), 
				slot.x + 5.2f, slot.y + 1f, font, Color.YELLOW, fontScale);
		drawString(batch, Integer.toString((int) wearable.atkSpdBonus), 
				slot.x + 5.2f, slot.y + 0.5f, font, Color.YELLOW, fontScale);
		drawString(batch, Integer.toString((int) wearable.avoidBonus), 
				slot.x + 5.2f, slot.y, font, Color.YELLOW, fontScale);
		drawString(batch, Integer.toString((int) wearable.critBonus), 
				slot.x + 5.2f, slot.y - 0.5f, font, Color.YELLOW, fontScale);
		drawString(batch, Integer.toString((int) wearable.defBonus), 
				slot.x + 5.2f, slot.y - 1f, font, Color.YELLOW, fontScale);
		drawString(batch, Integer.toString((int) wearable.hitBonus), 
				slot.x + 5.2f, slot.y - 1.5f, font, Color.YELLOW, fontScale);
		drawString(batch, Integer.toString((int) wearable.fullHpBonus), 
				slot.x + 5.2f, slot.y - 2f, font, Color.YELLOW, fontScale);
		drawString(batch, Integer.toString((int) wearable.fullMpBonus), 
				slot.x + 5.2f, slot.y - 2.5f, font, Color.YELLOW, fontScale);
	}
	
	private void drawInventoryGrids(SpriteBatch batch) {
		for (int y = 0; y < handler.itemSlots.length; ++y) {
			for (int x = 0; x < handler.itemSlots[y].length; ++x) {
				batch.draw(pauseTexture, handler.position.x + (gridX + gridWidth * x), 
						handler.position.y - (gridY + gridHeight * y), 
						gridWidth, gridHeight, 544, 0, 38, 60, false, false);
			}
		}
	}
	
	private void drawInventoryItems(SpriteBatch batch) {
		ItemEntity[][] items = handler.inventory.items;
		for (int y = 0; y < items.length; ++y) {
			for (int x = 0; x < items[y].length; ++x) {
				ItemEntity item = items[y][x];
				if (item != null) {
					Rectangle slotPos = handler.itemSlots[y][x];
					drawItem(batch, itemTexture, item, slotPos, scale);
				}
			}
		}
	}

	private void drawPlayerEquipment(SpriteBatch batch) {
		Player player = handler.player;
		drawItem(batch, itemTexture, player.weapon, handler.weaponSlot, scale);
		drawItem(batch, itemTexture, player.helm, handler.helmSlot, scale);
		drawItem(batch, itemTexture, player.shield, handler.shieldSlot, scale);
		drawItem(batch, itemTexture, player.armor, handler.armorSlot, scale);
		drawItem(batch, itemTexture, player.gloves, handler.glovesSlot, scale);
		drawItem(batch, itemTexture, player.boots, handler.bootsSlot, scale);
	}
	
	// For debugging
	public void debug(ShapeRenderer sr) {
		ItemEntity[][] items = handler.inventory.items;
		for (int y = 0; y < items.length; ++y) {
			for (int x = 0; x < items[y].length; ++x) {
				drawBoundingRect(sr, handler.itemSlots[y][x], Color.WHITE);
			}
		}
		
		drawEquipmentRectangle(sr);
	}
	
	
	private void drawEquipmentRectangle(ShapeRenderer sr) {
		drawBoundingRect(sr, handler.helmSlot, Color.CYAN);
		drawBoundingRect(sr, handler.weaponSlot, Color.CYAN);
		drawBoundingRect(sr, handler.shieldSlot, Color.CYAN);
		drawBoundingRect(sr, handler.armorSlot, Color.CYAN);
		drawBoundingRect(sr, handler.glovesSlot, Color.CYAN);
		drawBoundingRect(sr, handler.bootsSlot, Color.CYAN);

	}
	
	
	public static InventoryRenderer getInstance(InventoryHandler handler) {
		// Make sure that there is only one instance
		if (renderer == null) {
			renderer = new InventoryRenderer(handler);
		}
		return renderer;
	}
	
}
