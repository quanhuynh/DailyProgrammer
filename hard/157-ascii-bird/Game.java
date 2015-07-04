/**
 * [4/11/2014] Challenge #157 [Hard] ASCII Bird
 * https://www.reddit.com/r/dailyprogrammer/comments/22slvn/4112014_challenge_157_hard_ascii_bird/
 * This program runs an ASCII version of the game Flappy Bird on the command line.
 * To put a twist into the game (and to make it more feasible bc I'm kinda lazy to recreate a whole game), ASCII Bird is turn-based.
 * Every turn, the player puts in how high the bird should jump, and the game will act accordingly. 
 */
import java.util.Random;
import java.util.Scanner;
public class Game {
	private int[][] tiles;
	private static int height = 12;
	private static int width =20;
	private int[] birdPos;
	private int gap;
	private int tilesTraveled;
	private int totalScore;

	public Game() {
		tiles = new int[width][height];
		birdPos = new int[2];
		birdPos[0] = 2;
		birdPos[1] = height/2;
		gap = 5;
		for (int j=0; j<height; j++) {
			for (int i=0; i<width; i++) {
				if (i==0 || j==0 || j==height-1 || i==width-1) {
					tiles[i][j] = 3;
				} else if (i==birdPos[0] && j==birdPos[1]) {
					tiles[i][j] = 2;
				} else {
					tiles[i][j] = 0;
				}
			}
		}
	}

	public void drawScreen() {
		for (int j=0; j<height; j++) {
			for (int i=0; i<width; i++) {
				if (tiles[i][j] == 1) {
					System.out.print("H");
				} else if (i==birdPos[0] && j==birdPos[1]) {
					System.out.print("@");
				} else if (tiles[i][j] == 3) {
					if (i==width-1) {
						System.out.println("#");
					} else {
						System.out.print("#");
					}
				} else if (tiles[i][j]==-1) {
					System.out.print("X");
				} else {
					System.out.print(".");
				}
			}
		}

	}

	public void updateGame() {
		for (int j=0; j<height; j++) {
			if (tiles[1][j] == 1) {
				tiles[1][j] = 0;
			}
		}
		for (int i=1; i<width; i++) {
			for (int j=1; j<height; j++) {
				if (tiles[i][j] == 1) {
					tiles[i-1][j] = 1;
					if (j != 0 && j != height-1) {
						tiles[i][j] = 0;
					} else {
						tiles[i][j] = 3;
					}
				}
			}
		}
		gap += 1;
		tilesTraveled += 1;
		if (gap == 6) {
			gap = 0;
			generateObstacle(width-2);
		}
		if (tilesTraveled > 16 && (tilesTraveled - 6) % 6 == 0) {
			score();
		}
	}
	
	public void score() {
		totalScore += 1;
	}
	public int retScore() {
		return totalScore;
	}
	
	public void generateObstacle(int col) {
		Random randGen = new Random();
		int obsHeight = randGen.nextInt((6 - 4) + 1) + 4;
		int gap = randGen.nextInt(3);
		for (int y=height-2; y>height-obsHeight; y--) {
			tiles[col][y] = 1;	//From top
		}
		for (int y=1; y<obsHeight - gap+1; y++) {
			tiles[col][y] = 1;	//From bottom
		}
	}

	public void jump(int height) {
		if (tiles[birdPos[0]+1][birdPos[1]-height] == 1) {
			tiles[birdPos[0]+1][birdPos[1]-height] = -1;
			birdPos[0] = -1;
			birdPos[1] = -1;
			drawScreen();
			endGame();
		}
		if (height==0) {
			birdPos[1] += 2;
		}
		birdPos[1] -= height;
	}
	
	public void endGame() {
		System.out.println("YOU DED!");
		System.out.println("Your final score: " + retScore());
		System.exit(0);
	}

	public static void main(String[] args) {
		Game g = new Game();
		g.drawScreen();
		while (true) {
			System.out.println("Score: " + g.retScore());
			System.out.println("Jump height? 0-4");
			Scanner input = new Scanner(System.in);
			String jumpHeight = input.next();
			if (jumpHeight != null) {
				g.jump(Integer.parseInt(jumpHeight));
			}
			g.updateGame();
			g.drawScreen();
		}
	}

}
