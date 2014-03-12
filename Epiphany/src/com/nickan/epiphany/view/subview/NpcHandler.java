package com.nickan.epiphany.view.subview;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nickan.epiphany.model.MoveableEntity.Action;
import com.nickan.epiphany.model.NonPlayerCharacter;
import com.nickan.epiphany.model.Player;
import com.nickan.epiphany.model.items.ItemEntity;
import com.nickan.epiphany.model.items.NpcInventory;
import com.nickan.epiphany.model.items.Wearable;
import com.nickan.epiphany.model.items.Wearable.WearableType;
import com.nickan.epiphany.view.World;
import com.nickan.epiphany.view.World.GameState;
import com.nickan.framework1_0.pathfinder1_0.Node;

public class NpcHandler {
	private static NpcHandler handler = null;
	World world;
	public NonPlayerCharacter blackSmith;
	public Player player;
	
	final int buySlotRows = 6;
	final int buySlotColumns = 4;
	Vector2 buySlotPos = new Vector2(-11.45f, 5.05f);
	
	NpcInventory blackSmithInventory = new NpcInventory(buySlotColumns, buySlotRows);
	Rectangle[][] buySlots = new Rectangle[buySlotRows][buySlotColumns];
	
	private NpcHandler(World world) {
		this.world = world;
		initialization();
		setBlackSmithItems();
	}
	
	private void initialization() {
		for (int y = 0; y < buySlotRows; ++y) {
			for (int x = 0; x < buySlotColumns; ++x) {
				buySlots[y][x] = new Rectangle(0, 0, 1f, 1f);
			}
		}
	}
	
	private void setBlackSmithItems() {
		blackSmithInventory.addItem(new Wearable("Just another shield", "Shield", WearableType.SHIELD, 0, 20));
	}
	
	
	// All Updates
	public void update() {
		// Updating the slots positions
		for (int y = 0; y < buySlotRows; ++y) {
			for (int x = 0; x < buySlotColumns; ++x) {
				updateSlotPosition(buySlots[y][x], buySlotPos.x + x * (buySlots[y][x].width + 0.835f), 
						buySlotPos.y - y * (buySlots[y][x].height + 0.725f));
			}
		}
		
		updateBlackSmith();
	}
	
	private void updateBlackSmith() {
		if (player.buyingAtBlackSmith) {
			if (player.hasDestinationReached()) {
				player.buyingAtBlackSmith = false;
				world.currentState = GameState.NPC_BLACKSMITH;
			}
		}
		
	}
	
	private void updateSlotPosition(Rectangle slot, float x, float y) {
		Vector2 position = player.getPosition();
		slot.setPosition(x + position.x, y + position.y);
	}
	
	// All Controllers
	
	public boolean isNpcClicked(float touchX, float touchY, boolean doubleClicked) {
		if (isBlackSmithClicked(touchX, touchY, doubleClicked)) {
			return true;
		}
		return false;
	}
	
	private boolean isBlackSmithClicked(float touchX, float touchY, boolean doubleClicked) {
		for (Node node: blackSmith.occupiedNodes) {
			if (node.isSame(touchX, touchY)) {
				player.buyingAtBlackSmith = true;
				return true;
			}
		}
		return false;
	}
	
	public boolean blackSmithInventoryClicked(float touchX, float touchY, boolean doubleClicked) {
		Rectangle buySlot = getSlotClicked(touchX, touchY);
		
		if (doubleClicked) {
			
		} else {
			
		}
		
		// Check if there is actually clicked slot
		if (buySlot != null) {
			int slotX = (int) touchX;
			int slotY = (int) touchY;
			
			// Check the equivalent item slot to be processed
			ItemEntity item = blackSmithInventory.items[slotY][slotX];
			if (item != null)
				showItemSpecs(item);
			
			return true;
		}
		return false;
	}
	
	private void showItemSpecs(ItemEntity item) {
		
	}
	
	
	private Rectangle getSlotClicked(float touchX, float touchY) {
		for (int y = 0; y < buySlotRows; ++y) {
			for (int x = 0; x < buySlotColumns; ++x) {
				if (buySlots[y][x].contains(touchX, touchY)) {
					return buySlots[y][x];
				}
			}
		}
		return null;
	}
	
	public static NpcHandler getInstance(World world) {
		if (handler == null) {
			handler = new NpcHandler(world);
		}
		return handler;
	}
	
}
