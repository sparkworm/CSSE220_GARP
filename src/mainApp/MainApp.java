package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Class: MainApp
 * @author Team F25R301
 * <br>Purpose: Top level class for CSSE220 Project containing main method
 * <br>Restrictions: None
 */
public class MainApp {
	
	
	private void runApp() {
		System.out.println("Write your cool final project here!");
        System.out.println("Second Print statement");
		
		//Launch image demo in a Frame
		imageDemo();
		
	} // runApp
	
	
	private void imageDemo() {
		JFrame frame = new JFrame("CSSE220 Final Project Demo");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add( new DemoImageComponent() );		
		frame.setVisible(true);
	}
	

	/**
	 * ensures: runs the application
	 * @param args unused
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();		
	} // main

}