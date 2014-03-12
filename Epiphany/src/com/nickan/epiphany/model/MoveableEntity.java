package com.nickan.epiphany.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.nickan.framework1_0.pathfinder1_0.Node;
import com.nickan.framework1_0.pathfinder1_0.PathFinder;

public class MoveableEntity extends Entity {
	private float previousX = 0;
	private float previousY = 0;
	private float traveledX = 0;
	private float traveledY = 0;
	private boolean movingHorizontal = false;
	private boolean movingVertical = false;

	public int moveIndicatorX = 0;
	public int moveIndicatorY = 0;
	public float rotation;
	public float speed;

	Vector2 velocity = new Vector2(0, 0);
	public Node currentNode = new Node();
	public Node nextNode = new Node();
	public Node targetNode = new Node();
	public Character targetPlayer;
	public List<Node> shortestPath = new ArrayList<Node>();

	public Node destinationNode = new Node();

	// Making the status of the movable entity to be more readable
	public enum Action { PATHFIND_STATIC_NODE, PATHFIND_ENEMY_NODE, UPDATE_PATHFIND_STATIC_NODE,
		UPDATE_PATHFIND_ENEMY_NODE, STANDING, IDLE }

	public Action currentAction = Action.STANDING;

	public MoveableEntity(Vector2 position, float width, float height, float rotation, float speed) {
		super(position, width, height);
		this.rotation = rotation;
		this.speed = speed;
		currentNode.set((int) position.x, (int) position.y);
		identifyOccupiedNode();
	}

	protected void update(float delta) {
		// Handles the movement if the moving horizontal or vertical is set to true, trigger by identifyMovent
		// And automatically set it to false when the movement is done in precisely 1 unit
		move(delta);

		// Usual update of variables
		updateBounds();
	}

	private void move(float delta) {
		if (movingHorizontal) {

			// Moving right
			if (moveIndicatorX == 1) {
				position.x += speed * delta;

				traveledX = position.x - previousX;
				if (traveledX >= 1) {
					movingHorizontal = false;
					traveledX = 0;
					position.x = previousX + 1;
				}

				// Moving left
			} else if (moveIndicatorX == -1) {
				position.x -= speed * delta;

				traveledX = position.x - previousX;
				if (Math.abs(traveledX) >= 1) {
					movingHorizontal = false;
					traveledX = 0;
					position.x = previousX - 1;
				}
			}

		}

		if (movingVertical) {

			// Moving upward
			if (moveIndicatorY == 1) {

				position.y += speed * delta;
				traveledY = position.y - previousY;

				// Cancel the updating if it has traveled 1 node
				if (traveledY >= 1) {
					movingVertical = false;
					traveledY = 0;
					position.y = previousY + 1;
				}

				// Moving downward
			} else if (moveIndicatorY == -1) {
				position.y -= speed * delta;

				traveledY = position.y - previousY;
				if (Math.abs(traveledY) >= 1) {
					movingVertical = false;
					traveledY = 0;
					position.y = previousY - 1;
				}
			}

		}

	}

	private void findPathWalkableNode(PathFinder pathFinder, Node targetNode) {
		shortestPath.clear();
		shortestPath.addAll( pathFinder.getShortestPath( new Node( (int) position.x, (int) position.y),
				targetNode) );

		// For the HUD
		destinationNode.set(shortestPath.get(0));
	}

	/*	Used to path find a not walkable node such as walls, house, etc */
	private void findPathNotWalkable(PathFinder pathFinder, List<Node> occupiedNodes) {
		if (!pathFinder.getShortestPath(currentNode, targetNode).isEmpty()) {
			// Find for nearest walkable node then set it as the target
			targetNode.set(pathFinder.getNearestWalkableNode());

			shortestPath.clear();
			identifyOccupiedNode();
			initializePath(pathFinder, occupiedNodes, targetNode);

			// Check if the target not is not the same as the current node, if yes the path finding is not necessary
			if (!targetNode.isSame(currentNode))
				findPathWalkableNode(pathFinder, targetNode);
		}
	}

	private void handleNotWalkableTargetNode(PathFinder pathFinder, List<Node> occupiedNodes, Node targetNode) {
		// If the target node belongs to occupied node
		if (isInList(occupiedNodes, targetNode)) {
			pathFindOccupiedNode(pathFinder, occupiedNodes, targetNode);
		} else {
			// This function should only use for walls, as it will find the nearest walkable node
			findPathNotWalkable(pathFinder, occupiedNodes);
		}
	}

	public void initializePath(PathFinder pathFinder, List<Node> occupiedNodes, Node targetNode) {
		this.targetNode.set(targetNode);
		pathFinder.setHeuristics(targetNode);

		// Set the occupied nodes as not walkable
		for (int i = 0; i < occupiedNodes.size(); ++i) {
			pathFinder.getNode(occupiedNodes.get(i)).walkable = false;
		}
	}

	public void identifyOccupiedNode() {
		if (!shortestPath.isEmpty())
			nextNode.set(shortestPath.remove(shortestPath.size() - 1));
		else
			nextNode.set(currentNode);
	}

	public void identifyMovement() {
		// Update for the next node to go to
		moveIndicatorX = nextNode.x - currentNode.x;
		moveIndicatorY = nextNode.y - currentNode.y;

		// Corrects the positioning of the character
		if (moveIndicatorX != 0) {
			movingHorizontal = true;
			traveledX = 0;
			previousX = currentNode.x;
		}

		if (moveIndicatorY != 0) {
			movingVertical = true;
			traveledY = 0;
			previousY = currentNode.y;
		}

		// Identifies the rotation
		velocity.set(moveIndicatorX, moveIndicatorY);
		rotation = velocity.angle();
	}

	public void pathFindStaticNode(PathFinder pathFinder, List<Node> occupiedNodes) {
		initializePath(pathFinder, occupiedNodes, targetNode);

		// The target node is walkable
		if (pathFinder.getNode(targetNode).walkable) {
			findPathWalkableNode(pathFinder, targetNode);

			currentAction = Action.UPDATE_PATHFIND_STATIC_NODE;
		} else {
			// Not walkable

			// If the node to be targeted is just adjacent, no point to use the path finder
			if (!nextNode.isAdjacentTo(targetNode)) {
				handleNotWalkableTargetNode(pathFinder, occupiedNodes, targetNode);

				currentAction = Action.UPDATE_PATHFIND_STATIC_NODE;

			} else {
				// The not walkable node is just adjacent
				currentAction = Action.STANDING;
			}
		}

	}

	public void pathFindEnemyNode(PathFinder pathFinder, List<Node> occupiedNodes) {
		targetNode.set(targetPlayer.nextNode);

		// If the node to be targeted is not adjacent, path find it
		if (!nextNode.isAdjacentTo(targetNode)) {
			pathFindOccupiedNode(pathFinder, occupiedNodes, targetNode);
			currentAction = Action.UPDATE_PATHFIND_ENEMY_NODE;
		} else {
			// It is adjacent to the target's next node
			currentAction = Action.STANDING;
			shortestPath.clear();
		}

	}

	public void updatePathFindEnemyNode(List<Node> occupiedNodes) {
		occupiedNodes.remove(nextNode);

		// Checking if the target player's next node is the same as the target node
		if (targetNode.isSame(targetPlayer.nextNode)) {
			// Continue with the current path list

			if (!shortestPath.isEmpty()) {
				nextNode.set(shortestPath.remove(shortestPath.size() - 1));

				// If the next node to be traveled is already occupied, re-find path, else move
				if (isInList(occupiedNodes, nextNode)) {

					currentAction = Action.PATHFIND_ENEMY_NODE;
					nextNode.set(currentNode);
				} else {
					identifyMovement();
					facePlayer(targetPlayer);
				}
			} else {
				// If there is no path stored
				currentAction = Action.STANDING;
			}
		} else {
			// Target's next node has changed, means it changed position, needs to path find it
			currentAction = Action.PATHFIND_ENEMY_NODE;
		}

		occupiedNodes.add(nextNode);
	}

	public void updatePathFindStaticNode(List<Node> occupiedNodes) {
		// Once established the path to be taken, remove the next node from the occupied list	- done
		//
		occupiedNodes.remove(nextNode);

		// If there is still path stored to travel
		if (!shortestPath.isEmpty()) {
			nextNode.set(shortestPath.remove(shortestPath.size() - 1));

			// The next node to travel is already occupied, refind path
			if (isInList(occupiedNodes, nextNode)) {
				currentAction = Action.PATHFIND_STATIC_NODE;
			} else {

				// If the next node is just the same as the current node, skip it if there is
				// Other node stored
				if (nextNode.isSame(currentNode)) {
					if (!shortestPath.isEmpty())
						nextNode.set(shortestPath.remove(shortestPath.size() - 1));
				}

				// Move only if the next node is confirmed to be walkable
				identifyMovement();
				if (!isMoving()) {
					currentAction = Action.STANDING;
				}
			}

		} else {
			// No path in the list
			currentAction = Action.STANDING;
			nextNode.set(currentNode);
		}

		occupiedNodes.add(nextNode);
	}

	/**
	 * Set the target node that is in the occupied node to be walkable first
	 * Then path find it, after path finding, set it as not walkable again, and remove it from the
	 * Shortest path result
	 */
	private void pathFindOccupiedNode(PathFinder pathFinder, List<Node> occupiedNodes, Node targetNode) {
		initializePath(pathFinder, occupiedNodes, targetNode);

		pathFinder.getNode(targetNode).walkable = true;
		findPathWalkableNode(pathFinder, targetNode);
		pathFinder.getNode(targetNode).walkable = false;

		removeFromList(shortestPath, targetNode);
	}

	public void facePlayer(Character character) {
		int distX = (int) (character.getPosition().x - position.x);
		int distY = (int) (character.getPosition().y - position.y);
		velocity.set(distX, distY);

		// Just set rotation divisible by 45
		float rawRotation = velocity.angle();
		rotation = rawRotation - (rawRotation % 45);
	}

	private boolean isInList(List<Node> list, Node node) {
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i).isSame(node))
				return true;
		}
		return false;
	}

	public boolean isMoving() {
		if (movingHorizontal || movingVertical)
			return true;
		return false;
	}

	public boolean hasDestinationReached() {
		if (!isMoving() && currentAction == Action.STANDING)
			return true;
		return false;
	}

	private Node removeFromList(List<Node> list, Node node) {
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i).x == node.x && list.get(i).y == node.y)
				return list.remove(i);
		}
		return null;
	}
}
