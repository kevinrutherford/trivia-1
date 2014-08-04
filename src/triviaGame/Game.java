package triviaGame;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;

public final class Game {
	private static final String[] board = new String[] {
		"Pop", "Science", "Sports", "Rock",
		"Pop", "Science", "Sports", "Rock",
		"Pop", "Science", "Sports", "Rock"
	};

	LinkedList<String> players = new LinkedList<String>();
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

	private HashMap<String, LinkedList<String>> questions = new HashMap<String, LinkedList<String>>();

	public Game(PrintStream out) {
		this.out = out;
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast("Science Question " + i);
			sportsQuestions.addLast("Sports Question " + i);
			rockQuestions.addLast("Rock Question " + i);
		}
		questions.put("Pop", popQuestions);
		questions.put("Science", scienceQuestions);
		questions.put("Sports", sportsQuestions);
		questions.put("Rock", rockQuestions);
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
				out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				currentPlayerMove(roll);
			} else {
				out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}
		} else
			currentPlayerMove(roll);
	}

	private void currentPlayerMove(int roll) {
		places[currentPlayer] = (places[currentPlayer] + roll) % 12;
		out.println(players.get(currentPlayer) + "'s new location is " + places[currentPlayer]);
		out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		String category = currentCategory();
		String question = questions.get(category).removeFirst();
		out.println(question);
	}

	private String currentCategory() {
		return board[places[currentPlayer]];
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]) {
			if (isGettingOutOfPenaltyBox) {
				out.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				out.println(players.get(currentPlayer) + " now has " + purses[currentPlayer] + " Gold Coins.");
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
			out.println(players.get(currentPlayer) + " now has " + purses[currentPlayer] + " Gold Coins.");
			boolean winner = didPlayerWin();
			passToNextPlayer();
			return winner;
		}
	}

	private void passToNextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.size();
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
