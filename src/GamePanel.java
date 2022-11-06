

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GamePanel extends JPanel{
	
	private Image gamebackground = new ImageIcon("src/image/backimage.png").getImage();
	private ImageIcon userImg = new ImageIcon("src/image/userimage.png");
	private ImageIcon userImg2 = new ImageIcon("src/image/userimage2.png");
	private ImageIcon canvasImg = new ImageIcon("src/image/canvas.png");
	private ImageIcon titleImg = new ImageIcon("src/image/title.png");
	private ImageIcon wordImg = new ImageIcon("src/image/showword.png");
	
	private JPanel contentPane;
	private JPanel colorBar;
	private JTextField textField;
	private JTextField username;
	private JTextField username2;
	private JTextField username3;
	private JTextField username4;
	private JTextField showWord;
	
	//색칠
	public JButton btnBlack, btnRed, btnBlue, btnGreen, btnYellow, btnEraser, btnClear;	
	
	private ImageIcon black = new ImageIcon("src/image/black.png");
	private ImageIcon red = new ImageIcon("src/image/red.png");
	private ImageIcon blue = new ImageIcon("src/image/blue.png");
	private ImageIcon green= new ImageIcon("src/image/green.png");
	private ImageIcon yellow = new ImageIcon("src/image/yellow.png");
	private ImageIcon eraser = new ImageIcon("src/image/eraseall.png");
	//private ImageIcon allclear = new ImageIcon("src/image/clearButton.png");
	
	
	public GamePanel() {
		setLayout(null);
		setBounds(100, 100, 863, 572);
		
		//메뉴바 시작
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 863, 40);
		add(menuBar);
				
		JLabel title = new JLabel("CatchCatchMind");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		menuBar.add(title);
		//메뉴바 끝
		
		//------그림 화면
		contentPane = new JPanel();
		contentPane.setBounds(200, 120, 450, 300);
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(null);
		add(contentPane);
		
		JLabel canvasLabel = new JLabel(canvasImg);
		canvasLabel.setBounds(200, 100, 450, 30);
		add(canvasLabel);
		
		//------그림 화면 끝
		
		//------제시어 화면
		JLabel wordLabel = new JLabel("제시어");
		wordLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		wordLabel.setBounds(480, 60, 400, 40);
		add(wordLabel);
		
		showWord = new JTextField();
		showWord.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		showWord.setText("딸기맛 사탕");
		showWord.setBounds(535, 66, 100, 25);
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
		JLabel user1Label = new JLabel(userImg);
		user1Label.setBounds(20, 40, 50, 50);
		add(user1Label);
		username = new JTextField();
		username.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		username.setText("user1");
		username.setBounds(20, 60, 100, 20);
		username.setEnabled(false);
		username.setColumns(10);
		add(username);
		
		JLabel score1Label = new JLabel("SCORE");
		score1Label.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
		score1Label.setBounds(10,103,80,20);
		add(score1Label);
		JLabel score1Label_score = new JLabel("50");
		score1Label_score.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
		score1Label_score.setBounds(90, 125, 80, 20);
		add(score1Label_score);
		
		JLabel user3Label = new JLabel(userImg);
		user3Label.setBounds(670, 70, 50, 60);
		add(user3Label);
		username3 = new JTextField();
		username3.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		username3.setText("user3");
		username3.setBounds(720, 70, 80, 20);
		username3.setEnabled(false);
		username3.setColumns(10);
		add(username3);
		
		JLabel score3Label = new JLabel("SCORE");
		score3Label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score3Label.setBounds(725,100,70,20);
		add(score3Label);
		JLabel score3Label_score = new JLabel("50");
		score3Label_score.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score3Label_score.setBounds(775, 100, 70, 20);
		add(score3Label_score);
		
		JLabel user4Label = new JLabel(userImg2);
		user4Label.setBounds(670, 180, 50, 60);
		add(user4Label);
		username4 = new JTextField();
		username4.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		username4.setText("user4");
		username4.setBounds(720, 180, 80, 20);
		username4.setEnabled(false);
		username4.setColumns(10);
		add(username4);
		
		JLabel score4Label = new JLabel("SCORE");
		score4Label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score4Label.setBounds(725,210,70,20);
		add(score4Label);
		JLabel score4Label_score = new JLabel("50");
		score4Label_score.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score4Label_score.setBounds(775, 210, 70, 20);
		add(score4Label_score);	
		//------사용자 끝
		
		//------색 버튼
		/*
		colorBar = new JPanel();
		colorBar.setBorder(null);
		colorBar.setBounds(200,430,450,100);
		colorBar.setOpaque(false);
		add(colorBar);		
		*/
		btnBlack = new JButton(black);
		btnRed = new JButton(red);
		btnBlue = new JButton(blue);
		btnGreen = new JButton(green);
		btnYellow = new JButton(yellow);
		btnEraser = new JButton(eraser);
		btnClear = new JButton("allclear");
		
		
		btnBlack.setBorderPainted(false); btnRed.setBorderPainted(false); 
		btnBlue.setBorderPainted(false); btnGreen.setBorderPainted(false); 
		btnYellow.setBorderPainted(false); btnEraser.setBorderPainted(false); 
		btnBlack.setContentAreaFilled(false);btnRed.setContentAreaFilled(false);
		btnBlue.setContentAreaFilled(false);btnGreen.setContentAreaFilled(false);
		btnYellow.setContentAreaFilled(false);btnEraser.setContentAreaFilled(false);
		btnBlack.setFocusPainted(false);btnRed.setFocusPainted(false);
		btnBlue.setFocusPainted(false);btnGreen.setFocusPainted(false);
		btnYellow.setFocusPainted(false);btnEraser.setFocusPainted(false);
			
		btnBlack.setBounds(250, 430, 42, 40);
		btnRed.setBounds(300, 430, 42, 40);
		btnBlue.setBounds(350, 430, 42, 40);
		btnGreen.setBounds(400, 430, 42, 40);
		btnYellow.setBounds(450, 430, 42, 40);
		btnEraser.setBounds(500, 430, 42, 40);
		add(btnBlack); add(btnRed); add(btnBlue); add(btnGreen); add(btnYellow);add(btnEraser);
		/*
		colorBar.add(btnBlack);
		colorBar.add(btnRed);
		colorBar.add(btnBlue);
		colorBar.add(btnGreen);
		colorBar.add(btnYellow);
		colorBar.add(btnEraser);
		colorBar.add(btnClear);
		*/
		//------색 버튼 끝
	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(gamebackground, 0, 0, getWidth(), getHeight(), this);
		
	}
}
