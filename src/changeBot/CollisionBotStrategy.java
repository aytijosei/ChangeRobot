package changeBot;

import robocode.AdvancedRobot;

import robocode.Event;
import robocode.RobotDeathEvent;

import java.awt.Color;
import java.awt.event.KeyEvent;
import robocode.ScannedRobotEvent;

/**
 * Implementation of common interface.
 * @author Aytan Abdullayeva <aa262129@fh-muenster.de>
 */
public class CollisionBotStrategy implements Strategy
{
	/* scanDirection: scan forward = 1, scan backward = -1 */
	int scanDirection = 1;
	
	public AdvancedRobot myRobot;
	
	@Override
	public void identify(AdvancedRobot robot) 
	{
		this.myRobot = robot;
	}

	@Override
	public void identity() 
	{
		/* Set colors */
		myRobot.setBodyColor(new Color(102, 153, 153));
		myRobot.setGunColor(new Color(0, 0, 0));
		myRobot.setRadarColor(new Color(0, 204, 153));
		myRobot.setScanColor(new Color(0, 255, 191));
		myRobot.setBulletColor(Color.red);
	}

	@Override
	public void move() 
	{
		/* Scan for other guns */
		myRobot.setAdjustRadarForGunTurn(true);
		
		/* Turn the scanner until we find an enemy robot */
		myRobot.setTurnRadarRight(Double.POSITIVE_INFINITY);
		myRobot.execute(); 
	}
	
	@Override
	public void fire(ScannedRobotEvent event) 
	{
		/* When we scan a robot, turn toward him */
		myRobot.setTurnRight(event.getBearing());
		
		/* Move little closer */
		if (event.getDistance() > 200) 
		{
			myRobot.setAhead(event.getDistance() / 2);
		}
		
		/* Not to close */
		if (event.getDistance() < 100) 
		{
			myRobot.setBack(event.getDistance());
		}
		
		/* Shoot the enemy */
		myRobot.fire(1);
		
		/* Set the radar to generate another scan event */
		scanDirection *= -1;
		//myRobot.setTurnRadarRight(Double.POSITIVE_INFINITY * scanDirection);
		
		myRobot.execute();
	}

	@Override
	public void reactEvent(Event event) 
	{
		/* If the robot we were shooting at died, scan around others. */
		if (event instanceof RobotDeathEvent)
		{
			myRobot.setTurnRadarRight(Double.POSITIVE_INFINITY);
			myRobot.execute();
		}
	}

	@Override
	public void reactKeyPressed(KeyEvent event) 
	{
		//TODO nothing
	}

	@Override
	public void reactKeyReleased(KeyEvent event) 
	{
		//TODO nothing
	}
}
