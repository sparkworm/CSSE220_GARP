package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class DemoImageComponent extends JComponent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		String fileName = "/images/example.png";
		BufferedImage img;
		try {
			//this can be used safely when exporting to a JAR
			InputStream stream = getClass().getResourceAsStream(  fileName );
			img = ImageIO.read( stream );
			g2d.drawImage(img, 0, 0, 100, 100, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // paintComponent
	
	
	
}
