/*
 * The EnemyBall class.
 * 
 * Author: Kartik Andalam
 * UPI:kand617
 * Date: 10/02/2011
 */
 
import java.awt.*;

public class EnemyBall {
	private Rectangle area;
	private int xSpeed,ySpeed;
	private boolean isVisible;
	private int counter; // Used for debugging purpose.
	
	
	


	public EnemyBall(){
		counter=0;
		
		int randPos=(int)(Math.random()*4);
		area= new Rectangle(A3Constants.ENEMY_INITIAL_POSITIONS[randPos]);
		//System.out.println("initial area pos: "+ area);
		
		isVisible=true;
		
		//Assigning x,y random Speeds.
		int xRandSpeed= (int)(Math.random()*4)+1;
		int yRandSpeed= (int)(Math.random()*4)+1;
	
		//Assignment of initial speed depending on position.
		if (area.y + area.height >= A3Constants.GAME_AREA_BOTTOM - 1) {
			ySpeed = -1 * yRandSpeed;
		} else {
			ySpeed = yRandSpeed;
		}

		if (area.x + area.width >= A3Constants.GAME_AREA_BOTTOM - 1) {
			xSpeed = -1 * xRandSpeed;

		} else {
			xSpeed = xRandSpeed;

		}
	}
	/*
	 * The draw method simply draws the enemy ball.
	 */
	public void draw(Graphics g){
		if(this.getIsVisible()){
			g.setColor(Color.GREEN);
			g.fillRect(area.x, area.y, area.width, area.height);
			g.setColor(Color.BLACK);
			// System.out.println("\n ~~~~~~~~~~Initial Values~~~~~~~~");
			// System.out.print(this.toString());
		}
		
	}

	/*
	 * The move() method will move and randomise speed and chance when hits the wall,
	 * also will keep the balls inbounds.
	 */
	public void move(){
		//
		area.x += xSpeed;
		area.y += ySpeed;

		if (area.y + area.height >= A3Constants.GAME_AREA_BOTTOM - 1) {
			// bottom
			area.y = A3Constants.GAME_AREA_BOTTOM - area.height - 1;
			// System.out.print("!!!!!HIT bottom!!!!");
			ySpeed = -1 * randomiseSpeed();
			randomiseChance();

		} else if (area.y <= A3Constants.GAME_AREA_TOP) {
			// top
			area.y = A3Constants.GAME_AREA_TOP;
			// System.out.print("!!!!! HIT TOP!!!!");
			ySpeed = randomiseSpeed();
			randomiseChance();
		}
		if (area.x + area.width >= A3Constants.GAME_AREA_RHS - 1) {
			// right
			area.x = A3Constants.GAME_AREA_RHS - area.width - 1;
			// System.out.print("!!!!! HIT RIGHT!!!!");
			xSpeed = -1 * randomiseSpeed();
			randomiseChance();

		} else if (area.x <= A3Constants.GAME_AREA_LHS) {
			// left
			area.x = A3Constants.GAME_AREA_LHS + 1;
			// System.out.print("!!!!! HIT LEFT!!!!");
			xSpeed = randomiseSpeed();
			randomiseChance();

		}

	}
	
	/*
	 * The andomiseSpeed() method will return a positive integer which is used for randomising
	 * speed when it hits the wall. (above)
	 */
	private int randomiseSpeed() {

		int range = A3Constants.MAX_SPEED - A3Constants.MIN_SPEED + 1;
		int randomSpeed = (int) (Math.random() * range) + A3Constants.MIN_SPEED;
		// int randomSpeed=1;
		// System.out.println(" new rand: "+randomSpeed+".."+this.toString());

		return randomSpeed;

	}

	/*
	 * The randomiseChance() method will randomise chance so that 10% it will
	 * disappear when the ball hits wall.
	 */
	private void randomiseChance() {

		int chance = (int) (Math.random() * 10);
		System.out.println("chance is:" + chance);
		if (chance == 0) {

			isVisible = false;
		} else {
			isVisible = true;
		}

	}
		
	

	
	public String toString() {
		return "EnemyBall [area=" + area.toString() + ", xSpeed=" + xSpeed + ", ySpeed="
				+ ySpeed + ", isVisible=" + isVisible + "]";
	}

	/*
	 * The method getIsVisible() returns if the ball is visible or not.
	 */
	public boolean getIsVisible() {

		return isVisible;
	}

	/*
	 * The method setIsVisible() will set the visibility to the input status..
	 */
	public void setIsVisible(boolean status) {
		if (status != true) {
			isVisible = false;
		}

	}

	/*
	 * The getCentrePoint() return the centre point of the enemey. This was made
	 * as specified by the assignment and used for the collision detetction.
	 */
	public Point getCentrePoint() {
		Point centre;

		int xPos = area.x + (area.width / 2);
		int yPos = area.y + (area.height / 2);
		centre = new Point(xPos, yPos);
		return centre;
	}

	/*
	 * The printAreaInfo() is used for debugging purposes.
	 */
	private void printAreaInfo() {

		System.out.println("checkpoint at " + counter + " : " + area);
		counter++;

	}

	/*
	 * The getEnemy() is used to return the enemy.
	 */
	public Rectangle getEnemy() {
		return area;
	}

}