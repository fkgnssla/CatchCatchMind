import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;

public class LobbyPanel extends JPanel {
	
	private JTextField textField;
	private JTextField nickname;
	private ImageIcon userImage = new ImageIcon(LobbyPanel.class.getResource("image/onion.png"));
	private Image background = new ImageIcon(LobbyPanel.class.getResource("image/back.png")).getImage();
	private JTable roomTable; //방 목록 테이블
	private JTable onlineUserTable; //온라인 사용자 테이블
	
	//음향
	private Clip clip1; //배경음악
	
	
	public LobbyPanel() {
		setBounds(100, 100, 863, 572);
		setLayout(null);
		
		//음향 시작
		loadAudioBack();
		clip1.start(); //배경음악 시작
		//음향 종료
		
		//메뉴바 시작
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 863, 40);
		add(menuBar);
		
		JLabel title = new JLabel(" CatchMind");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		menuBar.add(title);
		//메뉴바 끝
		
		//방 목록 시작
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 537, 343);
		add(scrollPane);
		
		roomTable = new JTable();
		scrollPane.setViewportView(roomTable);
		roomTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"방 번호", "모드", "방 제목", "방장", "현재 인원", "상태"
			}
		));
		
		DefaultTableModel model = (DefaultTableModel)roomTable.getModel();
		String test1[]= {"1","개인전","고수만","방구석피카소","1/6","대기중"};
		model.addRow(test1); //방 생성
		//방 목록 끝
		
		//전체채팅 시작
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 403, 537, 97);
		add(scrollPane_1);
		
		JTextArea textArea = new JTextArea(); //채팅창
		scrollPane_1.setViewportView(textArea);
		
		JLabel chatLabel = new JLabel("대화창 --- Chatting Room");
		scrollPane_1.setColumnHeaderView(chatLabel);
		
		textField = new JTextField();
		textField.setBounds(10, 505, 453, 21);
		add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("입력");
		btnNewButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		btnNewButton.setBounds(473, 505, 74, 23);
		add(btnNewButton);
		//전체채팅 끝
		
		//내 정보 시작
		JLabel lblNewLabel = new JLabel(userImage);
		lblNewLabel.setBounds(559, 50, 123, 130);
		add(lblNewLabel);
		
		nickname = new JTextField();
		nickname.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		nickname.setBounds(559, 190, 123, 21);
		nickname.setText("우아우우웅웅");
		nickname.setEnabled(false);
		add(nickname);
		nickname.setColumns(10);
		
		JLabel goldLabel = new JLabel("Gold"); //나중에 이미지로 수정
		goldLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		goldLabel.setBounds(684, 63, 50, 15); 
		add(goldLabel);
		
		JLabel winLabel = new JLabel("이긴 횟수"); 
		winLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		winLabel.setBounds(684, 99, 61, 15);
		add(winLabel);
		
		JLabel expLabel = new JLabel("경험치");
		expLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		expLabel.setBounds(684, 136, 50, 15);
		add(expLabel);
		
		JLabel lblNewLabel_1 = new JLabel("256");
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(750, 63, 50, 15);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("3");
		lblNewLabel_1_1.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(750, 99, 50, 15);
		add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("3");
		lblNewLabel_1_1_1.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		lblNewLabel_1_1_1.setBounds(750, 136, 50, 15);
		add(lblNewLabel_1_1_1);
		
		JButton consonantBuyButton = new JButton("초성 아이템 구매"); //초성 구매 버튼
		consonantBuyButton.setBounds(694, 169, 152, 23);
		add(consonantBuyButton);
		
		JButton passBuyButton = new JButton("건너뛰기 아이템 구매");
		passBuyButton.setBounds(694, 202, 152, 23);
		add(passBuyButton);
		//내 정보 끝
		
		//온라인 사용자
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(559, 235, 281, 266);
		add(scrollPane_2);
		
		JLabel onlineUserLabel = new JLabel("온라인 사용자");
		onlineUserLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		scrollPane_2.setColumnHeaderView(onlineUserLabel);
		
		onlineUserTable = new JTable();
		onlineUserTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"닉네임", "이긴 횟수", "경험치"
				}
			));
		scrollPane_2.setViewportView(onlineUserTable);
		
		//온라인 사용자
	}
	
	public void paintComponent(Graphics g) { //그리는 함수
		super.paintComponents(g);
		g.drawImage(background, 0, 0, getWidth(), getHeight() ,this);//background를 그려줌
	}
	
	//입장하기 클릭 시 효과음
	public void loadAudioBack() {
		try {
			clip1= AudioSystem.getClip();
			File audioFile = new File("sound/lobbyRoom.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip1.open(audioStream);
		}
		catch (Exception e) {return;}
	}
}