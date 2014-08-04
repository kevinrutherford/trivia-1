package triviaGame;

public class Player {

	String playerName;
	boolean inPenaltyBox;
	int purse;
	int position;

	public Player(String playerName) {
		this.playerName = playerName;
		this.inPenaltyBox = false;
		this.purse = 0;
		this.position = 0;
	}
	
	public String toString() {
		return playerName;
	}

}
