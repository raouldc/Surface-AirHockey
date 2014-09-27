/*
 * Purpose: creates and moves computer controlled player.
 * 
 * Author: Adriana Ferraro
 * Date: SS 2011
*/

import javax.swing.*;

public class A3 {
	public static final int GAP = A3Constants.GAP;
	public static final int GAME_AREA_WIDTH = A3Constants.GAME_AREA_WIDTH;
	public static final int GAME_AREA_HEIGHT = A3Constants.GAME_AREA_HEIGHT;
	
	public static void main(String[] args) {
		JFrame gui = new A3JFrame("A3 by kand617", 3, 3, GAME_AREA_WIDTH + GAP * 2, GAME_AREA_HEIGHT + GAP * 2);
	}
}
