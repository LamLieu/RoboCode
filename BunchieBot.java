package JVR;
import robocode.*;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * BunchieBot - a robot by (your name here)
 */
public class BunchieBot extends Robot {

   private String target;
   private boolean isTrigger;
   private int counter;

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

      setAdjustGunForRobotTurn(true); // When the robot turns, the gun doesn't turn the same way
      setAdjustRadarForGunTurn(true); // When the gun turns, the radar doesn't turn the same way
      // Robot main loop
      while (true) {
         if (!isTrigger) {
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
      // If cannot find target, then target first scanned robot
      if (target ==  null)
         target = e.getName();
      if (e.getName() == target) {
         // Gets distance of the robot that hit us and fires based on distance
         if (e.getDistance() < 200)
            fire(3);
         else
            fire(2.4);
      }
   }

   /**
    * onHitRobot:  Set him as our new target
    */
   public void onHitRobot(HitRobotEvent e) {
      // Set the target
      trackName = e.getName();
      // Back up a bit.
      // Note:  We won't get scan events while we're doing this!
      // An AdvancedRobot might use setBack(); execute();
      gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
      turnGunRight(gunTurnAmt);
      fire(3);
      back(50);
   }

   public void onRobotDeath(RobotDeathEvent e) {
      isTrigger = false;
   }

   /**
    * onHitByBullet: What to do when you're hit by a bullet
    */
   public void onHitByBullet(HitByBulletEvent e) {
      // Switch target to robot that hit our bot
      target = e.getName();
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
      if (counter >= 10) {
         isTrigger = false;
         counter = 0;
      }
   }
}
