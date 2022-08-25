import static org.junit.Assert.*;

import org.junit.Test;

public class SpriteTest {
	MainFrame f= new MainFrame();
	Sprite ball = new Sprite(0, f.getWidth(), 0, f.getHeight(), f);

	@Test
	public void testConstructor() {
		assertTrue("Incorrect Xmax boundary", ball.getMaxX()== 2000);
		assertTrue("Incorrect Ymax boundary", ball.getMaxY()== 1600);
		assertTrue("Incorrect Xmin boundary", ball.getMinX()==0);
		assertTrue("Incorrect Ymin boundary", ball.getMinY()==0);
		
		assertTrue("Velocity set wrong init dX", ball.getDx()==0);
		assertTrue("Velocity set wrong init dY", ball.getDy()==0);
		
		assertTrue("Ball placed incorrectly X", ball.getX()==975);
		assertTrue("Ball placed incorrectly Y", ball.getY()==775);
		
	}
	
	@Test 
	public void testUpdate(){
		ball.setDx(10);
		ball.setDy(10);
		ball.update();
		
		assertTrue("cannot move within bounds", ball.getX()==985);
		assertTrue("cannot move within bounds", ball.getY()==785);
		
		ball.setDx(1100);
		ball.setDy(1000);
		ball.update();
	
		
		assertTrue("cannot 'bounce' maxX", ball.getX()==1765);
		assertTrue("cannot 'bounce' maxY", ball.getY()==1365);
		
		ball.setDx(-1800);
		ball.setDy(-1400);
		ball.update();
		
		
		
		assertTrue("cannot 'bounce minX", ball.getX()==35);
		assertTrue("cannot 'bounce minY", ball.getY()==35);
	}
	
	@Test
	public void testAccelerate(){
		ball.setDx(10);
		ball.setDy(10);
		
		ball.accelerate(1.2);
		assertTrue("acceleration doesn't work for a positive number DX", ball.getDx()==12);
		assertTrue("acceleration doesn't work for a positive number DY", ball.getDy()==12);
		
		ball.accelerate(-.2);
		assertTrue("acceleration doesn't work for a negative number DX", ball.getDx()==-2.4000000000000004);
		assertTrue("acceleration doesn't work for a negative number DY", ball.getDy()==-2.4000000000000004);
		
		ball.accelerate(0);
		assertTrue("acceleration doesn't work for zero DX", ball.getDx()==0);
		assertTrue("acceleration doesn't work for zero DY", ball.getDy()==0);
	}

}
