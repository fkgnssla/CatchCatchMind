import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GameFrame extends JFrame{
	private GamePanel gp;
	private LobbyPanel lp;
	
	public GameFrame(LobbyPanel lp, User user) {
		this.lp = lp;
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 863, 572);
		setLocationRelativeTo(null);
		
		gp = new GamePanel(lp, user);
		gp.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(gp);
		gp.setLayout(null);
		gp.init(); //그래픽 요소 초기화
	}
	
	public void DoEvent(Data data) {
		if(data.code.matches("500") || data.code.matches("501") || data.code.matches("502")) {
			gp.DoMouseEvent(data);
			return;
		}
		gp.DoGameEvent(data);
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
