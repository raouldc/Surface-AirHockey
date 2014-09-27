/*
 * Constants for Assignment 3
 * 
 * Author: Adriana Ferraro
 * Date: SS 2011
*/

import java.awt.*;

public class A3Constants {
//-------------------------------------------------------
// width, height of game area and gap
//-------------------------------------------------------
	public static final int GAP = 60;
	public static final int SMALL_GAP = 30;
	public static final int GAME_AREA_WIDTH = 600;
	public static final int GAME_AREA_HEIGHT = GAME_AREA_WIDTH;
	
	
//-------------------------------------------------------
// Game area definitions
//-------------------------------------------------------
	public static final Rectangle GAME_AREA = new Rectangle(GAP, GAP, GAME_AREA_WIDTH, GAME_AREA_HEIGHT);
	public static final int GAME_AREA_TOP = GAME_AREA.y;
	public static final int GAME_AREA_BOTTOM = GAME_AREA.y + GAME_AREA.height;
	public static final int GAME_AREA_RHS = GAME_AREA.x + GAME_AREA.width;
	public static final int GAME_AREA_LHS = GAME_AREA.x;

//-------------------------------------------------------
// Constants to do with the enemy balls
//-------------------------------------------------------	
	public static final int ENEMY_BALL_SIZE = 20;
	public static final int NUMBER_OF_ENEMIES = 15;
	public static final int MIN_SPEED = 10;
	public static final int MAX_SPEED = 18;
		
//-------------------------------------------------------
// Constants for the four possible enemy start positions
//-------------------------------------------------------
	public static final Rectangle[] ENEMY_INITIAL_POSITIONS = {
		new Rectangle(GAME_AREA.x, GAME_AREA.y, ENEMY_BALL_SIZE, ENEMY_BALL_SIZE), 									
		new Rectangle(GAME_AREA.x + GAME_AREA.width - ENEMY_BALL_SIZE, GAME_AREA.y, ENEMY_BALL_SIZE, ENEMY_BALL_SIZE),  
		new Rectangle(GAME_AREA.x + GAME_AREA.width - ENEMY_BALL_SIZE, GAME_AREA.y + GAME_AREA.height - ENEMY_BALL_SIZE, ENEMY_BALL_SIZE, ENEMY_BALL_SIZE),  
		new Rectangle(GAME_AREA.x, GAME_AREA.y + GAME_AREA.height - ENEMY_BALL_SIZE,  ENEMY_BALL_SIZE, ENEMY_BALL_SIZE), 
	} ;

//-------------------------------------------------------
// Colours for the enemy balls
//-------------------------------------------------------	
	public static final Color[] COLOURS = {Color.CYAN, Color.MAGENTA, Color.RED, Color.PINK, Color.ORANGE, Color.YELLOW};
	
//-------------------------------------------------------
// Constants defining the game area walls
//-------------------------------------------------------	
	public static final int NORTH_WALL = 0;
	public static final int EAST_WALL = NORTH_WALL + 1;
	public static final int SOUTH_WALL = EAST_WALL + 1;
	public static final int WEST_WALL = SOUTH_WALL + 1;

//-------------------------------------------------------
// Constants for the player points start, win, lose
//-------------------------------------------------------	
	public static final int INITIAL_POINTS = 25;
	public static final int WIN_GAME_POINTS = INITIAL_POINTS * 2;
	public static final int LOSE_GAME_POINTS = 0;
	public static final int LOSS_GAIN_PER_HIT = 3;
	
//-------------------------------------------------------
// Constants to do with the player 
//-------------------------------------------------------	
	public static final int PLAYER_INITIAL_SIZE = ENEMY_BALL_SIZE * 3 / 2;
	public static final int PLAYER_SIZE_INCREASE = 2;
	public static final int PLAYER_MAX_SIZE = PLAYER_INITIAL_SIZE * 3;
	public static final Rectangle PLAYER_STARTING_POSITION = new Rectangle(GAME_AREA.x + GAME_AREA.width/2 - PLAYER_INITIAL_SIZE/2, GAME_AREA.y + GAME_AREA.height/2 - PLAYER_INITIAL_SIZE/2, PLAYER_INITIAL_SIZE, PLAYER_INITIAL_SIZE);
	public static final int PLAYER_SPEED = 15;
	
//-------------------------------------------------------
// Constants for the four  player directions
//-------------------------------------------------------	
	public static final int UP = 0;
	public static final int LEFT = UP + 1;
	public static final int DOWN = LEFT + 1;
	public static final int RIGHT = DOWN + 1;	

//-------------------------------------------------------
// Constants to do with the munchy objects
//-------------------------------------------------------	
	public static final int NUMBER_OF_MUNCHIES = 10;
	public static final int MUNCHY_MIN_SIZE = 10;
	public static final int MUNCHY_MAX_SIZE = MUNCHY_MIN_SIZE * 2;	
	
//-------------------------------------------------------
// Fonts and sizes
//-------------------------------------------------------
	public static final int SMALL_FONT_SIZE = 16;
	public static final int MEDIUM_FONT_SIZE = 20;
	public static final int LARGE_FONT_SIZE = 30;
	public static final int HUGE_FONT_SIZE = 60;
	public static final Font MEDIUM_FONT = new Font("SansSerif", Font.CENTER_BASELINE, MEDIUM_FONT_SIZE);
	public static final Font SMALL_FONT = new Font("GENEVA", Font.CENTER_BASELINE, SMALL_FONT_SIZE);
	public static final Font LARGE_FONT = new Font("GENEVA", Font.CENTER_BASELINE, LARGE_FONT_SIZE);	
	public static final Font HUGE_FONT = new Font("SansSerif", Font.BOLD, HUGE_FONT_SIZE);
	
	
	//-------------------------------------------------------
	// Bullets
	//-------------------------------------------------------
	public static final int NUMBER_OF_BULLETS = 15;
	
	
	//-------------------------------------------------------
	// Scoreboard locations
	//-------------------------------------------------------
	public static final int SCOREBOARD_X=560;
	public static final int SCOREBOARD_Y=55;
	
}
