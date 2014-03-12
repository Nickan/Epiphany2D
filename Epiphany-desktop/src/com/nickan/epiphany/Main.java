package com.nickan.epiphany;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Epiphany Pre-Alpha 0.0.0.1";
		
		// To be changed later
		cfg.useGL20 = false;
//		cfg.width = 360;
//		cfg.height = 220;
		cfg.width = 800;
		cfg.height = 600;
		
		new LwjglApplication(new Epiphany(), cfg);
	}
}
