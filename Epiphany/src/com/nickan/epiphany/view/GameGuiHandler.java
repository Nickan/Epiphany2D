package com.nickan.epiphany.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nickan.epiphany.model.Enemy;
import com.nickan.epiphany.model.Player;

public class GameGuiHandler {
	World world;
	public Vector2 position;
	
	private float potSlotX = -7;
	private float potSlotY = 6;
	private float skillSlotX = -7;
	private float skillSlotY = -7;
	private float movementSlotX = -9;
	private float movementSlotY = -6;
	private float targetButtonX = 9;
	private float targetButtonY = -6;
	
	private float atkButtonX = 10;
	private float atkButtonY = -1;
	
	int tarAtkNodeX = 0;
	int tarAtkNodeY = 5;
	
	public float hpBarX = -11;
	public float hpBarY = 7;
	
	public float enemyNameX = 0;
	public float enemyNameY = 7;
	
	Rectangle atkButton = new Rectangle(0, 0, 1, 1f);
	
	List<Rectangle> potSlots = new ArrayList<Rectangle>();
	List<Rectangle> skillSlots = new ArrayList<Rectangle>();
	List<Rectangle> moveButtons = new ArrayList<Rectangle>();
	List<Rectangle> targetButtons = new ArrayList<Rectangle>();
	
	private GameGuiHandler(World world) {
		this.world = world;
		for (int i = 0; i < 2; ++i) {
			potSlots.add(new Rectangle(0, 0, 1, 1));
		}
		
		for (int i = 0; i < 5; ++i) {
			skillSlots.add(new Rectangle(0, 0, 1, 1));
		}
		
		for (int i = 0; i < 4; ++i) {
			moveButtons.add(new Rectangle(0, 0, 1, 1));
		}
		
		for (int i = 0; i < 4; ++i) {
			targetButtons.add(new Rectangle(0, 0, 1, 1));
		}
		
	}
	
	public void update(Vector2 position) {
		this.position = position;
		for (int i = 0; i < potSlots.size(); ++i) {
			potSlots.get(i).x = i * 2 + potSlotX + position.x;
			potSlots.get(i).y = potSlotY + position.y;
		}
		
		for (int i = 0; i < skillSlots.size(); ++i) {
			skillSlots.get(i).x = i * 2 + skillSlotX + position.x;
			skillSlots.get(i).y = skillSlotY + position.y;
		}
		
		for (int i = 0; i < moveButtons.size(); ++i) {
			switch (i) {
			case 0: moveButtons.get(i).x = movementSlotX + position.x;
					moveButtons.get(i).y = movementSlotY + position.y;
					break;
			case 1:	moveButtons.get(i).x = movementSlotX - 1.5f + position.x;
					moveButtons.get(i).y = movementSlotY + 1.5f + position.y;
					break;
			case 2:	moveButtons.get(i).x = movementSlotX - 3 + position.x;
					moveButtons.get(i).y = movementSlotY + position.y;
					break;
			case 3:	moveButtons.get(i).x = movementSlotX - 1.5f + position.x;
					moveButtons.get(i).y = movementSlotY - 1.5f + position.y;
					break;
			}
		}
		
		for (int i = 0; i < targetButtons.size(); ++i) {
			switch (i) {
			case 0: targetButtons.get(i).x = targetButtonX + position.x;
					targetButtons.get(i).y = targetButtonY + position.y;
					break;
			case 1:	targetButtons.get(i).x = targetButtonX - 1.5f + position.x;
					targetButtons.get(i).y = targetButtonY + 1.5f + position.y;
					break;
			case 2:	targetButtons.get(i).x = targetButtonX - 3 + position.x;
					targetButtons.get(i).y = targetButtonY + position.y;
					break;
			case 3:	targetButtons.get(i).x = targetButtonX - 1.5f + position.x;
					targetButtons.get(i).y = targetButtonY - 1.5f + position.y;
					break;
			}
		}

		updateSlotPosition(atkButton, atkButtonX, atkButtonY);
//		updateSlotPosition(pauseButton, pauseButtonX, pauseButtonY);
		
		updateTargetCursor();
	}
	
	private void updateSlotPosition(Rectangle slot, float x, float y) {
		slot.setPosition(x + position.x, y + position.y);
	}
	
	// Updates the attack target node which traces the target enemy until any target button has been pressed
	private void updateTargetCursor() {
		Player player = world.player;
		Enemy enemy;
		
		boolean hasTarget = false;
		// Loop through all the enemies
		for (int i = 0; i < world.enemies.size(); ++i) {
			enemy = world.enemies.get(i);
			if (!enemy.alive) {
				continue;
			}
			
			if (player.targetCursorMove) {
				player.targetCursorMove = false;
				break;
			}
	
			// Break the operation once there is a targeted enemy
			if (player.attackTargetNode.isSame(enemy.currentNode) || player.attackTargetNode.isSame(enemy.nextNode)) {
				player.attackTargetNode.set(enemy.nextNode);
				player.targetCursorMove = false;
				player.targetPlayer = enemy;
				enemy.isClicked = true;
				hasTarget = true;
				break;
			}
			
		}
		
		// If there is no target locked enemy
		if (!hasTarget || player.targetCursorMove) {
			switch (world.movementController) {
			case GUI_CONTROLLER:
				player.attackTargetNode.set((int) position.x + tarAtkNodeX, (int) position.y + tarAtkNodeY);
				
				// Cancels the enemy being clicked when the attack target node is set outside the enemy's nodes
				if (player.targetPlayer != null) {
					if (!player.attackTargetNode.isSame(player.targetPlayer.currentNode) && 
							!player.attackTargetNode.isSame(player.targetPlayer.nextNode)) {
						
						if (!player.attackHandler.attacking && !player.targetPlayer.isSingleClicked) {
							player.targetPlayer.isClicked = false;
						}
						
					}
				}
				break;
			case CLICK_CONTROLLER:
				player.attackTargetNode.set(0, 0);
				break;
			}
			
			
		} else {
			// Set it so that when the attack cursor node has gotten outside the enemy's node, it should be placed correctly
			tarAtkNodeX = player.attackTargetNode.x - (int) position.x;
			tarAtkNodeY = player.attackTargetNode.y - (int) position.y;
			
		}
	}
	
	
	public boolean potSlotClicked(float touchX, float touchY) {
		for (int i = 0; i < potSlots.size(); ++i) {
			if (potSlots.get(i).contains(touchX, touchY)) {
				processPotSlotId(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean skillSlotClicked(float touchX, float touchY) {
		for (int id = 0; id < skillSlots.size(); ++id) {
			if (skillSlots.get(id).contains(touchX, touchY)) {
				processSkillSlotId(id);
				return true;
			}
		}
		return false;
	}
	
	public boolean movementSlotClicked(float touchX, float touchY) {
		for (int id = 0; id < moveButtons.size(); ++id) {
			if (moveButtons.get(id).contains(touchX, touchY)) {
				processMovementSlotId(id);
				return true;
			}
		}
		return false;
	}
	
	public boolean targetSlotClicked(float touchX, float touchY) {
		for (int id = 0; id < targetButtons.size(); ++id) {
			if (targetButtons.get(id).contains(touchX, touchY)) {
				processTargetSlotId(id);
				return true;
			}
		}
		return false;
	}
	
	public boolean atkButtonIsClicked(float touchX, float touchY) {
		if (atkButton.contains(touchX, touchY)) {
			processAtkButton();
			return true;
		}
		return false;
	}
	
	private void processAtkButton() {
		Player player = world.player;
		
		if (player.targetPlayer == null)
			return;
		
		if (player.targetPlayer.nextNode.isSame(player.attackTargetNode))
			world.player.attackEnemy(world.player.targetPlayer);
	}
	
	private void processPotSlotId(int slotId) {
		switch (slotId) {
		case 0:
			break;
		case 1:
			break;
		}
	}
	
	private void processSkillSlotId(int slotId) {
		
	}
	
	private void processMovementSlotId(int slotId) {
		Player player = world.player;
		switch (slotId) {
		case 0: player.moveRight = true;
			break;
		case 1: player.moveUp = true;
			break;
		case 2: player.moveLeft = true;
			break;
		case 3: player.moveDown = true;
			break;
		}
	}
	
	private void processTargetSlotId(int slotId) {
		world.player.targetCursorMove = true;
		switch (slotId) {
		case 0:
			if (tarAtkNodeX < 11)
				++tarAtkNodeX;
			break;
		case 1: 
			if (tarAtkNodeY < 7)
				++tarAtkNodeY;
			break;
		case 2: 
			if (tarAtkNodeX > -11)
				--tarAtkNodeX;
			break;
		case 3: 
			if (tarAtkNodeY > -7)
				--tarAtkNodeY;
			break;
		}
	}

	
	public static GameGuiHandler newInstance(World world) {
		GameGuiHandler guiSlot = new GameGuiHandler(world);
		return guiSlot;
	}
	
}
