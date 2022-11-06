import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;

public class IntroPanel extends JPanel {

	private Image background = new ImageIcon(StartFrame.class.getResource("image/back.png")).getImage();
	private ImageIcon loginLogo = new ImageIcon(StartFrame.class.getResource("image/loginLogo.png"));
	private final JTextField textField = new JTextField();

	public IntroPanel() {
		setBounds(0, 0, 863, 572);
		setLayout(null);
		
//		JLabel logo = new JLabel(loginLogo);
//		logo.setBounds(12, 212, 89, 89);
//		add(logo);
		
		
		textField.setBounds(260, 397, 283, 36);
		add(textField);
		textField.setColumns(10);
		
		JLabel nickname = new JLabel("닉네임");
		nickname.setFont(new Font("맑은 고딕", Font.BOLD, 33));
		nickname.setBounds(135, 390, 101, 54);
		add(nickname);
		
		JLabel gameStart = new JLabel("입장하기");
		gameStart.setFont(new Font("맑은 고딕", Font.BOLD, 33));
		gameStart.setBounds(594, 390, 156, 54);
		add(gameStart);
		//입장하기 눌렀을 때
		gameStart.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				System.out.println("asd");
				new LobbyFrame();	//게임을 다시 시작하기위한 구성이 들어있는 함수
			}
			public void mouseEntered(MouseEvent e) {
				gameStart.setFont(new Font("맑은 고딕",Font.BOLD,33));
				gameStart.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				gameStart.setFont(new Font("맑은 고딕",Font.BOLD,30));
				gameStart.setForeground(Color.BLACK);
			}
		});
		
	}

	public void paintComponent(Graphics g) { //그리는 함수
		super.paintComponents(g);
		g.drawImage(background, 0, 0, getWidth(), getHeight() ,this);//background를 그려줌
	}
}
