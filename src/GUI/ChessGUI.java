package GUI;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ChessGUI {

	 

	public static void main(String[] args) {
		JMenuBar menus;
		JMenu fileMenu;
		JMenuItem quitItem;
		JMenuItem resetItem; 
		
		JFrame frame = new JFrame("Chess Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fileMenu = new JMenu("File");
		quitItem = new JMenuItem("quit");
		resetItem = new JMenuItem("reset");

		fileMenu.add(resetItem);
		fileMenu.add(quitItem);
		menus = new JMenuBar();
		frame.setJMenuBar(menus);
		menus.add(fileMenu);

		ChessPanel panel = new ChessPanel(quitItem, resetItem);
		frame.getContentPane().add(panel);

		frame.pack();
		frame.setVisible(true);
	}
}