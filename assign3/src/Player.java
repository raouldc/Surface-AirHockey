/*
 * The Player class.
 * 
 * Author: Kartik Andalam
 * UPI:kand617
 * Date: 10/02/2011
 */

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
	private Rectangle area;
	private int speed;
	private int lives;

	public Player() {
		speed = A3Constants.PLAYER_SPEED;
		area = A3Constants.PLAYER_STARTING_POSITION;
		lives = (A3Constants.PLAYER_MAX_SIZE - area.width) / A3Constants.PLAYER_SIZE_INCREASE;
	}

	/*
	 * The move() method's purpose is to simply move the positions x,y of the
	 * user, depending on the direction provided.
	 */
	public void move(int direction) {

		if (direction == A3Constants.UP) {

			area.y -= A3Constants.PLAYER_SPEED;

		} else if (direction == A3Constants.DOWN) {

			area.y += A3Constants.PLAYER_SPEED;

		} else if (direction == A3Constants.LEFT) {

			area.x -= A3Constants.PLAYER_SPEED;

		} else {

			area.x += A3Constants.PLAYER_SPEED;
		}

	}

	/*
	 * The adjustPosition() make sures the player is in the game area, if not.
	 * it resets it according to which wall it is out of. For example if it is
	 * above the top wall, it will reset just below the top wall.
	 */

	public void adjustPosition() {

		if (area.y <= A3Constants.GAME_AREA_TOP) {

			area.y = A3Constants.GAME_AREA_TOP + 1;
		}
		if (area.y + area.height >= A3Constants.GAME_AREA_BOTTOM) {

			area.y = A3Constants.GAME_AREA_BOTTOM - area.height - 1;
		}
		if (area.x <= A3Constants.GAME_AREA_LHS) {

			area.x = A3Constants.GAME_AREA_LHS + 1;
		}
		if (area.x + area.width >= A3Constants.GAME_AREA_RHS) {

			area.x = A3Constants.GAME_AREA_RHS - area.width - 1;
		}

	}

	/*
	 * The draw method calls the adjust position to make sure the postion of the
	 * player is valid before drawing
	 */

	public void draw(Graphics g) {

		adjustPosition();// adjusts such that its inside the game area.
		g.setColor(Color.YELLOW);
		g.fillRect(area.x, area.y, area.width, area.height);
	}

	public void increaseSize() {
//		 System.out.println("increased size before: "+area.width+area.height);
		if (area.width <= A3Constants.PLAYER_MAX_SIZE) {
			area.width += A3Constants.PLAYER_SIZE_INCREASE;
			area.height += A3Constants.PLAYER_SIZE_INCREASE;

			// calculating lives remaining.
			lives = (A3Constants.PLAYER_MAX_SIZE - area.width)
					/ A3Constants.PLAYER_SIZE_INCREASE;
		} else {
			lives = 0;
		}
//		 System.out.println("after : "+area.width+area.height);

	}

	/*
	 * The method hasBeenHit checks for any collision between the enemies and
	 * user.
	 */
	public boolean hasBeenHit(EnemyBall[] enemies) {
		for (int i = 0; i < enemies.length; i++) {

			// if(area.contains(enemies[i].getCentrePoint())&&enemies[i].getIsVisible()){
			// not using the if condition above as my enemies are square, should
			// disappear on contact
			if (area.intersects(enemies[i].getEnemy()) && enemies[i].getIsVisible()) {
				enemies[i].setIsVisible(false);
				System.out.println("been hit!!! ");
				return true;

			}
		}
		return false;
	}

	/*
	 * The method getMiddlePos() return the middle position of the player.
	 * originally used for collision detection.
	 */
	public Point getMiddlePos() {

		Point middle;
		int xPos = area.x + area.width / 2;
		int yPos = area.y;
		middle = new Point(xPos, yPos);
		return middle;

	}

	/*
	 * Returns if the player has reached their max size.
	 */
	public boolean hasReachedMax() {
		if (lives == 0) {
			return true;
		}
		return false;
	}

	/*
	 * returns the number of lives left.
	 */
	public int getLives() {
		return lives;
	}

}
