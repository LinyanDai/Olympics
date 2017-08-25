import java.awt.*;  
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList; 

class Maze extends Canvas{
    Map map;
    int width;
    int height;
    int pad = 20;
    int gridWidth = 10;
    Grid end;

    public Maze(Map map){
        this.map = map;
        this.width = map.rowNum * gridWidth;
        this.height = map.colNum * gridWidth;
        end =  map.map[map.rowNum - 1][map.colNum - 1];
    }

	public void paint(Graphics g) {  
		g.setColor(Color.BLACK);
		g.drawLine(pad, pad, pad, pad + height);
		g.drawLine(pad, pad, pad + width, pad);

		for(int i = 0; i < map.rowNum; i++){
			for(int j = 0; j < map.colNum; j++){
				g.setColor(Color.BLACK);
				if(map.map[i][j].hasWall("down")){
					g.drawLine(pad + j * gridWidth, pad + (i + 1) * gridWidth, pad + (j + 1) * gridWidth , pad + (i + 1) * gridWidth);
				}
				if(map.map[i][j].hasWall("right")){
                    g.drawLine(pad + (j + 1) * gridWidth, pad + i * gridWidth, pad + (j + 1) * gridWidth, pad + (i + 1) * gridWidth);
				}
				if(map.map[i][j].green){
					g.setColor(Color.GREEN);
					g.fillRect(pad + j * gridWidth + 1, pad + i * gridWidth + 1, gridWidth - 2, gridWidth - 2);
				}else if(map.map[i][j].yellow){
					g.setColor(Color.YELLOW);
					g.fillRect(pad + j * gridWidth + 1, pad + i * gridWidth + 1, gridWidth - 2, gridWidth - 2);
				}else if(map.map[i][j].grey){
					g.setColor(Color.GRAY);
					g.fillRect(pad + j * gridWidth + 1, pad + i * gridWidth + 1, gridWidth - 2, gridWidth - 2);
				}
			}
		}
		g.setColor(Color.RED);
		g.fillOval(pad, pad, gridWidth, gridWidth);
		g.setColor(Color.GREEN);
		g.fillOval(pad + (map.colNum - 1) * gridWidth , pad + (map.rowNum - 1) * gridWidth, gridWidth, gridWidth);
	}

	public void traceBack(){
		Grid g = end;
		while(g != null){
			g.yellow = false;
			g.grey = true;

			update();
			g = g.root;
		}
	}

	public void update(){
		// try{
		// 	Thread.sleep(100);
		// } catch (Exception e){
		// 	System.out.println("Error");
		// }
		repaint();
	}

	public void bfs(){
		Queue<Grid> q = new LinkedList<Grid>();
		map.map[0][0].root = null;
		map.map[0][0].visited = true;
		q.add(map.map[0][0]);

		while(!q.isEmpty()){
			Grid cur = q.poll();
			cur.yellow = true;
			update();

			if(cur.equals(end)){
				traceBack();
				break;
			}

			if(!cur.hasWall("up") && !cur.getUp().visited){
				Grid x = cur.getUp();
				x.root = cur;
				x.visited = true;
				q.add(x);
			}
			if(!cur.hasWall("right") && !cur.getRight().visited){
				Grid x = cur.getRight();
				x.root = cur;
				x.visited = true;
				q.add(x);
			}
			if(!cur.hasWall("down") && !cur.getDown().visited){
				Grid x = cur.getDown();
				x.root = cur;
				x.visited = true;
				q.add(x);
			}
			if(!cur.hasWall("left") && !cur.getLeft().visited){
				Grid x = cur.getLeft();
				x.root = cur;
				x.visited = true;
				q.add(x);
			}

		}
	}

	public void dfs(){
		Stack<Grid> s = new Stack<Grid>();
		map.map[0][0].root = null;
		map.map[0][0].visited = true;
		s.add(map.map[0][0]);

		while(!s.isEmpty()){
			Grid cur = s.pop();
			//cur.yellow = true;
			update();

			if(cur.equals(end)){
				traceBack();
				break;
			}

			if(!cur.hasWall("up") && !cur.getUp().visited){
				Grid x = cur.getUp();
				x.root = cur;
				x.visited = true;
				s.add(x);
			}
			if(!cur.hasWall("right") && !cur.getRight().visited){
				Grid x = cur.getRight();
				x.root = cur;
				x.visited = true;
				s.add(x);
			}
			if(!cur.hasWall("down") && !cur.getDown().visited){
				Grid x = cur.getDown();
				x.root = cur;
				x.visited = true;
				s.add(x);
			}
			if(!cur.hasWall("left") && !cur.getLeft().visited){
				Grid x = cur.getLeft();
				x.root = cur;
				x.visited = true;
				s.add(x);
			}

		}
	}	
}