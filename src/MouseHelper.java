import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.Timer;


/**
 * An instance of this class creates the mouse motion listener and the mouse listener that
 * are attached to the game panel. The mouse helper class is responsible for starting the timer
 * when the mouse is clicked, restarting the game if it is paused when the mouse is clicked, and 
 * moving the paddle with the location of the mouse.
 * 
 * @author Rosie Fasullo
 *
 */
public class MouseHelper implements MouseMotionListener, MouseListener {

	private Game game;
	private Timer t;
	private Random rand;

	public MouseHelper(Game g){

		this.game=g;
		rand = new Random();
	}

	public void giveTimer(Timer time){
		this.t= time;
	}

	/**
	 * When the mouse moves, the paddle is moved to the mouse's x coordinate
	 */

	@Override
	public void mouseMoved(MouseEvent e) {
		game.moveTo(e.getX() - game.getPaddle().width/2);                      //The paddle is moved from the center, instead of the leftmost edge

	}

	/**
	 *  mouseClicked is used to start the game when it is at a state of standstill. When the game is paused,
	 *  the algorithm sets the velocity of the ball to a non zero number. When the game is about to begin, the
	 *  algorithm sets the velocity of the ball to 3 in both dy and dx. 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(game.getPause()==true){
			game.getBall().setDx(rand.nextInt(5) +2);                    //The ball has a maximum velocity in either
			game.getBall().setDy((rand.nextInt(5) + 2));               // direction of 5 and a minimum velocity of 1
			game.setPause(false);
		} else {
			game.getBall().setDx(3.0);
			game.getBall().setDy(3.0);
			game.setStart(false);
		}


	}

	//Unused Methods//

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {	
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
