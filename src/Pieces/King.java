package Pieces;

import chess.ChessPiece;
import chess.IChessPiece;
import chess.Move;
import chess.Player;

public class King extends ChessPiece {

	public King(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String type() {
		return "King";
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		// boolean valid = false;
		// must utilize method from ChessPiece, add specific functionality here
		// if move in general is valid
		// if toRow - fromRow == 1 && toCol == fromCol ||
		// toCol - ffom == 1,  && to row == from
		// to row - from == 1 && toCol - from == 1
		// if true, valid = true
		if (Math.abs(move.fromRow - move.toRow) <= 1 && Math.abs(move.fromColumn - move.toColumn) <= 1) {
			return true;
		}
		return false;
	}
}
