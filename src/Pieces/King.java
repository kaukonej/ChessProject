package Pieces;

import chess.ChessPiece;
import chess.IChessPiece;
import chess.Move;
import chess.Player;

public class King extends ChessPiece {
	public King(Player player) {
		super(player);
	}

	@Override
	public String type() {
		return "King";
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if (super.isValidMove(move, board)) {
			if (Math.abs(move.fromRow - move.toRow) <= 1 && Math.
					abs(move.fromColumn - move.toColumn) <= 1) {
				return true;
			}
		}
		return false;
	}
}
