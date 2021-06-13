package changeBot;

import robocode.AdvancedRobot;
import java.awt.event.KeyEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

/**
 *  <p>
 *  This ChangeBot is a combination of robots that I have developed previously.  
 *  <p>
 *  This robot can change its strategy at the push of a button. With 'c' key, you can swap the strategy of the ChangeBot during operation.
 *  @author Aytan Abdullayeva <aa262129@fh-muenster.de>
 */
public class ChangeBot extends AdvancedRobot 
{
	Strategy strategy = null;
	Strategy[] strategies = new Strategy[3];
	int strategyPos = 0;
	
	public void run() 
	{
		strategies[0] = new KeyboardBotStrategy();
		strategies[0].identify(this);
		strategies[1] = new EdgingBotStrategy();
		strategies[1].identify(this);
		strategies[2] = new CollisionBotStrategy();
		strategies[2].identify(this);
		strategy = strategies[0];
		
		/* The first identity of the first robot: KeyboardBot */
		strategy.identity();
		
		for (;;) 
		{
			strategy.move();
		}
	}
	
	/**
	 * <p>
	 * Stoping the movement of the robots
	 */
	public void pauseTheMovement()
	{
		execute();
		
		/* KeyboardBot-EdgingBot-CollisionBot have to stop */
		setAdjustGunForRobotTurn(false);
		setAdjustRadarForRobotTurn(false);
		setAdjustRadarForGunTurn(false);
		
		setAhead(0);		
		setTurnRight(0);
		setTurnGunRight(0);
		setTurnRadarRight(0);
		
		setFire(0);
		execute();
		
		double correctionGun = getGunHeading() - getHeading();
		turnGunLeft(correctionGun);
		
		double correctionRadar = getRadarHeading() - getHeading();
		turnRadarLeft(correctionRadar);
	}
	
	@Override
	public void onKeyPressed(KeyEvent event) 
	{
		out.println("key" + event.getKeyChar());
		
		if(event.getKeyChar() == 'c') 
		{
			pauseTheMovement();
			
			toggleStrategies();
			strategy.identity();
			
			out.println("Change!");
		} 
		else 
		{
			strategy.reactKeyPressed(event);
		}
	}
	
	@Override
	public void onKeyReleased(KeyEvent event)
	{
		out.println("key" + event.getKeyChar());
		
		if(event.getKeyChar() == 'c') 
		{
			out.println("Change!");
		} 
		else 
		{
			strategy.reactKeyReleased(event);
		}
	}
	
	public void onHitByBullet(HitByBulletEvent hbbe) 
	{
		strategy.reactEvent(hbbe);
	}
	
	public void onHitRobot(HitRobotEvent hre) 
	{
		strategy.reactEvent(hre);
	}
	
	public void onHitWall(HitWallEvent hwe) 
	{
		strategy.reactEvent(hwe);
	}
	
	public void onRobotDeath(RobotDeathEvent rde)
	{
		strategy.reactEvent(rde);
	}
	
	public void onScannedRobot(ScannedRobotEvent sre)
	{
		strategy.fire(sre);
	}
	
	private synchronized void toggleStrategies() 
	{
		strategyPos += 1;
		
		if(strategyPos >= strategies.length) 
		{
			strategyPos = 0;
		}
		out.println("Change of strategy " + strategyPos);
		strategy = strategies[strategyPos];
	}
}
