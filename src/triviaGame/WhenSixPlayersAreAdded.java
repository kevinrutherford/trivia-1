package triviaGame;

import static org.junit.Assert.*;
import java.util.LinkedList;
import org.junit.Test;

public final class WhenSixPlayersAreAdded implements GameListener {

	@Test
	public void theGameIsPlayable() throws Exception {
		Game game = new Game(this);
		game.addPlayer("player1");
		assertFalse(game.isPlayable());
		game.addPlayer("player2");
		assertTrue(game.isPlayable());
		game.addPlayer("player3");
		assertTrue(game.isPlayable());
		game.addPlayer("player4");
		assertTrue(game.isPlayable());
		game.addPlayer("player5");
		assertTrue(game.isPlayable());
		game.addPlayer("player6");
		assertTrue(game.isPlayable());
	}

	public void playerAdded(LinkedList<Player> players) { }
	public void diceRolled(Player currentPlayer, int roll) { }
	public void playerMoved(Player currentPlayer, String currentCategory) { }
	public void playerLeavingPenaltyBox(Player currentPlayer) { }
	public void playerStayingInPenaltyBox(Player currentPlayer) { }
	public void playerEnteringPenaltyBox(Player currentPlayer) { }
	public void correctAnswerGiven(Player currentPlayer) { }
	public void incorrectAnswerGiven() { }
	public void questionAsked(String question) { }
}
