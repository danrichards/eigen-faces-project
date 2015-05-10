package assignment04;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GetImageFromElementsTest extends JFrame {
	public JPanel grid;
	public BufferedImage faceImageFromFile;
	public float[][] faceMatrix;
		
	public GetImageFromElementsTest() {

		initWindow();        
        
		try {
			System.out.println("HORIZONTAL TEST:");
			
			String file = Images.directory("Images") + "0.bmp";
			faceImageFromFile = ImageIO.read(new File(file));
			faceMatrix = new float[faceImageFromFile.getWidth()][faceImageFromFile.getHeight()];
			System.out.println("Image file: " + file);
			
			for (int y = 0; y < faceImageFromFile.getHeight(); y++) {
				for (int x = 0; x < faceImageFromFile.getWidth(); x ++) {
					int rgb = faceImageFromFile.getRGB(x, y);
					Color c = new Color(rgb);
					int red = c.getRed();
					int green = c.getGreen();
					int blue = c.getBlue();
					faceMatrix[x][y] = (299 * red + 587 * green + 114 * blue) / 1000;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addImagesGridToWindow();
		
	}
	
	/**
	 * Initialize the window.
	 */
	public void initWindow() {
		setBounds(100, 100, 400, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
        	setVisible(true);
            getContentPane().setLayout(null);
        } catch (Exception e) {
        	System.out.println("An error has occurred.");
            e.printStackTrace();
        }
	}
	
	/**
	 * Draw a grid of the images in our library
	 */
	public void addImagesGridToWindow() {        
        // start a grid
        grid = new JPanel(null);
        grid.setBounds(10, 10, 360, 200);
        grid.setBorder(BorderFactory.createTitledBorder("Vertical and Horizontal Tests"));
        
        // add images to the grid
        
        // first, output the actually Buffered Image from a file        
        JLabel imgWithLabel = new JLabel("Face from file", new ImageIcon(faceImageFromFile), JLabel.CENTER);
    	imgWithLabel.setVerticalTextPosition(JLabel.BOTTOM);
    	imgWithLabel.setHorizontalTextPosition(JLabel.CENTER);
    	grid.add(imgWithLabel);
    	imgWithLabel.setBounds(10, 10, 150, 200);

    	// now, output the Buffered Image rendered from a matrix
        imgWithLabel = new JLabel("Face from matrix", new ImageIcon(Images.renderImageFromMatrix(faceMatrix)), JLabel.CENTER);
    	imgWithLabel.setVerticalTextPosition(JLabel.BOTTOM);
    	imgWithLabel.setHorizontalTextPosition(JLabel.CENTER);
    	grid.add(imgWithLabel);
    	imgWithLabel.setBounds(200, 10, 150, 200);
    	
    	// add the grid to the frame
    	add(grid);
        grid.setVisible(true);     
        revalidate();
        repaint();
	}

	public static void main(String[] args) {
		new GetImageFromElementsTest();
	}

}
