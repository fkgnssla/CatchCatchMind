

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel{
	
	private Image gamebackground = new ImageIcon("src/image/backimg.jpg").getImage();
	private ImageIcon userImg = new ImageIcon("src/image/userimage.png");
	private ImageIcon userImg2 = new ImageIcon("src/image/userimage2.png");
	private ImageIcon canvasImg = new ImageIcon("src/image/canvas.png");
	private ImageIcon titleImg = new ImageIcon("src/image/title.png");
	private ImageIcon wordImg = new ImageIcon("src/image/showword.png");
	
	private JPanel contentPane;
	private JPanel colorBar;
	private JPanel userPanel;
	private JTextField textField;
	private JTextField username;
	private JTextField username2;
	private JTextField username3;
	private JTextField username4;
	private JTextField showWord;
	
	//버튼
	public JButton btnBlack, btnRed, btnBlue, btnGreen, btnYellow, btnEraser, btnClear, btnStart, btnClose;	
	
	private ImageIcon black = new ImageIcon("src/image/black.png");
	private ImageIcon red = new ImageIcon("src/image/red.png");
	private ImageIcon blue = new ImageIcon("src/image/blue.png");
	private ImageIcon green= new ImageIcon("src/image/green.png");
	private ImageIcon yellow = new ImageIcon("src/image/yellow.png");
	private ImageIcon eraser = new ImageIcon("src/image/eraseall.png");
	private ImageIcon allclear = new ImageIcon("src/image/clearButton.png");
	private ImageIcon startImg = new ImageIcon("src/image/startbtn.png");
	private ImageIcon closeImg = new ImageIcon("src/image/exit.png");
	
	//음향
	private Clip clip1; //배경음악
	
	public GamePanel() {
		setLayout(null);
		setBounds(100, 100, 863, 572);
		
		//음향 시작
//		loadAudioBack();
//		clip1.start(); //배경음악 시작
		//음향 종료
		
//		//메뉴바 시작
//		JMenuBar menuBar = new JMenuBar();
//		menuBar.setBounds(0, 0, 863, 40);
//		add(menuBar);
//				
//		JLabel title = new JLabel("CatchCatchMind");
//		title.setFont(new Font("맑은 고딕", Font.BOLD, 16));
//		menuBar.add(title);
		//메뉴바 끝
		
		//------그림 화면
		contentPane = new JPanel();
		contentPane.setBounds(210, 136, 428, 314);
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(null);
		add(contentPane);
		
		JLabel canvasLabel = new JLabel(canvasImg);
		canvasLabel.setBounds(200, 100, 450, 30);
		//add(canvasLabel);
		
		//------그림 화면 끝
		
		//------제시어 화면
		JLabel wordLabel = new JLabel("제시어");
		wordLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		wordLabel.setBounds(475, 72, 100, 40);
		add(wordLabel);
		
		showWord = new JTextField();
		showWord.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		showWord.setText("딸기맛 사탕");
		showWord.setBounds(530, 81, 100, 25);
		showWord.setEnabled(false);
		showWord.setColumns(10);
		add(showWord);
		//------제시어 끝
		
		//------게임 채팅 시작
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds( 670, 320 ,160, 160);
		add(scrollPane);
		
		JTextArea gameTextArea = new JTextArea();
		scrollPane.setViewportView(gameTextArea);
		
		JLabel chatLabel = new JLabel("대화창");
		scrollPane.setColumnHeaderView(chatLabel);
		
		textField = new JTextField();
		textField.setBounds(670,480,130,30);
		add(textField);
		textField.setColumns(10);
		JButton btnInput = new JButton("입력");
		btnInput.setBounds(800, 480, 30, 30);
		add(btnInput);
		//------게임 채팅 끝
		
		//------사용자
		//user1
		JLabel user1Label = new JLabel(userImg);
		user1Label.setBounds(106, 112, 68, 110);
		add(user1Label);
		username = new JTextField();
		username.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		username.setText("user1");
		username.setBounds(37, 129, 68, 22);
		username.setEnabled(false);
		username.setColumns(10);
		add(username);
		
		JLabel score1Label = new JLabel("SCORE");
		score1Label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score1Label.setBounds(42,186,65,17);
		add(score1Label);
		JLabel score1Label_score = new JLabel("50");
		score1Label_score.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score1Label_score.setBounds(40, 160, 65, 17);
		add(score1Label_score);
		
		//user2
		JLabel user2Label = new JLabel(userImg);
		user2Label.setBounds(105, 214, 68, 110);
		add(user2Label);
		
		username = new JTextField();
		username.setText("user2");
		username.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		username.setBounds(36, 231, 68, 22);
		username.setEnabled(false);
		username.setColumns(10);
		JLabel score2Label = new JLabel("SCORE");
		score2Label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score2Label.setBounds(42,288,65,17);
		JLabel score2Label_score = new JLabel("70");
		score2Label_score.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score2Label_score.setBounds(40, 260, 65, 17);
		add(username);	
		add(score2Label);
		add(score2Label_score);
		
		//user3
		JLabel user3Label = new JLabel(userImg);
		user3Label.setBounds(746, 108, 68, 110);
		add(user3Label);
		
		username3 = new JTextField();
		username3.setText("user3");
		username3.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		username3.setBounds(676, 125, 68, 22);
		username3.setEnabled(false);
		username3.setColumns(10);
		JLabel score3Label = new JLabel("SCORE");
		score3Label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score3Label.setBounds(679, 180, 65, 17);	
		JLabel score3Label_score = new JLabel("50");
		score3Label_score.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score3Label_score.setBounds(679, 155, 65, 17);
		add(username3);
		add(score3Label);
		add(score3Label_score);
		
		//user4
		JLabel user4Label = new JLabel(userImg2);
		user4Label.setBounds(744, 210, 68, 110);
		add(user4Label);
		
		username4 = new JTextField();
		username4.setText("user4");
		username4.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		username4.setBounds(674, 228, 68, 22);
		username4.setEnabled(false);
		username4.setColumns(10);
		JLabel score4Label = new JLabel("SCORE");
		score4Label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score4Label.setBounds(679, 282, 65, 17);	
		JLabel score4Label_score = new JLabel("50");
		score4Label_score.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score4Label_score.setBounds(679, 255, 65, 17);
		add(username4);
		add(score4Label);
		add(score4Label_score);	
		//------사용자 끝
		
		//------색 버튼
		btnBlack = new JButton(black);
		btnRed = new JButton(red);
		btnBlue = new JButton(blue);
		btnGreen = new JButton(green);
		btnYellow = new JButton(yellow);
		btnEraser = new JButton(eraser);
		btnClear = new JButton(allclear);
		
		btnBlack.setPressedIcon(new ImageIcon("src/image/black_pressed.png"));
		btnRed.setPressedIcon(new ImageIcon("src/image/red_pressed.png"));
		btnBlue.setPressedIcon(new ImageIcon("src/image/blue_pressed.png"));
		btnGreen.setPressedIcon(new ImageIcon("src/image/green_pressed.png"));
		btnYellow.setPressedIcon(new ImageIcon("src/image/yellow_pressed.png"));
		//btnEraser.setPressedIcon(new ImageIcon("src/image/black_pressed.png"));
		//btnClear.setPressedIcon(new ImageIcon("src/image/black_pressed.png"));
		
		btnBlack.setBorderPainted(false); btnRed.setBorderPainted(false); btnClear.setBorderPainted(false); 
		btnBlue.setBorderPainted(false); btnGreen.setBorderPainted(false); 
		btnYellow.setBorderPainted(false); btnEraser.setBorderPainted(false); 
		btnBlack.setContentAreaFilled(false);btnRed.setContentAreaFilled(false); btnClear.setContentAreaFilled(false);
		btnBlue.setContentAreaFilled(false);btnGreen.setContentAreaFilled(false);
		btnYellow.setContentAreaFilled(false);btnEraser.setContentAreaFilled(false);
		btnBlack.setFocusPainted(false);btnRed.setFocusPainted(false); btnClear.setFocusPainted(false);
		btnBlue.setFocusPainted(false);btnGreen.setFocusPainted(false);
		btnYellow.setFocusPainted(false);btnEraser.setFocusPainted(false);
			
		btnBlack.setBounds(230, 460, 50, 50);
		btnRed.setBounds(280, 460, 50, 50);
		btnBlue.setBounds(330, 460, 50, 50);
		btnGreen.setBounds(380, 460, 50, 50);
		btnYellow.setBounds(430, 460, 50, 50);
		btnEraser.setBounds(480, 460, 50, 50);
		btnClear.setBounds(530, 460, 100, 50);
		add(btnBlack); add(btnRed); add(btnBlue); add(btnGreen); add(btnYellow);add(btnEraser);add(btnClear);
		
		btnBlack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		//------색 버튼 끝

		//------게임 시작 버튼
		btnStart = new JButton(startImg);
		btnStart.setBorderPainted(false); btnStart.setContentAreaFilled(false);btnStart.setFocusPainted(false);
		btnStart.setBounds(660, 14, 60, 60);
		add(btnStart);
		
		//------게임 나가기 버튼
		btnClose = new JButton(closeImg);
		btnClose.setBorderPainted(false); btnClose.setContentAreaFilled(false);btnClose.setFocusPainted(false);
		btnClose.setBounds(730, 13, 60, 60);
		add(btnClose);
		
		JButton btnHint = new JButton("HINT");
		btnHint.setBounds(79, 390, 75, 30);
		add(btnHint);
		
		JButton btnPic = new JButton("밑그림");
		btnPic.setBounds(79, 430, 75, 30);
		add(btnPic);
		//------게임 시작 나가기 버튼 끝
	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(gamebackground, 0, 0, getWidth(), getHeight(), this);
		
	}
	
	public void loadAudioBack() {
		try {
			clip1= AudioSystem.getClip();
			File audioFile = new File("sound/gameRoom.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip1.open(audioStream);
		}
		catch (Exception e) {return;}
	}
	
}
