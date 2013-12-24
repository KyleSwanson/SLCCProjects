/**
 * FlipBookPro
 * 
 * Authors: (Team Flip) Brady Carlson, Christopher Nash, Joshua Gourley, Kyle Swanson, Zach Pollock
 * 
 * Description: CS2450 - Software Engineering term project
 * 
 * Copyright 2012
 * 
 */


package FlipBookPro;

public class FlipBookPro_Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserInterface().setVisible(true);
            }
        });
	}

}
