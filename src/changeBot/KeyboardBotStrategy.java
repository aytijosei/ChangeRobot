package changeBot;

import robocode.AdvancedRobot;
import robocode.Event;

import static java.awt.event.KeyEvent.KEY_PRESSED;
import static java.awt.event.KeyEvent.KEY_RELEASED;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_J;
import static java.awt.event.KeyEvent.VK_K;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_UP;

import java.awt.Color;
import java.awt.event.KeyEvent;
import robocode.ScannedRobotEvent;

/**
 * Implementation of common interface.
 * @author Aytan Abdullayeva <aa262129@fh-muenster.de>
 */
public class KeyboardBotStrategy implements Strategy
{
	/* moveDirection: move straight ahead = 1, move backward = -1, stand still = 0 */
	int moveDirection;
	
	/* turnDirection: turn right = 1, turn left = -1, don't turn = 0 */
	int turnDirection;
	
	/* fireGun: fire = 1, don't fire = 0 */
	int fireGun;
	
	/* Amount of pixels to move */
	double amountOfPixels;
	
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
		myRobot.setBodyColor(new Color(220, 220, 137));
		myRobot.setGunColor(new Color(200, 200, 70));
		myRobot.setRadarColor(new Color(204, 255, 153));
		myRobot.setScanColor(new Color(137, 137, 41));
	}
	
	@Override
	public void move() 
	{
		/* 
		 * Sets ahead the robot to move 'straight ahead', 'backward' or 'dont move'
		 * depending on the 'moveDirection' and 'AmountOfPixels' to move.
		 */
		myRobot.setAhead(amountOfPixels * moveDirection);

		/* 
		 * Sets the robot to 'turn right' or 'turn left' or 'dont turn' depending on the turn direction.
		 * Sets the gun to 'move right' or 'move left' or 'dont move' depending on the move direction.
		 * Body turns and gun moves should be equal.
		 */
		myRobot.setTurnRight(90 * turnDirection); 
		myRobot.setTurnGunRight(90 * moveDirection);
		
		/* Fire with gun, unless fire = 0 */
		if (fireGun > 0) 
		{
			myRobot.setFire(fireGun);
		}
		
		myRobot.execute();
	}

	@Override
	public void reactEvent(Event event) 
	{
		//TODO nothing
	}
	
	@Override
	public void reactKeyPressed(KeyEvent event)
	{
		switch (event.getKeyCode()) 
		{
		    /* Move the robot straight ahead */
		    case VK_UP:
		    case VK_K:
				moveDirection = 1;
				amountOfPixels = Double.POSITIVE_INFINITY;
		    	break;
		    	
		    /* Move the robot backwards */	
		    case VK_DOWN:
		    case VK_L:
		    	moveDirection = -1;
				amountOfPixels = Double.POSITIVE_INFINITY;
		    	break;
			
		    /* Rotate robot to the right */
		    case VK_RIGHT:
		    case VK_J:
		    	turnDirection = 1;
		    	break;
		    	
		    /* Rotate robot to the left */
		    case VK_LEFT:
		    case VK_H:
		    	turnDirection = -1;
		    	break;
		    	
		    /* Fire the enemies */
			case KEY_PRESSED:
			case VK_SPACE:
				fireGun = 1;
				myRobot.setBulletColor(new Color(255, 153, 0));
				break;
		}
	}
	
	@Override
	public void reactKeyReleased(KeyEvent event)
	{
		switch (event.getKeyCode()) 
		{
		  /* Move = stand still */
		  case VK_UP:
		  case VK_K:	
		  case VK_DOWN:
	      case VK_L:
	    	  moveDirection = 0;
	    	  amountOfPixels = 0;
	    	  break;
				
		   /* Turn = stop turning */
	       case VK_RIGHT:
		   case VK_J:
		   case VK_LEFT:
		   case VK_H:
			   turnDirection = 0;
			   break;
			   
		   /* Don't fire the enemies */
		   case KEY_RELEASED:
		   case VK_SPACE:
			   fireGun = 0;
			   break;
		 }
	}

	@Override
	public void fire(ScannedRobotEvent event) 
	{
		//TODO nothing
	}
}
