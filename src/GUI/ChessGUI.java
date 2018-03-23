package GUI;
import javax.swing.JFrame;

public class ChessGUI {

	 public static void main(String[] args) {
	  JFrame frame = new JFrame("Chess Game");
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	  ChessPanel panel = new ChessPanel();
	  //frame.getContentPane().add(panel);
	  frame.setContentPane(panel);

	  frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	  frame.setUndecorated(true);
	  
	  frame.pack();
	  frame.setVisible(true);
	 }
	}
