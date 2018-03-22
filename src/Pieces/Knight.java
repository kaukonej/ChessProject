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
		int absDistanceCol = Math.abs(move.fromColumn - move.toColumn);
		int absDistanceRow = Math.abs(move.fromRow - move.toRow);
		if (move.fromColumn != move.toColumn && move.fromRow != move.toRow && absDistanceCol <= 2 && absDistanceRow <= 2 && absDistanceCol != absDistanceRow) {
			return true;
		}
		return false;
	}
}
