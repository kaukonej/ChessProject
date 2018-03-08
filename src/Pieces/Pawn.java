package Pieces;

import chess.ChessPiece;
import chess.Move;
import chess.Player;

public class Pawn extends ChessPiece {

	private int row;
	private int col;
	private boolean hasMoved = false;
	// Row is Y, col is X
	public Pawn(Player player) {
		super(player);
	}

	//	public Pawn(Player player, int row, int col) {
	//		super(player);
	//		this.row = row;
	//		this.col = col;
	//	}

	@Override
	public String type() {
		return "Pawn";
	}

	public boolean isValidMove(Move myMove) {
		// must utilize method from ChessPiece, add specific functionality here
		int pom; // Plus Or Minus
		if (super.isValidMove(myMove, board)) {
			if (super.player() == Player.WHITE) { // white
				pom = 1;
			} else {
				pom = -1;
			}
			if (myMove.fromRow == myMove.toRow + pom) {
				row = myMove.toRow;
				hasMoved = true;
				// TODO Make sure it only moves diagonally if there's an enemy piece there
				if (myMove.fromColumn == myMove.toColumn + 1) {
					col = myMove.toColumn;
					return true;
				} else if (myMove.fromColumn == myMove.toColumn - 1) {
					col = myMove.toColumn;
					return true;
				} else if (myMove.fromColumn == myMove.toColumn) {
					return true;
				}
			} else if (!hasMoved && myMove.fromRow == myMove.toRow + (2 * pom)) {
				row = myMove.toRow;
				hasMoved = true;
				return true;
			}
		}
		return false;
	}

	//	public void setRow(int row) {
	//		if (row <= 7 && row >= 0) {
	//			this.row = row;
	//		}
	//	}
	//	public void setColumn(int col) {
	//		if (col <= 7 && col >= 0) {
	//			this.col = col;
	//		}
	//	}
}
