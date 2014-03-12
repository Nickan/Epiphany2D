package com.nickan.epiphany.view;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nickan.epiphany.model.Character;
import com.nickan.epiphany.model.Enemy;
import com.nickan.epiphany.model.MoveableEntity.Action;
import com.nickan.epiphany.model.Player;
import com.nickan.epiphany.view.World.GameState;

public class InputHandler implements InputProcessor {
	private World world;
	private Vector3 touch = new Vector3();
	private Vector2 previousTouch = new Vector2();
	private boolean storedClick = false;
	private float clickDelay = 0.5f;
	
	private int movementPointer = -1;
	private int targetPointer = -1;
	
	
	public InputHandler(World world) {
		this.world = world;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touch.set(screenX, screenY, 0);
		world.getCamera().unproject(touch);
		switch (world.currentState) {
		case UPDATE_GAME:
			processUpdateTouchDown(screenX, screenY, pointer, button);
			break;
		case PAUSE_GAME:
			processPauseTouchDown(screenX, screenY, pointer, button);
			break;
		case NPC_BLACKSMITH:
			processBlackSmith(screenX, screenY, pointer, button);
			break;
		default:
			break;
		}
		
		return true;
	}
	
	
	private void processUpdateTouchDown(int screenX, int screenY, int pointer, int button) {
		if (world.backButton.contains(touch.x, touch.y)) {
			world.currentState = GameState.PAUSE_GAME;
			return;
		}
		
		switch (world.movementController) {
		case GUI_CONTROLLER:
			guiMovementPressed(touch.x, touch.y, pointer);
			break;
		case CLICK_CONTROLLER:
			Player player = world.player;
			
			if (doubleClicked(screenX, screenY)) {
				cancelAllClickedEnemies();
				updateDoubleClick(touch.x, touch.y);
				
				world.npcHandler.isNpcClicked(touch.x, touch.y, true);
			} else {
				player.attackHandler.attacking = false;
				updateSingleClick(touch.x, touch.y);
				
				world.npcHandler.isNpcClicked(touch.x, touch.y, false);
			}
			
			if (player.targetPlayer != null) {
				player.attackTargetNode.set(player.targetPlayer.nextNode);
			}
			
			break;
		}

		guiControlPressed(touch.x, touch.y, pointer);
	}
	
	private void processPauseTouchDown(int screenX, int screenY, int pointer, int button) {
		if (world.backButton.contains(touch.x, touch.y)) {
			world.currentState = GameState.UPDATE_GAME;
			return;
		}
		
		// Just process one button at a time
		if (pointer != 0) return;
		
		// Item management, I know the system is kind of messy, but I will fix all that up later
		if (doubleClicked(screenX, screenY)) {
			world.inventoryHandler.isInventoryClicked(touch.x, touch.y, true);
			world.statusHandler.isStatusButtonClicked(touch.x, touch.y, true);
		} else {
			world.inventoryHandler.isInventoryClicked(touch.x, touch.y, false);
		}
		
	}
	
	private void processBlackSmith(int screenX, int screenY, int pointer, int button) {
		if (world.backButton.contains(touch.x, touch.y)) {
			world.currentState = GameState.UPDATE_GAME;
			return;
		}
		
		if (pointer != 0) return;
		
		// Item management, I know the system is kind of messy, but I will fix all that up later
		if (doubleClicked(screenX, screenY)) {
			world.inventoryHandler.isInventoryClicked(touch.x, touch.y, true);
			world.statusHandler.isStatusButtonClicked(touch.x, touch.y, true);
		} else {
			world.inventoryHandler.isInventoryClicked(touch.x, touch.y, false);
		}
	}
	
	
	private void updateSingleClick(float touchX, float touchY) {
		// One click the enemy, just indicate that it has been clicked
		Player player = world.player;
		player.attackHandler.attacking = false;

		boolean clickedEnemy = false;

		for (int i = 0; i < world.enemies.size(); ++i) {
			Enemy enemy = world.enemies.get(i);
			if (enemy.alive) {
				if (isCharClicked(enemy, touchX, touchY)) {
					cancelAllClickedEnemies();
					
					enemy.isClicked = true;
					enemy.isSingleClicked = true;
					clickedEnemy = true;
					player.targetPlayer = enemy;
					break;
				}
			}
		}

		if (!clickedEnemy) {
			singleClickPathFind(player, touchX, touchY);
		}
	}
	
	private void updateDoubleClick(float touchX, float touchY) {
		Player player = world.player;
		player.attackHandler.attacking = false;
		
		Enemy enemy = getClickedEnemy(touchX, touchY);
		if (enemy != null) 
			attackClickPathFind(player, enemy);
		else 
			singleClickPathFind(player, touchX, touchY);
	}
	
	private Enemy getClickedEnemy(float touchX, float touchY) {
		for (Enemy enemy: world.enemies) {
			if (!enemy.alive) continue;
			
			if (isCharClicked(enemy, touchX, touchY)) {
				enemy.isClicked = true;
				return enemy;
			}
		}
		return null;
	}
	
	private void singleClickPathFind(Player player, float touchX, float touchY) {
		// If it clicks to the player, don't path find
		if (!isCharClicked(player, touchX, touchY)) {		
			player.currentAction = Action.PATHFIND_STATIC_NODE;
			player.attackHandler.attacking = false;
			player.targetNode.set((int) touchX, (int) touchY);
//			player.attackHandler.targetHit = true;
		}
	}
	
	private void attackClickPathFind(Player player, Enemy enemy) {
		player.attackEnemy(enemy);
		enemy.isClicked = true;
	}
		
	private boolean isCharClicked(Character character, float touchX, float touchY) {
		// Make the upper body of the enemy to be clickable, so that it is not hard to be clicked
		if (character.getBounds().contains(touchX, touchY) || character.getBounds().contains(touchX, touchY - 1))
			return true;
		return false;	
	}
	
	
	private void cancelAllClickedEnemies() {
		for (int i = 0; i < world.enemies.size(); ++i) {
			world.enemies.get(i).isClicked = false;
			world.enemies.get(i).isSingleClicked = false;
		}
	}
	
	private boolean guiMovementPressed(float touchX, float touchY, int pointer) {
		boolean isPressed = false;
		if (world.guiSlotHandler.movementSlotClicked(touchX, touchY)) {
			movementPointer = pointer;
			isPressed = true;
		}
		
		// Set it up later....
		if (world.guiSlotHandler.targetSlotClicked(touchX, touchY)) {
			targetPointer = pointer;
			isPressed = true;
		}
		return isPressed;
	}
	
	private boolean guiControlPressed(float touchX, float touchY, int pointer) {
		boolean isPressed = false;
		// Testing with potSlot
		if ( world.guiSlotHandler.skillSlotClicked(touchX, touchY) || world.guiSlotHandler.potSlotClicked(touchX, touchY) ||
				world.guiSlotHandler.atkButtonIsClicked(touchX, touchY) ) {
			isPressed = true;
		}
		return isPressed;
	}
	
	
	private boolean doubleClicked(int screenX, int screenY) {
		boolean doubleClicked = false;
		// Implementing the double clicking
		if (storedClick) {
			if (world.timePassed < clickDelay) {

				// Check to see if the user clicked on the same area
				// Make an allowance
				if (Math.abs(previousTouch.x - screenX) < 32 && Math.abs(previousTouch.y - screenY) < 32 ) {
					storedClick = false;
					doubleClicked = true;
				}
			}
		} else {
			storedClick = true;
		}

		previousTouch.set(screenX, screenY);
		world.timePassed = 0;
		return doubleClicked;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// Canceling area
		
		if (movementPointer == pointer) {
			movementPointer = -1;
			world.player.moveRight = false;
			world.player.moveLeft = false;
			world.player.moveUp = false;
			world.player.moveDown = false;
		}
		
		if (targetPointer == pointer) {
			targetPointer = -1;
//			world.player.targetCursorMove = false;
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
}
