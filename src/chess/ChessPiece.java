package chess;

public abstract class ChessPiece implements IChessPiece {

	private Player owner;

	protected ChessPiece(Player player) {
		this.owner = player;
	}

	public abstract String type();

	public Player player() {
		// complete this
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		// complete this
		// if "from" space has a piece
		// AND "to" space is empty
		// put from piece at to space
		// else if has opposite color piece
		// take piece, then move
		// else, return false
	}
}
