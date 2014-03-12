package com.nickan.epiphany.model.items.consumables;

import com.nickan.epiphany.model.Character;
import com.nickan.epiphany.model.items.ItemEntity;

public abstract class Consumable extends ItemEntity {
	public int quantity;
	int price;
	public ConsumableType consumableType;
	private String stringDesc;
	
	public Consumable(String desc, String name, ConsumableType consumableType, int quantity, int price) {
		super(desc, name, ItemType.CONSUMABLE);
		this.quantity = quantity;
		this.price = price;
		this.consumableType = consumableType;
		this.stringDesc = desc;
		this.desc = stringDesc + "Quantity: " + Integer.toString(quantity);
	}
	
	public void increaseQuantity() {
		this.desc = stringDesc + "Quantity: " + Integer.toString(++quantity);
	}
	
	public void decreaseQuantity() {
		this.desc = stringDesc + "Quantity: " + Integer.toString(--quantity);
	}
	
	public int getPrice() {
		return price * quantity;
	}
	
	public abstract void use(Character character);
	
	public enum ConsumableType { HP_REGEN, MP_REGEN }
	
}
