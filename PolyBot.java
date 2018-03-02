package IfAndOnlyIfs;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * PolyBot - a robot by (your name here)
 */
public class PolyBot extends Robot
{
	/**
	 * run: PolyBot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here
		int totalBots = getOthers();
		double height = getBattleFieldHeight();
		double width = getBattleFieldWidth();
		boolean endGame = false;
		boolean beginningGame = true;
		boolean midGame = false;
		int frameCount = 0;
		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		setColors(Color.green,Color.yellow,Color.green); // body,gun,radar
		
		// Robot main loop
		while(true) {
		//Activates if loop when there are less than 50% bots remaining
			frameCount++;
			if(frameCount%20 == 0) {
				if(getOthers() < totalBots/2) {	
					midGame = true;
					beginningGame = false;
				}
				if(getOthers() == 2) {
					endGame = true;
					midGame = false;
				}
			}


		//Activates when there are 1 or 2 bots left

		else {
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunRight(360);
			}
		}
	}
	
	public void shoot() {
		
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		double distance = e.getDistance();
		out.println(distance + "away from " + e.getName());
		
		double firePower = 500 / distance;
		out.println("firepower is at: " + firePower);
		fire(firePower);
		fire(firePower);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
	}	
}
