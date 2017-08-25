import java.util.Random;

public class SetUnion{
	Map map;
	int numOfSet;

	public SetUnion(Map map){
		this.map = map;
		numOfSet = map.colNum * map.rowNum;
	}

	public void add(Grid g1, Grid g2){
		Grid root1 = find(g1);
		Grid root2 = find(g2);

		if(!root1.equals(root2)){
			map.breakWall(g1, g2);
			if(root1.level > root2.level){
				root2.root = root1;
				root1.level = Math.max(root1.level, root2.level + 1);
			} else{
				root1.root = root2;
				root2.level = Math.max(root2.level, root1.level + 1);
			}
			numOfSet --;
		}
	}

    public Grid find(Grid g){
    	Grid cur = g;
    	while(! g.equals(g.root)){
    		g = g.root;
    	}
    	while(! cur.equals(g)){
    		Grid temp = cur.root;
    		cur.root = g;
    		cur.level = 1;
    		cur = temp;
    	}
    	return cur;
    }

    public Grid randomGrid(){
    	Random r = new Random();
    	int x = r.nextInt(map.rowNum);
    	int y = r.nextInt(map.rowNum);
    	return map.map[x][y];
    }

    public Grid randomNeighbor(Grid g){
    	Grid nei = null;

    	while (nei == null){
    		Random r = new Random();
    		int x = r.nextInt(4);
    		switch(x){
    			case 0: 	nei = g.getRight();
    						break;
    			case 1: 	nei = g.getLeft();
    						break;
    			case 2: 	nei = g.getDown();
    						break;
    			default: 	nei = g.getUp();
    		}
    	}
    	
    	return nei;
    }

    public void createMaze(){
    	while(numOfSet > 1){
    		Grid g1 = randomGrid();
    		Grid g2 = randomNeighbor(g1);
    		add(g1, g2);
    	}
    }
}