package IfAndOnlyIfs;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * PolyBot - a robot by (your name here)
 */
public class PolyBot extends AdvancedRobot
{
	/**
	 * run: PolyBot's default behavior
	 */

	public boolean beginningGame = true;
	public boolean midGame = false;
	public boolean endGame = false;
	boolean check = false;
	public int totalBots;
	public double enemyBearing;
	public double  enemyHeading;
	
	public void run() {
		// Initialization of the robot should be put here
		totalBots = getOthers();			
		double height = getBattleFieldHeight();
		double width = getBattleFieldWidth();

		int frameCount = 0;

		setColors(Color.green,Color.yellow,Color.green);
		
		while(true) {
			if(endGame) {
				turnGunLeft(enemyBearing);
				turnLeft(enemyBearing);
				setAhead(10000);
			}
			else {
		   		movement();
			   	turnGunRight(360);
			   	movement();
				turnGunLeft(360);
			}				

			if(check) {
			check = false;
				if(getOthers() < totalBots/2) {	
					midGame = true;
					beginningGame = false;
			}
				if(getOthers() == 2) {
					endGame = true;
					midGame = false;
				}
			}
		}
	}
	

	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		enemyBearing = e.getBearing();
		enemyHeading = e.getHeading();
		double distance = e.getDistance();
		out.println(distance + " away from " + e.getName());
		
		double firePower = 700 / distance;
		out.println("firepower is at: " + firePower);
		fire(firePower);
		check = true;

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

        /* For when bot hits top right corner */
        if (getY() == getBattleFieldHeight() && getX() == getBattleFieldWidth()) {
            turnRight(90);
            ahead(100);
            if (getY() == getBattleFieldHeight() && getX() == getBattleFieldWidth())
                back(100);
        }

        /* For when bot hits bottom right corner */
        if (getY() == 0 && getX() == 0) {
            turnRight(90);
            ahead(100);
            if (getY() == 0 && getX() == 0) 
                back(100);

        }

        /* For when bot hits top left corner */
        if (getY() == getBattleFieldHeight() && getX() == 0) {
            turnRight(90);
            ahead(100);
            if (getY() == 0 && getX() == 0) 
                back(100);
        }

        /* For when bot hits bottom left corner */
        if (getY() == 0 && getX() == getBattleFieldWidth()) {
            turnRight(90);
            ahead(100);
            if (getY() == 0 && getX() == 0)
                back(100);
        }

        /* For when the bot hits the top or bottom wall */
        if (getY() == getBattleFieldHeight()) {
            turnLeft(90);
            ahead(100);
        }
        else if (getY() == 0) {
            turnLeft(90);
            ahead(100);
        }

        /* For when the bot hits the left or right wall */
        if (getX() == getBattleFieldWidth()) {
            turnLeft(90);
            ahead(100);
        }
        else if (getX() == 0) {
            turnLeft(90);
            ahead(100);
        }
		execute();
    }

    // Some code from http://robowiki.net/wiki/GoTo
    private void movement() {
        double targetX, targetY;
		
        // Calculates the closest vertex of the diamond
		targetY = battleFieldHeightComparison(getY());
		targetX = battleFieldWidthComparison(getX());
		// Makes the coordinate positive
        targetX = (targetX < 0) ? -targetX : targetX;
        targetY = (targetY < 0) ? -targetY : targetY;
        // Calculates the angle to target location
        double angleToTarget = Math.atan2(targetX, targetY);

        /* Calculate the turn required get there */
        double targetAngle = robocode.util.Utils.normalRelativeAngle(angleToTarget - getHeadingRadians());

        /*
         * The Java Hypot method is a quick way of getting the length
         * of a vector. Which in this case is also the distance between
         * our robot and the target location.
         */
        double distance = Math.hypot(targetX, targetY);

        /* This is a simple method of performing set front as back */
        double turnAngle = Math.atan(Math.tan(targetAngle));
        setTurnRightRadians(turnAngle);
        if(targetAngle == turnAngle) {
            setAhead(distance);
        } else {
            setBack(distance);
        }
		execute();
    }

    /* Calculates which target x coordinate the bot should go based on spawn location */
    private double battleFieldHeightComparison(double robotY) {
        if (robotY < getBattleFieldHeight() / 2) {
            return getBattleFieldHeight() / 10;
        }
        else if (robotY > getBattleFieldHeight() / 2) {
            return getBattleFieldHeight() - getBattleFieldHeight() / 10;
        }
        return getBattleFieldHeight() / 10;
    }

    private double battleFieldWidthComparison(double robotX) {
        if (robotX < getBattleFieldWidth() / 2) {
            return getBattleFieldWidth() / 10;
        }
        else if (robotX > getBattleFieldWidth() / 2) {
            return getBattleFieldWidth() - getBattleFieldWidth() / 10;
        }
        return getBattleFieldWidth() / 10;
}
	
}
