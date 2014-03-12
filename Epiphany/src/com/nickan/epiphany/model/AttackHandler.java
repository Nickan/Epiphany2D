package com.nickan.epiphany.model;


import com.nickan.framework1_0.RandomNumber;


/**
 * Separate the concerns and handles the attack functionality of the Character class
 * @author Nickan
 *
 */
public class AttackHandler {
	float attackTimer = 0;
	/** For the indication if the attack has hit the target player */
	public enum AttackStatus { HIT, MISS, NONE };

	public boolean attacking = true;

	public AttackHandler() { }

	AttackStatus getAttackStatus(float hitChance, float attackDelay, float delta) {
		attackTimer += delta;

		if (attackTimer >= attackDelay) {
			attackTimer -= attackDelay;

			if (attackHasHit(hitChance)) {
				return AttackStatus.HIT;
			} else {
				// Testing
				return AttackStatus.MISS;
			}
		}

		return AttackStatus.NONE;
	}

	boolean attackHasHit(float hitChance) {
		int min = 0;
		int max = 100;
		return (RandomNumber.getRandomInt(min, max) < hitChance);
	}

	void reset() {
		attackTimer = 0;
	}

}
