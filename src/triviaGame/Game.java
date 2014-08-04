package triviaGame;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	private static final String[] board = new String[] {
		"Pop", "Science", "Sports", "Rock",
		"Pop", "Science", "Sports", "Rock",
		"Pop", "Science", "Sports", "Rock"
	};

	ArrayList<String> players = new ArrayList<String>();
	int[] places = new int[6];
	int[] purses = new int[6];
	boolean[] inPenaltyBox = new boolean[6];

	LinkedList<String> popQuestions = new LinkedList<String>();
	LinkedList<String> scienceQuestions = new LinkedList<String>();
	LinkedList<String> sportsQuestions = new LinkedList<String>();
	LinkedList<String> rockQuestions = new LinkedList<String>();

	int currentPlayer = 0;
	boolean isGettingOutOfPenaltyBox;
	private final PrintStream out;

	public Game(PrintStream out) {
		this.out = out;
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
		}
	}

	public String createRockQuestion(int index) {
		return "Rock Question " + index;
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		players.add(playerName);
		places[howManyPlayers()] = 0;
		purses[howManyPlayers()] = 0;
		inPenaltyBox[howManyPlayers()] = false;

		out.println(playerName + " was added");
		out.println("They are player number " + players.size());
		return true;
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		out.println(players.get(currentPlayer) + " is the current player");
		out.println("They have rolled a " + roll);

		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				out.println(players.get(currentPlayer)
						+ " is getting out of the penalty box");
				places[currentPlayer] = places[currentPlayer] + roll;
				if (places[currentPlayer] > 11)
					places[currentPlayer] = places[currentPlayer] - 12;

				out.println(players.get(currentPlayer) + "'s new location is "
						+ places[currentPlayer]);
				out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				out.println(players.get(currentPlayer)
						+ " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}

		} else {

			places[currentPlayer] = places[currentPlayer] + roll;
			if (places[currentPlayer] > 11)
				places[currentPlayer] = places[currentPlayer] - 12;

			out.println(players.get(currentPlayer) + "'s new location is "
					+ places[currentPlayer]);
			out.println("The category is " + currentCategory());
			askQuestion();
		}

	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			out.println(rockQuestions.removeFirst());
	}

	private String currentCategory() {
		return board[places[currentPlayer]];
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]) {
			if (isGettingOutOfPenaltyBox) {
				out.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				out.println(players.get(currentPlayer) + " now has "
						+ purses[currentPlayer] + " Gold Coins.");

				boolean winner = didPlayerWin();
				passToNextPlayer();

				return winner;
			} else {
				passToNextPlayer();
				return true;
			}

		} else {

			out.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			out.println(players.get(currentPlayer) + " now has "
					+ purses[currentPlayer] + " Gold Coins.");

			boolean winner = didPlayerWin();
			passToNextPlayer();

			return winner;
		}
	}

	private void passToNextPlayer() {
		currentPlayer++;
		if (currentPlayer == players.size())
			currentPlayer = 0;
	}

	public boolean wrongAnswer() {
		out.println("Question was incorrectly answered");
		out.println(players.get(currentPlayer) + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		passToNextPlayer();
		return true;
	}

	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
