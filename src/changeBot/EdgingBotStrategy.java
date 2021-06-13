package changeBot;

import robocode.AdvancedRobot;

import robocode.Event;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;

import java.awt.Color;
import java.awt.event.KeyEvent;
import robocode.ScannedRobotEvent;

/**
 * Implementation of common interface.
 * @author Aytan Abdullayeva <aa262129@fh-muenster.de>
 */
public class EdgingBotStrategy implements Strategy
{
	/* moveDirection: move straight ahead = 1, move backward = -1 */
	int moveDirection = 1;
	
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
		myRobot.setBodyColor(new Color(255, 51, 204));
		myRobot.setGunColor(new Color(0, 0, 0));
		myRobot.setRadarColor(new Color(204, 204, 255));
		myRobot.setBulletColor(new Color(255, 51, 153));
		myRobot.setScanColor(new Color(255, 179, 255));
			
		/* Get your current rotation with getHeading() */
		double currentRotation = myRobot.getHeading();
		    
		/* Turn your robot to face in the direction of the wall */
		myRobot.turnLeft(currentRotation);
		myRobot.turnGunRight(90);
	}

	@Override
	public void move() 
	{
		myRobot.ahead(Double.POSITIVE_INFINITY);
	}

	@Override
	public void reactEvent(Event event) 
	{
		if (event instanceof HitWallEvent)
		{
			/* Turn 90 angle when you hit the wall */
			myRobot.turnRight(90 * moveDirection);
		}
		else if (event instanceof HitRobotEvent)
		{
			/* Go 100 amount of pixel backward */
			myRobot.back(100);
			
			/* Move the robot 0 pixels forward */
			myRobot.ahead(0);
			
			myRobot.turnRight(180);
			myRobot.turnGunRight(180);
			moveDirection *= -1;
		}
		else if (event instanceof HitByBulletEvent)
		{
			/* Switch directions when we are hit */
			myRobot.ahead(0);
			myRobot.turnRight(180);
			myRobot.turnGunRight(180);
			moveDirection *= -1;
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
	
	@Override
	public void fire(ScannedRobotEvent event) 
	{
		myRobot.fire(1);	
	}
}
