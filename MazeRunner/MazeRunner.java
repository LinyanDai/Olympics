import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MazeRunner extends JFrame implements ActionListener, KeyListener{
	JPanel jp1, jp2, jp3;
	JButton createMaze, restart, newGame, showSolution;
	JLabel jl1, jl2, congrats;
	JTextField numOfRow, numOfCol;

	Map map;
	Maze m;
	int curR = 0;
	int curC = 0;

	MazeRunner(){
		setTitle("Maze Runner");
		setLocation(500, 500);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();

		jl1 = new JLabel("    Number of Rows: ");
		jl2 = new JLabel("    Number of Columns: ");
		congrats = new JLabel("You made it! Congratulations!  ");

		numOfCol = new JTextField("10");
		numOfRow = new JTextField("10");

		createMaze = new JButton("Create Maze");
		restart = new JButton("Restart");
		newGame = new JButton("New Game");
		showSolution = new JButton("showSolution");

		createMaze.addActionListener(this);
		restart.addActionListener(this);
		newGame.addActionListener(this);
		showSolution.addActionListener(this);

		jp1.setLayout(new GridLayout(3, 2));
		jp1.add(jl1);
		jp1.add(numOfRow);
		jp1.add(jl2);
		jp1.add(numOfCol);
		jp1.add(createMaze);
		jp1.setSize(300,200);

		jp2.setLayout(new BorderLayout());

		jp3.setLayout(new GridLayout(1, 3));
		jp3.add(restart);
		jp3.add(newGame);
		jp3.add(showSolution);

		setLayout(new BorderLayout());
		add(jp1, BorderLayout.CENTER);
		setSize(jp1.getSize());
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void createMaze(){
		int row = 1;
		int col = 1;
		try{
			row = Integer.parseInt(numOfRow.getText().trim());
			if(row <= 1){
				numOfRow.setText("Invalid Input! Must be an integer larger than 1.");
				return;
			}
		} catch (Exception e){
			numOfRow.setText("Invalid Input! Must be an integer larger than one.");
		}

		try{
			col = Integer.parseInt(numOfCol.getText().trim());
			if(col <= 1){
				numOfCol.setText("Invalid Input! Must be an integer.");
				return;
			}
		} catch (Exception e){
			numOfCol.setText("Invalid Input! Must be an integer.");
		}

		map = new Map(row, col);
		SetUnion su = new SetUnion(map);
		su.createMaze();

		m = new Maze(map);
		jp2.add(m, BorderLayout.CENTER);
		jp2.add(jp3, BorderLayout.SOUTH);
		jp2.setSize(m.width + m.pad * 5, m.height + 5 * m.pad);


		remove(jp1);
		add(jp2, BorderLayout.CENTER);

		addKeyListener(this);
		requestFocus();
		validate();
		setSize(jp2.getSize());
	}

	public void newGame(){
		numOfCol = new JTextField("10");
		numOfRow = new JTextField("10");
		remove(jp2);
		add(jp1, BorderLayout.CENTER);
		validate();
		setSize(jp1.getSize());
		//removeKeyListener(this);
	}

	public void restart(){
		for(int i = 0; i < map.rowNum; i++){
			for(int j = 0; j < map.colNum; j++){
				map.map[i][j].yellow = false;
				map.map[i][j].grey = false;
				map.map[i][j].green = false;
				m.update();
			}
		}
		curC = 0;
		curR = 0;
		addKeyListener(this);
		requestFocus();
	}

	public void actionPerformed(ActionEvent e) { 
		String ch = e.getActionCommand().trim();

		switch(ch){
			case "Create Maze": 	createMaze();
									break;
			case "Restart": 		restart();
									break;
			case "New Game": 		newGame();
									break;
			default:				
									//removeKeyListener(this);
									m.dfs();
		}
	}

	@Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(! map.map[curR][curC].hasWall("right")){
            	map.map[curR][curC].green = false;
            	curC ++;
            	map.map[curR][curC].yellow = true;
            	map.map[curR][curC].green = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(! map.map[curR][curC].hasWall("left")){
            	map.map[curR][curC].green = false;
            	curC --;
            	map.map[curR][curC].yellow = true;
            	map.map[curR][curC].green = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if(! map.map[curR][curC].hasWall("up")){
            	map.map[curR][curC].green = false;
            	curR --;
            	map.map[curR][curC].yellow = true;
            	map.map[curR][curC].green = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(! map.map[curR][curC].hasWall("down")){
            	map.map[curR][curC].green = false;
            	curR ++;
            	map.map[curR][curC].yellow = true;
            	map.map[curR][curC].green = true;
            }
        }
        m.update();
        
        if(curR == map.rowNum - 1 && curC == map.colNum - 1){
        	jp2.add(congrats, BorderLayout.EAST);
        	validate();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

	public static void main(String[] args){

        MazeRunner f = new MazeRunner();   

	}
}