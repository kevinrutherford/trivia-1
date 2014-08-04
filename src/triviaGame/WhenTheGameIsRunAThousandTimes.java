package triviaGame;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

public final class WhenTheGameIsRunAThousandTimes {
	
	@Test
	public void characterizationTest() throws IOException {
		String master = new GoldenMaster().currentValue();
		ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
		GameRunner.runAThousandGames(new PrintStream(outputBuffer));
		assertEquals(master, outputBuffer.toString());
	}
}