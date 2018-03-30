package chess;

/**********************************************************************
 * Class that all the chess pieces are derived from. Contains
 * basic checks such as checking if it's within the board, if
 * the move is only over empty squares, or if a move if valid
 * for the general rules of chess.
 * 
 * @author Justin Kaukonen, Adrian Harrell, Aaron
 *********************************************************************/
public abstract class ChessPiece implements IChessPiece {
	/* The owner of this piece */
	private Player owner;

	/******************************************************************
	 * When creating a piece, sets the owner to the passed-in player.
	 * @param player The owner this piece will be assigned to.
	 *****************************************************************/
	protected ChessPiece(Player player) {
		this.owner = player;
	}

	/******************************************************************
	 * Returns the type of piece that this is called on.
	 * @see chess.IChessPiece#type()
	 *****************************************************************/
	public abstract String type();

	/******************************************************************
	 * Returns the owner of this piece.
	 * @see chess.IChessPiece#player()
	 * @return The owner of this piece.
	 *****************************************************************/
	public Player player() {
		return owner;
	}

	/******************************************************************
	 * Checks to ensure a move keeps a piece within the confines of
	 * the board. There is no platform 9 3/4.
	 * @param move The move to check if it stays within the board.
	 * @return true If the piece remains within the board.
	 *****************************************************************/
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

	/******************************************************************
	 * Ensures a piece makes a valid move. It must remain within the 
	 * board, the piece must exist, and it must move to either a space 
	 * occupied by an enemy player, or an empty space.
	 * @see chess.IChessPiece#isValidMove(chess.Move, 
	 * chess.IChessPiece[][])
	 * @param move Move to check for validity.
	 * @param board The board that the pieces are on.
	 * @return true If the move follow the rules of chess.
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if (withinBoard(move)) {
			if (board[move.fromRow][move.fromColumn] != null) {
				if (board[move.toRow][move.toColumn] != null) {
					if (board[move.toRow][move.toColumn].player() != 
							owner) {
						return true;
					}
				} else if (board[move.toRow][move.toColumn] == null) {
					return true;
				}
			}

		}
		return false; // return valid;
	}

	/******************************************************************
	 * This method returns a Boolean value depending on the squares that 
	 * lie strictly between the move.from and move.to squares.
	 * @param move The move being checked for moving only over empty
	 * spaces.
	 * @param board The board that the pieces are on.
	 * @return true If the spaces between the to and from pieces are
	 * all blank.
	 *****************************************************************/
	public boolean moveIsOnlyOverEmptySquares(Move move, 
			IChessPiece[][] board) {
		boolean isValid = true;
		int absDistanceCol = Math.abs(move.fromColumn - move.toColumn);
		int absDistanceRow = Math.abs(move.fromRow - move.toRow);
		//Vertical Moves
		if(absDistanceCol == 0) {
			// Plus Or Minus modifier
			int pom = 1;
			if(move.fromRow > move.toRow) {
				pom = -1;
			}
			// Starting space is 0, absDistance loop is the end space.
			for(int i = 1; i <= absDistanceRow - 1; i++) {
				if(board[move.fromRow + i*pom][move.fromColumn] != 
						null) {
					isValid = false;
				}
			}
		}

		// Horizontal Moves
		if(absDistanceRow == 0) {
			// Plus Or Minus modifier
			int pom = 1;
			if(move.fromColumn > move.toColumn) {
				pom = -1;
			}
			// Starting space is 0, absDistance is the end space.
			for(int i = 1; i <= absDistanceCol - 1; i++) {
				if(board[move.fromRow][move.fromColumn + i*pom] != 
						null) {
					isValid = false;
				}
			}
		}

		// Diagonal moves
		if (absDistanceCol == absDistanceRow) {
			// Plus Or Minus modifiers
			int pomRow = 1;
			int pomCol = 1;
			if(move.fromRow > move.toRow) {
				pomRow = -1;
			}
			if(move.fromColumn > move.toColumn) {
				pomCol = -1;
			}
			// Starting space is 0, absDistance loop is the end space.
			for (int i = 1; i <= absDistanceCol - 1; i++) {
				if(board[move.fromRow + i*pomRow][move.fromColumn 
				                                 + i*pomCol] != null) {
					isValid = false;
				}
			}
		}
		return isValid;
	}
}