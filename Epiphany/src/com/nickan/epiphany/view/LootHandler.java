package com.nickan.epiphany.view;

import com.badlogic.gdx.math.Rectangle;
import com.nickan.epiphany.model.Enemy;
import com.nickan.epiphany.model.Player;
import com.nickan.epiphany.model.items.Gold;
import com.nickan.epiphany.model.items.ItemEntity;
import com.nickan.epiphany.model.items.ItemEntity.ItemType;

/**
 * Handles the automation of setting the loot update of animation, detects if the animation has stopped,
 * then it stops its stateTime
 * 
 * @author Nickan
 *
 */
public class LootHandler {
	private static LootHandler lootHandler = new LootHandler();
	int lootNumber = 100;
	float[] lootUpdateTime = new float[lootNumber];
	ItemEntity[] loots = new ItemEntity[lootNumber];
	Rectangle[] lootBounds = new Rectangle[lootNumber];
	
	
	private LootHandler() {
		for (int id = 0; id < lootNumber; ++id) {
			lootUpdateTime[id] = 0;
			loots[id] = null;
			lootBounds[id] = new Rectangle(-1, -1, 1, 1);
		}
	}
	
	public static LootHandler getInstance() {
		return lootHandler;
	}
	
	
	// Updates the loot state time for animation and the loots on the ground
	void update(Player player, float delta) {
		for (int id = 0; id < lootNumber; ++id) {
			if (loots[id] != null) {
				lootUpdateTime[id] += delta;
			}
		}
		
		handleLootsOnGround(player);
	}
	
	void allotLootOnGround(Enemy enemy) {
		if (enemy.lootDrop == null) {
			return;
		}
		
		for (int id = 0; id < lootNumber; ++id) {
			if (loots[id] == null) {
				loots[id] = enemy.lootDrop;
				lootBounds[id].set(enemy.getBounds());
				break;
			}
		}
	}
	
	
	public void handleLootsOnGround(Player player) {
		// Limits the checks every player's stop
		if (player.isMoving())
			return;
		
		for (int id = 0; id < lootNumber; ++id) {
			// Check only if there is a registered loot
			if (loots[id] != null) {
				if (lootBounds[id].overlaps(player.getBounds())) {
					
					// Needs to know if the item can be carried
					// Methods naming problems... >_<
					if (hasPickedUp(player, loots[id]))
						freeLootId(id);
					break;
				}
				
			}
		}
	}
	
	private boolean hasPickedUp(Player player, ItemEntity item) {
		if (item.itemType == ItemType.MONEY) {
			player.gold += ((Gold) item).amount;
			return true;
		}

		if (player.inventory.putInInventory(item)) {
			return true;
		}
		return false;
	}
	
	private void freeLootId(int id) {
		loots[id] = null;
		lootUpdateTime[id] = 0;
		lootBounds[id].setPosition(-1, -1);
	}
	
}
