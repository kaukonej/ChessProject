package Pieces;

import chess.ChessPiece;
import chess.Player;

public class Bishop extends ChessPiece {

	protected Bishop(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String type() {
		return "Bishop";
	}

	public boolean isValidMove() {
		// must utilize method from ChessPiece, add specific functionality here
		return false;
	}
}
