class Grid{
	private Grid up;
	private Grid down;
	private Grid left;
	private Grid right;

	private boolean[] hasWall;

	Grid root;
	int level;

	boolean yellow;
	boolean green;
	boolean visited;
	boolean grey;

	public Grid(){
		up = null;
		down = null;
		left = null;
		right = null;

		hasWall = new boolean[]{true, true, true, true};

		root = this;
		level = 1;

		yellow = false;
		green = false;
		visited = false;
		grey = false;
	}

	public boolean hasWall(String s){
		boolean h;
		switch(s) {
			case "up": 		h = hasWall[0];
							break;
			case "down":	h =  hasWall[1];
							break;
			case "left":	h =  hasWall[2];
							break;
			default:		h = hasWall[3];
		}
		return h;
	}


	public void setNeighbors(Grid up, Grid down, Grid left, Grid right){
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}

	public void breakWall(int pos){
		hasWall[pos] = false;
	}

	public Grid getUp(){
		return up;
	}

	public Grid getDown(){
		return down;
	}

	public Grid getLeft(){
		return left;
	}

	public Grid getRight(){
		return right;
	}
}