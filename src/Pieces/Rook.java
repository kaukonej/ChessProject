package Pieces;

import chess.ChessPiece;
import chess.Player;

public class Rook extends ChessPiece {

	protected Rook(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String type() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValidMove() {
		// must utilize method from ChessPiece, add specific functionality here
		return false;
	}
}
