package com.nickan.epiphany.model.items;

public class Gold extends ItemEntity {
	public int amount;
	
	public Gold(int amount) {
		super("", "", ItemType.MONEY);
		this.amount = amount;
	}

	@Override
	public int getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

}
