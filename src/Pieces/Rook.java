package Pieces;

import chess.ChessPiece;
import chess.IChessPiece;
import chess.Move;
import chess.Player;

public class Rook extends ChessPiece {	
	public Rook(Player player) {
		super(player);
	}

	@Override
	public String type() {
		return "Rook";
	}

	public boolean isValidMove(Move myMove, IChessPiece[][] board) {
		// check if anyone in the way
		if(super.isValidMove(myMove, board) && (myMove.fromRow == myMove.toRow || myMove.fromColumn == myMove.toColumn)) {
			if (moveIsOnlyOverEmptySquares(myMove, board)) {
				return true;
			}
		}
		return false;
	}
}