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
		// boolean valid = false; (allows multiple checks

		// if from != to (can't do nothing)
		// TODO: If not moving into check
		if (withinBoard(move)) {
			if (board[move.fromRow][move.fromColumn] != null) {
				//if (board[move.fromRow][move.fromColumn].player() == owner) { // if piece at coord belongs to owner of this piece
				if (board[move.toRow][move.toColumn] != null) { // if move to space is empty
					if (board[move.toRow][move.toColumn].player() != owner) {
						return true;
					}
				} else if (board[move.toRow][move.toColumn] == null) {
					return true;
				}
			}
			//}

		}
		return false; // return valid;
	}

	/**
	 * This method returns a Boolean value depending on the squares that 
	 * lie strictly between the move.from and move.to squares
	 * @param move
	 * @param board
	 * @return
	 */
	public boolean moveIsOnlyOverEmptySquares(Move move, IChessPiece[][] board) {
		boolean isValid = true;
		int absDistanceCol = Math.abs(move.fromColumn - move.toColumn);
		int absDistanceRow = Math.abs(move.fromRow - move.toRow);
		//Vertical Moves
		if(absDistanceCol == 0) {
			int pom = 1;
			if(move.fromRow > move.toRow) {
				pom = -1;
			}
			for(int i = 1; i <= absDistanceRow - 1; i++) {
				if(board[move.fromRow + i*pom][move.fromColumn] != null) {
					isValid = false;
				}
			}
		}

		// Horizontal Moves
		if(absDistanceRow == 0) {
			int pom = 1;
			if(move.fromColumn > move.toColumn) {
				pom = -1;
			}
			for(int i = 1; i <= absDistanceCol - 1; i++) {
				if(board[move.fromRow][move.fromColumn + i*pom] != null) {
					isValid = false;
				}
			}
		}

		if (absDistanceCol == absDistanceRow) {
			int pomRow = 1;
			int pomCol = 1;
			if(move.fromRow > move.toRow) {
				pomRow = -1;
			}
			if(move.fromColumn > move.toColumn) {
				pomCol = -1;
			}
			for (int i = 1; i <= absDistanceCol - 1; i++) {
				if(board[move.fromRow + i*pomRow][move.fromColumn + i*pomCol] != null) {
					isValid = false;
				}
			}
		}
		return isValid;
	}
}