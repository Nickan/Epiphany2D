package com.nickan.epiphany.model;

/**
 * Will set the stateTime of the animation, depending on the attackTimer of the Character
 * @author Nickan
 *
 */
public class AttackAnimationHandler {
	private boolean firstAttack = true;

	/** Will be called in renderer for animation to dictate the state time of the animation */
	public float stateTime = 0;

	private float timeToLandHit;
	private float timeToPlayAnimation = 0;
	private float playTime = 0;

	public AttackAnimationHandler() { }

	/**
	 * Should be called to be able to give correct stateTime of the animation
	 * @param attackTime - The current attackTime of the character
	 * @param delta - Time between frames
	 */
	void update(float attackTime, float delta) {
		// I need to specify if it's first attack, as it is not nice to see to start the first attack
		// by returning the sword first from the attack
		if (firstAttack) {

			// How will I detect that it is a second attack??
			if (attackTime >= timeToPlayAnimation) {
				stateTime = attackTime - timeToPlayAnimation;
				firstAttack = false;
			}

		} else {

			// The second to infinity attack
			stateTime = attackTime - timeToPlayAnimation;
			if (stateTime < 0) {
				stateTime = timeToLandHit + attackTime;
			}
		}

		/**
		 * When I put this statement in else condition above, it doesn't work. I wonder why...
		 */
		// Restart the position of the animation when it is done attacking
		if (stateTime > playTime) {
			stateTime = 0;
		}
	}

	/**
	 * Set up the synchronized attack animation
	 * @param attackDelay - From the character's attack delay
	 */
	void setAttackAnimationHitDelay(float attackDelay) {
		this.playTime = attackDelay;

		final float defaultAnimationPlayTime = 0.6f;
		final int totalFrames = 12;
		final int frameNumberToLandHit = 9;

		// A quick fix of animation sync, needs to be changed later if needed
		// If the delay timer is slow, don't synchronize the attack animation, as it is so boring to watch
		if (attackDelay > defaultAnimationPlayTime) {
			timeToPlayAnimation = getTimeToPlayAnimation(defaultAnimationPlayTime, totalFrames,
					frameNumberToLandHit);
			timeToPlayAnimation += attackDelay - defaultAnimationPlayTime;

			// Set the play time of the animation
			playTime = defaultAnimationPlayTime;
		} else {
			timeToPlayAnimation = getTimeToPlayAnimation(attackDelay, totalFrames, frameNumberToLandHit);

			// Set the play time of the animation
			playTime = attackDelay;
		}

	}

	/**
	 * Calculates the start or the delay to play the animation
	 *
	 * @param playTime - The whole time it takes to play the animation
	 * @param totalFrames - Number of frames in animation
	 * @param frameNumberToLandHit - What frame number will the attack land to be able to synchronize to attack animation
	 * @return
	 */
	private float getTimeToPlayAnimation(float playTime, int totalFrames, int frameNumberToLandHit) {
		final float delayPerFrame = playTime / totalFrames;
		timeToLandHit = delayPerFrame * frameNumberToLandHit;
		return playTime - timeToLandHit;
	}

	void reset() {
		stateTime = 0;
		firstAttack = true;
	}

}
