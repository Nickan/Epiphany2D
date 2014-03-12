package com.nickan.epiphany.model.items;


public abstract class ItemEntity {	
	public String desc = "";
	public String name = "";
	
	public final ItemType itemType;
	// I just made the money an item, to make the looting system more manageable
	public enum ItemType { CONSUMABLE, WEARABLE, MONEY }
	
	public boolean showSpecs = false;
	
	public ItemEntity(String desc, String name, ItemType itemType) {
		this.desc = desc;
		this.name = name;
		this.itemType = itemType;
	}
	
	public abstract int getPrice();

}
