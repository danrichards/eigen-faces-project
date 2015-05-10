package assignment04;

import com.wolfram.jlink.*;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import csc450Lib.linalg.base.ColumnVector;
import csc450Lib.linalg.base.Matrix;

public class Images {
	
	/**
	 * Additional Console output
	 */
	public final boolean DEBUG = true;
	
	/**
	 * The number of images in our library
	 */
	public int M;
	
	/**
	 * The images in our library
	 */
	public BufferedImage files[];
	
	/**
	 * The image we'll try and recognize in our library. Sample are in src/probes.
	 */
	public BufferedImage probe;
	
	/**
	 * The probe minus mean average 
	 */
	public ColumnVector probePhi;
	
	/**
	 * The probe's weights with respect to u (eigen faces)
	 */
	public ColumnVector probeOmega;
	
	/**
	 * The norm of Omega[i] - probeOmega
	 */
	public float[] e;
	
	/**
	 * Index of image with lowest e
	 */
	public int best;
		
	/**
	 * An array of M matrices (N x N)
	 */
	public Matrix[] Gamma;
	
	/**
	 * M column vectors (rows transposed and appended upon one another)
	 */
	public ColumnVector[] GammaCV;
	
	/**
	 * [Phi1, Phi2, PhiM], differences from average, M x N^2)
	 */
	public Matrix A;
	
	/**
	 * Our average column vector (N^2 x 1)
	 */
	public ColumnVector Psi;
	
	/**
	 * Covariance, an M x M matrix calculated from Transpose[A] * A
	 */
	public Matrix L;
	
	/**
	 * M Eigen vectors (M x 1), EigenVectors[L]. Conveniently, Mathematica returns our eigen vectors descending by their eigen values
	 */
	public ColumnVector[] v;
	
	/**
	 * M Eigen values corresponding to A, u and v (unused)
	 */
	public float[] rank;

	/**
	 * M Eigen vectors (M x 1), EigenVectors[L].
	 */
	public ColumnVector[] u;
	
	/**
	 * Weights generated for our training with our eigen faces
	 */
	public ColumnVector[] Omega;
	
	/**
	 * Connect to Mathematica
	 */
	public static KernelLink ml = null;
	
	/**
	 * Constructor
	 * 
	 * @param set
	 */
	public Images(String[] set, KernelLink ml) {
		/**
		 * Include MathLink
		 */
		this.ml = ml;
		
		/**
		 * Number of training images
		 */
		M = set.length;
		
		/**
		 * Load our library
		 */
		loadImages(set);
		
		/**
		 * Run calculations, average, diffs, covariance
		 */
		turk(set);
		
		/**
		 * Load our probe
		 */
		loadProbe(2);
		
		/**
		 * Run recognition
		 */
		recognize();
	}
	
	private void recognize() {
		/**
		 * Find probeOmega
		 */		
		float[] w = new float[M];
		for (int j = 0; j < M; j++)
			w[j] = Matrix.multiply(Matrix.transpose(u[j]), probePhi).get(0, 0);
		probeOmega = new ColumnVector(w);
		
		/**
		 * Iterate through Omega, subtract probeOmega, store the norm() in the error rating. Find the lowest norm().
		 * 
		 * @return
		 */
		float lowest = 0;
		int needle = 0;
		e = new float[M];
		for (int i = 0; i < Omega.length; i++) {
			e[i] = Matrix.subtract(Omega[i], probeOmega).norm();
			if (i == 0) {
				lowest = e[i];
			} else if(e[i] < lowest) {
				lowest = e[i];
				needle = i;
			}
		}
		best = needle;		
	}

	/**
	 * Load the probe image
	 * 
	 * @param i
	 */
	private void loadProbe(int i) {
		probe = loadImage(Images.directory("probes") + Integer.toString(i) + ".bmp");
		ColumnVector probeCV = new ColumnVector(Images.renderArrayFromImage(probe));
		probePhi = Matrix.subtract(probeCV, Psi);
		
		Matrix output = new Matrix(ColumnVector.split(probePhi, files[0].getWidth()));
		if (DEBUG) System.out.println("probe Phi (" + output.rows() + "x" + output.cols() +")");
//		if (DEBUG) System.out.println(output.toString("{", "}", ",", true));
	}

	/**
	 * Load the images from our set.
	 * 
	 * @param set
	 */
	private void loadImages(String[] set) {
		/**
		 * Load all the images and render their respective gray scale matrices (integers 0-255)
		 */
		files = new BufferedImage[M];
		Gamma = new Matrix[M];
		GammaCV = new ColumnVector[M];
		
		for (int i = 0; i < M; i++) {
			files[i] = loadImage(Images.directory("images") + set[i] + ".bmp");
			Gamma[i] = Images.renderMatrixFromImage(files[i]);
			GammaCV[i] = new ColumnVector(Images.renderArrayFromImage(files[i]));
		}
	}

	/**
	 * Load the images into our global
	 */
	public void turk(String[] set) {		
		u = new ColumnVector[M];
		v = new ColumnVector[M];
		Omega = new ColumnVector[M];
		
		/**
		 * Get the average of all the training images (N^2 Column Vectors)
		 */
		Psi = ColumnVector.average(GammaCV);
		
		/**
		 * Calculates Phi1, Phi2, PhiM (training image - mean average)
		 */
		ColumnVector[] cvA = new ColumnVector[M];
		for (int i = 0; i < M; i++)
			cvA[i] = Matrix.subtract(GammaCV[i], Psi);
		A = new Matrix(cvA);
		if (DEBUG) System.out.println("diffs calculated (" + Integer.toString(A.rows()) + " x " + Integer.toString(A.cols()) + ")");
		
		/**
		 * Calculates A^T . A, an M x M matrix. Instead of an N^2 x N^2 matrix
		 */
		L = Matrix.covariance(A);
		if (DEBUG) System.out.println("covariance calculated (" + Integer.toString(L.rows()) + " x " + Integer.toString(L.cols()) + ")");
		if (DEBUG) System.out.println(L.toString("{", "}, ", ", ", true));
		
		/**
		 * Calculates eigen vectors v
		 */
		v = Matrix.getEigenVectors(ml, L);
		if (DEBUG) System.out.println("v vectors calculated (" + Integer.toString(v.length) + " x " + Integer.toString(v[0].rows()) + ")");
		if (DEBUG) System.out.println((new Matrix(v).toString("{", "}", ",", true)));
		
		/**
		 * Calculates eigen-faces, M (N^2 ColumnVectors) u
		 */
		for (int i = 0; i < M; i++)
			u[i] = Matrix.multiply(A, v[i]);
		if (DEBUG) System.out.println("u vectors calculated (" + Integer.toString(u.length) + " x " + Integer.toString(u[0].rows()) + ")");
		
		/**
		 * Calculates the weights with respect to each Phi (training image - mean)
		 */
		for (int i = 0; i < M; i++) {			
			float[] w = new float[M];
			for (int j = 0; j < M; j++) {
				w[j] = Matrix.multiply(Matrix.transpose(u[j]), new ColumnVector(A.getCol(i))).get(0, 0);
			}
			Omega[i] = new ColumnVector(w);
		}
		if (DEBUG) System.out.println("Omega calculated (" + Integer.toString(Omega.length) + " x " + Integer.toString(Omega[0].rows()) + ")");
		
	}
	
	/**
	 * Load an image into our library
	 * 
	 * @param file
	 * @param i
	 */
	public BufferedImage loadImage(String file) {
		try {
			BufferedImage img = ImageIO.read(new File(file));				
			if (img.getType() != BufferedImage.TYPE_BYTE_GRAY) {
				BufferedImage tempImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);					
				img = tempImage;
			}
			return img;
		} catch (IOException e) {
			System.out.println("Invalid image path: " + file);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Number of images in the library.
	 * 
	 * @return
	 */
	public int count() {
		return M;
	}
	
	/**
	 * Width of the first image in the library
	 * 
	 * @return int
	 */
	public int widthOfFirst() {
		return files[0].getWidth();
	}
	
	/**
	 * Height of the first image in the library
	 * 
	 * @return int
	 */
	public int heightOfFirst() {
		return files[0].getHeight();
	}
	
	/**
	 * Buffered image that has been loaded.
	 * 
	 * @param i
	 * @return BufferedImage
	 */
	public BufferedImage getBufferedImage(int i) {
		return this.files[i];
	}
	
	/**
	 * Image matrix that has already been rendered.
	 * 
	 * @param i
	 * @return BufferedImage
	 */
	public Matrix getMatrixImage(int i) {
		return this.Gamma[i];
	}
	
	/**
	 * Single pixel from a single image
	 * @param i
	 * @param x
	 * @param y
	 * @return
	 */
	public int pixel(int i, int x, int y, boolean grayScale) {
		return grayScale == true 
			? Images.grayPixel(this.getBufferedImage(i).getRGB(x, y)) 
			: this.getBufferedImage(i).getRGB(x, y);
	}
	
	/**
	 * Call pixel with gray = true
	 * 
	 * @param i
	 * @param x
	 * @param y
	 * @param gray
	 * @return
	 */
	public int pixel(int i, int x, int y) {
		return (int) Gamma[i].get(x, y);
	}
	
	/**
	 * Pixel's gray
	 * 
	 * @see http://stackoverflow.com/questions/25463005/how-to-convert-an-image-to-a-0-255-grey-scale-image
	 * @param rgb
	 * @return
	 */
	public static int grayPixel(int rgb) {
		Color c = new Color(rgb);
		int red = c.getRed();
		int green = c.getGreen();
		int blue = c.getBlue();
		return (299 * red + 587 * green + 114 * blue) / 1000;
	}
	
	/**
	 * One of our images as a 2D array of integers, 0 (black) - 255 (white)
	 * 
	 * @param i
	 * @return
	 */
	public Matrix renderMatrixFromImage(int i) {
		return Images.renderMatrixFromImage(this.getBufferedImage(i));
	}
	
	/**
	 * One of our images as a 2D array of integers, 0 (black) - 255 (white)
	 * 
	 * @param i
	 * @return
	 */
	public float[][] renderPrimitiveMatrixFromImage(int i) {
		return Images.renderPrimitiveMatrixFromImage(this.getBufferedImage(i));
	}
	
	/**
	 * Image as a 2D array of integers, 0 (black) - 255 (white)
	 * 
	 * @param img
	 */
	public static float[][] renderPrimitiveMatrixFromImage(BufferedImage img) {
		float[][] img2D = new float[img.getWidth()][img.getHeight()];
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x ++) {
				img2D[x][y] = Images.grayPixel(img.getRGB(x, y)); 
			}
		}
		return img2D;
	}
	
	/**
	 * Image as a 2D array of integers, 0 (black) - 255 (white)
	 * 
	 * @param img
	 */
	public static Matrix renderMatrixFromImage(BufferedImage img) {
		return new Matrix(Images.renderPrimitiveMatrixFromImage(img));
	}
	
	/**
	 * One of our images as a ColumnVector, by column, 0 (black) - 255 (white)
	 * 
	 * @param i
	 * @return
	 */
	public ColumnVector renderColumnVectorFromImage(int i) {
		return new ColumnVector(Images.renderArrayFromImage(this.getBufferedImage(i)));
	}
	
	/**
	 * One of our images as a ColumnVector, 0 (black) - 255 (white)
	 * 
	 * @param i
	 * @return
	 */
	public static ColumnVector renderColumnVectorFromImage(BufferedImage img) {
		return new ColumnVector(Images.renderArrayFromImage(img));
	}
	
	/**
	 * One of our images as a 1D array of integers, by column, 0 (black) - 255 (white)
	 * 
	 * @param i
	 * @return
	 */
	public float[] renderArrayFromImage(int i) {
		return Images.renderArrayFromImage(this.getBufferedImage(i));
	}
	
	/**
	 * Image as a 1D array of integers, by column , 0 (black) - 255 (white)
	 * 
	 * @param img
	 */
	public static float[] renderArrayFromImage(BufferedImage img) {
		float[] img1D = new float[img.getWidth() * img.getHeight()];
		int rows = img.getHeight();
		int cols = img.getWidth();
		for (int x = 0; x < rows; x ++) {
			for (int y = 0; y < cols; y++) {
				img1D[y+(x*rows)] = Images.grayPixel(img.getRGB(x, y)); 
			}
		}
		return img1D;
	}
	
	/**
	 * Render an grayscale image from a 1D array of 0-255 floats 
	 * 
	 * @see http://stackoverflow.com/questions/23587070/using-setrgb-to-set-0-255-values-instead-of-hex-code-values
	 * @param m
	 * @return
	 */
	public static BufferedImage renderImageFromArray(float[] cv, int width) {
		int height = cv.length / width;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		int x = 0, y = 0;
		for (int i = 0; i < cv.length; i ++) {
			x = i / height;
			y = i % width;
			int rgb = (255 << 24) | ((int) cv[i] << 16) | ((int) cv[i] << 8) | (int) cv[i];
			img.setRGB(x, y, rgb);
		}
		return img;
	}
	
	/**
	 * Render an grayscale image from a ColumnVector of 0-255 floats 
	 * 
	 * @see http://stackoverflow.com/questions/23587070/using-setrgb-to-set-0-255-values-instead-of-hex-code-values
	 * @param m
	 * @return
	 */
	public static BufferedImage renderImageFromArray(ColumnVector cv, int width) {
		return Images.renderImageFromArray(cv.getCol(0), width);
	}
	
	/**
	 * Render an grayscale image from a 2D array of 0-255 floats 
	 * 
	 * @see http://stackoverflow.com/questions/23587070/using-setrgb-to-set-0-255-values-instead-of-hex-code-values
	 * @param m
	 * @return
	 */
	public static BufferedImage renderImageFromMatrix(float[][] m) {
		int height = m.length;
		int width = m[0].length;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		for (int y = 0; y < height; y ++) {
			for (int x = 0; x < img.getHeight(); x++) {
				int rgb = (255 << 24) | ((int) m[x][y] << 16) | ((int) m[x][y] << 8) | (int) m[x][y];
				img.setRGB(x, y, rgb);
			}
		}
		return img;
	}
	
	/**
	 * Render an grayscale image from a Matrix of 0-255 floats 
	 * 
	 * @see http://stackoverflow.com/questions/23587070/using-setrgb-to-set-0-255-values-instead-of-hex-code-values
	 * @param m
	 * @return
	 */
	public static BufferedImage renderImageFromMatrix(Matrix mat) {
		return Images.renderImageFromMatrix(mat.getMatrix());
	}

	/**
	 * File path to the images.
	 * 
	 * @return String
	 */
	public static String directory(String name) {
		return OSValidator.isMac() 
			? System.getProperty("user.dir") + "/" + name + "/" 
			: System.getProperty("user.dir") + "\\" + name + "\\";
	}
	
	/**
	 * Average of the training set
	 */
	public ColumnVector average() {
		return Psi;
	}
	
	/**
	 * Average of the training set
	 */
	public Matrix covariance() {
		return L;
	}
	
	public BufferedImage probe() {
		return probe;
	}
	
	public ColumnVector probePhi() {
		return probePhi;
	}
	
	public float e(int i) {
		return e[i];
	}
	
}
