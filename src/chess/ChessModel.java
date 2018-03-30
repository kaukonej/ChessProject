package chess;

import java.awt.Color;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Queen;
import Pieces.Rook;

/**********************************************************************
 * ChessModel is a class that manages a game of chess. Pieces are
 * placed on a board, and you can move pieces, check if pieces can
 * be moved to certain locations, as well as see if either player
 * is in check or checkmate. Pawns which reach the opposite side
 * of the board can promote as per the rules of chess. This class
 * also contains methods for a chess AI, which can get out of
 * check, take opponent's pieces, escape from harm, and so on.
 * 
 * @author Justin Kaukonen, Adrian Harrell, Aaron
 *********************************************************************/

public class ChessModel implements IChessModel {
	/* Board that contain the chess pieces */
	private IChessPiece[][] board;

	/* The current player; whose turn it is */
	private Player player;

	/* The current state of the game (check/not in check) */
	private GameState gameState;

	/* If the game is over, this is true */
	private boolean isComplete;

	/* Move found for the AI using the appropraite AI method */
	private Move foundMove;

	/* Stores the from column index for the last move */
	private int prevFromCol;

	/* Stores the from row index for the last move */
	private int prevFromRow;

	/* Stores the to column index for the last move */
	private int prevToCol;

	/* Stores the to row index for the last move */
	private int prevToRow;

	/* Stores the from piece for the last move */
	private IChessPiece lastFromPiece;

	/* Stores the to piece for the last move */
	private IChessPiece lastToPiece;

	/******************************************************************
	 * Basic constructor. Creates a board, and the reset() method
	 * places all the pieces and sets the variables to their
	 * initial states.
	 *****************************************************************/
	public ChessModel() {
		board = new IChessPiece[8][8];
		reset();
	}

	/******************************************************************
	 * Method used to set all the variables to their initial state, as
	 * well as the board to its initial state.
	 *****************************************************************/
	public void reset() {
		isComplete = false;
		player = Player.WHITE;
		gameState = GameState.NOT_IN_CHECK;

		// Sets the board to all blank spaces
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				board[row][col] = null;
			}
		}

		// Places special pieces for black
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
		// And white pawns
		board[6][0] = new Pawn(Player.WHITE);
		board[6][1] = new Pawn(Player.WHITE);
		board[6][2] = new Pawn(Player.WHITE);
		board[6][3] = new Pawn(Player.WHITE);
		board[6][4] = new Pawn(Player.WHITE);
		board[6][5] = new Pawn(Player.WHITE);
		board[6][6] = new Pawn(Player.WHITE);
		board[6][7] = new Pawn(Player.WHITE);
	}

	/******************************************************************
	 * If a pawn reaches either end of the board, this method is called.
	 * It may then choose to promote itself to either a rook, knight,
	 * bishop, or queen.
	 * @param piece The piece that the pawn is promoted to.
	 * @param move The move that moved the pawn to the end of the board.
	 *****************************************************************/
	public void promote(int piece, Move move) {
		if(piece == 0)
			board[move.toRow][move.toColumn] = new Rook(player.next());
		else if(piece == 1)
			board[move.toRow][move.toColumn] = new Knight(player.next());
		else if(piece == 2)
			board[move.toRow][move.toColumn] = new Bishop(player.next());
		else if(piece == 3)
			board[move.toRow][move.toColumn] = new Queen(player.next());
	}

	/******************************************************************
	 * This method checks whether or not a move is valid for a given
	 * piece on the board.
	 * @see chess.IChessModel#isValidMove(chess.Move)
	 * @param move Passed-in moved to be evaluated.
	 * @return true if the piece exists at the passed-in move's
	 * coordinates, and that piece belongs to the current player.
	 * The move must also be valid for the piece itself.
	 *****************************************************************/
	public boolean isValidMove(Move move) {
		// The piece you are moving must exist
		if (board[move.fromRow][move.fromColumn] != null) {
			// It must belong to you (You can't move your opponent's 
			// pieces)
			if (board[move.fromRow][move.fromColumn].player() == 
					player) {
				// Check if the move is valid for the piece at the given 
				// coordinates on the board
				if (board[move.fromRow][move.fromColumn].
						isValidMove(move, board)) {
					// Make sure the move doesn't put themselves in check
					return true;
				}
			}
		}
		return false;
	}

	/******************************************************************
	 * Checks to see if, after a given move, the current player will be 
	 * in check.
	 * @param move Move to check.
	 * @return true If a player is not in check after the move is taken.
	 *****************************************************************/
	public boolean inCheckAfterMove(Move move) {
		boolean inCheckAfterMove = false;
		// Save piece in "to" tile, and then move "from" to "to" tile.
		IChessPiece tempTo = board[move.toRow][move.toColumn];
		board[move.toRow][move.toColumn] = 
				board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;

		// If the current player is in check, method will return true
		if (inCheck(player)) {
			inCheckAfterMove = true;
		}
		// Undo the move that was made, and return true.
		board[move.fromRow][move.fromColumn] = 
				board[move.toRow][move.toColumn];
		board[move.toRow][move.toColumn] = tempTo;
		return inCheckAfterMove;
	}

	/******************************************************************
	 * Using a given move, move the "from" piece to the "to" piece.
	 * Also saves the move, so that you can undo() it after (for use
	 * in other methods, not for player use)
	 * @see chess.IChessModel#move(chess.Move)
	 * @param move Move that will be done.
	 *****************************************************************/
	public void move(Move move) {
		saveMove(move);
		board[move.toRow][move.toColumn] = 
				board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
	}

	/******************************************************************
	 * Saves the coordinates of the last move.
	 * @param move Move that will be saved.
	 *****************************************************************/
	public void saveMove(Move move)
	{
		prevFromCol = move.fromColumn;
		prevFromRow = move.fromRow;
		lastFromPiece = board[move.fromRow][move.fromColumn];
		prevToCol = move.toColumn;
		prevToRow = move.toRow;
		lastToPiece = board[move.toRow][move.toColumn];
	}

	/******************************************************************
	 * Undoes the last move. For use in other methods only, not used
	 * to allow the player to undo mistakes because they can't think
	 * a single move ahead.
	 *****************************************************************/
	public void undo() {
		board[prevFromRow][prevFromCol] = lastFromPiece;
		board[prevToRow][prevToCol] = lastToPiece;
	}

	/******************************************************************
	 * Check to see if a given player is in check.
	 * @see chess.IChessModel#inCheck(chess.Player)
	 * @param playerToCheck The player to check if they're in check.
	 * @return true If any piece can reach the passed-in player's king.
	 *****************************************************************/
	public boolean inCheck(Player playerToCheck) {
		Player opponentType;
		int toCol = 0;
		int toRow = 0;
		int fromCol = 0;
		int fromRow = 0;

		// Sets the type of pieces to look for based on the passed-in 
		// player.
		if (playerToCheck == Player.WHITE) {
			opponentType = Player.BLACK;
		} else {
			opponentType = Player.WHITE;
		}

		// Check board for player's king, save coordinates
		for (int col = 0; col <= 7; col++) {
			for (int row = 0; row <= 7; row++) {
				if (board[row][col] != null) {
					if (board[row][col].type().equals("King") && 
							board[row][col].player() == playerToCheck) {
						toCol = col;
						toRow = row;
					}
				}
			}
		}

		// Search board for pieces that belong to your opponent
		for (int col = 0; col <= 7; col++) {
			for (int row = 0; row <= 7; row++) {
				if (board[row][col] != null) {
					if (board[row][col].player() == opponentType) {
						fromCol = col;
						fromRow = row;
						// See if any opponent piece can capture your 
						// king
						Move checkMove = new Move(fromRow, fromCol, 
								toRow, toCol);
						changeTurn(opponentType);
						if (isValidMove(checkMove)) {
							// If it's a valid move, the king is in 
							// check.
							gameState = GameState.IN_CHECK;
							changeTurn(playerToCheck);
							return true;
						}
					}
				}
			}
		}
		// If nothing is found, player must not be in check.
		gameState = GameState.NOT_IN_CHECK;
		changeTurn(playerToCheck);
		return false;
	}

	/******************************************************************
	 * Checks to see if a given player is in checkmate. This is our
	 * gameOver() method, essentially, we just renamed it to
	 * "inCheckmate" since that seemed to describe what it did more
	 * accurately, wheras "gameOver" might get confused with 
	 * setComplete().
	 * @param playerToCheck The player to check to see if they're in 
	 * checkmate.
	 * @return true If the passed-in player cannot take any moves
	 * without remaining in check.
	 *****************************************************************/
	public boolean inCheckmate(Player playerToCheck) {
		int fromRow;
		int fromCol;
		// If a move cannot be found, then the player is in checkmate.
		boolean canFindMove = false;
		// Check the whole board for pieces belonging to the player.
		for(int row = 0; row <= 7; row++) {
			for(int col = 0; col <= 7; col++) {
				if (board[row][col] != null) {
					if (board[row][col].player() == playerToCheck) {
						fromRow = row;
						fromCol = col;
						// Try to move the found piece literally 
						// everywhere on the board.
						for (int row2 = 0; row2 <= 7; row2++) {
							for (int col2 = 0; col2 <= 7; col2++) {
								Move checkMove = new Move(fromRow, 
										fromCol, row2, col2);
								// If any of these moves may be taken, 
								// then the player is not checkmated.
								if (isValidMove(checkMove) && 
										!inCheckAfterMove(checkMove)) {
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

	/******************************************************************
	 * Checks if a given move will put a piece in danger.
	 * @param playerToCheck The player to check if they're in danger
	 * after a move.
	 * @param move The move to check if the player will be in danger
	 * after.
	 * @returns true If any enemy piece can take the passed-in
	 * player's piece after the passed-in move.
	 *****************************************************************/
	public boolean inDangerAfterMove(Player playerToCheck, Move move)
	{
		Player opponentType;
		boolean isValid = false;

		if (playerToCheck == Player.WHITE) {
			opponentType = Player.BLACK;
		} else {
			opponentType = Player.WHITE;
		}
		
		// Look for opponent pieces
		for(int col = 0; col <= 7; col++) { 
			for(int row = 0; row <= 7; row++) {
				// If a piece exists at the coordinates
				if(board[row][col] != null ) { 
					// If piece belongs to the enemy player
					if(board[row][col].player() == opponentType) { 
						// If your piece can move to the "to" coords
						if(board[move.fromRow][move.fromColumn].
								isValidMove(move, board)) { 
							saveMove(move);
							move(move);
							// Test if enemy piece can reach your piece 
							// after moving.
							Move test = new Move(row, col, move.toRow, 
									move.toColumn);
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
		changeTurn(playerToCheck);
		return isValid; // If none of their pieces can get you than it 
		// isn't in danger.
	}

	// AI method, if the computer is in check, it moves the computer 
	// out of check.
	private boolean moveOutOfCheck() {
		// Searches entire board for own pieces, and tries every move.
		for(int row = 0; row <= 7; row++) {
			for(int col = 0; col <= 7; col++) {
				if (board[row][col] != null) {
					if (board[row][col].player() == Player.BLACK) {
						for (int row2 = 0; row2 <= 7; row2++) {
							for (int col2 = 0; col2 <= 7; col2++) {
								Move checkMove = new Move(row, col, 
										row2, col2);
								// If any of these moves may be taken, 
								// then the player can escape check.
								if (isValidMove(checkMove) && 
										!inCheckAfterMove(checkMove)) {
									foundMove = checkMove;
									return true;
								}
							}
						}
					}
				}
			}
		}
		// If no move is found, the computer is checkmated.
		return false;
	}

	// AI method, checks if any of its own pieces are in danger.
	// Please forgive the text wrap. I literally couldn't get it
	// below 72 characters in most cases.
	private boolean isPieceInDanger(Player p)
	{
		Player opponentType = Player.WHITE;

		// Search entire board for opponents
		for (int col = 0; col <= 7; col++) {
			for (int row = 0; row <= 7; row++) {
				if (board[row][col] != null) {
					if (board[row][col].player() == opponentType) {
						//Search entire board for computer pieces
						for (int row2 = 0; row2 <= 7; row2++) {
							for (int col2 = 0; col2 <= 7; col2++) {
								if (board[row2][col2] != null) {
									if (board[row2][col2].player() == 
											Player.BLACK) {
										// If any of the opponent pieces 
										// can get to your black pieces, 
										// they're in danger.
										Move newMove = new Move(row, 
												col, row2, col2);
										changeTurn(opponentType);
										if (isValidMove(newMove)) {
											// If they're in danger, 
											// search for a move that 
											// gets them out of danger.
											for (int row3 = 0; row3 < 8; 
													row3++) {
												for (int col3 = 0; col3 
														< 8; col3++) {
													Move escapeMove = 
														new Move(row2, 
														col2, row3, col3);
													// Check to make sure that, 
													// if they can do the move, 
													// that it actually gets 
													// them out of danger.
													if (isValidMove(escapeMove) 
															&& !inDangerAfterMove
															(Player.BLACK, 
																	escapeMove)) {
														// If it does, take that move.
														foundMove = escapeMove;
														changeTurn(Player.BLACK);
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
			}
		}
		return false;
	}

	// AI method, checks to see if the computer can take a piece.
	private boolean canTakePiece(Player p)
	{
		Player opponentType = Player.WHITE;

		// Checks the entire board for computer pieces
		for (int col = 0; col <= 7; col++) {
			for (int row = 0; row <= 7; row++) {
				if (board[row][col] != null) {
					if (board[row][col].player() == p) {
						// Checks the entire board for moves it can 
						// potentially take
						for (int row2 = 0; row2 <= 7; row2++) {
							for (int col2 = 0; col2 <= 7; col2++) {
								Move newMove = new Move(row, col, row2, 
										col2);
								// If it can both reach that location, 
								// and that location has an enemy, take 
								// that move.
								if (isValidMove(newMove) && 
										board[row2][col2].player() == 
										opponentType) {
									foundMove = newMove;
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	// AI method, tries to put the player in check.
	private boolean canPutOpponentInCheck(Player p)
	{
		Player opponentType = Player.WHITE;
		boolean canPutInCheck = false;

		// Checks the entire board for computer pieces
		for (int col = 0; col <= 7; col++) {
			for (int row = 0; row <= 7; row++) {
				if (board[row][col] != null) {
					if (board[row][col].player() == p) {
						// Checks the entire board for places it can 
						// move.
						for (int row2 = 0; row2 <= 7; row2++) {
							for (int col2 = 0; col2 <= 7; col2++) {
								Move testMove = new Move(row, col, row2, 
										col2);
								// If the move is valid, try it.
								if(isValidMove(testMove)) {
									move(testMove);
									// If it puts white in check, take 
									// that move.
									if (inCheck(opponentType)) {
										canPutInCheck = true;
										foundMove = testMove;
									}
									undo();
								}
							}
						}
					}
				}
			}
		}
		return canPutInCheck;
	}

	// AI method to find a move.
	private Move aiMove()
	{
		// PRIORITY 1: If in check, move out of check.
		if(inCheck(Player.BLACK))
		{
			if (moveOutOfCheck()) {
				return foundMove;
			}
		}

		// PRIORITY 2: If in danger, move out of danger.
		// isPieceInDanger call saves the move.
		if(isPieceInDanger(Player.BLACK))
		{
			return foundMove;
		}

		// PRIORITY 3: If computer can take enemy piece, do that move.
		if(canTakePiece(Player.BLACK))
		{
			return foundMove;
		}

		// PRIORITY 4: If the computer can put the opponent in check, 
		// do that move.
		if(canPutOpponentInCheck(Player.BLACK))
		{
			return foundMove;
		}

		// PRIORITY 5: If none of the above pick a random move
		Random rand = new Random();
		int num = rand.nextInt(60);
		int increment = 0;
		for (int col = 0; col <= 7; col++) {
			for (int row = 0; row <= 7; row++) {
				if (board[row][col] != null) {
					if (board[row][col].player() == Player.BLACK) {
						for (int row2 = 0; row2 <= 7; row2++) {
							for (int col2 = 0; col2 <= 7; col2++) {
								Move newMove = new Move(row, col, row2, 
										col2);
								changeTurn(Player.BLACK);
								// Tries to make a safe move
								if(isValidMove(newMove) && 
										!inDangerAfterMove(Player.BLACK, 
												newMove))
								{
									foundMove = newMove;
									if(increment == num)
									{
										return foundMove;
									}
									increment++;
									// If a move is found that's valid 
									// at all, saves that one, in case 
									// no "Safe" move is found
								} else if (isValidMove(newMove) && 
										foundMove == null) {
									foundMove = newMove;
								}
							}
						}
					}
				}
			}
		}
		return foundMove;
	}

	/******************************************************************
	 * Takes a single move for the AI, and changes the turn to the play
	 * er's turn.
	 *****************************************************************/
	public void aiTurn()
	{
		move(aiMove());
		nextTurn();
	}

	/******************************************************************
	 * Enumeration used to keep track of whether someone is in check
	 * or not.
	 *****************************************************************/
	public enum GameState {
		IN_CHECK, NOT_IN_CHECK;
	}

	/* GETTERS AND SETTERS */
	/******************************************************************
	 * Returns the current players.
	 * @see chess.IChessModel#currentPlayer()
	 * @return The current player (Player.BLACK or Player.WHITE)
	 *****************************************************************/
	public Player currentPlayer() {
		return player;
	}

	/******************************************************************
	 * Returns the piece at a given coordinate.
	 * @param row The row to check.
	 * @param col The column to check.
	 * @return The IChessPiece at the given coordinates on the board.
	 *****************************************************************/
	public IChessPiece pieceAt( int row, int col ) {
		return board[row][col];
	}

	/******************************************************************
	 * Returns if the game is or isn't complete.
	 * @see chess.IChessModel#isComplete()
	 * @returns isComplete, which is set to true when someone is 
	 * checkmated.
	 *****************************************************************/
	public boolean isComplete() {
		return isComplete;
	}

	/*
	 * Gets the current state of whether someone is in check or not.
	 * @returns either GameState.IN_CHECK or GameState.NOT_IN_CHECK
	 * depending on whether the player is or is not in check.
	 */
	public GameState getState() {
		return gameState;
	}

	/******************************************************************
	 * Sets whether the game is over to the passed in boolean flag.
	 *****************************************************************/
	public void setComplete(boolean flag) {
		if (isComplete == false) {
			isComplete = flag;
		}
	}

	/******************************************************************
	 * Changes the turn to the passed in player.
	 *****************************************************************/
	public void changeTurn(Player p)
	{
		player = p;
	}

	/******************************************************************
	 * Sets the current turn to the next player, so they can take their 
	 * turn.
	 *****************************************************************/
	public void nextTurn()
	{
		if (player == Player.WHITE) {
			player = Player.BLACK;
		} else {
			player = Player.WHITE;
		}
	}
}