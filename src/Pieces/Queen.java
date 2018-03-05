package Pieces;

import chess.ChessPiece;
import chess.Player;

public class Queen extends ChessPiece {

	protected Queen(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String type() {
		return "Queen";
	}

	public boolean isValidMove() {
		// must utilize method from ChessPiece, add specific functionality here
		return false;
	}
}
