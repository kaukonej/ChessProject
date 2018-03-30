package Pieces;

import chess.ChessPiece;
import chess.IChessPiece;
import chess.Move;
import chess.Player;

public class Pawn extends ChessPiece {
	/******************************************************************
	 * Constructs a pawn ChessPiece for the player entered by calling 
	 * the parent constructor in ChessPiece
	 * @param player the owner of the piece being constructed
	 *****************************************************************/
	public Pawn(Player player) {
		super(player);
	}

	/******************************************************************
	 *Gives  the types of ChessPiece
	 *@return A string describing the type of ChessPiece.
	 *@Override ChessPiece's type method.
	 ******************************************************************/
	public String type() {
		return "Pawn";
	}

	/******************************************************************
	 * This method checks a move in contrast to other pieces on the 
	 * board implementing the piece's unique logic.
	 * @return Boolean for if the move is valid.
	 * @param Move where the piece is to go
	 * @param Board used to see if there are current pieces in the way.
	 *****************************************************************/
	public boolean isValidMove(Move myMove, IChessPiece[][] board) {
		boolean isValid = false;
		if (super.isValidMove(myMove, board)) {
			// If it's a white piece, use this set of logic.
			if (super.player() == Player.WHITE) {
				if(myMove.fromRow == 6 &&
						myMove.toRow == myMove.fromRow - 2 &&
						myMove.fromColumn == myMove.toColumn && 
						board[myMove.toRow][myMove.toColumn] == null) {
					isValid = true;
				} else if (myMove.fromRow == myMove.toRow + 1) {
					// if only moving forward
					if(myMove.fromColumn == myMove.toColumn &&
							board[myMove.toRow][myMove.toColumn]
									== null) {
						isValid = true;
					}	
					// if trying to capture left diagonal
					else if(myMove.fromColumn == myMove.toColumn+1 &&
							board[myMove.toRow][myMove.toColumn]
									!= null) {
						if (board[myMove.toRow][myMove.toColumn].
								player() == Player.BLACK) {
							isValid = true;
						}
					}
					// if trying to capture right diagonal
					else if(myMove.fromColumn == myMove.toColumn - 1 &&
							board[myMove.toRow][myMove.toColumn] 
									!= null) {
						if (board[myMove.toRow][myMove.toColumn].
								player() == Player.BLACK) {
							isValid = true;
						}
					}
				}
			} else if (super.player() == Player.BLACK) {
				if(myMove.fromRow == 1 &&
						myMove.toRow == myMove.fromRow + 2 &&
						myMove.fromColumn == myMove.toColumn &&
						board[myMove.toRow][myMove.toColumn] == null) {
					isValid = true;
				} else if(myMove.fromRow == myMove.toRow - 1) {
					// if only moving forward
					if(myMove.fromColumn == myMove.toColumn &&
							board[myMove.toRow][myMove.toColumn] 
									== null) {
						isValid = true;
					}	
					// if trying to capture left diagonal
					else if(myMove.fromColumn == myMove.toColumn + 1 &&
							board[myMove.toRow][myMove.toColumn] 
									!= null) {
						if (board[myMove.toRow][myMove.toColumn].
								player() == Player.WHITE) {
							isValid = true;
						}
					}
					// if trying to capture right diagonal
					else if(myMove.fromColumn == myMove.toColumn - 1 &&
							board[myMove.toRow][myMove.toColumn] 
									!= null) {
						if (board[myMove.toRow][myMove.toColumn].
								player() == Player.WHITE) {
							isValid = true;
						}
					}
				}
			}
		}
		return isValid;
	}
}