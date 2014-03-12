package com.nickan.epiphany.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.nickan.epiphany.Epiphany;
import com.nickan.epiphany.view.InputHandler;
import com.nickan.epiphany.view.World;
import com.nickan.epiphany.view.WorldRenderer;

public class GameScreen implements Screen {
	private World world;
	private WorldRenderer worldRenderer;
	
	public GameScreen(Epiphany game) {
		world = new World(game);
		worldRenderer = new WorldRenderer(world);
		world.setWorldRenderer(worldRenderer);
		Gdx.input.setInputProcessor(new InputHandler(world));
	}
	
	@Override
	public void render(float delta) {
		world.update(delta);
		worldRenderer.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize();
	}

	@Override
	public void show() {
		worldRenderer.show();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		worldRenderer.dispose();
	}

}
