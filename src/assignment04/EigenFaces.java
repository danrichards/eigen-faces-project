package assignment04;

import java.awt.Button;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;

import csc450Lib.linalg.base.ColumnVector;
import csc450Lib.linalg.base.Matrix;

/**
 * Write a Eigenfaces implementation.
 * 
 * @notes I had to re-write a lot of my functions in the Matrix library to accommodate integers.
 * 
 * @author Dan Richards
 * @date 5/10/2015
 * @professor Herve
 *
 */
@SuppressWarnings("serial")
public class EigenFaces extends JFrame implements ActionListener {
	
	public Images images;
	public JPanel grid;
	public int cols;
	public int windowWidth;
	public int windowHeight;
	
	/**
	 * Connect to MathLink
	 */
	public static KernelLink ml = null;
	public static String[] mathematica = new String[] {"./MathLinkTest", "-linkmode", "launch", "-linkname", "\"C:/Program Files\\Wolfram Research\\Mathematica\\10.0\\MathKernel.exe\" -mathlink"};
		
	/**
	 * We may want to use a subset later, so we'll pass our library as an array.
	 */
	public String[] trainingSet = new String[] {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19"};

	public EigenFaces() {	
		System.out.println("Images should be located in " + Images.directory("images"));
		images = new Images(trainingSet, ml);
        
		initWindow();
		
        addImageGridToWindow(trainingSet.length);
        
        addAverageImageToWindow();
        
        addProbeImageToWindow();

        addProbePhiImageToWindow();

        addAbsProbePhiImageToWindow();
        
        addInvAbsProbePhiImageToWindow();
	}
	
	/**
	 * Draw probe image we've configured to try to recognize in Images.java
	 * 
	 * @see Images.java globals
	 */
	private void addProbeImageToWindow() {
		JLabel imgWithLabel = new JLabel("Selected Probe", new ImageIcon(images.probe()), JLabel.CENTER);
    	imgWithLabel.setVerticalTextPosition(JLabel.BOTTOM);
    	imgWithLabel.setHorizontalTextPosition(JLabel.CENTER);
    	getContentPane().add(imgWithLabel);
    	imgWithLabel.setBounds(170, 0 , images.widthOfFirst(), images.heightOfFirst() + 50); 
        revalidate();
        repaint();		
	}

	/**
	 * Draw probe image minus the mean
	 */
	private void addProbePhiImageToWindow() {
		JLabel imgWithLabel = new JLabel("Probe - Mean", new ImageIcon(Images.renderImageFromArray(images.probePhi(), images.widthOfFirst())), JLabel.CENTER);
    	imgWithLabel.setVerticalTextPosition(JLabel.BOTTOM);
    	imgWithLabel.setHorizontalTextPosition(JLabel.CENTER);
    	getContentPane().add(imgWithLabel);
    	imgWithLabel.setBounds(330, 0 , images.widthOfFirst(), images.heightOfFirst() + 50); 
        revalidate();
        repaint();		
	}

	/**
	 * Draw probe image minus the mean
	 */
	private void addAbsProbePhiImageToWindow() {
		Matrix fill255 = new Matrix(images.widthOfFirst(), images.heightOfFirst(), 255f);
		Matrix probePhi = new Matrix(ColumnVector.split(images.probePhi(), images.heightOfFirst()));
		JLabel imgWithLabel = new JLabel("Abs(Probe - Mean)", new ImageIcon(Images.renderImageFromArray(ColumnVector.abs(images.probePhi()), images.widthOfFirst())), JLabel.CENTER);
    	imgWithLabel.setVerticalTextPosition(JLabel.BOTTOM);
    	imgWithLabel.setHorizontalTextPosition(JLabel.CENTER);
    	getContentPane().add(imgWithLabel);
    	imgWithLabel.setBounds(490, 0 , images.widthOfFirst(), images.heightOfFirst() + 50); 
        revalidate();
        repaint();
	}

	/**
	 * Draw probe image minus the mean
	 */
	private void addInvAbsProbePhiImageToWindow() {
		ColumnVector absProbePhi = ColumnVector.abs(images.probePhi());
		ColumnVector cv255 = new ColumnVector(absProbePhi.rows(), 255f);
		ColumnVector inverted = Matrix.subtract(cv255, absProbePhi);
				
		JLabel imgWithLabel = new JLabel("Inv(Abs(Probe - Mean))", new ImageIcon(Images.renderImageFromArray(inverted, images.widthOfFirst())), JLabel.CENTER);
    	imgWithLabel.setVerticalTextPosition(JLabel.BOTTOM);
    	imgWithLabel.setHorizontalTextPosition(JLabel.CENTER);
    	getContentPane().add(imgWithLabel);
    	imgWithLabel.setBounds(650, 0 , images.widthOfFirst(), images.heightOfFirst() + 50); 
        revalidate();
        repaint();
	}
	
	/**
	 * Draw the average face to the window
	 */
	private void addAverageImageToWindow() {
		JLabel imgWithLabel = new JLabel("Average Face", new ImageIcon(Images.renderImageFromArray(images.average(), images.widthOfFirst())), JLabel.CENTER);
    	imgWithLabel.setVerticalTextPosition(JLabel.BOTTOM);
    	imgWithLabel.setHorizontalTextPosition(JLabel.CENTER);
    	getContentPane().add(imgWithLabel);
    	imgWithLabel.setBounds(10, 0 , images.widthOfFirst(), images.heightOfFirst() + 50); 
        revalidate();
        repaint();
	}
	
	/**
	 * Draw a grid of the images in our library
	 */
	private void addImageGridToWindow(int howMany) {
        grid = new JPanel(null);
        grid.setBounds(10, 200, images.widthOfFirst() * cols + 20, (int) (images.heightOfFirst() * Math.ceil(images.count() / (double) cols) + 150));
        grid.setBorder(BorderFactory.createTitledBorder("Image Library with Error Ratings (Lowest is best)"));
        for (int i = 0; i < howMany; i++) {
        	JLabel imgWithLabel = new JLabel(NumberFormat.getInstance().format(images.e(i)), new ImageIcon(images.getBufferedImage(i)), JLabel.CENTER);
        	imgWithLabel.setVerticalTextPosition(JLabel.BOTTOM);
        	imgWithLabel.setHorizontalTextPosition(JLabel.CENTER);
        	grid.add(imgWithLabel);
        	imgWithLabel.setBounds(i % cols * images.widthOfFirst() + 10, i / cols * (images.heightOfFirst() + 50) + 10, images.widthOfFirst(), images.heightOfFirst() + 50);
        }
        add(grid);
        grid.setVisible(true);     
        revalidate();
        repaint();
	}

	/**
	 * Initialize the window.
	 * 
	 * @see http://stackoverflow.com/questions/3680221/how-can-i-get-the-monitor-size-in-java
	 */
	private void initWindow() {
		setTitle("Eigen Faces");
		
		// Maximize the Window
        setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        windowWidth = gd.getDisplayMode().getWidth();
        windowHeight = gd.getDisplayMode().getHeight();
        cols = (windowWidth - 60) / images.widthOfFirst();
        setBounds(0, 0, windowWidth, windowHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        try {
        	setVisible(true);
            getContentPane().setLayout(null);
        } catch (Exception e) {
        	System.out.println("An error has occurred.");
            e.printStackTrace();
        }
        
        // draw a grid and some buttons
        Button refresh = new Button("Refresh");
        refresh.setLocation(0, 12);
        refresh.setSize(100,30);
        refresh.addActionListener(this);
        
        JPanel panel = new JPanel(null);
        panel.add(refresh);
        panel.setBounds(windowWidth - 210, 10, 200, 150);
        
        add(panel);
	}
	

	public void actionPerformed(ActionEvent e) {
    	System.out.println("refreshed");
        revalidate();
        repaint();
    }
	
	/**
	 * Run it
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {			
		try {
			ml = MathLinkFactory.createKernelLink(mathematica);
		} catch (MathLinkException e) {
			System.out.println("Fatal error opening link: " + e.getMessage());
			return;
		}
		
		new EigenFaces();
	}
	
}
