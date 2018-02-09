package JVR;
import robocode.*;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * BunchieBot - a robot by (your name here)
 */
public class BunchieBot extends Robot
{

	public boolean isTrigger;
	public int counter;
	/**
	 * run: BunchieBot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here
		isTrigger = false;
		counter = 0;
		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			if(!isTrigger) {
			ahead(100);
			turnGunRight(360);
			turnGunRight(360);
	}
	else {
		turnGunRight(25);
		turnGunLeft(25);
	}		
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		isTrigger = true;
		fire(100);
	}
	
	public void onRobotDeath(RobotDeathEvent e) {
		isTrigger = false;
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		turnLeft(90);
	}	
	public void onBulletMiss(BulletMissedEvent e) {
		counter++;
		if(counter >= 10) {
			isTrigger = false;
			counter = 0;
		}
	}
}
