package Pieces;

import chess.ChessPiece;
import chess.IChessPiece;
import chess.Move;
import chess.Player;

public class Bishop extends ChessPiece {

	/******************************************************************
	 * Polymorphic method setting the Bishop to the correct player.
	 * @param player The color the piece is being set as
	 * (black or white).
	 *****************************************************************/
	public Bishop(Player player) {
		super(player);
	}

	/******************************************************************
	 * Returns the name of the piece when called.
	 * @return "Bishop" the name of the piece.
	 *****************************************************************/
	@Override
	public String type() {
		return "Bishop";
	}

	/******************************************************************
	 * Checks to see if the move initiated is valid for the Bishop
	 * specifically. (Horizontal or vertical movement which can't go 
	 * past pieces in the direction it is going).
	 * @param move The move that we are checking to be valid.
	 * @param board The board that is being checked.
	 * @return True if the move is Valid for the given Bishop, 
	 * false if the move is not.
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		// must utilize method from ChessPiece, add specific functionality here
		if (super.isValidMove(move, board)) {
			int absDistanceCol = Math.abs(move.fromColumn - 
					move.toColumn);
			int absDistanceRow = Math.abs(move.fromRow - 
					move.toRow);
			if (absDistanceCol == absDistanceRow) {
				if (moveIsOnlyOverEmptySquares(move, board)) {
					return true;
				}
			}
		}
		return false;
	}
}
