package Pieces;

import chess.ChessPiece;
import chess.Player;

public class Pawn extends ChessPiece {

	protected Pawn(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String type() {
		return "Pawn";
	}

	public boolean isValidMove() {
		// must utilize method from ChessPiece, add specific functionality here
		return false;
	}
}
