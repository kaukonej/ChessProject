package chess;

public class ChessModel implements IChessModel {	 
	private IChessPiece[][] board;
	private Player player;
	// declare other instance variables as needed

	public ChessModel() {
		// complete this
	}
	public boolean gameOver( ) {
		return false;
	}
	public boolean isValidMove(Move move) {
		// complete this
		return false;
	}
	public void move(Move move) {
		// complete this
	}
	public boolean inCheck(Player p) {
		return false;
	}
	public Player currentPlayer() {
		// complete this
	}
	public int numRows() {
		// complete this
		return 0;
	}
	public int numColumns() {
		// complete this
		return 0;
	}
	public IChessPiece pieceAt( Square s ) {
		// complete this
	}
	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	// add other public or helper methods as needed
}
