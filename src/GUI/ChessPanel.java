package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import chess.ChessModel;
import chess.Move;
import chess.Player;

public class ChessPanel extends JPanel {

	private JButton[][] board;
	private ChessModel model;

	private JMenuItem newGameItem;
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

	int fromRow;
	int fromCol;
	int toRow;
	int toCol;
	int click;

	// declare other instance variables as needed

	private ButtonListener buttonListener = new ButtonListener();

	public ChessPanel() {
		game = new ChessModel();

		iconBlank = new ImageIcon("blank.jpg"); // get a blank image later
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
				board[row][col] = new JButton("(" + col + ", " + row + ")");
				board[row][col].addActionListener(new ButtonListener());
				// Add each element to the GridBagLayout
				loc = new GridBagConstraints();
				loc.gridx = col;
				loc.gridy = row;
				loc.insets.bottom = 10;
				loc.insets.top = 10;
				add(board[row][col], loc);				
			}
		}

		currentTurnLabel = new JLabel("Next:");
		loc.gridx = 0;
		loc.gridy = 9;
		loc.insets.bottom = 15;
		loc.insets.top = 15;
		add(currentTurnLabel, loc);

		currentTurnIcon = new JLabel("",iconWPawn,SwingConstants.CENTER);
		loc.gridx = 1;
		loc.gridy = 9;
		loc.insets.bottom = 15;
		loc.insets.top = 15;
		add(currentTurnIcon, loc);
		//		
		displayBoard();
		revalidate();
		repaint();
	}


	// method that updates the board
	private void displayBoard() {
		// complete this
		// Should update board with correct pieces
		for (int row = 0; row <= 7; row++) {
			for (int col = 0; col <= 7; col++) {
				if (game.pieceAt(row, col) == null) {
					board[row][col].setIcon(iconBlank); // TODO set black or white depending on row and column.
				}
				else if (game.pieceAt(row, col).player() == Player.WHITE) {
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

	// add other helper methods as needed

	// inner class that represents action listener for buttons
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			for(int row = 0; row <= 7; row++)
				for(int col = 0; col <= 7; col++)
					if(board[row][col] == event.getSource()) {
						if(board[row][col] != null && click%2 == 0) {
							fromRow = row;
							fromCol = col;
						}
						else if(click%2 == 1) {
							toRow = row;
							toCol = col;
							Move m = new Move(fromRow,fromCol,toRow,toCol);

							if(game.isValidMove(m)) {
								game.move(m);
								board[toRow][toCol].setIcon(board[fromRow][fromCol].getIcon());
								board[fromRow][fromCol].setIcon(null);
								//displayBoard();
								revalidate();
								repaint();
							}

							//								if(game.inCheck(game.currentPlayer().next()))
							//									JOptionPane.showMessageDialog(null,"You are about to be bopped");
						}
					}
			click ++;



			// complete this
			// If button is pressed, get x and y if and only if there is something at that coordinate.
			// when next button is pressed, get x and y if and only if there is something at that coordinate, and if there is check if it's a valid move (for that specific piece)
			// if it is a valid move, do the move, and update the board
			// if a player is in check or checkmate, show message, take appropriate action
			// if move happens, swap turn and change label

		}
	}
}

