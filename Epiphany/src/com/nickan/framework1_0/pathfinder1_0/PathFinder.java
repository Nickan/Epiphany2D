package com.nickan.framework1_0.pathfinder1_0;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;



/**
 *		Reads what's in the tiled map, if it reads the "collision", it will set it as unwalkable. Pretty unplanned 
 *  path finder as I make some of the tiles unwalkable by setting the nodes one by one as unwalkable, great for 
 *  learning purposes, but very unfriendly to be used for real games, as I know there are numerous optimized path 
 *  finder out there. I will try one of those later, but for now I don't care, I am learning. :D
 *
 *	@author Nickan
 */
public class PathFinder {
	List<Node> openList = new ArrayList<Node>();
	List<Node> possibleNextNodeList = new ArrayList<Node>();
	List<Node> closedList = new ArrayList<Node>();
	List<Node> adjacentNodes = new ArrayList<Node>();
	List<Node> shortestPath = new ArrayList<Node>();
	Node[][] nodes;
	TiledMapTileLayer collisionLayer;

	// Still being fixed...
	boolean unwalkableIsClicked = false;

	// Under construction...
	boolean includeDiagonal = true;

	Node goalNode = new Node();

	// For debugging
	int loopTimes = 0;

	public PathFinder(int width, int height) {
		this.nodes = new Node[height][width];

		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				nodes[y][x] = new Node();
			}
		}
	}

	public List<Node> getShortestPath(Node startNode, Node endNode) {
		openList.clear();
		possibleNextNodeList.clear();
		closedList.clear();
		shortestPath.clear();

		goalNode.set(endNode);
		Node tempStartNode = nodes[startNode.y][startNode.x];
		closedList.add(tempStartNode);

		// For debugging
//		System.out.println("Start Node: " + startNode.x + ": " + startNode.y);
//		System.out.println("goalNode Node: " + goalNode.x + ": " + goalNode.y);

		loopTimes = 0;

		boolean hasFound = false;
		while (!hasFound) {
			possibleNextNodeList.clear();
			Node currentNode = closedList.get(closedList.size() - 1);
			if (currentNode == null)
				return shortestPath;
			setAllAdjacentNodesTo(currentNode);

			// For debugging
//			System.out.print("Current node ");
//			currentNode.print();

			tempDiagonalNodesRule(currentNode);

			// Analyze all the adjacent nodes to current node
			analyzeAllAdjacentNodes(currentNode);

			// After taking care of the parenting and adding to the open list,
			// identify the closest node from the current node
			Node closestNode = getClosestNodeFromPossibleNextNodeList(currentNode);
			if (closestNode != null) {
				openList.remove(closestNode);
				closedList.add(closestNode);
				// If the newly added node in the closed list is the target node
				if (closestNode.isSame(goalNode)) {
//					System.out.println("Target found");
//					System.out.println("Looped over " + loopTimes + " times!");
//					System.out.println("Target node: " + tempNode.x + ": " + tempNode.y);
					break;
				}
			} else {
				closestNode = getClosestNodeFromOpenList(tempStartNode);
				openList.remove(closestNode);
				closedList.add(closestNode);
			}

			++loopTimes;
			if (loopTimes > 200) {
				System.out.println("Looped over 200 times!");
				break;
			}
		}

		return getTrackedParentNodes();
	}

	public Node getNearestWalkableNode() {
		int currentHeuristic = 9999;
		Node nearestNode = null;

		// Loop through all the nodes in the closed list
		for (int i = 0; i < closedList.size(); ++i) {
			Node tempNode = closedList.get(i);

			if (tempNode.heuristic < currentHeuristic) {
				currentHeuristic = tempNode.heuristic;
				nearestNode = tempNode;
			}
		}
		return nearestNode;
	}

	private void analyzeAllAdjacentNodes(Node currentNode) {
		for (int i = 0; i < adjacentNodes.size(); ++i) {
			Node tempNode = adjacentNodes.get(i);

			// Not in closed list
			if (!closedList.contains(tempNode)) {
				possibleNextNodeList.add(tempNode);

				// Not in open list, new added nodes
				if (!openList.contains(tempNode)) {
					tempNode.parentNode = currentNode;
					openList.add(tempNode);

					// Assign the movement cost
					// Diagonal Tiles
					if (isPlacedDiagonally(currentNode, tempNode) ) {
						int diagonalMoveCost = 14;
						tempNode.movementCost += diagonalMoveCost + currentNode.movementCost;
					} else {
						int horiAndVertMoveCost = 10;
						tempNode.movementCost += horiAndVertMoveCost + currentNode.movementCost;
					}
				}

				// For the clicked unwalkable node
				// Still need to be fixed.....
				if (unwalkableIsClicked) {
					if (tempNode.x == goalNode.x && tempNode.y == goalNode.y) {
						goalNode = currentNode;
					}
				}

				// Check its current movement cost to see if the parenting needs to be changed
				// It is diagonally placed beside the current node
				if (isPlacedDiagonally(currentNode, tempNode)) {
					int diagonalCost = 14;
					if (currentNode.movementCost + diagonalCost < tempNode.movementCost) {
						tempNode.movementCost = currentNode.movementCost + diagonalCost;
						tempNode.parentNode = currentNode;
					}

				// Not diagonally placed beside the current node
				} else {
					int horiOrVerCost = 10;
					if (currentNode.movementCost + horiOrVerCost < tempNode.movementCost) {
						tempNode.movementCost = currentNode.movementCost + horiOrVerCost;
						tempNode.parentNode = currentNode;
					}

				}
			}

		}
	}

	private void tempDiagonalNodesRule(Node currentNode) {
		// Might think of other ways not to include diagonal tiles, maybe make the setAllAdjacentNodes() narrows its scope
		// Why add if you can exclude it in the first place???

		// Remove all the diagonal tiles
		if (!includeDiagonal) {
			for (int i = 0; i < adjacentNodes.size(); ++i) {
				Node tempNode = adjacentNodes.get(i);
				if (isPlacedDiagonally(currentNode, tempNode)) {
					adjacentNodes.remove(tempNode);
				}
			}

			// Just for now, if the include diagonal is set to false, after 2 loops, enable it
			if (loopTimes > 2) {
				includeDiagonal = true;
			}
		}
	}

	private Node getClosestNodeFromOpenList(Node currentNode) {
		Node closestNode = null;
		int movementCost = 9999;
		for (int i = 0; i < openList.size(); ++i) {
			Node tempNode = openList.get(i);
			if (tempNode.movementCost + tempNode.heuristic < movementCost) {
				movementCost = tempNode.movementCost + tempNode.heuristic ;
				closestNode = tempNode;
			}
		}
		return closestNode;
	}

	private List<Node> getTrackedParentNodes() {
		int loop = 0;
		Node tempLastNode = closedList.get(closedList.size() - 1);
		shortestPath.clear();

		shortestPath.add(tempLastNode);

		boolean hasFound = false;
		Node parentNode = null;

		while(!hasFound) {
			parentNode = tempLastNode.parentNode;

			if (parentNode != null) {

				// I am still figuring it out why the enemies behave irregularly when I delete the walkable condition
				if (parentNode.walkable)
					shortestPath.add(parentNode);
				tempLastNode = parentNode;

				// Break the operation once the goal node has been found as the parent node
				if (goalNode.x == parentNode.x && goalNode.y == parentNode.y)
					break;

			} else {
				break;
			}

			++loop;
			if (loop > 100) {
				System.out.println("Over 100 loops");
				break;
			}
		}

		return shortestPath;
	}

	private Node getClosestNodeFromPossibleNextNodeList(Node currentNode) {
		int currentCost = 9999;
		Node closestNode = null;
		for (int i = 0; i < possibleNextNodeList.size(); ++i) {
			Node tempNode = possibleNextNodeList.get(i);
			if (tempNode.heuristic + tempNode.movementCost < currentCost) {
				currentCost = tempNode.heuristic + tempNode.movementCost;
				closestNode = tempNode;
			}
		}
		return closestNode;
	}

	// Assumes that the currentNode is just beside the node being checked
	// Otherwise it will not work properly
	// If neither x nor y aligns to each other, that means they are diagonally placed
	private boolean isPlacedDiagonally(Node currentNode, Node node) {
		if ( (currentNode.x != node.x) &&
				(currentNode.y != node.y) ) {
			return true;
		}
		return false;
	}

	// Reconstruct this later, about the exclusion of the diagonal tiles
	private void setAllAdjacentNodesTo(Node currentNode) {
		adjacentNodes.clear();

		int startX = currentNode.x - 1;
		int startY = currentNode.y - 1;

		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {

				// Don't include the current node
				if (y == 1 && x == 1) {
					continue;
				}
				// Node bounds
				if ( (startX + x >= 0 && startX + x < collisionLayer.getWidth()) &&
						(startY + y >= 0 && startY + y < collisionLayer.getHeight()) ) {
					Node adjNode = nodes[startY + y][startX + x];

					if (adjNode.walkable)
						adjacentNodes.add(adjNode);
				}
			}
		}
	}

	public void setHeuristics(Node endTile) {
		for (int y = 0; y < collisionLayer.getHeight(); ++y) {
			for (int x = 0; x < collisionLayer.getWidth(); ++x) {
				nodes[y][x].reset();
				// Collidable
				if (collisionLayer.getCell(x, y) != null) {
					nodes[y][x].set(x, y, getHeuristic(x, y, endTile) * 10, false);
				} else {
					nodes[y][x].set(x, y, getHeuristic(x, y, endTile) * 10, true);
				}
			}
		}

		//...
//		printHeuristics();
	}

	private int getHeuristic(int tileX, int tileY, Node endTile) {
		int hX = Math.abs(tileX - endTile.x);
		int hY = Math.abs(tileY - endTile.y);
		return hX + hY;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	public void setIncludeDiagonal(boolean includeDiagonal) {
		this.includeDiagonal = includeDiagonal;
	}

	public Node getNode(int x, int y) {
		return nodes[y][x];
	}

	public Node getNode(Node node) {
		return nodes[node.y][node.x];
	}

	/*
	// For debugging
	private void printOpenList() {
		for (int i = 0; i < openList.size(); ++i) {
			System.out.println("OpenList: " + openList.get(i).x + ": " + openList.get(i).y);
		}
	}

	private void printPossibleNextNodeList() {
		for (int i = 0; i < possibleNextNodeList.size(); ++i) {
			System.out.println("possibleNextNodeList: " + possibleNextNodeList.get(i).x + ": " + possibleNextNodeList.get(i).y);
		}
	}

	private void printClosedList() {
		for (int i = 0; i < closedList.size(); ++i) {
			System.out.println("ClosedList: " + closedList.get(i).x + ": " + closedList.get(i).y);
		}
	}

	private void printHeuristics() {
		for (int y = collisionLayer.getHeight() - 1; y >= 0; --y) {
			for (int x = 0; x < collisionLayer.getWidth(); ++x) {
				System.out.print(nodes[y][x].heuristic);
				if (nodes[y][x].heuristic < 100) {
					System.out.print("   ");
				} else if (nodes[y][x].heuristic < 1000)
					System.out.print("  ");
				else {
					System.out.print(" ");
				}
			}
			System.out.println("");
		}
	}
	*/

}
