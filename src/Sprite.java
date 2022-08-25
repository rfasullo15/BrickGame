import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


/**
 * An instance of this class creates the ball that bounces around onscreen. This class 
 * also creates a Rectangle object that occupies the same space as the ball, in order
 * to make collision detection between the ball and the other Rectangles in the program much easier. 
 * 
 * @author Rosie Fasullo
 *
 */
public class Sprite {
	private double x,y;
	private double dx,dy;
	private Color spriteColor = new Color(165, 12, 157); // magenta
	private int minX, minY, maxX, maxY;
	
	private static final int SIZE= 50;

	
	MainFrame frame;
	Rectangle rect;
	
	public Sprite(int leftX, int rightX, int topY, int bottomY, MainFrame f){
		this.minX= leftX;       //
		this.minY= topY;		   // These four variable define the 
		this.maxX= rightX;     // bounds in which the sprite can travel
		this.maxY= bottomY;   //
		
		this.x= maxX/2-SIZE/2;
		this.y= maxY/2- SIZE/2;
		
		this.dx=0;                // These two variables define
		this.dy=0;                // the velocity of the sprite
		
		rect = new Rectangle((int)x,(int)y,SIZE, SIZE);
		this.frame = f;
	}
	
	public Rectangle getRect(){
		return rect;
	}
	
	public int getSize(){
		return SIZE;
	}

	public double getX() {
		return x;
	}
	
	/**
	 * This method sets the x value of the ball, and then sets the x value of the rectangle to
	 * the same value of the ball, to ensure they are traveling in the same space
	 */

	public void setX(double x) {
		this.x = (int) x;
		rect.x= (int) this.x;
		
	}

	public double getY() {
		return y;
	}
	
	/**
	 * This method sets the y value of the ball, and then sets the y value of the rectangle to
	 * the same value of the ball, to ensure they are traveling in the same space
	 */

	public void setY(double y) {
		this.y = (int) y;
		rect.y= (int) this.y;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
		
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public Color getSpriteColor() {
		return spriteColor;
	}

	public void setSpriteColor(Color spriteColor) {
		this.spriteColor = spriteColor;
	}
	
	
	
	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	/**
	 * Using the Graphics2D object passed by the game, 
	 * the ball renders both itself, and the rectangle, which is colored to 
	 * blend into the background so it is not seen by the user
	 */
	
	public void drawOn(Graphics2D g){
		g.setColor(frame.getBGColor());			//background color of the frame
		g.fill(rect);
		g.setColor(spriteColor);
		g.fillOval((int)x, (int)y, SIZE, SIZE);
		
	}
	
	/**
	 *  The method update changes the value of the ball and the rectangle and then 
	 *  checks to see if they have moved outside the bounds of the frame. If they have,
	 *  their position is reset to be the distance that they would have traveled outside of the 
	 *  frame inside the frame, to make it appear as though the ball had "bounced"
	 */
	public void update(){
		x+=(int)dx;
		y+=(int)dy;
		rect.x+= (int)dx;
		rect.y+= (int)dy;
	
		/*
		 * Check if the ball is traveling outside the right bound
		 */
		if(x+3*SIZE>maxX){            			  // Something weird is happening here, maybe a buffering issue?
			int extra= (int)x +3*SIZE -maxX;    
			x=maxX-extra;
			rect.x=(int) this.x;
			dx*=-1;
		}
		
		/*
		 * Check if the ball is traveling outside the left bound
		 */
		
		if(x<minX){
			int extra= (int) x +minX;
			x= minX-extra;
			rect.x=(int) this.x;
			dx*=-1;
		}
		
		/*
		 * Check if the ball is traveling outside the bottom bound.
		 * Secretly, it should never get to this point, because the game resets the loss before
		 * the ball gets that far
		 */
		if(y+SIZE>maxY){
			int extra = (int)y+SIZE - maxY;
			y= maxY - extra;
			rect.y= (int) this.y;
			dy*=-1;
		}
		
		/*
		 * Checks to see if the ball is traveling outside the upper bound
		 */
		
		if(y<minY){
			int extra = (int)y + minY;
			y=minY-extra;
			rect.y= (int) this.y;
			dy*=-1;
		}
		
		
		
	}
	
	/**
	 *  accelerate the ball by multiplying the velocities by some value 'factor', increasing
	 *  the amount that is added to the x and y values every time
	 */
	
	public void accelerate(double factor){
		dx*=factor;
		dy*=factor;
		
	}
		

}
