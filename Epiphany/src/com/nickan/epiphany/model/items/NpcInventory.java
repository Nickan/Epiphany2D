package com.nickan.epiphany.model.items;

public class NpcInventory {
	public ItemEntity[][] items;
	
	public NpcInventory(int columns, int rows) {
		items = new ItemEntity[rows][columns];
		
		for (int y = 0; y < rows; ++y) {
			for (int x = 0; x < columns; ++x) {
				items[y][x] = null;
			}
		}
	}
	
	public ItemEntity sellItemAt(int colNum, int rowNum) {
		ItemEntity item = items[rowNum][colNum];
		items[rowNum][colNum] = null;
		return item;
	}
	
	public void addItem(ItemEntity item) {
		ItemEntity slot = getFreeSlot();
		if (slot == null) {
			System.out.println("No free slot");
		} else {
			slot = item;
		}
	}
	
	private ItemEntity getFreeSlot() {
		for (int y = 0; y < items.length; ++y) {
			for (int x = 0; x < items[0].length; ++x) {
				if (items[y][x] == null)
					return items[y][x];
			}
		}
		return null;
	}
	
	public int getItemPrice(int colNum, int rowNum) {
		return items[rowNum][colNum].getPrice();
	}

}
