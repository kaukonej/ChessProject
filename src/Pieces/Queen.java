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
		// must utilize method from ChessPiece, add specific functionality here
		// queen is just a bishop and a rook, if they can move a queen can do it
		return false;
	}
}
