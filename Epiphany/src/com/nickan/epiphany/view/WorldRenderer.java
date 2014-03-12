package com.nickan.epiphany.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nickan.epiphany.Epiphany;
import com.nickan.epiphany.model.AttackDamageRenderer;
import com.nickan.epiphany.model.Character;
import com.nickan.epiphany.model.Player;
import com.nickan.epiphany.model.items.ItemBounds.ButtonBounds;
import com.nickan.epiphany.view.subview.CommonGraphics;
import com.nickan.epiphany.view.subview.NpcRenderer;
import com.nickan.framework1_0.Animation.PlayMode;
import com.nickan.framework1_0.CameraManager;

public class WorldRenderer {
	int cursorX = 0;
	int cursorY = 0;

	float stateTime = 0;

	private SpriteBatch batch;
	private World world;
	OrthographicCamera camera;

	ShapeRenderer sr;

	TiledMap tiledMap;
	TiledMapRenderer renderer;
	TiledMapTileLayer collisionLayer;

	// All animations handler
	AllAnimations aniKnight;

	List<BaseAllAnimations> aniEnemyKnights = new ArrayList<BaseAllAnimations>();

	List<BaseAllAnimations> aniDrawOrderList = new ArrayList<BaseAllAnimations>();

	List<BaseAllAnimations> otherCharacters = new ArrayList<BaseAllAnimations>();

	// For drawing the name of the enemy/player currently being clicked
	BitmapFont font;
	BitmapFont arialFont;

	// GUI
	GameGuiRenderer guiSprite;
	InventoryRenderer inventoryRenderer;
	StatusRenderer statusRenderer;

	LootRenderer lootRenderer;

	NpcRenderer npcRenderer;

	Texture itemTexture;
	Texture pauseTexture;

	AttackDamageRenderer atkDmgRenderer = AttackDamageRenderer.getRenderer();

	public WorldRenderer(World world) {
		this.world = world;
		tiledMap = new TmxMapLoader().load("graphics/tiledmap/background.tmx");
		renderer = new OrthogonalTiledMapRenderer(tiledMap, 1f / 32f);
		sr = new ShapeRenderer();

		collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Collision");

		initializeCharacters();

		world.pathFinder.setCollisionLayer(collisionLayer);

		// For drawing letters on the screen
		font = new BitmapFont(Gdx.files.internal("fonts/courier.fnt"), false);
		font.setScale(1f / 35f);
		font.setUseIntegerPositions(false);

		arialFont = new BitmapFont(Gdx.files.internal("fonts/arial.fnt"), false);
		arialFont.setScale(1f/ 35f);
		arialFont.setUseIntegerPositions(false);

		guiSprite = GameGuiRenderer.newGuiSprite(world.guiSlotHandler);
		inventoryRenderer = InventoryRenderer.getInstance(world.inventoryHandler);
		statusRenderer = StatusRenderer.getInstance(world.statusHandler);
		lootRenderer = new LootRenderer(world.lootHandler);
		npcRenderer = NpcRenderer.getInstance(world.npcHandler);

		itemTexture = new Texture("graphics/tileset pickupthings.png");
		itemTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		pauseTexture = new Texture("graphics/pause.png");
		pauseTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		inventoryRenderer.itemTexture = itemTexture;
		inventoryRenderer.pauseTexture = pauseTexture;
		statusRenderer.pauseTexture = pauseTexture;
		guiSprite.pauseTextures = pauseTexture;
		lootRenderer.itemTexture = itemTexture;
		npcRenderer.pauseTexture = pauseTexture;
		npcRenderer.itemTexture = itemTexture;


		// Drop coins tile set
		TextureRegion[] region = new TextureRegion[7];
		for (int x = 0; x < region.length; ++x) {
			region[x] = new TextureRegion(itemTexture, 160 + (32 * x), 352, 32, 64);
		}

		lootRenderer.aniDropCoins = new Animation(0.1f, region);
		lootRenderer.aniDropCoins.setPlayMode(Animation.NORMAL);
	}

	private void initializeCharacters() {
		float animationDelay = 1f;
		aniKnight = new AllAnimations(world.player, "graphics/character animations/full plated knight", 128, 128, 4, 12, animationDelay);

		aniEnemyKnights.clear();

		for (int i = 0; i < world.enemies.size(); ++i) {
			aniEnemyKnights.add(new AllAnimations(world.enemies.get(i), aniKnight));
		}

		otherCharacters.clear();
		SingleAnimation blackSmith = new SingleAnimation(world.blackSmith,
				"graphics/character animations/black smith/black smith.png", 96, 96, 5, 9, 1.5f);
		blackSmith.animation.setPlayMode(PlayMode.LOOP);
		otherCharacters.add(blackSmith);
	}

	public void render(float delta) {
		if (delta > 0.4f)
			return;

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// Position the camera according to player's position
		Player player = world.player;
		camera.position.set(player.getPosition().x, player.getPosition().y, 0);
		camera.update();

		switch (world.currentState) {
		case UPDATE_GAME:
			drawUpdate();

			if (Epiphany.debug) {
				guiSprite.drawUpdateDebug(sr);
			}
			lootRenderer.draw(batch);
			break;
		case PAUSE_GAME:
			drawPause();

			if (Epiphany.debug) {
				guiSprite.drawPauseDebug(sr);
				inventoryRenderer.debug(sr);
			}
			break;

		case NPC_BLACKSMITH:
			drawBlackSmithMode();

			if (Epiphany.debug) {
				npcRenderer.debug(sr);
			}
			break;
		default:
			break;
		}

		// New Implementation after a long break for studying 3D
		drawHud(delta);
	}

	/**
	 * My code is a mess, most of the GUI should be put in HUD
	 */
	private void drawHud(float delta) {
		CommonGraphics graphics = CommonGraphics.getGraphics(batch);

		// Temporary, can be optimize later
		graphics.begin();
		atkDmgRenderer.draw(graphics, arialFont, delta);
		graphics.end();
	}

	private void drawUpdate() {
		// Background tiles
		renderer.setView(camera);
		renderer.render();

		batch.setProjectionMatrix(camera.combined);

		if (Epiphany.debug) {
			for (int i = 0; i < aniDrawOrderList.size(); ++i) {
				BaseAllAnimations allAnimations = aniDrawOrderList.get(i);
				debugging(sr, camera, allAnimations.character);
			}
		}

		// Uses shapeRenderer, so it should be drawn outside the batch
		//				drawHealthBar();
		for (int i = 0; i < world.enemies.size(); ++i) {
			if (world.enemies.get(i).isClicked && world.enemies.get(i).alive) {
				guiSprite.drawEnemyHealthBar(camera, sr, world.enemies.get(i));
			}
		}
		guiSprite.drawPlayerStatus(camera, sr);

		// Needs to be processed separately, should be on top of all the drawings
		batch.begin();
		drawAnimations(batch);

		guiSprite.drawUpdate(batch);

		for (int i = 0; i < world.enemies.size(); ++i) {
			if (world.enemies.get(i).isClicked && world.enemies.get(i).alive) {
				guiSprite.drawName(batch, font, world.enemies.get(i));
			}
		}

		guiSprite.drawLetters(batch, font, world.player);


		// Background shield and Pause button
		drawFromTexture(batch, pauseTexture, ButtonBounds.menuBg, world.backButton.x, world.backButton.y, 1, 1);
		drawFromTexture(batch, pauseTexture, ButtonBounds.pause, world.backButton.x, world.backButton.y, 1, 1);
		batch.end();
	}

	private void drawPause() {
		batch.begin();
		Vector2 pos = world.player.getPosition();
		batch.draw(pauseTexture, pos.x - 12.5f, pos.y - 7.5f, 25, 15, 0, 0, 512, 512, false, false);

		inventoryRenderer.draw(batch, font, arialFont);
		statusRenderer.draw(batch, font, arialFont);

		// Background shield box and resume icon
		drawFromTexture(batch, pauseTexture, ButtonBounds.menuBg, world.backButton.x, world.backButton.y, 1, 1);
		drawFromTexture(batch, pauseTexture, ButtonBounds.resume, world.backButton.x, world.backButton.y, 1, 1);
		batch.end();
	}

	private void drawBlackSmithMode() {
		batch.begin();
		Vector2 pos = world.player.getPosition();
		batch.draw(pauseTexture, pos.x - 12.5f, pos.y - 7.5f, 25, 15, 0, 0, 512, 512, false, false);

		inventoryRenderer.draw(batch, font, arialFont);

		// Background shield box and resume icon
		drawFromTexture(batch, pauseTexture, ButtonBounds.menuBg, world.backButton.x, world.backButton.y, 1, 1);
		drawFromTexture(batch, pauseTexture, ButtonBounds.resume, world.backButton.x, world.backButton.y, 1, 1);

		npcRenderer.draw(batch, font, arialFont);
		batch.end();
	}


	private void drawAnimations(SpriteBatch batch) {
		// Arrange the drawing of the animations according to its position.y
		arrangeDrawOrderList();

		for (int i = 0; i < aniDrawOrderList.size(); ++i) {
			BaseAllAnimations allAnimations = aniDrawOrderList.get(i);
			allAnimations.draw(batch);

			if (Epiphany.debug) {
				debugging(sr, camera, allAnimations.character);
			}
		}
	}

	private void arrangeDrawOrderList() {
		// Add all of the AllAnimations here, adding order not necessary
		aniDrawOrderList.clear();
		determineDrawPriority(aniKnight);

		for (int i = 0; i < aniEnemyKnights.size(); ++i) {
			BaseAllAnimations eAniKnight = aniEnemyKnights.get(i);
			determineDrawPriority(eAniKnight);
		}

		for (BaseAllAnimations baseAnimation: otherCharacters) {
			determineDrawPriority(baseAnimation);
		}
	}

	/**
	 * Determines if the allAnimations drawing position along z-axis, by its player's y-axis.
	 * The higher the y value, the first to be drawn
	 * @param allAnimations
	 */
	private void determineDrawPriority(BaseAllAnimations allAnimations) {
		// Loop through all the AllAnimations' player to analyze their position.y
		for (int i = 0; i < aniDrawOrderList.size(); ++i) {
			BaseAllAnimations tempAni = aniDrawOrderList.get(i);

			// Add it then cancel all operations
			if (allAnimations.character.getPosition().y >= tempAni.character.getPosition().y) {
				aniDrawOrderList.add(i, allAnimations);
				return;
			}
		}

		// If it is the lowest
		aniDrawOrderList.add(allAnimations);
	}

	/**
	 * Draw the specified rectangle from the texture, centering it from the given coordinate
	 * @param batch
	 * @param texture
	 * @param srcRect
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	private void drawFromTexture(SpriteBatch batch, Texture texture, Rectangle srcRect,
			float x, float y, float width, float height) {

		// Draws the texture in the center of the position given
		batch.draw(texture, x - (width - 1f) / 2, y - (height - 1f) / 2,
				width, height, (int) srcRect.x, (int) srcRect.y,
				(int) srcRect.width, (int) srcRect.height, false, false);
	}


	private void debugging(ShapeRenderer sr, OrthographicCamera cam, Character character) {
		Rectangle bounds = character.getBounds();

		sr.setProjectionMatrix(cam.combined);

		sr.begin(ShapeType.Line);
		sr.setColor(Color.CYAN);
		sr.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		sr.end();
	}

	public void resize() {
		camera = CameraManager.newStretchedCamera(Epiphany.width / 32f, Epiphany.height / 32f);
		batch.setProjectionMatrix(camera.combined);
	}

	public void show() {
		batch = new SpriteBatch();
		camera = CameraManager.newStretchedCamera(Epiphany.width / 32f, Epiphany.height / 32f);
		batch.setProjectionMatrix(camera.combined);
	}


	public void dispose() {
		batch.dispose();
		sr.dispose();
		tiledMap.dispose();
		font.dispose();
		arialFont.dispose();
		itemTexture.dispose();
		pauseTexture.dispose();
		aniKnight.dispose();
	}

}
