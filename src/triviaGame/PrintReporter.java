package triviaGame;

import java.io.PrintStream;
import java.util.LinkedList;

public class PrintReporter implements GameListener {

	private PrintStream out;

	public PrintReporter(PrintStream out) {
		this.out = out;
	}

	@Override
	public void playerAdded(LinkedList<Player> players) {
		out.println(players.getLast() + " was added");
		out.println("They are player number " + players.size());
	}

	@Override
	public void diceRolled(Player currentPlayer, int roll) {
		out.println(currentPlayer + " is the current player");
		out.println("They have rolled a " + roll);
	}

	@Override
	public void playerMoved(Player currentPlayer, String currentCategory) {
		out.println(currentPlayer + "'s new location is " + currentPlayer.position);
		out.println("The category is " + currentCategory);
	}

	@Override
	public void playerLeavingPenaltyBox(Player currentPlayer) {
		out.println(currentPlayer + " is getting out of the penalty box");
	}

	@Override
	public void playerStayingInPenaltyBox(Player currentPlayer) {
		out.println(currentPlayer + " is not getting out of the penalty box");
	}

	@Override
	public void playerEnteringPenaltyBox(Player currentPlayer) {
		out.println(currentPlayer + " was sent to the penalty box");
	}

	@Override
	public void correctAnswerGiven(Player currentPlayer) {
		out.println("Answer was correct!!!!");
		out.println(currentPlayer + " now has " + currentPlayer.purse + " Gold Coins.");
	}

	@Override
	public void incorrectAnswerGiven() {
		out.println("Question was incorrectly answered");
	}

	@Override
	public void questionAsked(String question) {
		out.println(question);
	}

}
