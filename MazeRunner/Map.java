class Map{
	Grid[][] map;
	int rowNum;
	int colNum;

	public Map(int rowNum, int colNum){
		if(rowNum <= 0 || colNum <= 0){
			System.out.println("Invalid number of rows or columns.");
			System.exit(1);
		}
		map = new Grid[rowNum][colNum];
		this.rowNum = rowNum;
		this.colNum = colNum;
		for(int i = 0; i < rowNum; i++){
			for(int j = 0; j < colNum; j++){
				map[i][j] = new Grid();
			}
		}

		for(int i = 0; i < rowNum; i++){
			for(int j = 0; j < colNum; j++){
				Grid up = i - 1 < 0 ? null : map[i - 1][j];
				Grid down = i + 1 >= rowNum ? null : map[i + 1][j];
				Grid left = j - 1 < 0 ? null : map[i][j - 1];
				Grid right = j + 1 >= colNum ? null : map[i][j + 1];
				map[i][j].setNeighbors(up, down, left, right);
			}
		}
	}

	public void breakWall(Grid g1, Grid g2){
		if(g1.equals(g2.getUp())){
			g1.breakWall(1);
			g2.breakWall(0);
		} else if(g1.equals(g2.getDown())){
			g1.breakWall(0);
			g2.breakWall(1);
		} else if(g1.equals(g2.getLeft())){
			g1.breakWall(3);
			g2.breakWall(2);
		} else if(g1.equals(g2.getRight())){
			g1.breakWall(2);
			g2.breakWall(3);
		}
	}
	
}