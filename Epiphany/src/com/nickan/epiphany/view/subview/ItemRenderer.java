package com.nickan.epiphany.view.subview;

import com.badlogic.gdx.graphics.Texture;
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


public abstract class ItemRenderer extends BaseRenderer {
	
	/** Requires a rectangle positioning, so that it will make the rectangle as a basis of its center even when scaled up
	 * or down.
	 * @author Nickan
	 *
	 */
	protected void drawItem(SpriteBatch batch, Texture itemTexture, ItemEntity item, Rectangle slot, float scale) {
		if (item == null) return;
		
		switch (item.itemType) {
		case CONSUMABLE:
			drawConsumableItem(batch, itemTexture, item, slot, scale);
			break;
		case WEARABLE:
			drawWearableItem(batch, itemTexture, item, slot, scale);
			break;
		default:
			break;
		}
	}
	
	private void drawConsumableItem(SpriteBatch batch, Texture itemTexture, ItemEntity item, Rectangle slot, float scale) {
		Consumable consumable = (Consumable)item;
		switch (consumable.consumableType) {
		case HP_REGEN:
			drawFromTexture(batch, itemTexture, PotionBounds.SMALL_HP[BoundType.ICON], 
					slot.x, slot.y, scale, scale);
			break;
		case MP_REGEN:
			drawFromTexture(batch, itemTexture, PotionBounds.SMALL_MP[BoundType.ICON], 
					slot.x, slot.y, scale, scale);
			break;
		default:
			break;
		}
	}

	private void drawWearableItem(SpriteBatch batch, Texture itemTexture, ItemEntity item, Rectangle slot, float scale) {
		Wearable wearable = (Wearable)item;
		Rectangle srcRect = null;
		switch (wearable.wearableType) {
		case WEAPON_SWORD:
			srcRect = WeaponBounds.SWORDS[wearable.Id][BoundType.ICON];
			break;
			// To be added later...
		case WEAPON_MACE:
			//			srcRect = WeaponBounds.MACES[wearable.Id][BoundType.ICON];
			break;
		case WEAPON_AXE:
			//			srcRect = WeaponBounds.AXES[wearable.Id][BoundType.ICON];
			break;


		case HELM:
			srcRect = HelmBounds.HELMS[wearable.Id][BoundType.ICON];
			break;
		case ARMOR:
			srcRect = ArmorBounds.ARMORS[wearable.Id][BoundType.ICON];
			break;
		case SHIELD:
			srcRect = ShieldBounds.SHIELDS[wearable.Id][BoundType.ICON];
			break;
		case GLOVES:
			srcRect = GlovesBounds.GLOVES[wearable.Id][BoundType.ICON];
			break;
		case BOOTS:
			srcRect = BootsBounds.BOOTS[wearable.Id][BoundType.ICON];
			break;
		default:
			break;
		}

		if (srcRect != null) {
			drawFromTexture(batch, itemTexture, srcRect, slot.x, slot.y, scale, scale);
		}
	}
	
}
