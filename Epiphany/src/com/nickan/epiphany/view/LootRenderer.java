package com.nickan.epiphany.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.nickan.epiphany.model.items.ItemEntity;
import com.nickan.epiphany.model.items.Wearable;
import com.nickan.epiphany.model.items.ItemBounds.ArmorBounds;
import com.nickan.epiphany.model.items.ItemBounds.BootsBounds;
import com.nickan.epiphany.model.items.ItemBounds.BoundType;
import com.nickan.epiphany.model.items.ItemBounds.GlovesBounds;
import com.nickan.epiphany.model.items.ItemBounds.HelmBounds;
import com.nickan.epiphany.model.items.ItemBounds.PotionBounds;
import com.nickan.epiphany.model.items.ItemBounds.ShieldBounds;
import com.nickan.epiphany.model.items.ItemBounds.WeaponBounds;
import com.nickan.epiphany.model.items.consumables.Consumable;

public class LootRenderer {
	LootHandler lootHandler;
	Animation aniDropCoins;
	Texture itemTexture;
	
	public LootRenderer(LootHandler lootHandler) {
		this.lootHandler = lootHandler;
	}
	
	void draw(SpriteBatch batch) {
		batch.begin();
		for (int id = 0; id < lootHandler.lootNumber; ++id) {
			if (lootHandler.loots[id] != null) {
				drawLoot(batch, lootHandler.loots[id], lootHandler.lootBounds[id], lootHandler.lootUpdateTime[id]);
			}
		}
		batch.end();
	}
	
	private void drawLoot(SpriteBatch batch, ItemEntity item, Rectangle bounds, float stateTime) {
		switch (item.itemType) {
		case CONSUMABLE:
			drawConsumableItem(batch, item, bounds);
			break;
		case WEARABLE:
			drawWearableItem(batch, item, bounds);
			break;
		default:
			batch.draw(aniDropCoins.getKeyFrame(stateTime), bounds.x, bounds.y, bounds.width, bounds.height);
			break;
		}
	}
	
	private void drawConsumableItem(SpriteBatch batch, ItemEntity item, Rectangle slot) {
		Consumable consumable = (Consumable)item;
		switch (consumable.consumableType) {
		case HP_REGEN:
			drawFromTexture(batch, itemTexture, PotionBounds.SMALL_HP[BoundType.UNCHOSEN], 
					slot.x, slot.y, slot.width, slot.height);
			break;
		case MP_REGEN:
			drawFromTexture(batch, itemTexture, PotionBounds.SMALL_MP[BoundType.UNCHOSEN], 
					slot.x, slot.y, slot.width, slot.height);
			break;
		default:
			break;
		}
	}

	private void drawWearableItem(SpriteBatch batch, ItemEntity item, Rectangle slot) {
		if (item == null)
			return;

		Wearable wearable = (Wearable)item;
		Rectangle srcRect = null;
		switch (wearable.wearableType) {
		case WEAPON_SWORD:
			srcRect = WeaponBounds.SWORDS[wearable.Id][BoundType.UNCHOSEN];
			break;
			// Later to be added
		case WEAPON_MACE:
			//			srcRect = WeaponBounds.MACES[wearable.Id][BoundType.ICON];
			break;
		case WEAPON_AXE:
			//			srcRect = WeaponBounds.AXES[wearable.Id][BoundType.ICON];
			break;


		case HELM:
			srcRect = HelmBounds.HELMS[wearable.Id][BoundType.UNCHOSEN];
			break;
		case ARMOR:
			srcRect = ArmorBounds.ARMORS[wearable.Id][BoundType.UNCHOSEN];
			break;
		case SHIELD:
			srcRect = ShieldBounds.SHIELDS[wearable.Id][BoundType.UNCHOSEN];
			break;
		case GLOVES:
			srcRect = GlovesBounds.GLOVES[wearable.Id][BoundType.UNCHOSEN];
			break;
		case BOOTS:
			srcRect = BootsBounds.BOOTS[wearable.Id][BoundType.UNCHOSEN];
			break;
		default:
			break;
		}

		if (srcRect != null) {
			drawFromTexture(batch, itemTexture, srcRect, slot.x, slot.y, slot.width, slot.height);
		}
	}

	
	// All helper methods
	private void drawFromTexture(SpriteBatch batch, Texture texture, Rectangle srcRect, 
			float x, float y, float width, float height) {

		// Draws the texture in the center of the position given
		batch.draw(texture, x - (width - 1f) / 2, y - (height - 1f) / 2, 
				width, height, (int) srcRect.x, (int) srcRect.y, 
				(int) srcRect.width, (int) srcRect.height, false, false);
	}
	
	
}
