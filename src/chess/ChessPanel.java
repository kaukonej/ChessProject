package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ChessPanel extends JPanel {

	private JButton[][] board;   
	private ChessModel model;

	// declare other instance variables as needed

	private ButtonListener buttonListener = new ButtonListener();

	public ChessPanel() {
		// complete this
	}

	// method that updates the board
	private void displayBoard() {
		// complete this
	}

	// add other helper methods as needed

	// inner class that represents action listener for buttons
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			// complete this
		}
	}
}

