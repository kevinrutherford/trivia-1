package triviaGame;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class GoldenMaster {

	private static final String MASTER_FILENAME = "./master.txt";

	public String currentValue() {
		try {
			if (!masterFile().exists())
				generate();
			return readGoldenMaster();
		} catch (IOException e) {
			return "";
		}
	}

	private void generate() throws IOException {
		GameRunner.runAThousandGames(new PrintStream(MASTER_FILENAME));
	}

	private String readGoldenMaster() throws IOException {
		byte[] text = Files.readAllBytes(Paths.get(MASTER_FILENAME));
		return new String(text, "UTF-8");
	}

	private File masterFile() {
		return new File(MASTER_FILENAME);
	}

}
