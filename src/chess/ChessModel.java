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
		// Special pieces for black
//		board[0][0] = new Rook(Player.BLACK);
//		board[1][0] = new Knight(Player.BLACK);
//		board[2][0] = new Bishop(Player.BLACK);
//		board[3][0] = new King(Player.BLACK);
//		board[4][0] = new Queen(Player.BLACK);
//		board[5][0] = new Bishop(Player.BLACK);
//		board[6][0] = new Knight(Player.BLACK);
//		board[7][0] = new Rook(Player.BLACK);
//		// Special pieces for white
//		board[0][7] = new Rook(Player.WHITE);
//		board[1][7] = new Knight(Player.WHITE);
//		board[2][7] = new Bishop(Player.WHITE);
//		board[3][7] = new King(Player.WHITE);
//		board[4][7] = new Queen(Player.WHITE);
//		board[5][7] = new Bishop(Player.WHITE);
//		board[6][7] = new Knight(Player.WHITE);
//		board[7][7] = new Rook(Player.WHITE);
//		// Black pawns
//		board[0][1] = new Pawn(Player.BLACK);
//		board[1][1] = new Pawn(Player.BLACK);
//		board[2][1] = new Pawn(Player.BLACK);
//		board[3][1] = new Pawn(Player.BLACK);
//		board[4][1] = new Pawn(Player.BLACK);
//		board[5][1] = new Pawn(Player.BLACK);
//		board[6][1] = new Pawn(Player.BLACK);
//		board[7][1] = new Pawn(Player.BLACK);
//		// White pawns
//		board[0][6] = new Pawn(Player.WHITE);
//		board[1][6] = new Pawn(Player.WHITE);
//		board[2][6] = new Pawn(Player.WHITE);
//		board[3][6] = new Pawn(Player.WHITE);
//		board[4][6] = new Pawn(Player.WHITE);
//		board[5][6] = new Pawn(Player.WHITE);
//		board[6][6] = new Pawn(Player.WHITE);
//		board[7][6] = new Pawn(Player.WHITE);
	}
	public boolean gameOver( ) {
		return false;
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
		
		// save from row
		// save from col
		// save from piece
		// save to row
		// save to col
		// save to piece
		
		// bpard[to] = board[from]
		// board[from] = null
	}
	
	// methods to do
	// numRows()
	//numCols
	//PieceAt
	//SetNextPlayer
	//SetPiece
	
	
	public boolean inCheck(Player p) {
		return false;
	}
	public Player currentPlayer() {
		// complete this
		return player;
	}
	public int numRows() {
		// complete this
		return 0;
	}
	public int numColumns() {
		// complete this
		return 0;
	}
	public IChessPiece pieceAt( int row, int col ) { // parameter was originally "Square s"
		return board[row][col];
	}
	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	// add other public or helper methods as needed
}
