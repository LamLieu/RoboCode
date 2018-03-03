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

        setColors(Color.green, Color.yellow, Color.green); // body,gun,radar

        // When the robot turns, the gun doesn't turn the same way
        setAdjustGunForRobotTurn(true);

        // Robot main loop
        while (true) {
            //Movement code
            movement();
            //Activates if loop when there are less than 50% bots remaining
            frameCount++;
            if (frameCount % 20 == 0) {
                if (getOthers() < totalBots / 2) {
                    midGame = true;
                    beginningGame = false;
                }
                if (getOthers() == 2) {
                    endGame = true;
                    midGame = false;
                }
            }


            //Activates when there are 1 or 2 bots left

            else {
                movement();
                turnGunLeft(360);
                movement();
                turnGunLeft(360);
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
        int count = 0;
        if (count % 2 == 0)
            ahead(50);
        else
            back(50);
        count++;
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
