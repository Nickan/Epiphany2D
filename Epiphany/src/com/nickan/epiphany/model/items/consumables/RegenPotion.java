package com.nickan.epiphany.model.items.consumables;

import com.nickan.epiphany.model.Character;

public abstract class RegenPotion extends Consumable {
	public float regenPoints;
	public float pointsPerSecond;

	public RegenPotion(String desc, String name, ConsumableType consumableType, float regenPoints, 
			float pointsPerSecond, int quantity, int price) {
		super(desc, name, consumableType, quantity, price);
		this.regenPoints = regenPoints;
		this.pointsPerSecond = pointsPerSecond;
	}
	
	public abstract void healing(Character character);
	
	public boolean isEmpty() {
		return (regenPoints <= 0) ? true: false;
	}
	
}
