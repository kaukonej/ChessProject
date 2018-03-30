package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import chess.ChessModel;
import chess.ChessModel.GameState;
import chess.Move;
import chess.Player;

public class ChessPanel extends JPanel {

	private JButton[][] board;
	private ChessModel model;

	private JMenuItem resetItem;
	private JMenuItem quitItem;

	private JLabel currentTurnLabel;
	private JLabel currentTurnIcon;

	private ImageIcon iconWPawn;
	private ImageIcon iconWKnight;
	private ImageIcon iconWRook;
	private ImageIcon iconWBishop;
	private ImageIcon iconWKing;
	private ImageIcon iconWQueen;

	private ImageIcon iconBPawn;
	private ImageIcon iconBKnight;
	private ImageIcon iconBRook;
	private ImageIcon iconBBishop;
	private ImageIcon iconBKing;
	private ImageIcon iconBQueen;

	private ImageIcon iconBlank;

	private ChessModel game;

	private int fromRow;
	private int fromCol;
	private int toRow;
	private int toCol;
	private int click;
	
	boolean firstWon = false;
	boolean AIGame = true;

	private boolean displayCoordinates = false;

	public ChessPanel(JMenuItem panelQuitItem, JMenuItem 
			panelResetItem) {
		game = new ChessModel();

		quitItem = panelQuitItem;
		resetItem = panelResetItem;

		quitItem.addActionListener(new ButtonListener());
		resetItem.addActionListener(new ButtonListener()); 

		iconWPawn = new ImageIcon ("wPawn.png");
		iconWKnight = new ImageIcon ("wKnight.png");
		iconWRook = new ImageIcon ("wRook.png");
		iconWBishop = new ImageIcon ("wBishop.png");
		iconWKing = new ImageIcon ("wKing.png");
		iconWQueen = new ImageIcon ("wQueen.png");
		iconBPawn = new ImageIcon ("bPawn.png");
		iconBKnight = new ImageIcon ("bKnight.png");
		iconBRook = new ImageIcon ("bRook.png");
		iconBBishop = new ImageIcon ("bBishop.png");
		iconBKing = new ImageIcon ("bKing.png");
		iconBQueen = new ImageIcon ("bQueen.png");

		setLayout(new GridBagLayout());
		GridBagConstraints loc = new GridBagConstraints();

		board = new JButton[8][8];

		for (int row = 0; row < 8; row++) { // 0, 1, 2, 3, 4, 5, 6, 7 (8 total)
			for (int col = 0; col < 8; col++) {
				board[row][col] = new JButton();
				if (displayCoordinates) { // Display coordinates of each tile?
					board[row][col] = new JButton("(" + col + ", " + row + ")");
				}
				// Set size of each tile, and add a listener so it can be clicked
				board[row][col].setPreferredSize(new Dimension(80, 80));
				board[row][col].addActionListener(new ButtonListener());
				// Add each element to the GridBagLayout
				loc = new GridBagConstraints();
				loc.gridx = col;
				loc.gridy = row;
				loc.insets.bottom = 0;
				loc.insets.top = 0;
				add(board[row][col], loc);				
			}
		}

		// Create a label and icon to display whose turn it currently is
		currentTurnLabel = new JLabel("Current Turn:");
		loc.gridx = 0;
		loc.gridy = 9;
		loc.insets.bottom = 0;
		loc.insets.top = 0;
		add(currentTurnLabel, loc);

		currentTurnIcon = new JLabel("",iconWPawn,SwingConstants.CENTER);
		loc.gridx = 1;
		loc.gridy = 9;
		loc.insets.bottom = 0;
		loc.insets.top = 0;
		add(currentTurnIcon, loc);
		
		String AIQuestion = "";
		
		 while (AIQuestion.length() != 1 || !Pattern.matches("[0,1]", 
	        		AIQuestion)) {
	        	AIQuestion = JOptionPane.showInputDialog(null, 
	        			"Would you like to play an AI game? 0 = Yes, "
	        			+ "1 = No");
	        	if (AIQuestion == null) { // If user presses 'Cancel'
	        		System.exit(1);
	        	} else if (AIQuestion.length() != 1) {
	            	JOptionPane.showMessageDialog(new JPanel(), "Input must be"
							+ " a single digit long.");
	            } else if (!Pattern.matches("[0-1]+", AIQuestion)) {
	            	JOptionPane.showMessageDialog(new JPanel(), "Input must be"
							+ " a positive integer, using only digits"
							+ " 0 or 1");
	            }
	        }
	        
	        if (AIQuestion.equals("0")) {
	        	AIGame = true;
	        } else if (AIQuestion.equalsIgnoreCase("1")) {
	        	AIGame = false;
	        }

		// Add all the icons to the board
		displayBoard();
		// Make the tiles black or white
		updateBackground();
	}

	/*
	 * Make tiles either black or white, like a chessboard.
	 */
	private void updateBackground() {
		for (int col = 0; col <= 7; col++) {
			for (int row = 0; row <= 7; row++) {
				if ((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1)) {
					board[row][col].setBackground(Color.WHITE);
				} else {
					board[row][col].setBackground(Color.BLACK);
				}
			}
		}
	}

	/*
	 * Updates the icons on the GUI to reflect the pieces on the game board.
	 */
	private void displayBoard() {
		// For each row and column, check what the piece there is.
		for (int row = 0; row <= 7; row++) {
			for (int col = 0; col <= 7; col++) {
				if (game.pieceAt(row, col) == null) {
					board[row][col].setIcon(null);
				} else {
					if (game.pieceAt(row, col).player() == Player.WHITE) {
						if (game.pieceAt(row, col).type() == "Pawn") {
							board[row][col].setIcon(iconWPawn);
						} else if (game.pieceAt(row, col).type() == "Bishop") {
							board[row][col].setIcon(iconWBishop);
						} else if (game.pieceAt(row, col).type() == "Knight") {
							board[row][col].setIcon(iconWKnight);
						} else if (game.pieceAt(row, col).type() == "King") {
							board[row][col].setIcon(iconWKing);
						} else if (game.pieceAt(row, col).type() == "Queen") {
							board[row][col].setIcon(iconWQueen);
						} else if (game.pieceAt(row, col).type() == "Rook") {
							board[row][col].setIcon(iconWRook);
						}
					} else if (game.pieceAt(row, col).player() == Player.BLACK){
						if (game.pieceAt(row, col).type() == "Pawn") {
							board[row][col].setIcon(iconBPawn);
						} else if (game.pieceAt(row, col).type() == "Bishop") {
							board[row][col].setIcon(iconBBishop);
						} else if (game.pieceAt(row, col).type() == "Knight") {
							board[row][col].setIcon(iconBKnight);
						} else if (game.pieceAt(row, col).type() == "King") {
							board[row][col].setIcon(iconBKing);
						} else if (game.pieceAt(row, col).type() == "Queen") {
							board[row][col].setIcon(iconBQueen);
						} else if (game.pieceAt(row, col).type() == "Rook") {
							board[row][col].setIcon(iconBRook);
						}
					}
				}

			}
		}
	}

	// An ActionListener inner class for buttons
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JComponent comp = (JComponent) event.getSource();
			if (comp == resetItem) {
				game.reset();
				
				String AIQuestion = "";
				
				 while (AIQuestion.length() != 1 || !Pattern.matches("[0,1]", 
			        		AIQuestion)) {
			        	AIQuestion = JOptionPane.showInputDialog(null, 
			        			"Would you like to play an AI game? 0 = Yes, "
			        			+ "1 = No");
			        	if (AIQuestion == null) { // If user presses 'Cancel'
			        		System.exit(1);
			        	} else if (AIQuestion.length() != 1) {
			            	JOptionPane.showMessageDialog(new JPanel(), "Input must be"
									+ " a single digit long.");
			            } else if (!Pattern.matches("[0-1]+", AIQuestion)) {
			            	JOptionPane.showMessageDialog(new JPanel(), "Input must be"
									+ " a positive integer, using only digits"
									+ " 0 or 1");
			            }
			        }
			        
			        if (AIQuestion.equals("0")) {
			        	AIGame = true;
			        } else if (AIQuestion.equalsIgnoreCase("1")) {
			        	AIGame = false;
			        }
				
				displayBoard();
				currentTurnIcon.setIcon(iconWPawn);
			} else if (comp == quitItem) {
				System.exit(1);
			} else {
				// Check button board for which button was pressed
				for(int row = 0; row <= 7; row++)
					for(int col = 0; col <= 7; col++)
						if(board[row][col] == event.getSource()) {
							// If first click, save coordinates, and change color to show selected
							if(board[row][col] != null && click%2 == 0) {
								fromRow = row;
								fromCol = col;
								board[fromRow][fromCol].setBackground(Color.CYAN);

								boolean canFindMove = false;
								// Check all the spots the selected piece could potentially move
								for (int checkCol = 0; checkCol <= 7; checkCol++) {
									for (int checkRow = 0; checkRow <= 7; checkRow++) {
										Move canMove = new Move(fromRow, fromCol, checkRow, checkCol);
										// If piece can move there but can be taken, color tile red. Otherwise, color orange.
										if (game.isValidMove(canMove)) {
											canFindMove = true;
											if (game.inDangerAfterMove(game.currentPlayer(), canMove)) {
												board[canMove.toRow][canMove.toColumn].setBackground(Color.RED);
											} else {
												board[checkRow][checkCol].setBackground(Color.ORANGE);
											}
										}
									}
								}
								// If a move cannot be found for the selected piece, let the user know why
								if (!canFindMove) {
									// Set color
									board[fromRow][fromCol].setBackground(null);
									if (game.getState() == GameState.IN_CHECK) {
										JOptionPane.showMessageDialog(new JPanel(), "This piece cannot move anywhere that prevents you from being in check.");
									} else {
										JOptionPane.showMessageDialog(new JPanel(), "This piece cannot move anywhere, you're likely blocked by another piece(s) or it is not your turn.");
									}
									updateBackground();
									// Set click to one again, so player can immediately choose another tile.
									click = 1;
								}
							}
							// If second click
							else if(click%2 == 1) {
								updateBackground();

								toRow = row;
								toCol = col;
								// Make a move to see if the selected piece can move to the selected coordinates
								Move m = new Move(fromRow,fromCol,toRow,toCol);

								// If the piece can move there, move it
								if(game.isValidMove(m) && !game.inCheckAfterMove(m)) {
									game.move(m);
									// If move into check
									if (game.inCheck(game.currentPlayer())) {
										game.undo();
									} else {
										game.nextTurn();
									}
									// TODO: Remove these change icon calls, use displayBoard() instead
//									board[toRow][toCol].setIcon(board[fromRow][fromCol].getIcon());
//									board[fromRow][fromCol].setIcon(iconBlank);
									displayBoard();
									// Set Current Turn icon to the correct color

									// Promotion
									if(game.pieceAt(m.toRow, m.toColumn).type().equals("Pawn") && 
											(m.toRow == 0 || m.toRow == 7)){
										int piece = -1;
										String input = "";
										while (piece < 0 || piece > 3) {
											input = JOptionPane.showInputDialog(null, "PROMOTION!! Which piece" +
													" would you like? Rook = 0, Knight = 1, Bishop = 2, Queen = 3");

											if(Pattern.matches("[0-9]+", input))											
												piece = Integer.parseInt(input);
										}
										game.promote(piece, m);
										displayBoard();
									}

									if (game.currentPlayer() == Player.BLACK) {
										currentTurnIcon.setIcon(iconBPawn);
									} else {
										currentTurnIcon.setIcon(iconWPawn);
									}
									Player checkPlayer = game.currentPlayer();
									if (game.inCheck(checkPlayer)) {
										JOptionPane.showMessageDialog(new JPanel(), "You're in check, buddy!!");
									}
									if (game.inCheckmate(checkPlayer)) {
										JOptionPane.showMessageDialog(new JPanel(), "You're in check, mate!!");
										game.setComplete(true);
									}
									// If the game is complete, let the player know someone has won the game
									if (game.isComplete()) {
										JOptionPane.showMessageDialog(new JPanel(), "Somebody won!");
										firstWon = true;
									}
									
									if (AIGame == true && game.currentPlayer() == Player.BLACK) {
										game.aiTurn();
										displayBoard();
										
										// Promotion
										if(game.pieceAt(m.toRow, m.toColumn).type().equals("Pawn") && 
												(m.toRow == 0 || m.toRow == 7)){
											game.promote(3, m);
											displayBoard();
										}

										if (game.currentPlayer() == Player.BLACK) {
											currentTurnIcon.setIcon(iconBPawn);
										} else {
											currentTurnIcon.setIcon(iconWPawn);
										}
										
										if (game.inCheck(Player.WHITE)) {
											JOptionPane.showMessageDialog(new JPanel(), "You're in check, buddy!! (AI)");
										}
										if (game.inCheckmate(Player.WHITE)) {
											JOptionPane.showMessageDialog(new JPanel(), "You're in check, mate!! (AI)");
											game.setComplete(true);
										}
										// If the game is complete, let the player know someone has won the game
										if (game.isComplete() && firstWon == false) {
											JOptionPane.showMessageDialog(new JPanel(), "The AI won!");
										}
									}
								}
							}
						}
				// If the game is over, disable all the buttons until the game is reset.
				if (game.isComplete()) {
					for(int row2 = 0; row2 <= 7; row2++) {
						for(int col2 = 0; col2 <= 7; col2++) {
							board[row2][col2].setEnabled(false);
						}
					}
				}
				click ++;
			}
		}
	}
}