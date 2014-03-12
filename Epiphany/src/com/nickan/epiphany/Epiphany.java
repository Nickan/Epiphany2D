package com.nickan.epiphany;

import com.badlogic.gdx.Game;
import com.nickan.epiphany.screen.MainScreen;

public class Epiphany extends Game {
	public static final float width = 800;
	public static final float height = 480;
	
	public static boolean debug = false;
	
	@Override
	public void create() {
		setScreen(new MainScreen(this));
	}
	
}