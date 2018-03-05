package IfAndOnlyIfs;
import robocode.*;
import robocode.util.Utils;
import java.awt.Color;

/**
 * PolyBot - a robot by Lam Lieu and Juan Vera
 */
public class PolyBot extends AdvancedRobot
{

    public int count = 0;
    public boolean check = true;
    public boolean beginGame = true;
    public boolean midGame = false;
    public boolean endGame = false;
    public int botCount;
    public double enemyBearing;
    public boolean isColliding = false;
        

    public void run() {
    
        setAdjustGunForRobotTurn(true);
        botCount = getOthers();
        setColors(Color.RED, Color.GREEN, Color.WHITE);
        setBulletColor(Color.BLACK);
        setTurnGunLeft(Double.POSITIVE_INFINITY);    
        
        if(getOthers() <= 2) {
            beginGame = false;
            midGame = false;
            endGame = true;
        }            
        
        while(true) {
        
            count++;
            
            if(isColliding && count > 4) {
                isColliding = false;
                setTurnGunLeft(Double.POSITIVE_INFINITY);
                execute();
            }

            if(beginGame)
                movement();            
            else if(midGame)
                movement2();
            else if(endGame)
                movement3();
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        if(!isColliding) {
            double firePower = 600 / e.getDistance();
            if(!endGame && !isColliding && e.getDistance() < 600) {
                fire(firePower);        
              }
            else if(endGame) {
                setAhead(10);
                setTurnRight(e.getBearing());
                double turn = getHeading() + e.getBearing() - getGunHeading();
                  setTurnGunRight(Utils.normalRelativeAngleDegrees(turn));
                 if((getGunHeading()-getHeading()) < 5 && !isColliding) {
                       fire(firePower);            
                }
            }
        }

        if(isColliding) {
            fire(3);
        }
        execute();
    }
    
    public void onRobotDeath(RobotDeathEvent e) {
        if(getOthers() < botCount/2 && getOthers() > 2) {
            midGame = true;
            beginGame = false;
        }
        else if(getOthers() <= 2) {
            beginGame = false;
            midGame = false;
            endGame = true;
        }
        execute();
    }
    
    public void onHitWall(HitWallEvent e) {
        setInterruptible(true);
        back(20);
        turnRight(90);
    }

    public void movement2() {
        checkWalls();
        setAhead(100);
        setTurnGunRight(100);
        setTurnLeft(2);
        execute();
    }
    
    public void movement3() {
        checkWalls();
        setAhead(100);
        setTurnGunRight(100);
        execute();
    }
    
    
    public void onHitRobot(HitRobotEvent e) {
        fire(3);
        setInterruptible(true);
        setBack(10);
        isColliding = true;
        count = 0;
        if(!endGame) {
            setTurnRight(e.getBearing());
            double turn = getHeading() + e.getBearing() - getGunHeading();
            setTurnGunRight(Utils.normalRelativeAngleDegrees(turn));
        }
        execute();
    }
    



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
         checkWalls();
        double distance = Math.hypot(targetX, targetY);
        setAhead(distance);
        execute();
    }
    
    public void checkWalls() {
        if ((getX() >= getBattleFieldWidth() - getBattleFieldWidth() / 10)
        || (getX() < getBattleFieldWidth() / 10)
        || (getY() >= getBattleFieldHeight() - getBattleFieldHeight() / 10)
        || (getY() < getBattleFieldHeight() / 10)) {        
            double count = 0;
            if (count == 0 || count % 2 == 0) {
                turnRight(55);
            }
            count++;
        }
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



