//package Phase1;
//package FORKIDS;
import java.awt.*;

import javax.swing.*;
public class MazeCell extends JPanel{
	private boolean[] borders;
	public static final int UP = 0, RIGHT = 1, DOWN=2, LEFT = 3;
	public static final int BLANK=0, VISITED=1, DEAD=2;
	
	private int wallThickness =4;
	private int padding = 2;
	private Stroke str;
	private Color lineColor = Color.BLACK;
	private Color textColor = Color.GREEN;
	private Color defaultBG = Color.GRAY;
	private Color[] colors = {defaultBG, new Color(31,132,204), new Color(242,239,34)};
	
	private int status;
	private int row, col;
	
	public MazeCell(int r, int c){
		super();
		str = new BasicStroke(wallThickness);
		row =r;
		col = c;
		borders = new boolean[4];
		for(int i=0; i<4; i++)
			borders[i] = true;
		this.setBackground(defaultBG);
		status = BLANK;
		
	}

	public int row(){return row;}
	public int col(){return col;}
	
	public void setStatus(int x){
		status = x;
		//this.setBackground(colors[status]);
		this.paint(this.getGraphics());
	}
	public boolean isBlank(){return status==BLANK;}
	public boolean isVisited(){return status==VISITED;}
	public boolean isDead(){return status==DEAD;}
	public int getStatus(){return status;}
	
		
	public boolean isBlockedUp(){return borders[UP];}
	public boolean isBlockedDown(){return borders[DOWN];}
	public boolean isBlockedLeft(){return borders[LEFT];}
	public boolean isBlockedRight(){return borders[RIGHT];}
	public boolean isBlockedDir(int dir){return borders[dir];}

	
	public void clearWallUp(){borders[UP] = false; repaint();}
	public void clearWallDown(){borders[DOWN] = false; repaint();}
	public void clearWallRight(){borders[RIGHT] = false; repaint();}
	public void clearWallLeft(){borders[LEFT] = false; repaint();}
	public void clearWallDir(int dir){ borders[dir] = false; repaint();}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(lineColor);
		((Graphics2D)g).setStroke(str);
		if(borders[UP])
			g.draw3DRect(2,2,this.getWidth(),1,true);
		if(borders[RIGHT])
			g.draw3DRect(this.getWidth()-padding,1,this.getWidth()-padding,this.getHeight(),true);
		if(borders[DOWN])
			g.draw3DRect(2,this.getHeight()-padding,this.getWidth(),this.getHeight()-padding,true);
		if(borders[LEFT])
			g.draw3DRect(2,2,1,this.getHeight(),true);
		
		//display your status		
		Color c = new Color( colors[status].getRed(), colors[status].getGreen(), colors[status].getBlue(), 100);
		g.setColor(c);
		g.fillRect( 0, 0, this.getWidth(), this.getHeight());
		g.setColor(colors[status]);
		//g.fillOval(this.getWidth()/3,this.getHeight()/3,this.getWidth()/3,this.getHeight()/3 );
		//g.draw3DRect(this.getWidth()/3,this.getHeight()/3,this.getWidth()/3,this.getHeight()/3, true);
		
		g.fill3DRect(this.getWidth()/3,this.getHeight()/3,this.getWidth()/2,this.getHeight()/2, true);
		
		//label the row & col of this cell
		//g.setColor(textColor);
		//g.drawString(this.toString(), 5, 15);	
		
	
		
		
		
	}
	

	public String toString(){
		return "("+row+", "+col+")";
	}

}
