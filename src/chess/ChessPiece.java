package chess;

public abstract class ChessPiece implements IChessPiece {

	/**
	 * STEPS PROGRESS:
	 * Step 1: Complete
	 * Step 2: ChessPiece isValidMove started, moveIsOnlyOverEmptySquares not started
	 * Step 3: type() works, isValidMove does not.
	 * Step 4: Completed (easy)
	 * Step 5: ChessModel isValidMove(), move(). some other incomplete
	 * Step 6: Not started
	 * Step 7: Not started
	 * Step 8 onwards: Not started
	 */
	private Player owner;

	protected ChessPiece(Player player) {
		this.owner = player;
	}

	public abstract String type();

	public Player player() {
		return owner;
	}
	
	private boolean withinBoard(Move move) {
		if (move.fromColumn <= 7 && move.fromColumn >= 0 && 
				move.fromRow <= 7 && move.fromColumn >= 0 && 
				move.toColumn <= 7 && move.toColumn >= 0 && 
				move.toRow <=7 && move.toColumn >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		// complete this
		if (withinBoard(move)) {
			if (board[move.fromColumn][move.fromRow].player() == owner) { // if piece at coord belongs to owner of this piece
				if (board[move.toColumn][move.toRow].player() == null) { // if move to space is empty
					//board[move.toColumn][move.toRow] = this; // set coord to this piece
					return true;
				} else if (board[move.toColumn][move.toRow].player() == owner.next()) { // else if coord has other person's piece
					//takePiece(); // capture the piece
					//board[move.toColumn][move.toRow] = this; // and set coord to this piece instead
					return true;
				}
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
