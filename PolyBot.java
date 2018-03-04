package IfAndOnlyIfs;

import robocode.*;
import robocode.util.Utils;

import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * PolyBot - a robot by Lam Lieu and Juan Vera
 */
public class PolyBot extends AdvancedRobot {
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

        setColors(Color.green, Color.yellow, Color.green);

        while (true) {
            movement();
            if (check) {
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
        if (endGame) {
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
        turnRight(90);
        ahead(100);
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
