package triviaGame;

public class Player {

	String playerName;
	boolean inPenaltyBox;
	int purse;
	int position;
	private GameListener listener;

	public Player(String playerName, GameListener listener) {
		this.playerName = playerName;
		this.listener = listener;
		this.inPenaltyBox = false;
		this.purse = 0;
		this.position = 0;
	}
	
	public String toString() {
		return playerName;
	}

}
