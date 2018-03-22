package Pieces;

import chess.ChessPiece;
import chess.IChessPiece;
import chess.Move;
import chess.Player;

public class Queen extends ChessPiece {

	public Queen(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String type() {
		return "Queen";
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		Rook testRook = new Rook(super.player());
		Bishop testBishop = new Bishop(super.player());
		if (testRook.isValidMove(move, board) || testBishop.isValidMove(move, board)) {
			return true;
		}
		return false;
	}
}
