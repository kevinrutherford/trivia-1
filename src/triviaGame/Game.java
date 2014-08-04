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

	private LinkedList<Player> players = new LinkedList<Player>();
	private Player currentPlayer = null;
	private boolean isGettingOutOfPenaltyBox;
	private final PrintStream out;
	private HashMap<String, LinkedList<String>> questions = new HashMap<String, LinkedList<String>>();

	public Game(PrintStream out) {
		this.out = out;
		createQuestions();
	}

	private void createQuestions() {
		LinkedList<String> popQuestions = new LinkedList<String>();
		LinkedList<String> scienceQuestions = new LinkedList<String>();
		LinkedList<String> sportsQuestions = new LinkedList<String>();
		LinkedList<String> rockQuestions = new LinkedList<String>();
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
		return (players.size() >= 2);
	}

	public boolean addPlayer(String playerName) {
		Player player = new Player(playerName);
		players.add(player);
		out.println(player + " was added");
		out.println("They are player number " + players.size());
		currentPlayer = players.getFirst();
		return true;
	}

	public void roll(int roll) {
		out.println(currentPlayer + " is the current player");
		out.println("They have rolled a " + roll);

		if (currentPlayer.inPenaltyBox) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				out.println(currentPlayer + " is getting out of the penalty box");
				currentPlayerMove(roll);
			} else {
				out.println(currentPlayer + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}
		} else
			currentPlayerMove(roll);
	}

	private void currentPlayerMove(int roll) {
		currentPlayer.position = (currentPlayer.position + roll) % 12;
		out.println(currentPlayer + "'s new location is " + currentPlayer.position);
		out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		String category = currentCategory();
		String question = questions.get(category).removeFirst();
		out.println(question);
	}

	private String currentCategory() {
		return board[currentPlayer.position];
	}

	public boolean playerGivesCorrectAnswer() {
		if (currentPlayer.inPenaltyBox && !isGettingOutOfPenaltyBox) {
			passToNextPlayer();
			return false;
		}
		out.println("Answer was correct!!!!");
		currentPlayer.purse++;
		out.println(currentPlayer + " now has " + currentPlayer.purse + " Gold Coins.");
		boolean winner = didPlayerWin();
		passToNextPlayer();
		return winner;
	}
	
	public boolean playerGivesWrongAnswer() {
		out.println("Question was incorrectly answered");
		out.println(currentPlayer + " was sent to the penalty box");
		currentPlayer.inPenaltyBox = true;
		passToNextPlayer();
		return false;
	}

	private void passToNextPlayer() {
		int index = players.indexOf(currentPlayer);
		index = (index + 1) % players.size();
		currentPlayer = players.get(index);
	}

	private boolean didPlayerWin() {
		return currentPlayer.purse == 6;
	}
}
