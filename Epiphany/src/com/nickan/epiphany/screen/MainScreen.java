package com.nickan.epiphany.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nickan.epiphany.Epiphany;
import com.nickan.framework1_0.CameraManager;

public class MainScreen implements Screen, InputProcessor {
	Epiphany game;
	private boolean startButtonIsPressed;
	
	private Vector3 touch;
	private Rectangle startButton;
	
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Texture allMenuTextures;
	
	public MainScreen(Epiphany game) {
		this.game = game;
		startButtonIsPressed = false;
		touch = new Vector3();
		startButton = new Rectangle(10, 7.5f, 4.5f, 1.5f);
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		
		// Background image
		spriteBatch.draw(allMenuTextures, 0, 0, 25f / 2, 15f / 2, 
				25, 15, 1, 1, 0, 0, 0, 800, 600, false, false);

		
		// Start button image, pressed and not pressed
		if (startButtonIsPressed) {
			spriteBatch.draw(allMenuTextures, startButton.x, startButton.y, startButton.width / 2, startButton.height / 2, 
					startButton.width, startButton.height, 1, 1, 0, 142, 605, 142, 48, false, false);
		} else {
			spriteBatch.draw(allMenuTextures, startButton.x, startButton.y, startButton.width / 2, startButton.height / 2, 
					startButton.width, startButton.height, 1, 1, 0, 0, 605, 142, 48, false, false);
//			spriteBatch.draw(allMenuTextures, startButton.x, startButton.y, startButton.width, startButton.height, 0, 605, 
//					(int) startButton.width, (int) startButton.height, false, false);
		}
		
		
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera = CameraManager.newStretchedCamera(Epiphany.width / 32f, Epiphany.height / 32f);
		spriteBatch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void show() {
		spriteBatch = new SpriteBatch();
		camera = CameraManager.newStretchedCamera(Epiphany.width / 32f, Epiphany.height / 32f);
		spriteBatch.setProjectionMatrix(camera.combined);
		allMenuTextures = new Texture("data/menubackground.png");
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
		spriteBatch.dispose();
		allMenuTextures.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touch.set(screenX, screenY, 0);
		camera.unproject(touch);
		
		// Start Button
		if ( (touch.x > startButton.x && touch.x < startButton.x + startButton.width) &&
				(touch.y > startButton.y && touch.y < startButton.y + startButton.height) ) {
			if (button == Buttons.LEFT) {
				startButtonIsPressed = true;
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touch.set(screenX, screenY, 0);
		camera.unproject(touch);
		
		// Start Button
		if ( (touch.x > startButton.x && touch.x < startButton.x + startButton.width) &&
				(touch.y > startButton.y && touch.y < startButton.y + startButton.height) ) {
			if (button == Buttons.LEFT) {
				startButtonIsPressed = false;
				game.setScreen(new GameScreen(game));
			}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
