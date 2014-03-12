package com.nickan.epiphany.model.items;

import com.nickan.epiphany.model.Player;
import com.nickan.epiphany.model.items.ItemEntity.ItemType;
import com.nickan.epiphany.model.items.consumables.Consumable;

public class Inventory {
	public ItemEntity[][] items = new ItemEntity[4][8];
	
	ItemEntity selectedItem = null;
	
	public Inventory() {
		clear();
	}
	
	public void useItemAt(int x, int y, Player player) {
		if (items[y][x] == null)
			return;
		
		switch (items[y][x].itemType) {
		case CONSUMABLE:
			handleConsumableUsage(x, y, player);
			break;
		case WEARABLE:
			handleWearableUsage(x, y, player);
			break;
		default:
			
			break;
		}
		
	}
	
	private void handleConsumableUsage(int indexX, int indexY, Player player) {
		Consumable consumable = (Consumable)items[indexY][indexX];
		consumable.use(player);
		if (consumable.quantity == 0) {
			items[indexY][indexX] = null;
		}
	}
	
	private void handleWearableUsage(int indexX, int indexY, Player player) {
		Wearable wearable = (Wearable)items[indexY][indexX];
		switch (wearable.wearableType) {
		case WEAPON_SWORD:
		case WEAPON_MACE:
		case WEAPON_AXE:
			if (player.weapon != null) {
				if (putInInventory(player.weapon) ) {
					player.weapon = wearable;
				}	
			} else {
				player.weapon = wearable;
			}
			items[indexY][indexX] = null;
			break;
		case HELM:
			if (player.helm != null) {
				if (putInInventory(player.helm) ) {
					player.helm = wearable;
				}	
			} else {
				player.helm = wearable;
			}
			items[indexY][indexX] = null;
			break;
		case ARMOR:
			if (player.armor != null) {
				if (putInInventory(player.armor) ) {
					player.armor = wearable;
				}	
			} else {
				player.armor = wearable;
			}
			items[indexY][indexX] = null;
			break;
		case SHIELD:
			if (player.shield != null) {
				if (putInInventory(player.shield) ) {
					player.shield = wearable;
				}	
			} else {
				player.shield = wearable;
			}
			items[indexY][indexX] = null;
			break;
		case GLOVES:
			if (player.gloves != null) {
				if (putInInventory(player.gloves) ) {
					player.gloves = wearable;
				}	
			} else {
				player.gloves = wearable;
			}
			items[indexY][indexX] = null;
			break;
		case BOOTS:
			if (player.boots != null) {
				if (putInInventory(player.boots) ) {
					player.boots = wearable;
				}	
			} else {
				player.boots = wearable;
			}
			items[indexY][indexX] = null;
			break;
		default:
			break;
		}
		
		player.refreshStatus();
	}
	
	// Returns true if the item is successfully stored in the items
	public boolean putInInventory(ItemEntity item) {
		// Check first if it is a consumable item, it means it is stackable
		// Then check if it hasn't reached its max value
		if (item.itemType == ItemType.CONSUMABLE) {
			return consumableStored((Consumable)item);
		} else {
			return wearableStored((Wearable) item);
		}
	}
	
	private boolean wearableStored(Wearable wearable) {
		for (int y = 0; y < items.length; ++y) {
			for (int x = 0; x < items[y].length; ++x) {
				if (items[y][x] == null) {
					items[y][x] = wearable;
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean consumableStored(Consumable consumable) {
		Consumable stackableItem = getStackableConsumable(consumable);
		if (stackableItem != null) {
			stackableItem.increaseQuantity();
			return true;
		}
		return false;
	}
	
	private Consumable getStackableConsumable(Consumable item) {
		for (int y = 0; y < items.length; ++y) {
			for (int x = 0; x < items[y].length; ++x) {
				
				if (items[y][x] == null) {
					continue;
				}
				
				if (items[y][x].itemType == ItemType.WEARABLE) {
					continue;
				}
					
				Consumable itemInInventory = (Consumable) items[y][x];
				if (itemInInventory.consumableType == item.consumableType) {
					
					if (itemInInventory.quantity < 20) {
						return itemInInventory;
					}
				}
			}
		}
		return null;
	}	
	
	private void clear() {
		for (int y = 0; y < items.length; ++y) {
			for (int x = 0; x < items[y].length; ++x) {
				items[y][x] = null;
			}
		}
	}

}
