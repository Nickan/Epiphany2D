package com.nickan.epiphany.view;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nickan.epiphany.model.Player;

public class StatusHandler {
	private static StatusHandler handler = null;
	Player player;
	Vector2 position;
	
	private Vector2 strPos = new Vector2(-6f, 2f);
	private Vector2 agiPos = new Vector2(-6f, 0.5f);
	private Vector2 dexPos = new Vector2(-6f, -1);
	private Vector2 vitPos = new Vector2(-6f, -2.5f);
	private Vector2 wisPos = new Vector2(-6f, -4);
	
	Rectangle strSlot = new Rectangle(0, 0, 1, 1);
	Rectangle agiSlot = new Rectangle(0, 0, 1, 1);
	Rectangle dexSlot = new Rectangle(0, 0, 1, 1);
	Rectangle vitSlot = new Rectangle(0, 0, 1, 1);
	Rectangle wisSlot = new Rectangle(0, 0, 1, 1);
	
	private StatusHandler(Player player) {
		this.player = player;
		this.position = player.getPosition();
	}
	
	public void update() {
		updateSlotPosition(strSlot, strPos);
		updateSlotPosition(agiSlot, agiPos);
		updateSlotPosition(dexSlot, dexPos);
		updateSlotPosition(vitSlot, vitPos);
		updateSlotPosition(wisSlot, wisPos);
	}
	
	private void updateSlotPosition(Rectangle slot, Vector2 pos) {
		slot.setPosition(pos.x + position.x, pos.y + position.y);
	}
	
	
	public boolean isStatusButtonClicked(float touchX, float touchY, boolean doubleClicked) {
		if (player.remainingStatusPoints > 0) {
			if (strSlotClicked(touchX, touchY, doubleClicked) ||
					agiSlotClicked(touchX, touchY, doubleClicked) ||
					dexSlotClicked(touchX, touchY, doubleClicked) ||
					vitSlotClicked(touchX, touchY, doubleClicked) ||
					wisSlotClicked(touchX, touchY, doubleClicked)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean strSlotClicked(float touchX, float touchY, boolean doubleClicked) {
		if (strSlot.contains(touchX, touchY)) {
			processStrSlot(doubleClicked);
			return true;
		}
		return false;
	}
	
	private boolean agiSlotClicked(float touchX, float touchY, boolean doubleClicked) {
		if (agiSlot.contains(touchX, touchY)) {
			processAgiSlot(doubleClicked);
			return true;
		}
		return false;
	}
	
	private boolean dexSlotClicked(float touchX, float touchY, boolean doubleClicked) {
		if (dexSlot.contains(touchX, touchY)) {
			processDexSlot(doubleClicked);
			return true;
		}
		return false;
	}
	
	private boolean vitSlotClicked(float touchX, float touchY, boolean doubleClicked) {
		if (vitSlot.contains(touchX, touchY)) {
			processVitSlot(doubleClicked);
			return true;
		}
		return false;
	}
	
	private boolean wisSlotClicked(float touchX, float touchY, boolean doubleClicked) {
		if (wisSlot.contains(touchX, touchY)) {
			processWisSlot(doubleClicked);
			return true;
		}
		return false;
	}
	
	private void processStrSlot(boolean doubleClicked) {
		if (!doubleClicked)
			return;
		
		player.statsHandler.addBaseStr();
	}
	
	private void processAgiSlot(boolean doubleClicked) {
		if (!doubleClicked)
			return;

		player.statsHandler.addBaseAgi();
	}
	
	private void processDexSlot(boolean doubleClicked) {
		if (!doubleClicked)
			return;
		player.statsHandler.addBaseDex();
	}
	
	private void processVitSlot(boolean doubleClicked) {
		if (!doubleClicked)
			return;
		player.statsHandler.addBaseVit();
	}
	
	private void processWisSlot(boolean doubleClicked) {
		if (!doubleClicked)
			return;
		player.statsHandler.addBaseWis();
	}
	
	public static StatusHandler getInstance(Player player) {
		if (handler == null) {
			handler = new StatusHandler(player);
		}
		return handler;
	}
}
