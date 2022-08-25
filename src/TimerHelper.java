import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 *  An instance of this class creates the action listener attached to the timer object. This class is responsible for telling the 
 *  game to repaint the game. It also checks to see if the game has ended yet, if it has, the timer stops.
 *  
 * @author Rosie Fasullo
 *
 */


public class TimerHelper implements ActionListener {
	
	private Game g;
	private Timer t;
	private MainFrame frame;
	
	public TimerHelper(MainFrame main, Game game){
		
		this.g= game;
		this.frame= main;
	}
	
	public void giveTimer(Timer time){				//The timer must be injected after the action listener is created, because the timer is not instatiated until after the tHelper is created
		this.t= time;
	}
	
	/**
	 *  The actionPerformed method checks to see if the game has ended, and tells the frame to repaint 
	 *  every time the timer goes off
	 */

	@Override
	public void actionPerformed(ActionEvent e) {		
		if(g.getIsGameOver()==true){
			t.stop();
		}
		g.moveBall();
		frame.repaint();
		
		
		
		
	}

	

}
