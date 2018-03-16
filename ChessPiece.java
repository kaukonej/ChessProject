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
			if (board[move.fromRow][move.fromColumn].player() == owner) { // if piece at coord belongs to owner of this piece
				if (board[move.toRow][move.toColumn] == null) { // if move to space is empty
					//board[move.toColumn][move.toRow] = this; // set coord to this piece
					return true; // valid = true
				} else if (board[move.toColumn][move.toRow].player() == owner.next()) { // else if coord has other person's piece
					//takePiece(); // capture the piece
					//board[move.toColumn][move.toRow] = this; // and set coord to this piece instead
					return true; // valid = true
				}
			}
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
		// Vertical Moves
		if(move.fromRow == move.toRow) {
			int pom = 1;
			if(move.fromColumn > move.toColumn)
			{
				pom = -1;
			}
			int moveLength = Math.abs(move.fromRow - move.toRow);
			for(int i = 0; i < moveLength; i++) {
				if(board[move.fromColumn][move.fromRow + i*pom] != null) {
					if(move.fromRow + move.toRow == i*pom)
					{
						return true;
					}
					return false;
				}
			}
		}

		// Horizontal Moves
		if(move.fromColumn == move.toColumn) {
			int pom = 1;
			if(move.fromColumn > move.toColumn)
			{
				pom = -1;
			}
			int moveLength = Math.abs(move.fromColumn - move.toColumn);
			for(int i = 0; i < moveLength; i++) {
				if(board[move.fromColumn][move.fromColumn + i*pom] != null) {
					if(move.fromColumn + move.toColumn == i*pom)
					{
						return true;
					}
					return false;
				}
			}
		}
		// Diagonal Moves
		// Get distance between spaces
		// TODO finish this for King, Queen, and Bishop this part of the method is NOT done
		int pom = 1;
		if(move.fromColumn > move.toColumn || move.fromRow > move.toRow)
		{
			pom = -1;
		}
		int moveLength = Math.abs((move.fromColumn - move.toColumn + move.fromRow - move.toRow)/2);
		for(int i = 0; i < moveLength; i++) {
			if(board[move.fromColumn][move.fromColumn + i*pom] != null) {
				return false;
			}
		}
		return true;
	}
}
