package Pieces;

import chess.ChessPiece;
import chess.IChessPiece;
import chess.Move;
import chess.Player;

public class Knight extends ChessPiece{

	public Knight(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String type() {
		return "Knight";
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		// must utilize method from ChessPiece, add specific functionality here
		return false;
	}
}
