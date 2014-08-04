package triviaGame;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

public final class GameTest {

	@Test
	public void characterizationTest() throws IOException {
		String master = new GoldenMaster().currentValue();
		ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
		GameRunner.runAThousandGames(new PrintStream(outputBuffer));
		assertEquals(master, outputBuffer.toString());
	}

	@Test
	public void offByOneError() throws Exception {
		Game game = new Game(System.out);
		game.addPlayer("player1");
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
}
