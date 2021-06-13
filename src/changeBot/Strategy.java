package changeBot;

import robocode.AdvancedRobot;

import java.awt.event.KeyEvent;
import robocode.Event;
import robocode.ScannedRobotEvent;

/**
 * Common interface for all strategies.
 * @author Aytan Abdullayeva <aa262129@fh-muenster.de>
 */
public interface Strategy
{
	void identify(AdvancedRobot robot);
	/**
	 * <p>
	 * Call the function the you want to change the identity of the robot.
	 */
	void identity();
	void move();
	/**
	 * <p>
	 * Call the function for the superclass of all Robocode events.
	 * @param event
	 */
	void reactEvent(Event event);
	/**
	 * <p>
	 * Call function when a key has been pressed.
	 * @param event
	 */
	void reactKeyPressed(KeyEvent event);
	/**
	 * <p>
	 * Call the function when a key has been released.
	 * @param event
	 */
	void reactKeyReleased(KeyEvent event);
	/**
	 * <p>
	 * Call the function when you scan a robot.
	 * @param event
	 */
	void fire(ScannedRobotEvent event);
}
