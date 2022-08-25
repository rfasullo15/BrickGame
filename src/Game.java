import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

/**
 *  An instance of this class renders the data centered portions of a breakout game. This class contains the paddle, the ball 
 *  and the bricks. It manages the game state, keeping track of how many times the player has lost in this instance of the game, 
 *  and keeps track of which bricks have been hit and which bricks have not been hit. The bricks are kept in an array, and when they are 
 *  hit the handles are given a null value, so that the next time the paint algorithm runs, the null brick will not get painted.
 *  
 * @author Rosie Fasullo
 *
 */
public class Game {

	private Rectangle paddle;   

	private Sprite ball;
	private double vVelocity;
	private double hVelocity;

	private Rectangle[] bricks;
	private int numOfBricks=45;
	private int bWidth=110;
	private int bHeight=60;
	private double acceleration = 1.05;

	private int paddleHeight= 100;				// Dimensions of the
	private int paddleWidth = 400; 			//  paddle
	private int paddleX, paddleY;

	private int frameWidth, frameHeight;

	private int ballCount;
	private boolean isGameOver;

	private boolean pause;
	private boolean start;
	MainFrame main;

	private Color brickColor;                 // light teal 
	private Color paddleColor = new Color (0, 128, 0);                // dark green 

	private Color stoppedBackground = new Color(65,65,65,150);   //translucent grey

	/**
	 * 
	 * The constructor creates the paddle, the ball and all of the bricks.
	 * The paddle is a Rectangle object, the ball is a Sprite object, and the 
	 * bricks are Rectangle objects that are kept in an array.
	 * 
	 */

	public Game(int fWidth, int fHeight, MainFrame frame){
		
		main= frame;
		frameWidth=fWidth;
		frameHeight= fHeight;



		paddleX= frameWidth/2 - paddleWidth/2;      // The paddle is set to be in the middle
		paddleY= (int) (frameHeight*.75);                // of the frame, 3/4 of the way down

		/*
		 * creating the major pieces in the game. First the paddle and the ball...
		 */
		paddle= new Rectangle(paddleX, paddleY, paddleWidth, paddleHeight);
		ball = new Sprite(0, fWidth, 0, fHeight, frame);          //The ball has a maximum movement area of the frame

		/*
		 * ...and then the array that holds the bricks
		 */
		bricks = new Rectangle[numOfBricks];      // The array that will hold the bricks

		int brickX= 36;
		int brickY= 50;
		int oneThird= numOfBricks/3;
		int twoThirds= 2*numOfBricks/3;
		int spacing = 28;							//How far the bricks are spaced apart both vertically and horizontally 

		/*
		 * Defining the spacing for each brick
		 */

		for(int k = 0; k<oneThird; k++){
			bricks[k]= new Rectangle( brickX+(k*(bWidth + spacing)), brickY, bWidth, bHeight);                                     // First row of bricks		
		}
		for(int k= oneThird; k<twoThirds; k++){
			bricks[k]= new Rectangle( brickX+( (k- oneThird) * (bWidth + spacing)), (brickY+ bHeight + spacing), bWidth, bHeight);            // Second row of bricks  
		}																																								  
		for(int k= twoThirds; k<numOfBricks; k++){
			bricks[k]= new Rectangle( brickX+( (k-twoThirds) * (bWidth + spacing)), brickY + 2* (bHeight + spacing), bWidth, bHeight);     // Thrid row of bricks
		}

		/*
		 * General game state statistics
		 */
		ballCount = 3;                                // the game starts with 3 balls, allowing for 3 loses 
		this.isGameOver=false;     
		this.pause = false;
		this.start = true;



	} // End of constructor

	public Rectangle getPaddle(){
		return paddle;
	}

	public boolean getPause(){
		return pause;
	}

	public int getPaddleX(){
		return paddleX;
	}

	public boolean getIsGameOver(){
		return isGameOver;
	}

	public Sprite getBall(){
		return ball;
	}

	/**
	 * This allows you to change the size of the paddle
	 */

	public void setPaddle(int width, int height){
		paddleWidth=width;         
		paddleHeight=height;
	}



	public void setStart(boolean s){
		start= s;
	}
	
	public void setPause(boolean p){
		pause = p;
	}

	/**
	 * The game resets everything to the positions they had in the beginning, putting the
	 * ball in the center of the screen, and the paddle in the middle, 3/4 of the way down. 
	 * The ball is given a random downward velocity. 
	 * 
	 */
	public void reset(){

		pause=true;

		paddleX= frameWidth/2 - paddleWidth/2;
		paddleY= (int) (frameHeight*.75);

		ball.setDx(0);
		ball.setDy(0);

		/*
		 * Putting the ball in the center of the frame
		 */
		ball.setX(frameWidth/2 - ball.getSize()/2);   
		ball.setY(frameHeight/2 - ball.getSize()/2);

	}

	/**
	 * The paddles x position is changed
	 */

	public void moveTo(int x){
		paddle.setLocation(x, paddleY);
	}





	/**
	 * This method calls the balls update method, and then checks to see if the ball 
	 * has traveled passed the paddle. If it has, the ball count is decremented. If the 
	 * ball count then equals 0, the game is over.
	 */
	public void moveBall(){

		ball.update();

		/*
		 * Checking for collisions between the bricks and the ball
		 */
		for(int k=0; k<numOfBricks; k++) {
			if(bricks[k]!= null){
				if(bricks[k].intersects(ball.getRect())){    				
					bricks[k]=null;										//If they are hit then they are remove from the array
					ball.accelerate(acceleration);
					ball.setDy(ball.getDy()*-1);							//and the vertical velocity is reversed and accelerate
				}
			}
		}

		/*
		 * Checking for collisions between the paddle and the ball
		 */

		if(paddle.intersects(ball.getRect())){	

			ball.accelerate(acceleration);				// If a collision occurs, reverse the vertical velocity and accelerate
			ball.setDy(ball.getDy() *-1);     				
		}

		/*
		 * Checking for a game loss
		 */
		if(!(paddle.intersects(ball.getRect())) && (ball.getY() + ball.getSize()> paddle.y + 50) && (ball.getY()>0)){ 					//This checks if the bottom of the ball has passed the top of the paddle
			ballCount--;
			if(ballCount > 0){
				this.reset();
			} else {
				isGameOver=true;
			}
		}
	} // End of update()
	

	/**
	 * The drawOn algorithm draws a white paddle, and tells the ball to call
	 * its own drawOn algorithm and draw itself.
	 */

	public void drawOn(Graphics g){
		Graphics2D g2 = (Graphics2D) g; 
		if(start==true){                 // Queues the game to render the start screen only when the game starts up for the first time
			startScreen(g2);
		} else {

			/*
			 * Draw the paddle
			 */

			g2.setColor(paddleColor);
			g2.fill(paddle);

			/*
			 * Draw the ball
			 */
			ball.drawOn(g2);    //passes Graphics2D object

			/*
			 * Draw the bricks
			 */
			

			int emptyBrickCount=0; 					// Variable  that will keep track of how many bricks are null

			for(int k = 0; k<numOfBricks; k++){
				if(bricks[k] != null){					// Check to make sure the bricks isnt null before attempting to draw
					brickColor = new Color (156-k*3, 233-k*3, 241-k*3); 				//The bricks get progressively darker
					g2.setColor(brickColor);
					g2.fill(bricks[k]);
					g.setColor(Color.DARK_GRAY);
					g.drawRect(bricks[k].x, bricks[k].y, bricks[k].width, bricks[k].height);
				} else if(bricks[k] == null){
					emptyBrickCount++; 
				}
			}

			g2.setColor(ball.getSpriteColor());
			for (int k=0; k<ballCount; k++){									// Rendering the balls that show how many tries the user has left
				g2.fillOval((int)(frameWidth*.89 + k*(ball.getSize() + 5)), (int)(frameHeight*.9), ball.getSize(), ball.getSize());
			}
			
			g.setColor(Color.white);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 50));
			g.drawString("Score: " + emptyBrickCount, (int)(frameWidth*.02), (int) (frameHeight*.925));
			g.drawString("Lives: ", (int) (frameWidth*.82), (int)(frameHeight*.925));
			

			if(pause==true){
				pauseScreen(g2);
			}

			if(emptyBrickCount==numOfBricks){     // If the number of null bricks is equal to the number of total bricks, the game is over
				isGameOver=true;
				youWin(g2);
			}

			if(ballCount<=0){
				youLose(g2);
			}
		} 
		
	}// end of drawOn


	private void youWin(Graphics g){
		g.setColor(stoppedBackground);
		g.fillRect(0, 0, frameWidth, frameHeight);
		
		g.setColor(Color.WHITE);

		g.setFont(new Font("Times New Roman", Font.PLAIN, 100));
		g.drawString("Congratulations! You Won!", frameWidth/2-560, frameHeight/2-100);
		g.drawString("Thanks for Playing! :)", frameWidth/2-460, frameHeight/2+50);
	}

	private void youLose(Graphics g){
		g.setColor(stoppedBackground);
		g.fillRect(0, 0, frameWidth, frameHeight);
		
		g.setColor(Color.WHITE);

		g.setFont(new Font("Times New Roman", Font.PLAIN, 100));
		g.drawString("Oh Bummer... You Lost... :(", frameWidth/2-560, frameHeight/2-100);
		g.drawString("Better Luck Next Time!", frameWidth/2-460, frameHeight/2+50);
	}

	private void startScreen(Graphics g){
		g.setColor(stoppedBackground);
		g.fillRect(0, 0, frameWidth, frameHeight);
		g.setColor(Color.WHITE);

		g.setFont(new Font("Times New Roman", Font.PLAIN, 100));
		g.drawString("Break-out!", frameWidth/2-210, frameHeight/2-100);
		g.drawString("Please Click to Continue", frameWidth/2-510, frameHeight/2+50);
	}

	private void pauseScreen(Graphics g){
		g.setColor(stoppedBackground);
		g.fillRect(frameWidth*1/6, frameWidth/8, frameWidth*2/3, frameWidth/2);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 100));
		g.drawString("Please Click to Continue", frameWidth/2-495, frameHeight/2-80);
	}



}
