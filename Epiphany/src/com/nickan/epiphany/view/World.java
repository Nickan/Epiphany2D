package com.nickan.epiphany.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nickan.epiphany.Epiphany;
import com.nickan.epiphany.model.Enemy;
import com.nickan.epiphany.model.NonPlayerCharacter;
import com.nickan.epiphany.model.Player;
import com.nickan.epiphany.view.subview.NpcHandler;
import com.nickan.framework1_0.pathfinder1_0.Node;
import com.nickan.framework1_0.pathfinder1_0.PathFinder;

public class World {
	Epiphany game;
	WorldRenderer worldRenderer;
	Player player;
	PathFinder pathFinder;
	TiledMapTileLayer collisionLayer;
	List<Node> occupiedNodes = new ArrayList<Node>();

	int worldWidth = 100;
	int worldHeight = 100;

	// For double click indicator needed in the input handler
	float timePassed = 0;

	Vector3 worldPos = new Vector3();

	List<Enemy> enemies = new ArrayList<Enemy>();

	public NonPlayerCharacter blackSmith;

	public enum GameState { UPDATE_GAME, PAUSE_GAME, NPC_BLACKSMITH }
	public GameState currentState = GameState.UPDATE_GAME;

	GameGuiHandler guiSlotHandler;
	InventoryHandler inventoryHandler;
	StatusHandler statusHandler;
	NpcHandler npcHandler;

	LootHandler lootHandler;

	// Settings
	enum MovementController { GUI_CONTROLLER , CLICK_CONTROLLER };
	MovementController movementController = MovementController.CLICK_CONTROLLER;

	Rectangle backButton = new Rectangle(0, 0, 1, 1);
	Vector2 backButtonPos = new Vector2(0, 6.5f);

	public World(Epiphany game) {
		this.game = game;

		//
		float movementSpeed = 2f;

		player = new Player(new Vector2(35, 15), 1f, 1f, 0, movementSpeed);
		Gdx.input.setInputProcessor(new InputHandler(this));

		pathFinder = new PathFinder(worldWidth, worldHeight);

		// Construction in progress...
		enemies.clear();
		enemies.add(new Enemy(new Vector2(24, 15), 1f, 1f, 0, movementSpeed / 2, "Dark knight"));
		enemies.add(new Enemy(new Vector2(23, 19), 1f, 1f, 0, movementSpeed / 2, "Bull Dog"));
		enemies.add(new Enemy(new Vector2(29, 15), 1f, 1f, 0, movementSpeed / 2, "Bull"));
		enemies.add(new Enemy(new Vector2(27, 18), 1f, 1f, 0, movementSpeed / 2, "Bull Dog Bansot"));

		blackSmith = new NonPlayerCharacter(new Vector2(25, 25), 1f, 1f, 0, 0, "BlackSmith");
		blackSmith.occupiedNodes.add(new Node(25, 25));
		blackSmith.occupiedNodes.add(new Node(25, 26));

		occupiedNodes.clear();

		initialize();

		guiSlotHandler = GameGuiHandler.newInstance(this);
		inventoryHandler = InventoryHandler.getInstance(player);
		statusHandler = StatusHandler.getInstance(player);
		lootHandler = LootHandler.getInstance();

		// No renderer counterpart
		npcHandler = NpcHandler.getInstance(this);
		npcHandler.blackSmith = blackSmith;
		npcHandler.player = player;
	}

	private void initialize() {
		for (int i = 0; i < enemies.size(); ++i) {
			occupiedNodes.add(enemies.get(i).nextNode);

			enemies.get(i).targetPlayer = player;
		}

		occupiedNodes.add(player.nextNode);
		occupiedNodes.addAll(blackSmith.occupiedNodes);
	}

	public void update(float delta) {
		if (delta > 0.4f)
			return;

		switch (currentState) {
		case UPDATE_GAME:
			updateCharacters(delta);

			// For the dropping of loots
			for (Enemy enemy: enemies) {
				if (enemy.hasDroppedLoots) {
					enemy.hasDroppedLoots = false;
					lootHandler.allotLootOnGround(enemy);
				}
			}
			lootHandler.update(player, delta);
			npcHandler.update();
			break;
		case PAUSE_GAME:
			inventoryHandler.update();
			statusHandler.update();
			break;
		case NPC_BLACKSMITH:
			inventoryHandler.update();
			break;
		default:
			break;
		}

		guiSlotHandler.update(player.getPosition());


		timePassed += delta;

		// Always accessible back/resume button
		Vector2 playerPos = player.getPosition();
		backButton.setPosition(backButtonPos.x + playerPos.x, backButtonPos.y + playerPos.y);
	}

	private void updateCharacters(float delta) {
		if (delta > 0.4)
			return;

		playerUpdate(delta);
		allEnemiesUpdate(delta);
	}

	private void playerUpdate(float delta) {
		if (player.alive) {
			player.update(pathFinder, occupiedNodes, delta);

			if (player.targetPlayer != null) {
				if (player.damage > 0) {
					Vector2 position = player.targetPlayer.getPosition();
					worldRenderer.atkDmgRenderer.addAttackDamageToScreen(position.x, position.y, "" + player.damage);
				}

				if (player.damage == 0) {
					Vector2 position = player.targetPlayer.getPosition();
					worldRenderer.atkDmgRenderer.addAttackDamageToScreen(position.x, position.y, "MISS!");
				}
			}
		}
	}

	// Enemies' update
	private void allEnemiesUpdate(float delta) {
		for (int i = 0; i < enemies.size(); ++i) {
			Enemy enemy = enemies.get(i);
			if (enemy.alive) {
				enemy.update(pathFinder, occupiedNodes, delta);

				if (enemy.attackHandler.attacking && (enemy.targetPlayer != null) ) {
					if (enemy.damage > 0) {
						Vector2 position = enemy.targetPlayer.getPosition();
						worldRenderer.atkDmgRenderer.addAttackDamageToScreen(position.x, position.y, "" + enemy.damage);
					}

					if (enemy.damage == 0) {
						Vector2 position = enemy.targetPlayer.getPosition();
						worldRenderer.atkDmgRenderer.addAttackDamageToScreen(position.x, position.y, "MISS!");
					}

					// Quick fix, it's not required but the hp test should be done here after all the necessary steps
					if (enemy.isTargetAlive()) {
						
					}

				}

			} else {

				// Erase the not walkable nodes it has occupied
				// Temporary solution, later it will be modified
				if (!enemy.occupiedNodeErased) {
					enemy.occupiedNodeErased = true;
					occupiedNodes.remove(enemy.nextNode);
//					occupiedNodes.remove(enemy.currentNode);
				}
			}
		}
	}


	public void setWorldRenderer(WorldRenderer worldRenderer) {
		this.worldRenderer = worldRenderer;
	}

	OrthographicCamera getCamera() {
		return worldRenderer.camera;
	}

}
