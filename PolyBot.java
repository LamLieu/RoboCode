package IfAndOnlyIfs;

import robocode.*;
import robocode.util.Utils;

import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * PolyBot - a robot by Lam Lieu and Juan Vera
 */
public class PolyBot extends AdvancedRobot {

    public boolean check = true;
    public boolean beginGame = true;
    public boolean midGame = false;
    public boolean endGame = false;
    public int botCount;

    public void run() {

        botCount = getOthers();

        setColors(Color.RED, Color.BLACK, Color.WHITE);

        while (true) {
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
        if (getOthers() < botCount / 2 && getOthers() > 2) {
            midGame = true;
            beginGame = false;
        }
        else if (getOthers() <= 2) {
            midGame = false;
            endGame = true;
        }
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        // Replace the next line with any behavior you would like
        turnRight(45);
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        int count = 0;
        if (count % 2 == 0) {
            turnRight(30);
            ahead(50);
        }
        else {
            turnLeft(30);
            ahead(50);
        }
        count++;
    }

    // Some code from http://robowiki.net/wiki/GoTo
    private void movement() {
        double targetX, targetY;
        double x1 = getBattleFieldWidth() / 2;
        double y1 = getBattleFieldHeight() / 2;
        double x2 = getBattleFieldWidth() - getBattleFieldWidth() / 20;
        double y2 = getBattleFieldHeight() - getBattleFieldHeight() / 20;
        double x3 = getBattleFieldWidth() / 20;
        double y3 = getBattleFieldHeight() / 20;
        // Calculates the closest vertex of the diamond that is closest to the robot
        targetY = battleFieldHeightComparison();
        targetX = battleFieldWidthComparison();
        // Sets new target coordinates when robot is on the vertex
        if (getX() == x1 && getY() == y2) {
            targetX = x3;
            targetY = y1;
        }
        else if (getX() == x3 && getY() == y1) {
            targetX = x1;
            targetY = y2;
        }
        else if (getX() == x1 && getY() == y2) {
            targetX = x2;
            targetY = y1;
        }
        else if (getX() == x2 && getY() == y2) {
            targetX = x1;
            targetY = y2;
        }
        else if (getX() == x3 && getY() == y3) {
            targetX = x1;
            targetY = y2;
        }
        // Detects if robot gets near wall and turns 55 deg if it's within range
        if (getX() >= getBattleFieldWidth() - getBattleFieldWidth() / 10) {
            double count = 0;
            if (count == 0 || count % 2 == 0) {
                turnRight(55);
            }
            count++;
        }
        else if (getX() < getBattleFieldWidth() / 20) {
            double count = 0;
            if (count == 0 || count % 2 == 0) {
                turnRight(55);
            }
        }
        if (getY() >= getBattleFieldHeight() - getBattleFieldHeight() / 20) {
            double count = 0;
            if (count == 0 || count % 2 == 0) {
                turnRight(55);
            }
            count++;
        }
        else if (getY() < getBattleFieldHeight() / 10) {
            double count = 0;
            if (count == 0 || count % 2 == 0) {
                turnRight(55);
            }
            count++;
        }
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
        setAhead(distance);
        execute();
    }

    /* Calculates which target x coordinate the bot should go based on spawn location */
    private double battleFieldHeightComparison() {
        double robotYCoord = getY();
        double halfOfBattleFieldHeight = getBattleFieldHeight();
        if (robotYCoord <= halfOfBattleFieldHeight) {
            return getBattleFieldHeight() / 20;
        }
        else if (robotYCoord > halfOfBattleFieldHeight) {
            return getBattleFieldHeight() - getBattleFieldHeight() / 20;
        }
        return getBattleFieldHeight() / 20;
    }

    private double battleFieldWidthComparison() {
        double robotXCoord = getX();
        double halfOfBattleFieldWidth = getBattleFieldWidth();
        if (robotXCoord <= halfOfBattleFieldWidth) {
            return getBattleFieldWidth() / 20;
        }
        else if (robotXCoord > halfOfBattleFieldWidth) {
            return getBattleFieldWidth() - getBattleFieldWidth() / 20;
        }
        return getBattleFieldWidth() / 20;
    }
}