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

	boolean beginningGame = true;
	boolean midGame = false;
	boolean endGame = false;
	boolean check = false;
	
	public void run() {
		// Initialization of the robot should be put here
		int totalBots = getOthers();
		GamePhase g1 = new GamePhase(totalBots);
		double height = getBattleFieldHeight();
		double width = getBattleFieldWidth();

		int frameCount = 0;

		setColors(Color.green,Color.yellow,Color.green);
		
		while(true) {
			turnRight(10);
			ahead(100);
			
			if(check) {
				g1.checkGamePhase(getOthers());
			}
		}
	}
	

	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		double distance = e.getDistance();
		out.println(distance + " away from " + e.getName());
		
		double firePower = 700 / distance;
		out.println("firepower is at: " + firePower);
		fire(firePower);
		check = true;
		if(endGame) {
			turnGunLeft(e.getHeading());
			turnLeft(e.getHeading());
			ahead(100);
		}
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
