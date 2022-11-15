import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GameFrame extends JFrame{
	private JPanel contentPane;
	private LobbyPanel lp;
	
	public GameFrame(LobbyPanel lp) {
		this.lp = lp;
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 863, 572);
		setLocationRelativeTo(null);
		
		contentPane = new GamePanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				GameFrame gframe = new GameFrame(new LobbyPanel("","",""));
//				gframe.setVisible(true);					
//			}
//		});
//	}

}
