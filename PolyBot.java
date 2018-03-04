package IfAndOnlyIfs;

        import robocode.*;
        import robocode.util.Utils;

        import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * PolyBot - a robot by Lam Lieu and Juan Vera
 */
public class PolyBot extends AdvancedRobot
{

	public boolean check = true;
	public boolean beginGame = true;
	public boolean midGame = false;
	public boolean endGame = false;
	public int botCount;
	
	public void run() {
	
		botCount = getOthers();
		
		setColors(Color.RED, Color.BLACK, Color.WHITE);
		
		while(true) {
			setTurnGunLeft(360);
			movement();
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		double firePower = 700 / e.getDistance();
		fire(firePower);
	}
	
	public void onRobotDeath(RobotDeathEvent e) {
		out.println("checking game");
		if(getOthers() < botCount/2 && getOthers() > 2) {
			midGame = true;
			beginGame = false;
		}
		else if(getOthers() <= 2) {
			midGame = false;
			endGame = true;
		}
	}
	
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
        double targetAngle = Utils.normalRelativeAngle(angleToTarget - getHeadingRadians());

        /*
         * The Java Hypot method is a quick way of getting the length
         * of a vector. Which in this case is also the distance between
         * our robot and the target location.
         */
        double distance = Math.hypot(targetX, targetY);

        /* This is a simple method of performing set front as back */
        double turnAngle = Math.atan(Math.tan(targetAngle));
        setTurnRightRadians(turnAngle);
        if (targetAngle == turnAngle) {
            setAhead(distance);
        }
        else {
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