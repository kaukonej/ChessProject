package Pieces;

import chess.ChessPiece;
import chess.IChessPiece;
import chess.Move;
import chess.Player;

public class King extends ChessPiece {
	/******************************************************************
	 * Constructs a King chess piece.
	 * @param player passed to the parent constructor in ChessPiece.
	 *****************************************************************/
	public King(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}
	/******************************************************************
	 *Gives  the types of ChessPiece
	 *@return A string describing the type of ChessPiece.
	 *@Override ChessPiece's type method.
	******************************************************************/
	public String type() {
		return "King";
	}
	
	/******************************************************************
	 * Returns a boolean for if the king has a valid move checking
	 * the move and the board.
	 * @param Board is passed to check where other pieces are.
	 * @param Move is passed to check in relation to the board.
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
	
		if (super.isValidMove(move, board)) {
			if (Math.abs(move.fromRow - move.toRow) <= 1 &&
					Math.abs(move.fromColumn - move.toColumn) <= 1) {
				return true;
			}
		}
		return false;
	}
}
