import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class LobbyFrame extends JFrame {

	private JPanel contentPane;

	public LobbyFrame(String username, String ip_addr, String port_no) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 863, 572);
		setLocationRelativeTo(null);//창이 가운데 나오게
		contentPane = new LobbyPanel(username, ip_addr, port_no);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
}
