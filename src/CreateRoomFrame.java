import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JTextField;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

public class CreateRoomFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private Room room;
	private Data data;
	private String title;
	private LobbyPanel lp;
	private Clip buttonClip; //버튼 효과음
	
	public CreateRoomFrame(LobbyPanel lp) {
		this.lp = lp;
		loadAudioBack();
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 362, 351);
		setLocationRelativeTo(null);//창이 가운데 나오게
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel createRoomLabel = new JLabel("방 생성하기");
		createRoomLabel.setFont(new Font("맑은 고딕", Font.BOLD, 35));
		createRoomLabel.setBounds(12, 22, 207, 74);
		contentPane.add(createRoomLabel);
		
		JLabel lblNewLabel = new JLabel("방 제목");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel.setBounds(12, 130, 76, 24);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		textField.setBounds(100, 130, 127, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("모드");
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel_1.setBounds(12, 188, 50, 15);
		contentPane.add(lblNewLabel_1);
		
		JRadioButton player3Radio = new JRadioButton("3인용");
		player3Radio.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		player3Radio.setBounds(100, 186, 68, 23);
		contentPane.add(player3Radio);
		
		JRadioButton player4Radio = new JRadioButton("4인용");
		player4Radio.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		player4Radio.setBounds(186, 186, 68, 23);
		contentPane.add(player4Radio);
		
		ButtonGroup group = new ButtonGroup();
		group.add(player3Radio);
		group.add(player4Radio);
		
		JLabel lblNewLabel_2 = new JLabel("생성하기");
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		lblNewLabel_2.setBounds(37, 249, 107, 33);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				buttonClip.start();
				buttonClip.setFramePosition(0);
				//서버로 Room 데이터 보내고 Frame 종료
				title = textField.getText();
				if(player3Radio.isSelected()) {
					createData("3인전"); //Data 설정
					lp.SendObject(data);
					dispose(); //프레임 종료
				} else if(player4Radio.isSelected()) {
					createData("4인전"); //Data 설정
					lp.SendObject(data);
					dispose(); //프레임 종료
				}
			}
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_2.setFont(new Font("맑은 고딕",Font.BOLD,26));
				lblNewLabel_2.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				lblNewLabel_2.setFont(new Font("맑은 고딕",Font.BOLD,24));
				lblNewLabel_2.setForeground(Color.BLACK);
			}
		});
		JLabel lblNewLabel_2_1 = new JLabel("돌아가기");
		lblNewLabel_2_1.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		lblNewLabel_2_1.setBounds(186, 249, 107, 33);
		contentPane.add(lblNewLabel_2_1);
		lblNewLabel_2_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				buttonClip.start();
				buttonClip.setFramePosition(0);
				dispose(); //프레임 종료
			}
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_2_1.setFont(new Font("맑은 고딕",Font.BOLD,26));
				lblNewLabel_2_1.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				lblNewLabel_2_1.setFont(new Font("맑은 고딕",Font.BOLD,24));
				lblNewLabel_2_1.setForeground(Color.BLACK);
			}
		});
		
		lblNewLabel_2_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//Frame 종료
			}
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_2_1.setFont(new Font("맑은 고딕",Font.BOLD,26));
				lblNewLabel_2_1.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				lblNewLabel_2_1.setFont(new Font("맑은 고딕",Font.BOLD,24));
				lblNewLabel_2_1.setForeground(Color.BLACK);
			}
		});
	}
	
	public void createData(String mode) {
		room = new Room(title, mode, lp.user); //방 번호는 서버에서 부여받는다.
		lp.user.room = room; //사용자 객체의 방 속성 변경
//		room.userVec.add(lp.user); //방 객체의 사용자 목록 속성 변경
		data = new Data(lp.user, "600", "createRoom");
		data.room = room;
	}
	
	public void loadAudioBack() {
		try {
			buttonClip= AudioSystem.getClip();
			File audioFile1 = new File("sound/btn.wav");
			AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(audioFile1);
			buttonClip.open(audioStream1);
		}
		catch (Exception e) {return;}
	}
}
