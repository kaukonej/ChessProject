package chess;

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
	private IChessPiece[][] undoBoard1;
	private IChessPiece[][] undoBoard2;
	private IChessPiece[][] undoBoard3;
	private boolean canUndo;
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
		canUndo = false;
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
	
	public enum GameState {
	    IN_CHECK, NOT_IN_CHECK;
	}
	
	public boolean isValidMove(Move move) {
		// complete this
		// if piece at "from" does not exist
		// return false
		// else if piece exists
		// check if it can move to "to" space
		// return true if it can, but otherwise
		// piece = board[][];
		// piece.isValidMove(new Move(piece.col, piece.row, piece.))

		if (board[move.fromRow][move.fromColumn].player() == player) {
			if (board[move.fromRow][move.fromColumn].isValidMove(move, board)) {
				if (gameState == GameState.NOT_IN_CHECK) {
					move(move);
				}
			}
		}

		// overloaded, not overridden
		// called from the GUI
		// if piece exists at from coords
		// if board[fromrow][fromcol].isValidMove(move, board) == true (if move works for piece at from
		// IChessPiece from piece = board[from][from]
		// IChessPiece to piece = board[to][to]

		// boolean valid = false;
		// YOU CANT MOVE INTO CHECK
		// if not in check,
		// move(move);
		// if in check is still false
		// set not in check
		// valid = true
		// if in check is true
		// set in check
		// valid = false;
		// board from = frompiece
		// board[to] = topiece
		return false; // return valid
	}
	public void move(Move move) {
		// complete this
		// if the move is a valid move,
		// if any pieces captured, store them somehow for later
		// set piece at "from" to "to"

		if (isValidMove(move)) {
			undoBoard3 = undoBoard2;
			undoBoard2 = undoBoard1;
			undoBoard1 = board;
			// maybe store captured piece if captured?
			board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
			board[move.fromRow][move.fromColumn] = null;
		}
	}

	public void undo() {
		if (undoBoard1 != null) {
			board = undoBoard1;
			undoBoard1 = undoBoard2;
			undoBoard2 = undoBoard3;
			undoBoard3 = null;
		} if (undoBoard1 == null) {
			canUndo = false;
		}
	}
	
	//TODO SetPiece??????
	
	private void setNextPlayer() {
		player = player.next();
	}

	public boolean inCheck(Player p) {
		// TODO: Add method, use GameState.IN_CHECK and GameState.NOT_IN_CHECK
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
		// TODO Auto-generated method stub
		return isComplete;
	}

	// add other public or helper methods as needed
}
