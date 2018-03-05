package Pieces;

import chess.ChessPiece;
import chess.Player;

public class King extends ChessPiece {

	protected King(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String type() {
		return "King";
	}

	public boolean isValidMove() {
		// must utilize method from ChessPiece, add specific functionality here
		return false;
	}
}
