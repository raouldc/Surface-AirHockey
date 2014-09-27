/*
 * The A3Jpanel class.
 * 
 * Author: Kartik Andalam
 * UPI:kand617
 * Date: 10/02/2011
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JPanel;
import javax.swing.Timer;

public class A3JPanel extends JPanel implements KeyListener, ActionListener,MouseListener {
	public static final int NUMBER_OF_ENEMIES = A3Constants.NUMBER_OF_ENEMIES;
	public static final Rectangle GAME_AREA = A3Constants.GAME_AREA;
	public Player user;
	public EnemyBall[] enemies;
	private Timer t;
	boolean hasGameStarted;
	boolean activatedGun;
	boolean hasGameEnded;

	private Bullet bullet;
	ServerSocket listener;
	public A3JPanel() {

		try {
			listener = new ServerSocket(9090);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		enemies = new EnemyBall[A3Constants.NUMBER_OF_ENEMIES];
		//enemies = new EnemyBall [1] ; // used for ease of tracking and testing features.

		for (int i = 0; i < enemies.length; i++) {
			// Making an array of enemies.

			enemies[i] = new EnemyBall();

		}

		bullet = new Bullet();
		activatedGun = false;

		user = new Player();

		hasGameStarted = false;
		hasGameEnded = false;

		t = new Timer(100, this);


		addKeyListener(this);
		addMouseListener(this);
		
		try {
			getInputFromNetwork();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	public void generateKeyEvent(){

		int random = (int)(Math.random()*4);


		switch (random) {
		case 0:
			user.move(A3Constants.UP);
			break;
		case 1:
			user.move(A3Constants.DOWN);
			break;
		case 2:
			user.move(A3Constants.RIGHT);
			break;
		default:
			user.move(A3Constants.LEFT);
			break;
		}


	}

	public void getInputFromNetwork() throws IOException{

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				Socket socket=null;

				try {
					while(true){
						socket = A3JPanel.this.listener.accept();
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						String x ="";
						while((x=in.readLine())!=null){
							System.out.println(x);
						}


						A3JPanel.this.generateKeyEvent();
						A3JPanel.this.repaint();

					}
				} catch(Exception e ){

				}finally {

					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}


			}
		});
		
		t.start();

	}






	public void keyPressed(KeyEvent e) {

		if (hasGameStarted && !hasGameEnded) {
			// Only take input from the keyboard if the game has started and not
			// ended. This is for moving the player only.

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				user.move(A3Constants.UP);

			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				user.move(A3Constants.DOWN);

			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				user.move(A3Constants.LEFT);

			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				user.move(A3Constants.RIGHT);

			}
			t.start();// Start the timer if any key is pressed.

			if (e.getKeyChar() == 'p') {
				t.stop();// stops the timer.
			}

			repaint();

		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		fillRectangle(g, GAME_AREA, Color.BLACK); //draws the rectangle by calling on a method. 


		if (user.hasBeenHit(enemies)) {
			user.increaseSize();
			if (user.hasReachedMax()) {
				hasGameEnded = true;
			}
		}
		if (hasGameStarted == false) {
			drawTitleScreen(g);
		} else if (hasGameEnded) {
			drawEndScreen(g);
			t.stop();

		} else {
			drawScoreBoard(g);
			user.draw(g);
			for (int i = 0; i < enemies.length; i++) {
				enemies[i].draw(g);
			}
			if (activatedGun) { // got license to kill
				bullet.draw(g);

			}

		}

	}

	/*
	 * The method is used to draw any given Rectangle with the "color" specified.
	 */
	private void fillRectangle(Graphics g, Rectangle rectToDraw, Color fillColor) {

		g.setColor(fillColor);
		g.fillRect(rectToDraw.x, rectToDraw.y, rectToDraw.width,rectToDraw.height);
		g.setColor(Color.BLACK);

	}

	/*
	 * The actionPerformed is related to the timer, 
	 * When the timer starts the enemies and bullets move in phase. 
	 */
	public void actionPerformed(ActionEvent e) {
		if (t.isRunning()) {

			for (int i = 0; i < enemies.length; i++) {
				enemies[i].move();
			}
			bullet.move();
			bullet.analyseCollison(enemies);

			repaint();
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	/*
	 * the Method Draws the title screen.
	 */
	public void drawTitleScreen(Graphics g) {

		String nameOfTheGame = "SQUARE INVADERS 2.5";
		String LoginName = "Kand617, ";
		String stageCompleted = "completed ALL Stages";
		String instructions1 = "Instructions: ";
		String instructions2 = "1) Click to start, use arrow keys to move,use p to pause";
		String instructions3 = "2) Avoid the small enemies";
		String instructions4 = "3) Click mouse to launch bullets, and kill enemies";
		String instructions5 = "4) Earn point by killing enemies.";
		String instructions6 = "5) If you collide with the enemies, you get bigger & lose life";
		String instructions7 = "6) Game is over when your lives remaining is zero!!";



		fillRectangle(g, GAME_AREA, Color.BLACK);
		g.setColor(Color.YELLOW);
		g.setFont(A3Constants.LARGE_FONT);
		g.drawString(nameOfTheGame, A3Constants.GAME_AREA.x + A3Constants.GAP* 1, A3Constants.GAME_AREA.y + A3Constants.GAP * 1);

		g.setFont(A3Constants.SMALL_FONT);
		g.drawString(LoginName + stageCompleted, A3Constants.GAME_AREA.x+ A3Constants.GAP * 1, A3Constants.GAME_AREA.y+ A3Constants.SMALL_GAP * 3);


		g.setColor(Color.WHITE);
		g.setFont(A3Constants.SMALL_FONT);
		g.drawString(instructions1, A3Constants.GAME_AREA.x + A3Constants.SMALL_GAP * 2, A3Constants.GAME_AREA.y + A3Constants.GAP * 3);
		g.drawString(instructions2, A3Constants.GAME_AREA.x	+ A3Constants.SMALL_GAP * 2, A3Constants.GAME_AREA.y + A3Constants.GAP * 3 + A3Constants.SMALL_GAP);
		g.drawString(instructions3, A3Constants.GAME_AREA.x	+ A3Constants.SMALL_GAP * 2, A3Constants.GAME_AREA.y + A3Constants.GAP * 4);
		g.drawString(instructions4, A3Constants.GAME_AREA.x	+ A3Constants.SMALL_GAP * 2, A3Constants.GAME_AREA.y + A3Constants.GAP * 4 + A3Constants.SMALL_GAP);
		g.drawString(instructions5, A3Constants.GAME_AREA.x	+ A3Constants.SMALL_GAP * 2, A3Constants.GAME_AREA.y + A3Constants.GAP * 5);
		g.drawString(instructions6, A3Constants.GAME_AREA.x	+ A3Constants.SMALL_GAP * 2, A3Constants.GAME_AREA.y + A3Constants.GAP * 5 + A3Constants.SMALL_GAP);
		g.drawString(instructions7, A3Constants.GAME_AREA.x	+ A3Constants.SMALL_GAP * 2, A3Constants.GAME_AREA.y + A3Constants.GAP * 6);

	}

	/*
	 * This method is used for drawing the score board.
	 */
	public void drawScoreBoard(Graphics g) {

		String points = "Score: " + Bullet.collisonCounter;
		String lives = "Lives remaining: " + user.getLives();

		g.setColor(Color.BLUE);
		g.setFont(A3Constants.SMALL_FONT);
		// Draws lives
		g.drawString(lives, A3Constants.GAP, A3Constants.SCOREBOARD_Y);
		// Draws the score/number of collisions between bullet and enemies
		g.drawString(points, A3Constants.SCOREBOARD_X, A3Constants.SCOREBOARD_Y);
		g.setColor(Color.BLACK);

	}

	public void mousePressed(MouseEvent e) {
		// sets hasGameStarted to true thus displaying the game area

		hasGameStarted = true;
		if(!hasGameEnded)
			System.out.println(hasGameStarted);
		repaint();

		if (t.isRunning()) {
			activatedGun = true;
			if (activatedGun == true) { // can kill

				if (!bullet.isActive) {
					// activate bullet
					bullet.isActive = true;
					// store launch info
					bullet.setIsVisible(true);
					bullet.launchPoint = user.getMiddlePos();
					bullet.updateLaunchPoint();
				}

			}

		}

	}

	/*
	 * The method drawEndScreen() draws the Game over screen.
	 */
	private void drawEndScreen(Graphics g) {
		String gameOver = "GAME OVER!!!";
		String score = "Your score: " + Bullet.collisonCounter;
		String thanks = "Thanks for playing =)";

		g.setColor(Color.WHITE);
		g.setFont(A3Constants.LARGE_FONT);
		g.drawString(gameOver, A3Constants.GAP * 5, A3Constants.GAP * 5);
		g.setFont(A3Constants.SMALL_FONT);
		g.drawString(score, A3Constants.GAP * 6, A3Constants.GAP * 6);
		g.setFont(A3Constants.SMALL_FONT);
		g.drawString(thanks, A3Constants.GAP * 6, A3Constants.GAP * 7);

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

}
