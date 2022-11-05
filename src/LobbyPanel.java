import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import java.awt.Font;

public class LobbyPanel extends JPanel {

	public LobbyPanel() {
		setBounds(100, 100, 800, 572);
		setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 800, 40);
		add(menuBar);
		
		JLabel title = new JLabel("     CatchMind");
		title.setFont(new Font("굴림", Font.BOLD, 14));
		menuBar.add(title);
	}
}
