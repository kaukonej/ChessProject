package chess;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Queen;
import Pieces.Rook;

public class ChessModel implements IChessModel {	 
	private IChessPiece[][] board;
	private Player player;
	private GameState gameState;
	private boolean isComplete;

	private int prevFromCol;
	private int prevFromRow;
	private int prevToCol;
	private int prevToRow;
	private IChessPiece lastFromPiece;
	private IChessPiece lastToPiece;
	// TODO: Undo method will probably need these variables
	// to row
	// to col
	// from row
	// from col
	// saveFromPiece
	//saveToPiece
	// declare other instance variables as needed

	public ChessModel() {
		// board is board[x][y] because that makes sense
		// complete this
		isComplete = false;
		board = new IChessPiece[8][8];
		player = Player.WHITE;
		gameState = GameState.NOT_IN_CHECK;
		// Special pieces for black
		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new King(Player.BLACK);
		board[0][4] = new Queen(Player.BLACK);
		board[0][5] = new Bishop(Player.BLACK);
		board[0][6] = new Knight(Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);
		// Special pieces for white
		board[7][0] = new Rook(Player.WHITE);
		board[7][1] = new Knight(Player.WHITE);
		board[7][2] = new Bishop(Player.WHITE);
		board[7][3] = new King(Player.WHITE);
		board[7][4] = new Queen(Player.WHITE);
		board[7][5] = new Bishop(Player.WHITE);
		board[7][6] = new Knight(Player.WHITE);
		board[7][7] = new Rook(Player.WHITE);
		// Black pawns
		board[1][0] = new Pawn(Player.BLACK);
		board[1][1] = new Pawn(Player.BLACK);
		board[1][2] = new Pawn(Player.BLACK);
		board[1][3] = new Pawn(Player.BLACK);
		board[1][4] = new Pawn(Player.BLACK);
		board[1][5] = new Pawn(Player.BLACK);
		board[1][6] = new Pawn(Player.BLACK);
		board[1][7] = new Pawn(Player.BLACK);
		// White pawns
		board[6][0] = new Pawn(Player.WHITE);
		board[6][1] = new Pawn(Player.WHITE);
		board[6][2] = new Pawn(Player.WHITE);
		board[6][3] = new Pawn(Player.WHITE);
		board[6][4] = new Pawn(Player.WHITE);
		board[6][5] = new Pawn(Player.WHITE);
		board[6][6] = new Pawn(Player.WHITE);
		board[6][7] = new Pawn(Player.WHITE);
	}

	private boolean isPieceInDanger (Player p) {
		Player opponentType;
		int toCol = 0;
		int toRow = 0;
		int fromCol = 0;
		int fromRow = 0;

		if (p == Player.WHITE) {
			opponentType = Player.BLACK;
		} else {
			opponentType = Player.WHITE;
		}

		//check board for black/white king, save
		for (int c = 0; c <= 7; c++) {
			for (int r = 0; r <= 7; r++) {
				if (board[r][c] != null) {
					if (board[r][c].player() == opponentType) {// && board[r][c].player() == p) {
						fromCol = c;
						fromRow = r;
						for (int c2 = 0; c2 <= 7; c2++) {
							for (int r2 = 0; r2 <= 7; r2++) {
								if (board[r2][c2] != null) {
									if (board[r2][c2].player() == p) {
										toCol = c2;
										toRow = r2;
										// make a move with from for the pieces, and to coords are the king's location
										Move checkMove = new Move(fromRow, fromCol, toRow, toCol);
										if (isValidMove(checkMove)) {
											if (board[toRow][toCol].player() == opponentType) {
												// if it's a valid move, the king is in check.
												return true;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isPieceInDanger(Player p, Move move)
	{
		Player opponentType;
		int toCol = 0;
		int toRow = 0;
		int fromCol = 0;
		int fromRow = 0;

		if (p == Player.WHITE) {
			opponentType = Player.BLACK;
		} else {
			opponentType = Player.WHITE;
		}

		// TODO: Redo this method. Have it actually try moving the piece there, and then see if it's a valid move.
		// If it is a valid move, undo the move you just made, and return true. If the opponent cannot reach you, return false.
		//check board for black/white king, save
		for (int c = 0; c <= 7; c++) {
			for (int r = 0; r <= 7; r++) {
				if (board[r][c] != null) {
					if (board[r][c].player() == opponentType) {// && board[r][c].player() == p) {
						fromCol = c;
						fromRow = r;
						// if the enemy piece can get to where you're trying to go

						// if enemy is of type pawn
						// r and c and enemy piece's coordinates
						if (board[r][c].type().equals("Pawn")) {
							// enemy pawn can't capture forward
							if (c == move.toColumn) {
								return false;
							}
							// but it can capture diagonal right
							// if one col right, one row up, and opponent is white, or one col right, one row down, and opponent is black
							if ((move.toColumn == c + 1 && move.toRow == r - 1 && opponentType == Player.WHITE) || (move.toColumn == c + 1 && move.toRow == r + 1 && opponentType == Player.BLACK)){
								return true;
								// as well as diagonal left
							} else if ((move.toColumn == c - 1 && move.toRow == r - 1 && opponentType == Player.WHITE) || (move.toColumn == c - 1 && move.toRow == r + 1 && opponentType == Player.BLACK)) {
								return true;
							}
						} else {
							Move newMove = new Move(fromRow, fromCol, move.toRow, move.toColumn);
							if (isValidMove(newMove)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public enum GameState {
		IN_CHECK, NOT_IN_CHECK;
	}



	public boolean isValidMove(Move move) {
		if (board[move.fromRow][move.fromColumn] != null) {
			//if (board[move.fromRow][move.fromColumn].player() == player) { //TODO: For some reason this line breaks everything. Be careful.
			if (board[move.fromRow][move.fromColumn].isValidMove(move, board)) {
				if (!inCheckAfterMove(move)) {
					return true;
				}
			}
			//}
		}
		return false;
	}

	private boolean inCheckAfterMove(Move move) {
		boolean inCheckAfterMove = false;

		IChessPiece tempTo = board[move.toRow][move.toColumn];
		board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;

		if (inCheck(player)) {
			inCheckAfterMove = true;
		}
		board[move.fromRow][move.fromColumn] = board[move.toRow][move.toColumn];
		board[move.toRow][move.toColumn] = tempTo;
		return inCheckAfterMove;
	}

	public void move(Move move) {
		board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
		saveMove(move);
		player = player.next();
		if (inCheckmate(player)) {
			isComplete = true;
		}
	}
	public void saveMove(Move move)
	{
		prevFromCol = move.fromColumn;
		prevFromRow = move.fromRow;
		lastFromPiece = board[move.fromRow][move.fromColumn];
		prevToCol = move.toColumn;
		prevToRow = move.toRow;
		lastFromPiece = board[move.fromRow][move.fromColumn];
	}
	public void undo() {
		board[prevFromRow][prevFromCol] = lastFromPiece;
		board[prevToRow][prevToCol] = lastToPiece;
	}

	//TODO SetPiece??????

	public boolean inCheck(Player p) {
		Player opponentType;
		int toCol = 0;
		int toRow = 0;
		int fromCol = 0;
		int fromRow = 0;

		if (p == Player.WHITE) {
			opponentType = Player.BLACK;
		} else {
			opponentType = Player.WHITE;
		}

		//check board for black/white king, save
		for (int c = 0; c <= 7; c++) {
			for (int r = 0; r <= 7; r++) {
				if (board[r][c] != null) {
					if (board[r][c].type().equals("King") && board[r][c].player() == p) {
						toCol = c;
						toRow = r;
					}
				}
			}
		}

		// search board for pieces
		for (int c = 0; c <= 7; c++) {
			for (int r = 0; r <= 7; r++) {
				if (board[r][c] != null) {
					if (board[r][c].player() == opponentType) {
						fromCol = c;
						fromRow = r;
						// make a move with from for the pieces, and to coords are the king's location
						Move checkMove = new Move(fromRow, fromCol, toRow, toCol);
						if (isValidMove(checkMove)) {
							// if it's a valid move, the king is in check.
							// TODO: Set gamestate to IN_CHECK
							gameState = GameState.IN_CHECK;
							return true;
						}
					}
				}
			}
		}
		gameState = GameState.NOT_IN_CHECK;
		return false;
	}
	public Player currentPlayer() {
		// complete this
		return player;
	}
	public int numRows() {
		// complete this
		return 8;
	}
	public int numColumns() {
		// complete this
		return 8;
	}
	public IChessPiece pieceAt( int row, int col ) { // parameter was originally "Square s"
		return board[row][col];
	}
	@Override
	public boolean isComplete() {
		return isComplete;
	}

	public boolean inCheckmate(Player p) {
		int fromRow;
		int fromCol;
		boolean canFindMove = false;
		for(int row = 0; row <= 7; row++) {
			for(int col = 0; col <= 7; col++) {
				if (board[row][col] != null) {
					if (board[row][col].player() == p) {
						fromRow = row;
						fromCol = col;

						for (int r = 0; r <= 7; r++) {
							for (int c = 0; c <= 7; c++) {
								Move checkMove = new Move(fromRow, fromCol, r, c);
								if (isValidMove(checkMove)) {
									canFindMove = true;
								}
							}
						}
					}
				}
			}
		}
		return !canFindMove;
	}

	public boolean getComplete() {
		return isComplete();
	}

	public GameState getState() {
		return gameState;
	}

	public void aiTurn()
	{
		if(inCheckmate(Player.BLACK))
		{
			//getOutOfCheck

		}

		if(isPieceInDanger(Player.BLACK))
		{
			//movePieceToSafety

		}

		if(canTakePiece(Player.BLACK))
		{
			//takePiece

		}

		if(canPutOpponentInCheck(Player.BLACK))
		{
			//putOpponentInCheck

		}
	}

	public boolean canTakePiece(Player p)
	{
		return false;
	}

	public boolean canPutOpponentInCheck(Player p)
	{
		return false;
	}

	// add other public or helper methods as needed
}
