package triviaGame;

import java.util.LinkedList;

public interface GameListener {
	void playerAdded(LinkedList<Player> players);
	void diceRolled(Player currentPlayer, int roll);
	void playerMoved(Player currentPlayer, String currentCategory);
	void playerLeavingPenaltyBox(Player currentPlayer);
	void playerStayingInPenaltyBox(Player currentPlayer);
	void playerEnteringPenaltyBox(Player currentPlayer);
	void correctAnswerGiven(Player currentPlayer);
	void incorrectAnswerGiven();
	void questionAsked(String question);
}
