package triviaGame;

import java.io.PrintStream;
import java.util.Random;

public final class GameRunner {

//	public static void main(String[] args) {
//		Random rand = new Random();
//		run(rand, System.out);
//	}

	public static void run(Random rand, PrintStream out) {
		Game game = new Game(out);
		game.addPlayer("Chet");
		game.addPlayer("Pat");
		game.addPlayer("Sue");
		boolean playerWins;
		do {
			game.roll(throwDice(rand));
			playerWins = answerQuestion(rand, game);
		} while (!playerWins);
	}

	private static boolean answerQuestion(Random rand, Game game) {
		return (rand.nextInt(9) == 7) ? game.playerGivesWrongAnswer() : game.playerGivesCorrectAnswer();
	}

	private static int throwDice(Random rand) {
		return rand.nextInt(5) + 1;
	}

	public static void runAThousandGames(PrintStream out) {
		Random rand = new Random(7919);
		for (int i = 0; i < 1000; i++)
			GameRunner.run(rand, out);
	}
}
