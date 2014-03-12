package com.nickan.epiphany.view;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nickan.epiphany.model.Player;
import com.nickan.epiphany.model.items.Inventory;
import com.nickan.epiphany.model.items.ItemEntity;

public class InventoryHandler {
	private static InventoryHandler handler = null;
	Player player;
	Inventory inventory;
	
	ItemEntity clickedItem = null;
	Vector2 clickedPos = new Vector2(6f, 5.2f);
	Rectangle clickedSlot = new Rectangle(0, 0, 1f, 1f);
	
	Vector2 position;
	
	private final int itemSlotWidth = 8;
	private final int itemSlotHeight = 4;
	private float itemSlotX = -2.45f;
	private float itemSlotY = -1.55f;
	Rectangle[][] itemSlots = new Rectangle[itemSlotHeight][itemSlotWidth];
	
	private Vector2 helmPos = new Vector2(0.2f, 5.25f);
	private Vector2 weaponPos = new Vector2(-1.8f, 3.25f);
	private Vector2 shieldPos = new Vector2(2.2f, 3.25f);
	private Vector2 armorPos = new Vector2(0.2f, 3.25f);
	private Vector2 glovesPos = new Vector2(-1f, 1.25f);
	private Vector2 bootsPos = new Vector2(1f, 1.25f);
	
	Rectangle helmSlot = new Rectangle(0, 0, 1, 1);
	Rectangle weaponSlot = new Rectangle(0, 0, 1, 1);
	Rectangle shieldSlot = new Rectangle(0, 0, 1, 1);
	Rectangle armorSlot = new Rectangle(0, 0, 1, 1);
	Rectangle glovesSlot = new Rectangle(0, 0, 1, 1);
	Rectangle bootsSlot = new Rectangle(0, 0, 1, 1);
	
	
	private InventoryHandler(Player player) {
		this.player = player;
		this.inventory = player.inventory;
		position = player.getPosition();
		
		for (int y = 0; y < itemSlotHeight; ++y) {
			for (int x = 0; x < itemSlotWidth; ++x) {
				itemSlots[y][x] = new Rectangle(0, 0, 1f, 1f);
			}
		}
	}
	
	public void update() {
		for (int y = 0; y < itemSlotHeight; ++y) {
			for (int x = 0; x < itemSlotWidth; ++x) {
				updateSlotPosition(itemSlots[y][x], itemSlotX + x * (itemSlots[y][x].width + 0.835f), 
						itemSlotY - y * (itemSlots[y][x].height + 0.725f));
			}
		}

		updateSlotPosition(helmSlot, helmPos);
		updateSlotPosition(weaponSlot, weaponPos);
		updateSlotPosition(shieldSlot, shieldPos);
		updateSlotPosition(armorSlot, armorPos);
		updateSlotPosition(glovesSlot, glovesPos);
		updateSlotPosition(bootsSlot, bootsPos);
		updateSlotPosition(clickedSlot, clickedPos);
	}
	
	private void updateSlotPosition(Rectangle slot, float x, float y) {
		slot.setPosition(x + position.x, y + position.y);
	}
	
	private void updateSlotPosition(Rectangle slot, Vector2 pos) {
		slot.setPosition(pos.x + position.x, pos.y + position.y);
	}
	
	boolean isInventoryClicked(float touchX, float touchY, boolean doubleClicked) {
		if ( itemSlotClicked(touchX, touchY, doubleClicked) ||
				helmSlotClicked(touchX, touchY, doubleClicked) ||
				armorSlotClicked(touchX, touchY, doubleClicked) ||
				weaponSlotClicked(touchX, touchY, doubleClicked) ||
				shieldSlotClicked(touchX, touchY, doubleClicked) ||
				glovesSlotClicked(touchX, touchY, doubleClicked) ||
				bootsSlotClicked(touchX, touchY, doubleClicked) ) {
			return true;
		}
		return false;
	}
	
	private boolean itemSlotClicked(float touchX, float touchY, boolean doubleClicked) {
		for (int y = 0; y < itemSlotHeight; ++y) {
			for (int x = 0; x < itemSlotWidth; ++x) {
				if (itemSlots[y][x].contains(touchX, touchY)) {
					processItemSlot(x, y, doubleClicked);
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean helmSlotClicked(float touchX, float touchY, boolean doubleClicked) {
		if (helmSlot.contains(touchX, touchY)) {
			processHelmSlot(doubleClicked);
			return true;
		}
		return false;
	}
	
	private boolean armorSlotClicked(float touchX, float touchY, boolean doubleClicked) {
		if (armorSlot.contains(touchX, touchY)) {
			processArmorSlot(doubleClicked);
			return true;
		}
		return false;
	}
	
	private boolean weaponSlotClicked(float touchX, float touchY, boolean doubleClicked) {
		if (weaponSlot.contains(touchX, touchY)) {
			processWeaponSlot(doubleClicked);
			return true;
		}
		return false;
	}
	
	private boolean shieldSlotClicked(float touchX, float touchY, boolean doubleClicked) {
		if (shieldSlot.contains(touchX, touchY)) {
			processShieldSlot(doubleClicked);
			return true;
		}
		return false;
	}
	
	private boolean glovesSlotClicked(float touchX, float touchY, boolean doubleClicked) {
		if (glovesSlot.contains(touchX, touchY)) {
			processGlovesSlot(doubleClicked);
			return true;
		}
		return false;
	}
	
	private boolean bootsSlotClicked(float touchX, float touchY, boolean doubleClicked) {
		if (bootsSlot.contains(touchX, touchY)) {
			processBootsSlot(doubleClicked);
			return true;
		}
		return false;
	}
	
	private void processItemSlot(int x, int y, boolean doubleClicked) {
		if (doubleClicked) {
			// It does not do anything if the clicked slot in the inventory is null
			inventory.useItemAt(x, y, player);
		} else {
			if (inventory.items[y][x] != null) {
				setClickedItem(inventory.items[y][x]);
			}
		}
	}
	
	private void setClickedItem(ItemEntity item) {
		clickedItem = item;
	}
	
	private void processHelmSlot(boolean doubleClicked) {
		setClickedItem(player.helm);
		if (!doubleClicked)
			return;
		
		if (player.helm != null) {
			player.helm.remove(player);
			player.refreshStatus();
			player.inventory.putInInventory(player.helm);
			player.helm = null;
		}
		player.refreshStatus();
	}
	
	private void processArmorSlot(boolean doubleClicked) {
		setClickedItem(player.armor);
		if (!doubleClicked)
			return;
		
		if (player.armor != null) {
			player.armor.remove(player);
			player.refreshStatus();
			inventory.putInInventory(player.armor);
			player.armor = null;
		}
		player.refreshStatus();
	}
	
	private void processWeaponSlot(boolean doubleClicked) {
		setClickedItem(player.weapon);
		if (!doubleClicked)
			return;

		if (player.weapon != null) {
			player.weapon.remove(player);
			player.refreshStatus();
			inventory.putInInventory(player.weapon);
			player.weapon = null;
		}
		player.refreshStatus();
	}
	
	private void processShieldSlot(boolean doubleClicked) {
		setClickedItem(player.shield);
		if (!doubleClicked)
			return;

		if (player.shield != null) {
			player.shield.remove(player);
			player.refreshStatus();
			inventory.putInInventory(player.shield);
			player.shield = null;
		}
		player.refreshStatus();
	}
	
	private void processGlovesSlot(boolean doubleClicked) {
		setClickedItem(player.gloves);
		if (!doubleClicked)
			return;

		if (player.gloves != null) {
			player.gloves.remove(player);
			player.refreshStatus();
			inventory.putInInventory(player.gloves);
			player.gloves = null;
		}
		player.refreshStatus();
	}
	
	private void processBootsSlot(boolean doubleClicked) {
		setClickedItem(player.boots);
		if (!doubleClicked)
			return;
		
		if (player.boots != null) {
			player.boots.remove(player);
			player.refreshStatus();
			inventory.putInInventory(player.boots);
			player.boots = null;
		}
		player.refreshStatus();
	}
	
	public static InventoryHandler getInstance(Player player) {
		if (handler == null) {
			handler = new InventoryHandler(player);
		}
		return handler;
	}
	
}
