package chess;

public abstract class ChessPiece implements IChessPiece {

	private Player owner;

	protected ChessPiece(Player player) {
		this.owner = player;
	}

	public abstract String type();

	public Player player() {
		return owner;
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		// complete this

		if (board[move.fromColumn][move.fromRow].player() == owner) { // if piece at coord belongs to owner of this piece
			if (board[move.toColumn][move.toRow].player() == null) { // if move to space is empty
				board[move.toColumn][move.toRow] = this; // set coord to this piece
				return true;
			} else if (board[move.toColumn][move.toRow].player() == owner.next()) { // else if coord has other person's piece
				//takePiece(); // capture the piece
				board[move.toColumn][move.toRow] = this; // and set coord to this piece instead
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method returns a Boolean value depending on the squares that 
	 * lie strictly between the move.from and move.to squares
	 * @param move
	 * @param board
	 * @return
	 */
	public boolean moveIsOnlyOverEmptySquares( Move move, IChessPiece[][] board) {
		// complete this
		return false;
	}
}
