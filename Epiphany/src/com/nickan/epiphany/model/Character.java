package com.nickan.epiphany.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.nickan.epiphany.model.items.Wearable;
import com.nickan.epiphany.model.items.Wearable.WearableType;
import com.nickan.epiphany.model.items.consumables.RegenPotion;
import com.nickan.framework1_0.pathfinder1_0.Node;
import com.nickan.framework1_0.pathfinder1_0.PathFinder;

public abstract class Character extends MoveableEntity {
	public String name = "";

	// Application of main status
	public int level = 1;
	public int remainingStatusPoints = 10;

	/** To be called in renderer to print on the screen */
	public int damage = -1;

	public float experience = 0;



	public boolean hasDroppedLoots = false;

	// Weapon and armor fields
	public Wearable weapon = null;
	public WearableType[] allowedWeapons;

	public Wearable helm = null;
	public Wearable armor = null;
	public Wearable shield = null;
	public Wearable gloves = null;
	public Wearable boots = null;

	public boolean alive;
	public boolean healing = false;
	public boolean isClicked;

	/** For the target cursor HUD */
	public boolean targetCursorMove = true;

	public boolean isSingleClicked = false;

	/** Will be used to set the animation play time */
	public boolean atkSpdUpdated = true;

	public RegenPotion currentHpPotion = null;
	public RegenPotion currentMpPotion = null;
	public List<RegenPotion> hpPotions = new ArrayList<RegenPotion>();

	// Separate the concern of the attack functionality
	public AttackHandler attackHandler;
	public StatisticsHandler statsHandler;
	public AttackAnimationHandler attackAnimationHandler;


	public Character(Vector2 position, float width, float height,
			float rotation, float speed) {
		super(position, width, height, rotation, speed);
		attackHandler = new AttackHandler();
		statsHandler = new StatisticsHandler();
		attackAnimationHandler = new AttackAnimationHandler();
	}

	/**
	 *  Control the behavior of the object here.
	 *  For correct character positioning, consider getting values when not moving
	 */
	protected abstract void updateBehavior();

	public void update(PathFinder pathFinder, List<Node> occupiedNodes, float delta) {
		if (!isMoving()) {
			currentNode.set((int) position.x, (int) position.y);
		}
		updateBehavior();

		if (!isMoving()) {
			handleMovement(pathFinder, occupiedNodes, delta);
		}

		updateHpPotionUsage();
		updateMpPotionUsage();
		super.update(delta);
	}


	/**
	 * This only be called if the character is not currently on the attack mode.
	 * Lots of functionality and variable names to be rewritten to make it more readable
	 * @param pathFinder - The pathfinder class
	 * @param occupiedNodes - List of occupied nodes
	 * @param delta -
	 */
	private void handleMovement(PathFinder pathFinder, List<Node> occupiedNodes, float delta) {
		switch (currentAction) {
		case STANDING:
			if (targetPlayer != null)
				handleAttack(delta);
			break;

		case PATHFIND_STATIC_NODE:
			pathFindStaticNode(pathFinder, occupiedNodes);
		case UPDATE_PATHFIND_STATIC_NODE:
			updatePathFindStaticNode(occupiedNodes);
			break;

		case PATHFIND_ENEMY_NODE:
			pathFindEnemyNode(pathFinder, occupiedNodes);
		case UPDATE_PATHFIND_ENEMY_NODE:
			updatePathFindEnemyNode(occupiedNodes);
			break;

		default:
			break;
		}

	}

	private void handleAttack(float delta) {
		if (attackHandler.attacking) {
			switch (attackHandler.getAttackStatus(getHitChance(), statsHandler.attackDelay, delta)) {
			case HIT:
				attackHasHit();
				break;
			case MISS:
				damage = 0;
				break;
			case NONE:
				damage = -1;
				break;
			}

			attackAnimationHandler.update(attackHandler.attackTimer, delta);
		} else {

			// I know I should change this later for balancing game play (I wish)
			attackAnimationHandler.reset();
			attackHandler.reset();
		}



	}

	/** Will be called if the attack has hit the target */
	private void attackHasHit() {
		applyDamage();
	}

	private void applyDamage() {
		damage = (int) (statsHandler.totalAtkDmg - targetPlayer.statsHandler.totalDef);

		if (damage < 1)
			targetPlayer.statsHandler.hp -= 1;
		else
			targetPlayer.statsHandler.hp -= damage;
	}

	/** Calculates to chance to hit the target */
	private float getHitChance() {
		float hitChance = 100 * (statsHandler.totalHit / targetPlayer.statsHandler.totalAvoid);
		return (hitChance > 100) ? 100 : hitChance;
	}

	/** Should be called if all other necessary things has been done, as it will make the targetPlayer as null */
	public boolean isTargetAlive() {
		if (targetPlayer == null)
			return false;

		if (targetPlayer.statsHandler.hp > 0) {

			return true;
		}
		targetKilled();
		return false;
	}

	private void targetKilled() {
		targetPlayer.hasBeenKilled();
		statsHandler.gainExperience(targetPlayer.level);
//		targetPlayer = null;

		targetCursorMove = true;
		attackHandler.reset();
	}

	void hasBeenKilled() {
		alive = false;
		isClicked = false;
		hasDroppedLoots = true;
	}


	private void updateHpPotionUsage() {
		// Triggers the heal when it is not null, handles the healing by the Potion class
		if (currentHpPotion != null) {
			currentHpPotion.healing(this);

			if (currentHpPotion.isEmpty()) {
				currentHpPotion = null;
			}
		}
	}


	private void updateMpPotionUsage() {
		// Triggers the heal when it is not null, handles the healing by the Potion class
		if (currentMpPotion != null) {
			currentMpPotion.healing(this);

			if (currentMpPotion.isEmpty()) {
				currentMpPotion = null;
			}
		}
	}


	public void usePotion(RegenPotion potion) {
		potion.use(this);
	}


	/**
	 * Detects whether the weapon being passed can be used by this character
	 * @param weaponType - The weapon to be tested
	 * @return - If can be equipped
	 */
	public boolean isWeaponAllowed(WearableType weaponType) {
		for (WearableType type: allowedWeapons) {
			if (type == weaponType) {
				return true;
			}
		}
		return false;
	}


	public void refreshStatus() {
		statsHandler.resetStats();
		applyEquipmentBonuses();
		statsHandler.applyFinalStats();
		this.atkSpdUpdated = true;
	}

	private void applyEquipmentBonuses() {
		if (weapon != null) {
			weapon.equipTo(this);
		}

		if (helm != null) {
			helm.equipTo(this);
		}

		if (armor != null) {
			armor.equipTo(this);
		}

		if (shield != null) {
			shield.equipTo(this);
		}

		if (gloves != null) {
			gloves.equipTo(this);
		}

		if (boots != null) {
			boots.equipTo(this);
		}
	}

	/**
	 * Should be called to set the character enemy to be attacked
	 * @param enemy
	 */
	public void attackEnemy(Character enemy) {
		currentAction = Action.PATHFIND_ENEMY_NODE;
		attackHandler.attacking = true;
		targetPlayer = enemy;
		facePlayer(enemy);

		attackAnimationHandler.setAttackAnimationHitDelay(statsHandler.attackDelay);
	}

	boolean targetIsInAttackRange() {
		return isInRange(targetPlayer.nextNode, statsHandler.attackRange);
	}

	boolean targetIsInSightRange() {
		return isInRange(targetPlayer.nextNode, statsHandler.sightRange);
	}

	protected boolean isInRange(Node node, int range) {
		return (Math.abs(currentNode.x - node.x) <= range &&
				Math.abs(currentNode.y - node.y) <= range) ? true : false;
	}

}
