package com.nickan.epiphany.model.items;

import com.nickan.epiphany.model.Character;

public class Wearable extends ItemEntity {
	public WearableType wearableType;
	public int Id;
	int price;
	
	public float agiBonus = 5;
	public float dexBonus = 5;
	public float strBonus = 5;
	public float vitBonus = 5;
	public float wizBonus = 5;
	
	// Sub stats bonus
	public float atkDmgBonus = 5;
	public float atkSpdBonus = 5;
	public float avoidBonus = 5;
	public float critBonus = 5;
	public float defBonus = 5;
	public float hitBonus = 5;
	public float fullHpBonus = 5;
	public float fullMpBonus = 5;
	
	public Wearable(String desc, String name, WearableType wearableType, int Id, int price) {
		super(desc, name, ItemType.WEARABLE);
		this.wearableType = wearableType;
		this.Id = Id;
	}
	
	public void equipTo(Character character) {
		// Main status bonus
		character.statsHandler.totalStr += strBonus;
		character.statsHandler.totalDex += dexBonus;
		character.statsHandler.totalVit += vitBonus;
		character.statsHandler.totalAgi += agiBonus;
		character.statsHandler.totalWis += wizBonus;

		// Sub status bonus
		character.statsHandler.totalAtkDmg += atkDmgBonus;
		character.statsHandler.totalAtkSpd += atkSpdBonus;
		character.statsHandler.totalHit += hitBonus;
		character.statsHandler.totalAvoid += avoidBonus;
		character.statsHandler.totalDef += defBonus;
		character.statsHandler.totalCrit += critBonus;
		character.statsHandler.totalFullHp += fullHpBonus;
		character.statsHandler.totalFullMp += fullMpBonus;
	}
	
	public void remove(Character character) {
		character.statsHandler.totalStr -= strBonus;
		character.statsHandler.totalDex -= dexBonus;
		character.statsHandler.totalVit -= vitBonus;
		character.statsHandler.totalAgi -= agiBonus;
		character.statsHandler.totalWis -= wizBonus;
		
		// Sub status bonus
		character.statsHandler.totalAtkDmg -= atkDmgBonus;
		character.statsHandler.totalAtkSpd -= atkSpdBonus;
		character.statsHandler.totalHit -= hitBonus;
		character.statsHandler.totalAvoid -= avoidBonus;
		character.statsHandler.totalDef -= defBonus;
		character.statsHandler.totalCrit -= critBonus;
		character.statsHandler.totalFullHp -= fullHpBonus;
		character.statsHandler.totalFullMp -= fullMpBonus;
	}
	
	public enum WearableType { HELM, WEAPON_SWORD, WEAPON_AXE, WEAPON_MACE, WEAPON_BOW, WEAPON_STAFF,
		ARMOR, BOOTS, GLOVES, MANTEAU, SHIELD, RING }

	@Override
	public int getPrice() {
		return price;
	}
	
}
