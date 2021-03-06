/**
 * [7/30/2014] Challenge #173 [Intermediate] Advanced Langton's Ant
 * https://www.reddit.com/r/dailyprogrammer/comments/2c4ka3/7302014_challenge_173_intermediate_advanced/
 * Langton's Ant is an interesting 2D Turing machine that simulates movement of an ant based on the color of the tiles it's standing on
 * For example, in the standard version, if a tile is white, the ant turns 90deg right, and if black, 90deg left, starting on an all-white board and pointing north
 * This program takes in (0) a sequence of instructions, either L or R, (1) number of steps to simulate Langton's Ant
 * For each L or R, the direction is mapped onto a color, so LRR means left on white, right on gray, right on red, etc.
 */

import java.awt.Color;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ant {
	
	private ArrayList<Color> colors;
	private static final int DIMENSION = 512;
	private String sequence;
	private int sequenceLength;
	private int direction;
	private int xCoord, yCoord;
	private BufferedImage img;
	
	public Ant(String sequence, int steps) {
		this.sequence = sequence;
		sequenceLength = sequence.length();
		direction = 90;
		xCoord = DIMENSION/2;
		yCoord = DIMENSION/2;
		img = new BufferedImage(DIMENSION, DIMENSION, BufferedImage.TYPE_INT_RGB);
		colors = new ArrayList<Color>();
		colors.add(Color.white);
		colors.add(Color.gray);
		colors.add(Color.red);
		colors.add(Color.black);
		colors.add(Color.darkGray);
		colors.add(Color.lightGray);
		for (int i=0; i<DIMENSION; i++) {
			for (int j=0; j<DIMENSION; j++) {
				img.setRGB(i, j, colors.get(0).getRGB());
			}
		}
		runAnt(steps);
	}
	
	/**
	 * Runs the Langton's Ant program
	 * @param steps Number of steps running
	 */
	public void runAnt(int steps) {
		while (steps > 0) {
			if (!inBound(xCoord, yCoord)) {
				System.out.println("Out of bound at " + steps);
				break;
			}
			int curColor = img.getRGB(xCoord, yCoord);
			for (int i=0; i<sequenceLength; i++) {
				if (colors.get(i).getRGB() == curColor) {
					changeDir(sequence.charAt(i)+"");
					if (i+1==sequenceLength) {
						img.setRGB(xCoord, yCoord, colors.get(0).getRGB());
					} else {
						img.setRGB(xCoord, yCoord, colors.get(i+1).getRGB());
					}
					moveAnt(direction);
					break;
				}
			}
			steps--;
		}
		saveImage();
	}
	
	/**
	 * Change direction (theta) based on L or R
	 * @param way Specifies left or right
	 */
	public void changeDir(String way) {
		if (way.equals("L")) {
			if (direction == 270) {
				direction = 0;
			} else {
				direction += 90;
			}
		} else {
			if (direction == 0) {
				direction = 270;
			} else {
				direction -= 90;
			}
		}
	}
	
	/**
	 * Move ant according to direction (theta)
	 * @param direction Direction ant is currently moving
	 */
	public void moveAnt(int direction) {
		if (direction == 0) {
			xCoord++;
		} else if (direction == 180) {
			xCoord--;
		} else if (direction == 90) {
			yCoord--;
		} else if (direction == 270) {
			yCoord++;
		} else {
			throw new IllegalArgumentException("Bad direction");
		}
	}
	
	/**
	 * Check boundary conditions (if this fails, stop program)
	 * @param x
	 * @param y
	 * @return Boolean on whether or not coordinate is in bound
	 */
	public boolean inBound(int x, int y) {
		return (x>=0 && y>=0 && 
				x<DIMENSION && y<DIMENSION);
	}
	
	/**
	 * Save the image as PNG
	 */
	public void saveImage() {
		File f = new File("output.png");
		try {
            ImageIO.write(img, "PNG", f);
        } catch(IOException e) {
            e.printStackTrace();
        }
		System.exit(0);
	}
	
	public static void main(String args[]) {
		int steps = Integer.parseInt(args[1]);
		new Ant(args[0], steps);
	}
}
