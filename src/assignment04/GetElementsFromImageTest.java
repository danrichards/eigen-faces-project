package assignment04;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GetElementsFromImageTest {
	
	public GetElementsFromImageTest() {
		
		try {
			System.out.println("HORIZONTAL TEST:");
			
			String file = Images.directory("images") + "horizontal-test.bmp";
			BufferedImage img = ImageIO.read(new File(file));

			
			for (int y = 0; y < img.getHeight(); y++) {
				for (int x = 0; x < img.getWidth(); x ++) {
					int rgb = img.getRGB(x,y);
					Color c = new Color(rgb);
					int red = c.getRed();
					int green = c.getGreen();
					int blue = c.getBlue();
					int gray = (299 * red + 587 * green + 114 * blue) / 1000;
					System.out.print(Integer.toString(gray) + "\t");
				}
				System.out.println("");			
			}			
						
			System.out.println("\n\n\n\n\n\n\n\n\nVERTICAL TEST:");
			
			file = Images.directory("images") + "vertical-test.bmp";
			img = ImageIO.read(new File(file));
			
			for (int y = 0; y < img.getHeight(); y++) {
				for (int x = 0; x < img.getWidth(); x ++) {
					int rgb = img.getRGB(x,y);
					Color c = new Color(rgb);
					int red = c.getRed();
					int green = c.getGreen();
					int blue = c.getBlue();
					int gray = (299 * red + 587 * green + 114 * blue) / 1000;
					System.out.print(Integer.toString(gray) + "\t");
				}
				System.out.println("");			
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //getClass().getResource("/0.bmp")
		
	}

	public static void main(String[] args) {
		new GetElementsFromImageTest();
	}

}
