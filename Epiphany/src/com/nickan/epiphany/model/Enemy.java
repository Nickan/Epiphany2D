package com.nickan.epiphany.model;

import com.badlogic.gdx.math.Vector2;
import com.nickan.epiphany.model.items.Gold;
import com.nickan.epiphany.model.items.ItemEntity;

public class Enemy extends Character {
	public boolean occupiedNodeErased = false;

	/** Defines the behavior of the enemy */
	public enum Status { AGGRESSIVE, NEUTRAL, ALLIES }
	public Status currentStatus = Status.AGGRESSIVE;

	// Loot
//	public ItemEntity lootDrop = new HpPotion("Heals Hp", "Small Hp Potion", 100, 5, 1);
//	public ItemEntity lootDrop = new Wearable("Sword", "sword nga!", WearableType.WEAPON_SWORD, SwordId.BALMUNG);
	public ItemEntity lootDrop = new Gold(20);

	public Enemy(Vector2 position, float width, float height, float rotation,
			float speed, String name) {
		super(position, width, height, rotation, speed);
		this.name = name;
		alive = true;
		this.statsHandler.attackRange = 1;
		this.statsHandler.sightRange = 4;
		this.statsHandler.baseVit = 1;
		identifyOccupiedNode();

		//....
		this.statsHandler.baseAgi = 1;
		refreshStatus();
		this.statsHandler.totalAtkDmg = 100;
	}

	@Override
	protected void updateBehavior() {
		if (isMoving())
			return;

		// No target player being set
		if (targetPlayer == null)
			return;

		if (targetIsInSightRange()) {
			targetPlayerInSight();
		} else {
			targetPlayerNotInSight();
		}

	}

	/**
	 * Defines the behavior of the enemy when the player is in sight
	 */
	private void targetPlayerInSight() {
		if (targetIsInAttackRange()) {
			switch (currentStatus) {
			case AGGRESSIVE:
				attackHandler.attacking = true;
				currentAction = Action.STANDING;
//				attackEnemy(targetPlayer);
				attackAnimationHandler.setAttackAnimationHitDelay(statsHandler.attackDelay);
				break;
			case NEUTRAL:
			case ALLIES:
				break;
			default:
				break;
			}

		} else {

			switch (currentStatus) {
			case AGGRESSIVE:
				attackEnemy(targetPlayer);
				break;
			case NEUTRAL:
			case ALLIES:
				break;
			default:
				break;
			}
		}
	}

	/**
	 * The counterpart of the targetPlayer()
	 */
	private void targetPlayerNotInSight() {
		switch (currentStatus) {
		case AGGRESSIVE:
			currentAction = Action.STANDING;
			attackHandler.attacking = false;
			break;
		case NEUTRAL:
		case ALLIES:
			break;
		default:
			break;
		}
	}



	public String getName() {
		return name;
	}

}
