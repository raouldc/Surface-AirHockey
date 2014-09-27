/*
 * The Bullet class.
 * 
 * Author: Kartik Andalam
 * UPI:kand617
 * Date: 10/02/2011
 */

import java.awt.*;

public class Bullet {
	private Rectangle area;
	private int ySpeed;
	private boolean isVisible;
	private int counter; // used for debugging check points...

	public Point launchPoint;
	public boolean isActive;
	public static int collisonCounter;

	public Bullet() {
		collisonCounter = 0;
		counter = 0;
		ySpeed = 40;

		launchPoint = new Point(0, 0);
		isActive = false;

		area = new Rectangle(50, 30, 5, 20); // position doesn't matter, as its
												// updated.
		isVisible = false;
		// System.out.println("initial area pos: "+ area);

	}

	/*
	 * Draw method simply draws the bullet, based on the launch point( current
	 * position of the player)
	 */
	public void draw(Graphics g) {
		if (isVisible) {

			g.setColor(Color.GRAY);
			area.x = launchPoint.x; // this changes the launch point to the
									// position of the user.

			g.fillRect(area.x, area.y, area.width, area.height);
			g.setColor(Color.BLACK);

			// System.out.println("\n ~~~~~~~~~~Initial Values~~~~~~~~");
			// System.out.print(this.toString());
		}
	}

	/*
	 * The move method only moves the bullet up and disappears if it goes
	 * past/touches the wall.
	 */
	public void move() {

		area.y -= ySpeed;
		// System.out.println("Bullet details...." + area);

		if (area.y <= A3Constants.GAME_AREA_TOP) {

			isVisible = false;
			isActive = false;

		}
	}

	public String toString() {
		return "Bullet area= " + area.toString() + "ySpeed= " + +ySpeed	+ ", isVisible=" + isVisible + "]";
	}

	/*
	 * The method simply returns if the bullet is visible or not.
	 */
	public boolean getIsVisible() {

		return isVisible;
	}

	/*
	 * The method simply set if the bullet is visible or not.
	 */
	public void setIsVisible(boolean status) {

		isVisible = status;

	}

	/*
	 * The method printAreaInfo() is used for debugging purposes.
	 */
	private void printAreaInfo() {

		System.out.println("checkpoint at " + counter + " : " + area);
		counter++;

	}

	/*
	 * The method getbullet() returns the bullet.
	 */
	public Rectangle getbullet() {

		return area;
	}

	/*
	 * The method updateLaunchPoint() sets the area positions to that of the
	 * user.
	 */
	public void updateLaunchPoint() {

		launchPoint.x -= area.width / 2;
		launchPoint.y -= area.height;

		area.y = launchPoint.y;

	}

	/*
	 * The method analyseCollison() is used to analyse the collision between
	 * enemy and bullet. after collision it the bullet will be invisble and is
	 * available to use(isActive=false) also the collision counter increases
	 * (used for score).
	 */
	public void analyseCollison(EnemyBall[] enemies) {
		for (int i = 0; i < enemies.length; i++) {

			if (area.intersects(enemies[i].getEnemy()) && enemies[i].getIsVisible() && isVisible) {
				enemies[i].setIsVisible(false);
				// System.out.println("been hit!!! by Bullet!!!!");

				collisonCounter++;

				isVisible = false;
				isActive = false;

				// System.out.println("bullet success counter: " +collisonCounter);

			}
		}
	}
}