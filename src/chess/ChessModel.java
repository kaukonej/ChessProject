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

	public ChessModel() {
		board = new IChessPiece[8][8];
		reset();
	}

	public void reset() {
		isComplete = false;
		player = Player.WHITE;
		gameState = GameState.NOT_IN_CHECK;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				board[r][c] = null;
			}
		}
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

	// promotes players pawn
	public void promote(int piece, Move m) {
		// TODO I don't think we need to save the move here, since move() does that.
		// This method should trigger if a pawn moves into the final row.
		//saveMove(m);
		//board[m.fromRow][m.fromColumn]= null;

		if(piece == 0)
			board[m.toRow][m.toColumn] = new Rook(player.next());
		else if(piece == 1)
			board[m.toRow][m.toColumn] = new Knight(player.next());
		else if(piece == 2)
			board[m.toRow][m.toColumn] = new Bishop(player.next());
		else if(piece == 3)
			board[m.toRow][m.toColumn] = new Queen(player.next());

		//player = player.next();
	}

	// Checks if a given move is valid with the current game board
	public boolean isValidMove(Move move) {
		// The piece you are moving must exist
		if (board[move.fromRow][move.fromColumn] != null) {
			// It must belong to you (You can't move your opponent's pieces)
			if (board[move.fromRow][move.fromColumn].player() == player) { //TODO: For some reason this line breaks everything. Be careful.
				// Check if the move is valid for the piece at the given coordinates on the board
				if (board[move.fromRow][move.fromColumn].isValidMove(move, board)) {
					// Make sure the move doesn't put themselves in check
					//if (!inCheckAfterMove(move)) {
					return true;
					//}
				}
			}
		}
		return false;
	}

	/*
	 * Checks to see if, after a given move, the current player will be in check.
	 */
		private boolean inCheckAfterMove(Move move) {
			boolean inCheckAfterMove = false;
			// Save piece in "to" tile, and then move "from" to "to" tile.
			IChessPiece tempTo = board[move.toRow][move.toColumn];
			board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
			board[move.fromRow][move.fromColumn] = null;
	
			// If the current player is in check, method will return true
			if (inCheck(player)) {
				inCheckAfterMove = true;
			}
			// Undo the move that was made, and return true.
			board[move.fromRow][move.fromColumn] = board[move.toRow][move.toColumn];
			board[move.toRow][move.toColumn] = tempTo;
			return inCheckAfterMove;
		}

	/*
	 * Move a piece from point A to point B, and check if the player is in checkmate. Also save the move so it may be undone.
	 * @see chess.IChessModel#move(chess.Move)
	 */
	public void move(Move move) {
		saveMove(move);
		board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
	}

	/*
	 * Saves the coordinates of the last move.
	 */
	public void saveMove(Move move)
	{
		prevFromCol = move.fromColumn;
		prevFromRow = move.fromRow;
		lastFromPiece = board[move.fromRow][move.fromColumn];
		prevToCol = move.toColumn;
		prevToRow = move.toRow;
		lastToPiece = board[move.toRow][move.toColumn];
	}

	/*
	 * Undo the last move.
	 */
	public void undo() {
		board[prevFromRow][prevFromCol] = lastFromPiece;
		board[prevToRow][prevToCol] = lastToPiece;
	}

	/*
	 * Check to see if a given player is in check.
	 * @see chess.IChessModel#inCheck(chess.Player)
	 */
	public boolean inCheck(Player p) {
		Player opponentType;
		int toCol = 0;
		int toRow = 0;
		int fromCol = 0;
		int fromRow = 0;

		// Sets the type of pieces to look for based on the passed-in player.
		if (p == Player.WHITE) {
			opponentType = Player.BLACK;
		} else {
			opponentType = Player.WHITE;
		}

		// Check board for black/white king, save coordinates
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

		 // change it so its valid if opponent piece moves to you
		// Search board for pieces
		for (int c = 0; c <= 7; c++) {
			for (int r = 0; r <= 7; r++) {
				if (board[r][c] != null) {
					if (board[r][c].player() == opponentType) {
						fromCol = c;
						fromRow = r;
						// See if any opponent piece can capture your king
						Move checkMove = new Move(fromRow, fromCol, toRow, toCol);
						changeTurn(opponentType);
						if (isValidMove(checkMove)) {
							// If it's a valid move, the king is in check.
							gameState = GameState.IN_CHECK;
							changeTurn(p);
							return true;
						}
					}
				}
			}
		}
		// If nothing is found, player must not be in check.
		gameState = GameState.NOT_IN_CHECK;
		changeTurn(p);
		return false;
	}

	/*
	 * Checks to see if a given player is in checkmate.
	 */
	public boolean inCheckmate(Player p) {
		int fromRow;
		int fromCol;
		// If a move cannot be found, then the player is in checkmate.
		boolean canFindMove = false;
		// Check the whole board for pieces belonging to the player.
		for(int row = 0; row <= 7; row++) {
			for(int col = 0; col <= 7; col++) {
				if (board[row][col] != null) {
					if (board[row][col].player() == p) {
						fromRow = row;
						fromCol = col;
						// Try to move the found piece literally everywhere on the board.
						for (int r = 0; r <= 7; r++) {
							for (int c = 0; c <= 7; c++) {
								Move checkMove = new Move(fromRow, fromCol, r, c);
								// If any of these moves may be taken, then the player is not checkmated.
								if (isValidMove(checkMove) && !inCheckAfterMove(checkMove)) {
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

	/*
	 * Checks if a given move will put a piece in danger.
	 */
	public boolean inDangerAfterMove(Player p, Move move)
	{
		Player opponentType;
		boolean isValid = false;

		if (p == Player.WHITE) {
			opponentType = Player.BLACK;
		} else {
			opponentType = Player.WHITE;
		}

		for(int c = 0; c <= 7; c++) { // Look for opponent pieces
			for( int r = 0; r <= 7; r++) {
				if(board[r][c] != null ) { // If a piece exists at the coordinates
					if(board[r][c].player() == opponentType) { // If piece belongs to the enemy player
						if(board[move.fromRow][move.fromColumn].isValidMove(move, board)) { // If your piece can move to the "to" coords
							saveMove(move);
							move(move);
							// Test if enemy piece can reach your piece after moving.
							Move test = new Move(r, c, move.toRow, move.toColumn);
							changeTurn(opponentType);
							if (isValidMove(test)) {
								isValid = true;
							}
							undo(); // Undo your piece's move
						}
					}
				}
			}
		}
		changeTurn(p);
		return isValid; // If none of their pieces can get you than it isn't in danger.
	}

	/*
	 * Do AI stuff
	 */
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

	/*
	 * Checks if any piece belonging to a given player is in danger.
	 */
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
		player = p;
		return false;
	}

	// TODO: Promotion, setPiece

	public boolean canTakePiece(Player p)
	{
		return false;
	}

	public boolean canPutOpponentInCheck(Player p)
	{
		return false;
	}

	public enum GameState {
		IN_CHECK, NOT_IN_CHECK;
	}

	public Player currentPlayer() {
		return player;
	}

	public int numRows() {
		return 8;
	}

	public int numColumns() {
		return 8;
	}

	public IChessPiece pieceAt( int row, int col ) {
		return board[row][col];
	}

	@Override
	public boolean isComplete() {
		return isComplete;
	}

	public boolean getComplete() {
		return isComplete();
	}

	public GameState getState() {
		return gameState;
	}
	
	public void setComplete(boolean flag) {
		isComplete = flag;
	}

	public void changeTurn(Player p)
	{
		player = p;
	}

	public void nextTurn()
	{
		if (player == Player.WHITE) {
			player = Player.BLACK;
		} else {
			player = Player.WHITE;
		}
	}
}