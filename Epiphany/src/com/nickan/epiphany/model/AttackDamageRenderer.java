package com.nickan.epiphany.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.nickan.epiphany.view.subview.CommonGraphics;

/**
 * Handles the passed attack damage to be rendered on the screen
 * @author Nickan
 *
 */
public class AttackDamageRenderer {
	private static AttackDamageRenderer attackDamageRenderer;
	private static final float DURATION = 5.0f;
	private static final float Y_SPEED = 1f;

	/**
	 * Will store the x and y for the position of the damage and z for the damage value to be shown
	 * on the screen
	 */
	private List<AttackDamageStorage> entities = new ArrayList<AttackDamageStorage>();

	private AttackDamageRenderer() {
		entities.clear();
	}

	private class AttackDamageStorage {
		private float x;
		private float y;
		private String strDamage;
		private float duration;

		private AttackDamageStorage(float x, float y, String strDamage, float duration) {
			this.x = x;
			this.y = y;
			this.strDamage = strDamage;
			this.duration = duration;
		}
	}

	public void draw(CommonGraphics graphics, BitmapFont font, float delta) {
		for (AttackDamageStorage storage: entities) {
			graphics.drawString(storage.strDamage, storage.x, storage.y, font, Color.BLUE, 1 / 32f);
			storage.duration += delta;
			storage.y += Y_SPEED * delta;
		}

		for (AttackDamageStorage storage: entities) {
			if (storage.duration > DURATION) {
				entities.remove(storage);
				break;
			}
		}
	}

	/**
	 *
	 * @param x	- x coordinates
	 * @param y - y coordinates
	 * @param attackDamage - The value of the damage to be shown
	 */
	public void addAttackDamageToScreen(float x, float y, String strDamage) {
		entities.add(new AttackDamageStorage(x + 0.5f, y + 0.5f, strDamage, 0));
	}


	public static AttackDamageRenderer getRenderer() {
		if (attackDamageRenderer == null)
			return new AttackDamageRenderer();
		return attackDamageRenderer;
	}

}
