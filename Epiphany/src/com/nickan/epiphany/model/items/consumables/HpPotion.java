package com.nickan.epiphany.model.items.consumables;

import com.badlogic.gdx.Gdx;
import com.nickan.epiphany.model.Character;
import com.nickan.epiphany.model.StatisticsHandler;

public class HpPotion extends RegenPotion {

	public HpPotion(String desc, String name, float regenPoints,
			float pointsPerSecond, int quantity, int price) {
		super(desc, name, ConsumableType.HP_REGEN, regenPoints, pointsPerSecond, quantity, price);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void use(Character character) {
		StatisticsHandler handler = character.statsHandler;
		
		decreaseQuantity();
		if (handler.hp >= handler.totalFullHp)
			return;
		
		// Means that there is potion currently activated
		if (character.currentHpPotion != null) {
			character.currentHpPotion.regenPoints += regenPoints;
		} else {
			character.currentHpPotion = this;
		}

		// Limit the heal points
		if (character.currentHpPotion.regenPoints + handler.hp > handler.totalFullHp)
			character.currentHpPotion.regenPoints = handler.totalFullHp - handler.hp;
	}

	@Override
	public void healing(Character character) {
		StatisticsHandler handler = character.statsHandler;
		float regen = pointsPerSecond * Gdx.graphics.getDeltaTime();

		// Check if the char needs to be healed
		if (handler.hp <= handler.totalFullHp) {

			if (regenPoints > 0) {
				// If there is enough regenPoints to be added to char hp by heal per second
				if (regenPoints >= regen) {
					regenPoints -= regen;
					handler.hp += regen;
				} else {
					regenPoints = 0;

					// Corrects the insufficient healing because of the inaccuracy of float
					int excessNum = (int) handler.hp % 10;
					if (excessNum !=0) {
						handler.hp += (10 - excessNum);
					}
				}

				// If the healing is done, full hp has been reached
				if (handler.hp >= handler.totalFullHp) {
					handler.hp = handler.totalFullHp;
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
