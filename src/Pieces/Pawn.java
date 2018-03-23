package Pieces;

import chess.ChessPiece;
import chess.IChessPiece;
import chess.Move;
import chess.Player;

public class Pawn extends ChessPiece {
	// Row is Y, col is X
	public Pawn(Player player) {
		super(player);
	}

	public String type() {
		return "Pawn";
	}

	public boolean isValidMove(Move myMove, IChessPiece[][] board) {
		boolean isValid = false;
		if (super.isValidMove(myMove, board)) {
			// If it's a white piece, use this set of logic.
			if (super.player() == Player.WHITE) {
				if(myMove.fromRow == 6 && myMove.toRow == myMove.fromRow - 2 && myMove.fromColumn == myMove.toColumn && board[myMove.toRow][myMove.toColumn] == null) {
					isValid = true;
				} else if (myMove.fromRow == myMove.toRow + 1) {
					// if only moving forward
					if(myMove.fromColumn == myMove.toColumn && board[myMove.toRow][myMove.toColumn] == null) {
						isValid = true;
					}	
					// if trying to capture left diagonal
					else if(myMove.fromColumn == myMove.toColumn + 1 && board[myMove.toRow][myMove.toColumn] != null) {
						if (board[myMove.toRow][myMove.toColumn].player() == Player.BLACK) {
							isValid = true;
						}
					}
					// if trying to capture right diagonal
					else if(myMove.fromColumn == myMove.toColumn - 1 && board[myMove.toRow][myMove.toColumn] != null) {
						if (board[myMove.toRow][myMove.toColumn].player() == Player.BLACK) {
							isValid = true;
						}
					}
				}
			} else if (super.player() == Player.BLACK) { // If it's a black piece, basically do the same thing, but backwards.
				if(myMove.fromRow == 1 && myMove.toRow == myMove.fromRow + 2 && myMove.fromColumn == myMove.toColumn && board[myMove.toRow][myMove.toColumn] == null) {
					isValid = true;
				} else if(myMove.fromRow == myMove.toRow - 1) {
					// if only moving forward
					if(myMove.fromColumn == myMove.toColumn && board[myMove.toRow][myMove.toColumn] == null) {
						isValid = true;
					}	
					// if trying to capture left diagonal
					else if(myMove.fromColumn == myMove.toColumn + 1 && board[myMove.toRow][myMove.toColumn] != null) {
						if (board[myMove.toRow][myMove.toColumn].player() == Player.WHITE) {
							isValid = true;
						}
					}
					// if trying to capture right diagonal
					else if(myMove.fromColumn == myMove.toColumn - 1 && board[myMove.toRow][myMove.toColumn] != null) {
						if (board[myMove.toRow][myMove.toColumn].player() == Player.WHITE) {
							isValid = true;
						}
					}
				}
			}
		}
		return isValid;
	}
}