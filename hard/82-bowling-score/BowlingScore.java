/*
	[2015-03-11] Challenge #82 [Hard] Bowling Score (but not really hard)
	https://www.reddit.com/r/dailyprogrammer/comments/x8r90/7272012_challenge_82_difficult_bowling_scores/
	This program takes in a data file (refer to data.txt) with scores of several bowling games. 
	It sorts out scores by rounds, and is able to calculate individual round scores as well as total score of a bowling game.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class BowlingScore {
	public static void main(String[] args) {
		File dataFile = new File("data.txt");
		try {
			Scanner inputFile = new Scanner(dataFile);
			while (inputFile.hasNextLine()) {
				String nextLine = inputFile.nextLine();
				Game newGame = new Game(nextLine);
				for (int i=0; i<newGame.game.size()-1; i++) {
					newGame.calculateRoundScore(i);
				}
				int totalScore = newGame.calculateTotalScore();
				System.out.println(totalScore);
			}
			inputFile.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
		
}

class Round {
	int firstBall, secondBall;
	public Round(int first, int second) {
		this.firstBall = first;
		this.secondBall = second;
	}
}

class Game {
	List<Round> game = new ArrayList<Round>();
	List<Integer> roundScores = new ArrayList<Integer>();
	public Game(String inputLine) {
		Scanner input = new Scanner(inputLine);
		while (input.hasNextInt()) {
			int first = input.nextInt();
			int second = input.nextInt();
			Round thisRound = new Round(first, second);
			this.game.add(thisRound);
		}
		input.close();

	}
	public int calculateRoundScore(int roundIndex) {
		int roundScore = 0;
		Round round = this.game.get(roundIndex);
		if (roundIndex >= 10) {
			return roundScore;
		}
		if (round.firstBall == 10) {		//Strike
			if (roundIndex == 9) {			//Last frame
				Round extraRound = this.game.get(10);
				roundScore = 10 + extraRound.firstBall + extraRound.secondBall;
			} else {
				int nextBall = this.game.get(roundIndex+1).firstBall;
				int nextNextBall = this.game.get(roundIndex+1).secondBall;
				if (nextBall == 10) {
					nextNextBall = this.game.get(roundIndex+2).firstBall;
				}
				roundScore = 10 + nextBall + nextNextBall;
			}
		} else if (round.firstBall + round.secondBall == 10) {	//Spare
			int nextBall = this.game.get(roundIndex+1).firstBall;
			roundScore = 10 + nextBall;
		} else {
			roundScore = round.firstBall + round.secondBall;
		}
		this.roundScores.add(roundScore);
		return roundScore;
	}
	public int calculateTotalScore() {
		int total = 0;
		for (int i=0; i<this.game.size()-1; i++) {
			total += this.roundScores.get(i);
		}
		return total;
	}
}