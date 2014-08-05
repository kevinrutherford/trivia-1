package triviaGame;

import java.util.HashMap;
import java.util.LinkedList;

public final class Game {
	private LinkedList<Player> players = new LinkedList<Player>();
	private Player currentPlayer = null;
	private HashMap<String, LinkedList<String>> questions = new HashMap<String, LinkedList<String>>();
	private GameListener listener;
	private Board board = new Board();

	public Game(GameListener listener) {
		this.listener = listener;
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

	public void addPlayer(String playerName) {
		Player player = new Player(playerName, listener);
		players.add(player);
		listener.playerAdded(players);
		currentPlayer = players.getFirst();
	}

	public void roll(int roll) {
		listener.diceRolled(currentPlayer, roll);
		if (currentPlayer.inPenaltyBox) {
			if (isEvenNumber(roll)) {
				listener.playerStayingInPenaltyBox(currentPlayer);
				return;
			} else {
				currentPlayer.inPenaltyBox = false;
				listener.playerLeavingPenaltyBox(currentPlayer);
			}
		}
		currentPlayerMove(roll);
	}

	private boolean isEvenNumber(int roll) {
		return roll % 2 == 0;
	}

	private void currentPlayerMove(int roll) {
		currentPlayer.position = (currentPlayer.position + roll) % 12;
		listener.playerMoved(currentPlayer, currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		String question = questions.get(currentCategory()).removeFirst();
		listener.questionAsked(question);
	}

	private String currentCategory() {
		return board.category(currentPlayer.position);
	}

	public boolean playerGivesCorrectAnswer() {
		if (!currentPlayer.inPenaltyBox) {
			listener.correctAnswerGiven(currentPlayer);
			currentPlayer.purse++;
		}
		boolean result = didPlayerWin();
		passToNextPlayer();
		return result;
	}
	
	public boolean playerGivesWrongAnswer() {
		listener.incorrectAnswerGiven();
		currentPlayer.inPenaltyBox = true;
		listener.playerEnteringPenaltyBox(currentPlayer);
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
