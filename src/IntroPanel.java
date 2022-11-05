import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

public class IntroPanel extends JPanel {

	private Image background = new ImageIcon(StartFrame.class.getResource("image/catchmind.png")).getImage();
	private ImageIcon loginLogo = new ImageIcon(StartFrame.class.getResource("image/loginLogo.png"));
	private final JTextField textField = new JTextField();
	
	public IntroPanel() {
		setBounds(0, 0, 800, 572);
		setLayout(null);
		
		JLabel logo = new JLabel(loginLogo);
		logo.setBounds(12, 212, 89, 89);
//		add(logo);
		
		
		textField.setBounds(260, 397, 283, 36);
		add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("입장하기");
		btnNewButton.setBounds(567, 403, 91, 23);
		add(btnNewButton);
		
		JLabel nickname = new JLabel("닉네임");
		nickname.setFont(new Font("굴림", Font.BOLD, 30));
		nickname.setBounds(135, 390, 101, 54);
		add(nickname);
	}

	public void paintComponent(Graphics g) { //그리는 함수
		super.paintComponents(g);
		g.drawImage(background, 0, 0, getWidth(), getHeight() ,this);//background를 그려줌
	}
}
