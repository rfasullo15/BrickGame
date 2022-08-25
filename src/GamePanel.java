import java.awt.Graphics;
import javax.swing.JPanel;


/**
 * An instance of this class renders the visual portion of the game. It is passed
 * the current game by the MainFrame and then it tells the game to draw itself.
 * 
 * @author Rosie Fasullo
 *
 */
public class GamePanel extends JPanel{
	
	private Game game;
		
	public GamePanel(Game g){
		super();
		this.game = g;   					//Game passed by the main frame

	}
	
	/**
	 * Game! draw yourself!
	 */
	
	@Override
	public void paint(Graphics g){       
		super.paint(g);
		game.drawOn(g);
		
	}

}
