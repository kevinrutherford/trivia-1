package triviaGame;

public class Board {
	private static final String[] squares = new String[] {
		"Pop", "Science", "Sports", "Rock",
		"Pop", "Science", "Sports", "Rock",
		"Pop", "Science", "Sports", "Rock"
	};

	String category(int position) {
		return squares[position];
	}
}
