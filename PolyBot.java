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
	
		int botCount = getOthers();
		
		setColors(Color.RED, Color.BLACK, Color.WHITE);
		
		while(true) {
			setTurnGunLeft(360);
			ahead(10);
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
}