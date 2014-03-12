package com.nickan.epiphany.model.items.consumables;

import com.badlogic.gdx.Gdx;
import com.nickan.epiphany.model.Character;
import com.nickan.epiphany.model.StatisticsHandler;

public class MpPotion extends RegenPotion {

	public MpPotion(String desc, String name, float regenPoints,
			float pointsPerSecond, int quantity, int price) {
		super(desc, name, ConsumableType.MP_REGEN, regenPoints, pointsPerSecond, quantity, price);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void use(Character character) {
		StatisticsHandler handler = character.statsHandler;
		decreaseQuantity();
		// Check if the character needs healing, otherwise do nothing
		if (handler.mp >= handler.totalFullMp)
			return;
			
		// Means that there is potion currently activated
		if (character.currentMpPotion != null) {
			character.currentMpPotion.regenPoints += regenPoints;
		} else {
			character.currentMpPotion = this;
		}

		// Limit the heal points
		if (character.currentMpPotion.regenPoints + handler.mp > handler.totalFullMp)
			character.currentMpPotion.regenPoints = handler.totalFullMp - handler.mp;
	}

	@Override
	public void healing(Character character) {
		StatisticsHandler handler = character.statsHandler;
		float regen = pointsPerSecond * Gdx.graphics.getDeltaTime();

		// Check if the char needs to be healed
		if (handler.mp <= handler.totalFullMp) {

			if (regenPoints > 0) {
				// If there is enough regenPoints to be added to char mp by heal per second
				if (regenPoints >= regen) {
					regenPoints -= regen;
					handler.mp += regen;
				} else {
					regenPoints = 0;

					// Corrects the insufficient healing because of the inaccuracy of float
					int excessNum = (int) handler.mp % 10;
					if (excessNum !=0) {
						handler.mp += (10 - excessNum);
					}
				}

				// If the healing is done, full hp has been reached
				if (handler.mp >= handler.totalFullMp) {
					handler.mp = handler.totalFullMp;
					regenPoints = 0;
				}
			} else {
				regenPoints = 0;
			}
		} else {
			// Healing should be canceled
			regenPoints = 0;
		}
	}

}
