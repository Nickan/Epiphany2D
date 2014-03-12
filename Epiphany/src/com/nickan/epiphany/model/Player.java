package com.nickan.epiphany.model;

import com.badlogic.gdx.math.Vector2;
import com.nickan.epiphany.model.items.Inventory;
import com.nickan.epiphany.model.items.Wearable;
import com.nickan.epiphany.model.items.Wearable.WearableType;
import com.nickan.epiphany.model.items.consumables.HpPotion;
import com.nickan.epiphany.model.items.consumables.MpPotion;
import com.nickan.framework1_0.pathfinder1_0.Node;

public class Player extends Character {
	// Fields for GUI controls
	public boolean moveLeft = false;
	public boolean moveRight = false;
	public boolean moveUp = false;
	public boolean moveDown = false;

	public boolean buyingAtBlackSmith = false;

	public int gold = 0;

	public Node attackTargetNode = new Node();
	public Inventory inventory = new Inventory();

	public Player(Vector2 position, float width, float height,
			float rotation, float speed) {
		super(position, width, height, rotation, speed);
		name = "Nickan";
		this.alive = true;
		identifyOccupiedNode();

		this.statsHandler.baseAgi = 10;
		this.statsHandler.baseVit = 0;
		this.statsHandler.baseAtkDmg = 30;
		refreshStatus();
		initializeInventoryItems();
		this.statsHandler.totalAtkDmg = 10;

		//...
		this.statsHandler.totalHit = 0.5f;
	}

	private void initializeInventoryItems() {
		inventory.items[2][2] = new HpPotion("It heals of course", "Hp! duh!", 100, 5, 1, 10);
		inventory.items[2][3] = new MpPotion("It heals of course", "Mp! duh!", 100, 5, 1, 10);

		inventory.items[1][1] = new Wearable("Can cut tomato with ease", "Tomato Executioner",
				WearableType.WEAPON_SWORD, 0, 10);
		inventory.items[0][0] = new Wearable("Protects your pimples", "Pimple Defender",
				WearableType.HELM, 0, 10);
		inventory.items[0][1] = new Wearable("Laundry container", "Plangganita",
				WearableType.SHIELD, 0, 10);
		inventory.items[0][2] = new Wearable("Well-made armor", "Leather armor",
				WearableType.ARMOR, 0, 10);
		inventory.items[0][3] = new Wearable("Must have when applying suppository", "Gloves",
				WearableType.GLOVES, 0, 10);
		inventory.items[0][4] = new Wearable("In Boots", "Pus",
				WearableType.BOOTS, 0, 10);
		inventory.items[0][5] = new Wearable("In Boots (Dupe)", "Pus",
				WearableType.BOOTS, 0, 10);
	}

	@Override
	protected void updateBehavior() {
		if (!isMoving()) {
			updateGuiMovementControl();
		}
	}

	private void updateGuiMovementControl() {
		if (moveLeft) {
			setSingleClickSearch(currentNode.x - 1, currentNode.y);
		} else if (moveRight) {
			setSingleClickSearch(currentNode.x + 1, currentNode.y);
		} else if (moveUp) {
			setSingleClickSearch(currentNode.x, currentNode.y + 1);
		} else if (moveDown) {
			setSingleClickSearch(currentNode.x, currentNode.y - 1);
		}
	}

	private void setSingleClickSearch(int nodeX, int nodeY) {
		targetNode.set(nodeX, nodeY);
		currentAction = Action.PATHFIND_STATIC_NODE;
	}

}
