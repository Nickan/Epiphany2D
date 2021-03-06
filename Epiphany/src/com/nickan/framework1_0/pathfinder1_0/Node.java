package com.nickan.framework1_0.pathfinder1_0;

public class Node {
	public int x;
	public int y;
	public int heuristic;
	int movementCost;
	Node parentNode;
	public boolean walkable = true;
	
	public Node() { }
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		heuristic = 0;
		movementCost = 0;
		parentNode = null;
	}
	
	public Node(int x, int y, int heuristic) {
		this.x = x;
		this.y = y;
		this.heuristic = heuristic;
		movementCost = 0;
		parentNode = null;
	}
	
	public void reset() {
		parentNode = null;
		movementCost = 0;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Node node) {
		this.x = node.x;
		this.y = node.y;
		this.heuristic = node.heuristic;
		this.movementCost = node.movementCost;
		this.parentNode = node.parentNode;
	}
	
	public void set(int x, int y, int heuristic, boolean walkable) {
		this.x = x;
		this.y = y;
		this.heuristic = heuristic;
		this.walkable = walkable;
	}
	
	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}
	
	public boolean isSame(Node node) {
		if (x == node.x && y == node.y)
			return true;
		return false;
	}
	
	public boolean isSame(int x, int y) {
		if (this.x == x && this.y == y)
			return true;
		return false;
	}
	
	public boolean isSame(float x, float y) {
		if (this.x == (int) x && this.y == (int) y)
			return true;
		return false;
	}
	
	public boolean isAdjacentTo(Node node) {
		if ( (this.x - 1 <= node.x && this.x + 1 >= node.x) && (this.y - 1 <= node.y && this.y + 1 >= node.y) ) 
			return true;
		return false;
	}
	
	//...
	public void print() {
		System.out.print(x + ": " + y);
		System.out.println();
	}
}