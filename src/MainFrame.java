import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * An instance of this class renders a breakout game. The refresh rate of the game
 * is currently set to 1000. The size of the game is a 2000x1600. The MainFrame 
 * is the object that holds all of the major parts of the game and injects the dependncies
 * into the other classes in the program.
 * 
 * @author Rosie Fasullo
 *
 */
public class MainFrame extends JFrame {
	
	private Timer t;
	private GamePanel gPanel;
	private TimerHelper tHelper;
	private MouseHelper mHelper;
	private Game g;

	private static final int T_INTERVAL= 15;        // The timer is set to go off every second 
	
	private static final int WIDTH= 2000;               // These are the dimension of 
	private static final int HEIGHT= 1600;              //  the frame and Game Panel
	private static final Color BG_COLOR= new Color(3,13,126); 			//dark blue
	
	
	public MainFrame(){
		

		g= new Game(WIDTH, HEIGHT, this);
		
		
		tHelper = new TimerHelper(this, g);
		mHelper = new MouseHelper(g);
		
		t= new Timer(T_INTERVAL, tHelper);
		tHelper.giveTimer(t);
		mHelper.giveTimer(t);
		
		
		
		
		/*
		 * Setting up the game panel
		 */
		gPanel = new GamePanel(g);
		gPanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		gPanel.setBackground(BG_COLOR);
		gPanel.addMouseListener(mHelper);
		gPanel.addMouseMotionListener(mHelper);
		
		/*
		 * Setting up the frame
		 */
		this.getContentPane().add(gPanel);
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		t.start();
		
		
	}
	
	public Color getBGColor(){
		return BG_COLOR;
	}
	
	public Timer getTimer(){
		return t;
	}
	
	public GamePanel getPanel(){
		return gPanel;
	}
	
	

}
