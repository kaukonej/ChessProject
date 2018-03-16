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
		// must utilize method from ChessPiece, add specific functionality here
		int typeMultiplier; // Plus Or minus 1, depending on the piece type
		boolean isValid = false;
		if (super.isValidMove(myMove, board)) {
			if (super.player() == Player.WHITE) { // white
				typeMultiplier = -1; // will move down the board
			} else {
				typeMultiplier = 1; // will move up the board
			}
			
			// move two for white only
			if(super.player() == Player.WHITE && myMove.fromRow == 1 && myMove.toRow == myMove.fromRow + (2 * typeMultiplier)) {
				isValid = true;
			}	
			// move two for black only
			else if(super.player() == Player.BLACK && myMove.fromRow == 7 && myMove.fromRow + (2*typeMultiplier) == myMove.toRow
					&& myMove.fromColumn == myMove.toColumn) {
				isValid = true;
			}
			// move one
			if(myMove.fromRow + typeMultiplier == myMove.toRow) {
				// if only moving forward
				if(myMove.fromColumn == myMove.toColumn) {
					isValid = true;
				}	
				// if trying to capture left diagonal
				else if(myMove.toColumn == myMove.fromColumn - 1 
						&& board[myMove.toRow][myMove.toColumn].player() == super.player().next()) {
					isValid = true;
				}	
				// if trying to capture right diagonal
				else if(myMove.toColumn == myMove.fromColumn + 1 
					&& board[myMove.toRow][myMove.toColumn].player() == super.player().next()) {
					isValid = true;
				}
			}
		}
		return isValid;
	}
}