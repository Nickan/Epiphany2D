package com.nickan.epiphany.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.nickan.epiphany.model.items.Inventory;
import com.nickan.framework1_0.pathfinder1_0.Node;

public class NonPlayerCharacter extends Character {
	String name = "";
	public List<Node> occupiedNodes = new ArrayList<Node>();
	Inventory inventory = new Inventory();
	
	public NonPlayerCharacter(Vector2 position, float width, float height,
			float rotation, float speed, String name) {
		super(position, width, height, rotation, speed);
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	@Override
	protected void updateBehavior() {
		// TODO Auto-generated method stub
		
	}

}
