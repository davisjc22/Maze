//package FORKIDS;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


import javax.swing.*;
public class MazeFrame extends JFrame implements ActionListener{
	private static final int UP = 0, RIGHT = 1, DOWN=2, LEFT = 3;
	private static int ROWS, COLS;
	
	public static final int BLANK=0, VISITED=1, DEAD=2;
	
	private JPanel controls, maze;
	private JButton Solve, Reset;
	
	
	//*** you will need a 2DArray of MazeCells****
	MazeCell[][] cells;
	CellStack stack = new CellStack();
	CellStack carve = new CellStack();
	
	/**Constructor**/
	public MazeFrame(){
		super("MAZE");		
				
		setUpControlPanel();//make the buttons & put them in the north		
		instantiateCells();//give birth to all the mazeCells & get them onto the screen		
		//carveALameMaze(); this will knock down walls to create a maze
		carveARandomMaze();
		
		//finishing touches
		this.setSize(ROWS*20,COLS*20);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(COLS*40,ROWS*40);
		this.setVisible(true);
	}//end constructor
	
	
	/* 1111111111111111    PHASE 1 STUFF    1111111111111111111111 */
	private void instantiateCells(){
		ROWS = Integer.parseInt(JOptionPane.showInputDialog(null, "Rows:"));//asks the user how many rows should be in the maze
		COLS = Integer.parseInt(JOptionPane.showInputDialog(null, "Cols:"));//asks the user how many cols should be in the maze
		
		
		maze = new JPanel();
		maze.setBackground(Color.WHITE);
		maze.setLayout(new GridLayout(ROWS, COLS));		
		//construct your 2D Array & instantiate EACH MazeCell
		//  be sure to add each MazeCell to the panel
		//            * call maze.add( ?the cell ? );
		/**~~~~  WRITE CODE HERE ~~~~**/
		cells = new MazeCell[ROWS][COLS];
		for(int r = 0; r<ROWS; r++)
			for(int c = 0; c<COLS; c++){
				cells[r][c] = new MazeCell(r,c);
				maze.add(cells[r][c]);
				
			}
		
		/**~~~~  *************** ~~~~**/
		//put the maze on the screen
		this.add(maze, BorderLayout.CENTER);
	}
	
	private void carveALameMaze(){//"hard code" a maze
		cells[8][0].clearWallLeft();
		cells[8][0].clearWallRight();
		cells[8][1].clearWallLeft();
		cells[8][1].clearWallRight();
		cells[8][2].clearWallLeft();
		cells[8][2].clearWallUp();
		cells[7][2].clearWallDown();
		cells[7][2].clearWallUp();
		cells[6][2].clearWallDown();
		cells[6][2].clearWallLeft();
		cells[6][1].clearWallRight();
		for(int i = 6; i>=2; i--){
			cells[i][1].clearWallUp();
			cells[i-1][1].clearWallDown();
		}
		for(int i = 6; i>=2; i--){
			cells[6][i].clearWallRight();
			cells[6][i+1].clearWallLeft();
		}
		for(int i = 6; i<=7; i++){
			cells[i][7].clearWallDown();
			cells[i+1][7].clearWallUp();
		}
		for(int i = 7; i>=5; i--){
			cells[8][i].clearWallLeft();
			cells[8][i-1].clearWallRight();
		}
		cells[6][6].clearWallUp();
		cells[5][6].clearWallDown();
		cells[5][6].clearWallUp();
		cells[4][6].clearWallDown();
		for(int i = 6; i>=5; i--){
			cells[4][i].clearWallLeft();
			cells[4][i-1].clearWallRight();
		}
		for(int i = 3; i<=8; i++){
			cells[1][i].clearWallLeft();
			cells[1][i-1].clearWallRight();
		}
		cells[1][8].clearWallDown();
		cells[2][8].clearWallUp();
		cells[2][8].clearWallDown();
		cells[3][8].clearWallUp();
		cells[3][8].clearWallLeft();
		cells[3][7].clearWallRight();
		cells[3][8].clearWallRight();
		cells[3][9].clearWallLeft();
		cells[3][9].clearWallRight();
		
		cells[4][4].clearWallUp();
		cells[3][4].clearWallDown();
		cells[3][4].clearWallUp();
		cells[2][4].clearWallDown();
		cells[2][4].clearWallUp();
		cells[1][4].clearWallDown();
		
		cells[6][2].clearWallUp();
		cells[5][2].clearWallDown();
		cells[5][2].clearWallUp();
		
		
		cells[4][2].clearWallDown();
		cells[4][2].clearWallUp();
		cells[3][2].clearWallDown();
		
		
		stack.push(cells[8][0]);
		cells[8][0].setStatus(VISITED);
		
	}
	
	/** 2222222222222222222    PHASE 2 STUFF   22222222222222222222222222 **/
	public boolean isInBounds(int r, int c){//if the row and col is real
		if(r<ROWS&&r>=0)
			if(c<COLS&&c>=0)
				return true;
		return false;
	}
	
	public boolean SolveStep(){//takes the next step in solving the maze
		if(stack.peek().isBlockedUp()==false){//if there isn't a wall
			if(getNeighbor(stack.peek(), UP).getStatus()==BLANK){//if its neighbor hasn't been visited and isn't dead
				stack.push(getNeighbor(stack.peek(), UP));//it'll move to the next square
				stack.peek().setStatus(VISITED);//it'll become visited
				return true;//breaks
			}
		}
		if(stack.peek().isBlockedRight()==false){
			if(getNeighbor(stack.peek(), RIGHT).getStatus()==BLANK){
				stack.push(getNeighbor(stack.peek(), RIGHT));
				stack.peek().setStatus(VISITED);
				return true;
			}
		}
		if(stack.peek().isBlockedDown()==false){
			if(getNeighbor(stack.peek(), DOWN).getStatus()==BLANK){
				stack.push(getNeighbor(stack.peek(), DOWN));
				stack.peek().setStatus(VISITED);
				return true;
			}
		}
		if(stack.peek().isBlockedLeft()== false){
			
			if(getNeighbor(stack.peek(), LEFT).getStatus()==BLANK){
				stack.push(getNeighbor(stack.peek(), LEFT));
				stack.peek().setStatus(VISITED);
				return true;
			}
		}
		//if it's a dead end
		if(stack.peek().col()==COLS-1)
			return true;
		stack.peek().setStatus(DEAD);//sets itself to dead
		stack.pop();//backs up and tries again
		
		return false;
	}
	
	
	/* 33333333333333333333    Phase 3 stuff    3333333333333333333333333 */
	
	public MazeCell getNeighbor(MazeCell mc, int dir){
		if(dir == UP){
			if(isInBounds((mc.row()-1), mc.col()))//makes sure it's a real neighbor
				return cells[mc.row()-1][mc.col()];
		}
		else if(dir == RIGHT){
			if(isInBounds((mc.row()), mc.col()+1))
				return cells[mc.row()][mc.col()+1];
		}
		else if(dir == DOWN){
			if(isInBounds((mc.row()+1), mc.col()))
				return cells[mc.row()+1][mc.col()];
		}
		else if(dir == LEFT){
			if(isInBounds((mc.row()), mc.col()-1))
				return cells[mc.row()][mc.col()-1];
		}
		return null;
	}
	public ArrayList<MazeCell> blankNeighbors(MazeCell mc){
		ArrayList<MazeCell> blank = new ArrayList();
		for(int i = 0; i<4; i++){//checks in every direction
			MazeCell neigh = getNeighbor(mc, i);
			if(neigh!=null)//if he has a neighbor
				if(neigh.getStatus()==BLANK)//if it isn't visited or dead
					blank.add(getNeighbor(mc, i));
		}
		return blank;
	}
	
	public int getDirectionFrom(MazeCell orig, MazeCell dest){
		int dir = -1;
		for(int i=0; i<4; i++)//checks all directions
			if(getNeighbor(orig, i)==dest)
				dir = i;
		return dir;
				
	}
	public void stepCarve(){
		//System.out.println(getDirectionFrom(cells[2][3], cells[2][2]));
		
		ArrayList<MazeCell> choices = blankNeighbors(carve.peek());//takes in all blank neighbors
		if(choices.size()!=0){//if there are blank neighbors
			MazeCell next = choices.get((int)(choices.size()*Math.random()));//picks a random blank neighbor
			bustWall(carve.peek(), next);//breaks down the wall
			carve.push(next);//adds it to the stack
			next.setStatus(VISITED);
			return;
		}
		else{//if it's a dead end
			carve.peek().setStatus(DEAD);
			carve.pop();//backs up
			
		}
		
	}
	
	public void bustWall(MazeCell orig, MazeCell dest){//breaks down both walls instead of one at a time
		if(getDirectionFrom(orig, dest)==UP){
			orig.clearWallUp();
			dest.clearWallDown();
		}
		if(getDirectionFrom(orig, dest)==RIGHT){
			orig.clearWallRight();
			dest.clearWallLeft();
		}
		if(getDirectionFrom(orig, dest)==DOWN){
			orig.clearWallDown();
			dest.clearWallUp();
		}
		if(getDirectionFrom(orig, dest)==LEFT){
			orig.clearWallLeft();
			dest.clearWallRight();
		}
	}
	
	
	
	public void carveARandomMaze(){
		for(int r = 0; r<ROWS; r++)
			cells[r][0].setStatus(DEAD);//Sets right-most column to dead(makes sure it won't make new entrances/exits randomly)
		for(int r = 0; r<ROWS; r++)
			cells[r][COLS-1].setStatus(DEAD);//Sets right-most column to dead
		MazeCell start = cells[(int)(Math.random()*ROWS)][0];
		start.clearWallRight();
		System.out.println(start + " : "+getNeighbor(start,RIGHT));
		getNeighbor(start, RIGHT).clearWallLeft();
		MazeCell finish = cells[(int)(Math.random()*ROWS)][COLS-1];
		finish.clearWallLeft();
		//getNeighbor(finish, LEFT).clearWallRight();
		finish.setStatus(BLANK);
		start.setStatus(VISITED);
		carve.push(start);//adds it to the stack
		
		while(carve.size!=0){
			stepCarve();     //carves out the maze
		}
		for(int r=0; r<ROWS; r++){
			for(int c=0; c<COLS; c++)
				cells[r][c].setStatus(BLANK);//
		}
		stack.push(start);//gives the stack the first step of the maze
		start.setStatus(VISITED);
			
			
	}
	
	//4444444444444444444  PHASE 4 STUFF 4444444444444444444444444444
	private void ResetMaze(){
		
	}
	
	
	//This gets called any time that you press a button
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==Solve){
			while(stack.peek().col()!=COLS-1){
				pause(100);//pauses it to make it look animated
				this.SolveStep();//
			}
			return;
		}
		if(e.getSource()==Reset){
			this.dispose();//trashes the old maze
			new MazeFrame();//makes a new maze
		}
		
		
	}//end action performed
	
	
	private void setUpControlPanel(){
		controls = new JPanel();
		Solve = new JButton("Solve");
		Solve.addActionListener(this);
		controls.add(Solve);
		
		Reset = new JButton("Reset");//puts text on button
		Reset.addActionListener(this);
		controls.add(Reset);
		
		
					
		this.add(controls, BorderLayout.NORTH);
	}
	
	public static void main(String[] args){	new MazeFrame();}
	
	public void pause(int mili){
		try{
			Thread.sleep(mili);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

}//end class
