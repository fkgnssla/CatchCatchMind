

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.ImageView;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel{
	
	private Image gamebackground = new ImageIcon("src/image/backimg.jpg").getImage();
	private ImageIcon userImg = new ImageIcon("src/image/userimage.png");
	private ImageIcon userImg2 = new ImageIcon("src/image/userimage2.png");
	private ImageIcon canvasImg = new ImageIcon("src/image/canvas.png");
	private ImageIcon titleImg = new ImageIcon("src/image/title.png");
	private ImageIcon wordImg = new ImageIcon("src/image/showword.png");
	
	private JPanel drawPanel;
	private JPanel colorBar;
	private JPanel userPanel;
	private JTextField textField;
	private JLabel username1;
	private JLabel username2;
	private JLabel username3;
	private JLabel username4;
	private JTextField showWord;
	private JTextPane textArea;
	private JButton btnInput;
	
	private JLabel score1Label_score;
	private JLabel score2Label_score;
	private JLabel score3Label_score;
	private JLabel score4Label_score;
	private JLabel presenterLabel;
	private JLabel roundLabel;
	private JLabel timerLabel;
	
	//버튼
	public JButton btnBlack, btnRed, btnBlue, btnGreen, btnYellow, btnEraser, btnClear, btnStart, btnClose;	
	public JButton btnPic, btnHint;
	
	private ImageIcon black = new ImageIcon("src/image/black.png");
	private ImageIcon red = new ImageIcon("src/image/red.png");
	private ImageIcon blue = new ImageIcon("src/image/blue.png");
	private ImageIcon green= new ImageIcon("src/image/green.png");
	private ImageIcon yellow = new ImageIcon("src/image/yellow.png");
	private ImageIcon eraser = new ImageIcon("src/image/eraseall.png");
	private ImageIcon allclear = new ImageIcon("src/image/clearButton.png");
	private ImageIcon startImg = new ImageIcon("src/image/startbtn.png");
	private ImageIcon closeImg = new ImageIcon("src/image/exit.png");

	private ImageIcon Imgrainbow = new ImageIcon("src/image/rainbow.jpg");
	private ImageIcon Imgchristmas = new ImageIcon("src/image/christmas.jpg");
	private ImageIcon Imgiphone = new ImageIcon("src/image/iphone.jpg");
	private ImageIcon Imgpenguine= new ImageIcon("src/image/penguine.jpg");
	private ImageIcon Imgcat = new ImageIcon("src/image/cat.jpg");
	private ImageIcon Imgangrybird = new ImageIcon("src/image/angrybird.jpg");
	private ImageIcon Imgkorea = new ImageIcon("src/image/korea.jpg");
	private ImageIcon Imgstrawberry = new ImageIcon("src/image/strawberry.jpg");
	private ImageIcon Imgnotebook = new ImageIcon("src/image/notebook.jpg");
	private ImageIcon Imgsea = new ImageIcon("src/image/sea.jpg");
	
	//음향
	private Clip clip1; //배경음악
	private Clip correctClip; //제시어 맞춘 경우
	private Clip incorrectClip; //제시어 못 맞춘 경우
	private Clip colorClip; //색 바꾸는 경우
	private Clip buttonClip; //버튼 효과음
	private Clip timeOutClip; //시간초과 효과음
	
	//Draw용 속성 시작=====================================
	int startX = 0; 
	int startY = 0; 
	int endX = 0; 
	int endY = 0;
	    
	private Graphics gc;
	private Graphics2D g2d;
	private int pen_size = 2; // minimum 2
		
	// 그려진 Image를 보관하는 용도, paint() 함수에서 이용한다.
	private Image panelImage; 
	//Draw용 속성 끝======================================
	
	private String word = ""; //제시어
	private String initw; //초성
	private String firstw; //첫글자
	String presenter = ""; //출제자 이름
	
	private GameFrame gf;
	private LobbyPanel lp;
	private User user; //현재 사용자
	private Room room; //현재 방
	
	// Mouse Event 수신 처리
	boolean flag = false;
	MyMouseEvent mouse = new MyMouseEvent();
	
	//시간 관련
	int count = 30;
	private Timer timer;
	private TimerTask task;
	
	public GamePanel(GameFrame gf, LobbyPanel lp, User user) {
		this.gf = gf;
		this.lp = lp;
		this.user = user;
		this.room = user.room;
		System.out.print(user.room + " dndndndndn\n");
		setLayout(null);
		setBounds(0, 0, 863, 572);
		
		//음향 시작
		loadAudioBack();
		if(user.loca==1) { //무조건 하나의 사용자에서만 배경음악이 나오도록
//			clip1.start(); //배경음악 시작
			clip1.loop(Clip.LOOP_CONTINUOUSLY);
		}
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
		drawPanel = new JPanel();
		drawPanel.setBounds(210, 136, 428, 314);
		drawPanel.setForeground(new Color(0, 0, 0));
		drawPanel.setBackground(Color.WHITE);
		drawPanel.setLayout(null);
		add(drawPanel);
		
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
		showWord.setBounds(530, 81, 100, 25);
		showWord.setEnabled(false);
		showWord.setColumns(10);
		add(showWord);
		//------제시어 끝
		
		//------게임 채팅 시작
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds( 670, 320 ,160, 160);
		add(scrollPane);
		
		textArea = new JTextPane(); 
		scrollPane.setViewportView(textArea);

		JLabel chatLabel = new JLabel("대화창");
		scrollPane.setColumnHeaderView(chatLabel);
		
		textField = new JTextField();
		textField.setBounds(670,480,112,30);
		add(textField);
		textField.setColumns(10);
		btnInput = new JButton("입력");
		btnInput.setFont(new Font("굴림", Font.PLAIN, 8));
		btnInput.setBounds(780, 480, 50, 30);
		add(btnInput);

		TextSendAction action = new TextSendAction();
		btnInput.addActionListener(action);
		textField.addActionListener(action);
		textField.requestFocus();
		//------게임 채팅 끝
		
		//------사용자
		//user1
		JLabel user1Label = new JLabel(userImg2);
		user1Label.setBounds(106, 112, 68, 110);
		add(user1Label);
		username1 = new JLabel();
		username1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		username1.setText("빈 자리");
		username1.setBounds(37, 129, 68, 22);
//		username1.setEnabled(false);
//		username1.setColumns(10);
		add(username1);
		
		JLabel score1Label = new JLabel("SCORE");
		score1Label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score1Label.setBounds(42,186,65,17);
		add(score1Label);
		score1Label_score = new JLabel("0");
		score1Label_score.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score1Label_score.setBounds(40, 160, 65, 17);
		add(score1Label_score);
		
		//user2
		JLabel user3Label = new JLabel(userImg);
		user3Label.setBounds(105, 214, 68, 110);
		add(user3Label);
		
		username3 = new JLabel();
		username3.setText("빈 자리");
		username3.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		username3.setBounds(36, 231, 68, 22);
//		username3.setEnabled(false);
//		username3.setColumns(10);
		JLabel score3Label = new JLabel("SCORE");
		score3Label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score3Label.setBounds(42,288,65,17);
		score3Label_score = new JLabel("0");
		score3Label_score.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score3Label_score.setBounds(40, 260, 65, 17);
		add(username3);	
		add(score3Label);
		add(score3Label_score);
		
		//user3
		JLabel user2Label = new JLabel(userImg);
		user2Label.setBounds(746, 108, 68, 110);
		add(user2Label);
		
		username2 = new JLabel();
		username2.setText("빈 자리");
		username2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		username2.setBounds(676, 125, 68, 22);
//		username2.setEnabled(false);
//		username2.setColumns(10);
		JLabel score2Label = new JLabel("SCORE");
		score2Label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score2Label.setBounds(679, 180, 65, 17);	
		score2Label_score = new JLabel("0");
		score2Label_score.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score2Label_score.setBounds(679, 155, 65, 17);
		add(username2);
		add(score2Label);
		add(score2Label_score);
		
		//user4
		JLabel user4Label = new JLabel(userImg2);
		user4Label.setBounds(744, 210, 68, 110);
		add(user4Label);
		
		username4 = new JLabel();
		username4.setText("빈 자리");
		username4.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		username4.setBounds(674, 228, 68, 22);
//		username4.setEnabled(false);
//		username4.setColumns(10);
		JLabel score4Label = new JLabel("SCORE");
		score4Label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		score4Label.setBounds(679, 282, 65, 17);	
		score4Label_score = new JLabel("0");
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
		btnStart.setVisible(false); //처음엔 게임시작 버튼 안 보이게
		btnStart.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				buttonClip.start();
				buttonClip.setFramePosition(0);
				
				Data data = new Data(user, "300", "GameStart!");
				data.room = room;
				lp.SendObject(data);
			}
		});
		add(btnStart);
		//------게임 시작 버튼 끝
		
		//------게임 나가기 버튼
		btnClose = new JButton(closeImg);
		btnClose.setBorderPainted(false); btnClose.setContentAreaFilled(false);btnClose.setFocusPainted(false);
		btnClose.setBounds(730, 13, 60, 60);
		btnClose.addMouseListener(new MouseAdapter() { //방 퇴장
			public void mousePressed(MouseEvent e) {
				clip1.stop(); //퇴장 시 배경음악 끄기
				buttonClip.start();
				buttonClip.setFramePosition(0);
				
				Data data = new Data(user, "602", "gameRoomExit"); //방 퇴장
				data.room = room;
				lp.SendObject(data);
				gf.dispose();
			}
		});
		add(btnClose);
		//------게임 나가기 버튼 끝
		
		//------힌트 버튼
		btnHint = new JButton("HINT");
		btnHint.setBounds(79, 390, 75, 30);
		btnHint.setVisible(true); 
		btnHint.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				buttonClip.start();
				buttonClip.setFramePosition(0);
				//힌트 팝업으로 띄워줌
				String [] hints = {"초성 힌트", "첫글자 힌트"};
				int hintindex = JOptionPane.showOptionDialog(null, "힌트를 선택하세요.", "Hint", 0, JOptionPane.QUESTION_MESSAGE, null, hints, "");
				
				if(hintindex == 0) {
					JOptionPane.showMessageDialog(null, "힌트  :  " + initw, "Hint", JOptionPane.PLAIN_MESSAGE); //초성힌트
				}
				else
					JOptionPane.showMessageDialog(null, "힌트  :  " + firstw, "Hint", JOptionPane.PLAIN_MESSAGE); //첫글자힌트
				
			}
		});
		add(btnHint);
		
		btnPic = new JButton("밑그림");
		btnPic.setBounds(79, 430, 75, 30);
		btnPic.setVisible(false); //처음엔 밑그림 버튼 안 보이게
		btnPic.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(word.equals("무지개")) {
					AppendImage(Imgrainbow);
				}else if(word.equals("크리스마스")) {
					AppendImage(Imgchristmas);
				}else if(word.equals("아이폰")) {
					AppendImage(Imgiphone);
				}else if(word.equals("펭귄")) {
					AppendImage(Imgpenguine);
				}else if(word.equals("고양이")) {
					AppendImage(Imgcat);
				}else if(word.equals("앵그리버드")) {
					AppendImage(Imgangrybird);
				}else if(word.equals("대한민국")) {
					AppendImage(Imgkorea);
				}else if(word.equals("딸기")) {
					AppendImage(Imgstrawberry);
				}else if(word.equals("노트북")) {
					AppendImage(Imgnotebook);
				}else if(word.equals("바다")) {
					AppendImage(Imgsea);
				}
			}
		});
		add(btnPic);
		
		//------힌트 버튼 끝
		
		//------현재 출제자, 남은 라운드, 남은 시간 Label
		presenterLabel = new JLabel("");
		presenterLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		presenterLabel.setBounds(253, 10, 324, 34);
		add(presenterLabel);
		
		roundLabel = new JLabel("");
		roundLabel.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		roundLabel.setBounds(30, 78, 148, 34);
		add(roundLabel);
		
		timerLabel = new JLabel("");
		timerLabel.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		timerLabel.setBounds(210, 73, 123, 38);
		add(timerLabel);
		//------현재 출제자, 남은 라운드, 남은 시간 Label 끝
		
		//버튼 이벤트 주기===============================
		btnEraser.addActionListener(new ActionListener() { //지우개 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!presenter.equals(user.name)) return; //출제자가 아니면 버튼 클릭 처리X
				colorClip.start();
				colorClip.setFramePosition(0);
				
				Data data = new Data(user, "503", "erase");
				SendObject(data);
				g2d.setColor(Color.WHITE);
			}
		});
		
		btnBlack.addActionListener(new ActionListener() { //검정색 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!presenter.equals(user.name)) return; //출제자가 아니면 버튼 클릭 처리X
				colorClip.start();
				colorClip.setFramePosition(0);
				
				Data data = new Data(user, "503", "black");
				SendObject(data);
				g2d.setColor(Color.BLACK);
			}
		});
		
		btnBlue.addActionListener(new ActionListener() { //파란색 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!presenter.equals(user.name)) return; //출제자가 아니면 버튼 클릭 처리X
				colorClip.start();
				colorClip.setFramePosition(0);
				
				Data data = new Data(user, "503", "blue");
				SendObject(data);
				g2d.setColor(Color.BLUE);
			}
		});
		
		btnGreen.addActionListener(new ActionListener() { //초록색 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!presenter.equals(user.name)) return; //출제자가 아니면 버튼 클릭 처리X
				colorClip.start();
				colorClip.setFramePosition(0);
				
				Data data = new Data(user, "503", "green");
				SendObject(data);
				g2d.setColor(Color.GREEN);
			}
		});
		
		btnYellow.addActionListener(new ActionListener() { //노란색 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!presenter.equals(user.name)) return; //출제자가 아니면 버튼 클릭 처리X
				colorClip.start();
				colorClip.setFramePosition(0);
				
				Data data = new Data(user, "503", "yellow");
				SendObject(data);
				g2d.setColor(Color.YELLOW);
			}
		});
		
		btnRed.addActionListener(new ActionListener() { //빨간색 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!presenter.equals(user.name)) return; //출제자가 아니면 버튼 클릭 처리X
				colorClip.start();
				colorClip.setFramePosition(0);
				
				Data data = new Data(user, "503", "red");
				SendObject(data);
				g2d.setColor(Color.RED);
			}
		});
		
		btnClear.addActionListener(new ActionListener() { //초기화 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!presenter.equals(user.name)) return; //출제자가 아니면 버튼 클릭 처리X
				colorClip.start();
				colorClip.setFramePosition(0);
				
				Data data = new Data(user, "503", "drawPanelInit");
				SendObject(data);
				g2d.setColor(Color.WHITE); //그리는 색상 => panel의 배경색
				g2d.fillRect(0,0, drawPanel.getWidth(),  drawPanel.getHeight());
				gc.drawImage(panelImage, 0, 0, drawPanel);
				g2d.setColor(Color.BLACK);
			}
		});
		//버튼 이벤트 주기===============================
	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(gamebackground, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void loadAudioBack() {
		try {
			clip1 = AudioSystem.getClip();
			File audioFile1 = new File("sound/gameRoom.wav");
			AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(audioFile1);
			clip1.open(audioStream1);
			
			correctClip = AudioSystem.getClip();
			File audioFile2 = new File("sound/correct.wav");
			AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(audioFile2);
			correctClip.open(audioStream2);
			
			incorrectClip = AudioSystem.getClip();
			File audioFile3 = new File("sound/incorrect.wav");
			AudioInputStream audioStream3 = AudioSystem.getAudioInputStream(audioFile3);
			incorrectClip.open(audioStream3);
			
			colorClip = AudioSystem.getClip();
			File audioFile4 = new File("sound/color.wav");
			AudioInputStream audioStream4 = AudioSystem.getAudioInputStream(audioFile4);
			colorClip.open(audioStream4);
			
			buttonClip = AudioSystem.getClip();
			File audioFile5 = new File("sound/btn.wav");
			AudioInputStream audioStream5 = AudioSystem.getAudioInputStream(audioFile5);
			buttonClip.open(audioStream5);
			
			timeOutClip = AudioSystem.getClip();
			File audioFile6 = new File("sound/timeout.wav");
			AudioInputStream audioStream6 = AudioSystem.getAudioInputStream(audioFile6);
			timeOutClip.open(audioStream6);
		}
		catch (Exception e) {return;}
	}
	
	public void init() {
		gc = drawPanel.getGraphics(); //panel 컴포넌트의 Graphics 객체를 반환한다.
		
		// Image 영역 보관용. paint() 에서 이용한다.
		panelImage = createImage(drawPanel.getWidth(), drawPanel.getHeight()); //panel의 크기를 가진 이미지 생성
		g2d = (Graphics2D)panelImage.getGraphics(); //panelImage 컴포넌트의 Graphics 객체를 반환한다.
		g2d.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND,0)); //굵기 6으로 설정
		
		//그림판 패널인 panel과 일치시키기
		g2d.setColor(drawPanel.getBackground()); //그리는 색상 => panel의 배경색
		g2d.fillRect(0,0, drawPanel.getWidth(),  drawPanel.getHeight()); //panel 크기만큼 사각형으로 채우기 => background-color 넣는 느낌
		g2d.setColor(Color.BLACK); //그리는 색상 => 검정
		
		//게임 방에 모든 플레이어 화면 갱신을 요청하는 데이터 송신
		Data data = new Data(user, "700", "playerInforUpdate");
		SendObject(data);
	}
	
	class MyMouseWheelEvent implements MouseWheelListener {
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
		}
		
	}
	// Mouse Event Handler
	class MyMouseEvent implements MouseListener, MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			//도전과제 시작
			endX = e.getX(); 
            endY = e.getY();
            System.out.println(startX + " " + startY + ", " + endX + " " + endY); //추가
            g2d.drawLine(startX, startY, endX, endY);       
            gc.drawImage(panelImage, 0, 0, drawPanel);
            startX = e.getX(); 
			startY = e.getY(); 
			SendMouseEvent(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
//			Color c = new Color(0,0,255);
//			vStart.add(e.getPoint());
//			for (int i = 1; i < vStart.size(); i++) {
//				if (vStart.get(i - 1) == null)
//					continue;
//				else if (vStart.get(i) == null)
//					continue;
//				else {
//					g2d.drawLine((int) vStart.get(i - 1).getX(), (int) vStart.get(i - 1).getY(),
//							(int) vStart.get(i).getX(), (int) vStart.get(i).getY());
//				}
//			}
			
			endX = e.getX(); 
            endY = e.getY(); 
            System.out.println(startX + " " + startY + ", " + endX + " " + endY);
			g2d.drawLine(startX, startY, endX, endY);  
			gc.drawImage(panelImage, 0, 0, drawPanel);
			startX = endX; //추가
            startY = endY; //추가
//			ChatMsg cm = new ChatMsg(UserName, "502", "MOUSE");
//    		cm.mouse_e = e;
//    		cm.pen_size = pen_size;
//    		SendObject(cm);
			
			Data data = new Data(user, "502", "Clicked");
			data.mouse_e = e;
    		SendObject(data);
//    		System.out.println(cm.code + " dsd\n");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// panel.setBackground(Color.YELLOW);

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// panel.setBackground(Color.CYAN);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			startX = e.getX(); //마우스가 눌렸을때 그때의 X좌표값으로 초기화
            startY = e.getY(); //마우스가 눌렸을때 그때의 Y좌표값으로 초기화
            Data data = new Data(user, "501", "Pressed");
            data.mouse_e = e;
    		SendObject(data);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// 드래그중 멈출시 보임
			gc.drawImage(panelImage, 0, 0, drawPanel);
			Data data = new Data(user, "502", "Released");
			data.mouse_e = e;
    		SendObject(data);
//    		System.out.println(cm.code + " dsd\n");
		}
	}
	
		//Mouse Event 수신 처리(그림판)
		public void DoMouseEvent(Data data) {
			
			if((startX == 0 && startY == 0) || flag) {//처음 그릴 때
				startX = data.mouse_e.getX(); 	
				startY = data.mouse_e.getY();
				
				//Clicked이라면 다시 처음 그리는 것 처럼 그려야하므로 flag를 바꾸지 않는다.
				if(!data.msg.equals("Clicked"))	flag = false; 
				
				endX = data.mouse_e.getX(); 
	            endY = data.mouse_e.getY(); 
				g2d.drawLine(startX, startY, endX, endY);   
				gc.drawImage(panelImage, 0, 0, drawPanel);
				return;
			}
			
			if(data.code.equals("502")) { //Released 또는 Clicked
				System.out.println("Sda\n");
				flag = true;
			}
			
			//dragged이면
			if(data.code.equals("500")) {
				endX = data.mouse_e.getX(); 
		        endY = data.mouse_e.getY(); 
		
		        g2d.drawLine(startX, startY, endX, endY);       
		        gc.drawImage(panelImage, 0, 0, drawPanel);
		        
		        startX = endX; 
		        startY = endY;
			}
			//pressed이면
			else if(data.code.equals("501")) {
				startX = endX; 	
				startY = endY;
			}
		}
		
		public void DoGameEvent(Data data) {
			if(data.code.equals("503")) { //색상 버튼, 지우개 버튼, 전체 지우기 버튼 
				if(data.msg.equals("erase"))
					g2d.setColor(Color.WHITE);
				else if(data.msg.equals("black"))
					g2d.setColor(Color.BLACK);
				else if(data.msg.equals("red"))
					g2d.setColor(Color.RED);
				else if(data.msg.equals("blue"))
					g2d.setColor(Color.BLUE);
				else if(data.msg.equals("green"))
					g2d.setColor(Color.GREEN);
				else if(data.msg.equals("yellow"))
					g2d.setColor(Color.YELLOW);
				else if(data.msg.equals("drawPanelInit")) {
					g2d.setColor(Color.WHITE); //그리는 색상 => panel의 배경색
					g2d.fillRect(0,0, drawPanel.getWidth(),  drawPanel.getHeight());
					gc.drawImage(panelImage, 0, 0, drawPanel);
					g2d.setColor(Color.BLACK);
				}
			} else if (data.code.equals("700")) { //플레이어 화면 갱신
				int i = data.user.loca;
				System.out.println("Gp 593L: " + i + "\n");
				if(i==1) {
					username1.setText(data.user.name);
					score1Label_score.setText(""+data.user.score);
				} else if(i==2) {
					username2.setText(data.user.name);
					score2Label_score.setText(""+data.user.score);
				} else if(i==3) {
					username3.setText(data.user.name);
					score3Label_score.setText(""+data.user.score);
				} else if(i==4) {
					username4.setText(data.user.name);
					score4Label_score.setText(""+data.user.score);
				}
			} else if (data.code.equals("603")) { //방이 꽉 찼다면 게임방 내 게임시작 버튼 활성화 (해당 방의 모든 사용자가 받는다.)
				System.out.println("게임시작 버튼 활성화!");
				btnStart.setVisible(true);
			} else if(data.code.equals("604")) { //방이 꽉 차지 않았다면 게임시작 버튼 비활성화(누군가 퇴장한 경우)
				System.out.println("게임시작 버튼 비활성화!");
				btnStart.setVisible(false);
			} else if(data.code.equals("703")) { //내가 출제자인 경우
				drawPanel.addMouseMotionListener(mouse);
				drawPanel.addMouseListener(mouse);
				System.out.println(user.name + "인 내가 출제자다!");
			
				//출제자는 밑그림 버튼만, 유저들은 힌트 버튼만
				btnPic.setVisible(true); //밑그림 버튼 활성화
				btnHint.setVisible(false); //힌트 버튼 비활성화
				
				showWord.setText(word); //제시어 출제자 화면에만 출력
			} else if(data.code.equals("704")) { //그림을 그리는 사용자를 GamePanel의 presenterLabel에 출력 요청하는 데이터 수신
				presenter = data.msg.split("/")[0];
				String round = data.msg.split("/")[1];
				roundLabel.setText("Round " + round);
				presenterLabel.setText("[" + presenter + "] 그리는 중...");
			} else if(data.code.equals("602")) {
				int i = Integer.parseInt(data.msg);
				if(i==1) {
					username1.setText("빈 자리");
					score1Label_score.setText("0");
				} else if(i==2) {
					username2.setText("빈 자리");
					score2Label_score.setText("0");
				} else if(i==3) {
					username3.setText("빈 자리");
					score3Label_score.setText("0");
				} else if(i==4) {
					username4.setText("빈 자리");
					score4Label_score.setText("0");
				}
			} else if(data.code.equals("900")) { //제시어 맞춘 경우 => 플레이어 화면갱신, 턴 바꾸기 / msg = 정답자 + " " + 출제자
				//정답자 (플레이어 화면 갱신) 시작
				int ansLoca = Integer.parseInt(data.msg.split(" ")[0]); //정답자 Loca
				//점수 갱신
				if(ansLoca==1) {
					score1Label_score.setText(Integer.parseInt(score1Label_score.getText())+10+"");
				} else if(ansLoca==2) {
					score2Label_score.setText(Integer.parseInt(score2Label_score.getText())+10+"");
				} else if(ansLoca==3) {
					score3Label_score.setText(Integer.parseInt(score3Label_score.getText())+10+"");
				} else if(ansLoca==4) {
					score4Label_score.setText(Integer.parseInt(score4Label_score.getText())+10+"");
				}
				//정답자 (플레이어 화면 갱신) 끝
				
				//출제자 (마우스이벤트X) 시작
				int publishLoca = Integer.parseInt(data.msg.split(" ")[1]); //출제자 Loca
				//점수 갱신
				if(ansLoca != 0) { //맞춘 사용자가 있을 경우에만 갱신
					if(publishLoca==1) { 
						score1Label_score.setText(Integer.parseInt(score1Label_score.getText())+7+"");
					} else if(publishLoca==2) {
						score2Label_score.setText(Integer.parseInt(score2Label_score.getText())+7+"");
					} else if(publishLoca==3) {
						score3Label_score.setText(Integer.parseInt(score3Label_score.getText())+7+"");
					} else if(publishLoca==4) {
						score4Label_score.setText(Integer.parseInt(score4Label_score.getText())+7+"");
					}	
				}
				
				if(user.loca == publishLoca) { //기존출제자가 나인 경우에만
					//마우스 이벤트X
					System.out.println("기존 출제자:  " + user.name + "==============");
					showWord.setText(""); //제시어 출제자 화면에만 출력
					drawPanel.removeMouseMotionListener(mouse);
					drawPanel.removeMouseListener(mouse);
					System.out.println("마우스 이벤트 종료================ ");
					
					btnPic.setVisible(false); //밑그림 버튼 비활성화
					btnHint.setVisible(true); //힌트 버튼 활성화
				}
				//기존출제자 (마우스이벤트X) 끝
				
				//다음 출제자인 경우(턴 바꾸기) 시작
				if(room.maxUserCount == publishLoca) publishLoca = 1;
            	else publishLoca = (publishLoca+1);
				if(user.loca == publishLoca) { //다음 출제자가 나인 경우에만
					//마우스 이벤트O
					System.out.println("다음 출제자:  " + user.name);
					drawPanel.addMouseMotionListener(mouse);
					drawPanel.addMouseListener(mouse);
					
					btnPic.setVisible(true); //밑그림 버튼 활성화
					btnHint.setVisible(false); //힌트 버튼 비활성화
					
					System.out.println("현재 단어: " + word);
					showWord.setText(word); //제시어 출제자 화면에만 출력
				}
			} else if(data.code.equals("901")) { //각각 사용자의 점수와 등수를 수신한다.(모든 라운드가 끝날을 때 받는다.)
				int score = Integer.parseInt(data.msg.split(" ")[0]);
				int rank = Integer.parseInt(data.msg.split(" ")[1]);
				
				JOptionPane.showMessageDialog(null,"[" + user.name + "]\n" + score + "점으로 순위는 " + rank + "등입니다!",
						"GameOver!!", JOptionPane.PLAIN_MESSAGE);
				//게임화면 초기화
				score1Label_score.setText("0");
				score2Label_score.setText("0");
				score3Label_score.setText("0");
				score4Label_score.setText("0");
				roundLabel.setText("");
				presenterLabel.setText("");
				textArea.setText("");
				btnStart.setVisible(true); //게임시작 버튼 보이게
				btnClose.setVisible(true); //게임퇴장 버튼 보이게
				showWord.setText("");
				//단어, 힌트 초기화
				word = initw = firstw =  "";
				
				//loca==1인 사용자를 위해 해주는 작업 (MyMouseEvent 제거) => 그림판 오류 수정
				drawPanel.removeMouseMotionListener(mouse);
				drawPanel.removeMouseListener(mouse);	
				
				if(timer!=null) timer.cancel();
				timerLabel.setText("");
			} else if(data.code.equals("602-1")) { //GamePanel의 사용자 loca도 1칸씩 땡기기 위한 데이터 송신
				//해당 메시지를 받은 사용자들의 loca를 1칸씩 감소
				user.loca-=1;
			}
		}
		
		//제시어 송신 처리(wordEvent를 수신 => 게임이 시작된 것)
		public void wordEvent(Data data) {
			btnStart.setVisible(false); //게임시작 버튼 안 보이게
			btnClose.setVisible(false); //게임퇴장 버튼 안 보이게
			
			if(data.code.equals("400")) { //제시어
				word = data.msg.split(" ")[0];
				initw = data.msg.split(" ")[1];
				firstw = data.msg.split(" ")[2];
			}
			
			if(timer!=null) timer.cancel(); //전에 돌아가던 것이 있다면 확실하게 멈추고
			timer = new Timer(); //다시 생성
			task = new TimerTask() {
				@Override
				public void run() {
					if(count<0) {
						timer.cancel();
						//다음 턴으로 넘기라는 데이터 송신해야함. (loca==1인 사용자가 대표로 보낸다.)
						if(user.loca == 1) {
							Data sendData = new Data(user, "900", "timeOver"); //제한시간 초과 알림
							SendObject(sendData);
							
							//그림판 초기화
							sendData = new Data(user, "503", "drawPanelInit");
							SendObject(sendData);
							g2d.setColor(Color.WHITE); //그리는 색상 => panel의 배경색
							g2d.fillRect(0,0, drawPanel.getWidth(),  drawPanel.getHeight());
							gc.drawImage(panelImage, 0, 0, drawPanel);	
						}
					}
					else {
						timerLabel.setText("남은 시간: " + count);
						count-=1;
					}
					
					if(count==1) { //제한시간이 3초 남았을 때 시작
						//효과음
						timeOutClip.start();
						timeOutClip.setFramePosition(0);
					}
				}
			};
			count = 30; //남은 시간 초기화
			timer.schedule(task, 0, 1000);
		}
		
		// Mouse Event 송신 처리
		public void SendMouseEvent(MouseEvent e) {
			Data data = new Data(user, "500", "Dragged");
			data.mouse_e = e;
			lp.SendObject(data);
		}

		//chatting
		public void GameChat(Data data) {
			if(data.code.equals("250")) {
				AppendText(data.msg);
			}
		}
		
		class TextSendAction implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnInput || e.getSource() == textField) {
					String msg = null;
					msg = textField.getText();
					//SendMessage(msg);
					Data data = new Data(user, "250", msg);
					SendObject(data);
					textField.setText(""); 
					textField.requestFocus();
					
					if(presenter.equals(user.name)) return; //출제자가 맞추면 정답처리X
					
					//제시어를 맞춘 경우
					if(msg.equals(word)) {
						correctClip.start();
						correctClip.setFramePosition(0);
						
						data = new Data(user, "900", msg); //정답 알림 송신
						SendObject(data);
						
						//그림판 초기화
						data = new Data(user, "503", "drawPanelInit");
						SendObject(data);
						g2d.setColor(Color.WHITE); //그리는 색상 => panel의 배경색
						g2d.fillRect(0,0, drawPanel.getWidth(),  drawPanel.getHeight());
						gc.drawImage(panelImage, 0, 0, drawPanel);
					} else {
						if(!word.equals("")) { //게임을 시작한 경우에만 효과음
							incorrectClip.start();
							incorrectClip.setFramePosition(0);
						}
					}
				}
			}
		}
		
		public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
			lp.SendObject(ob);
		}
		//채팅 화면에 출력
		public void AppendText(String msg) {
			msg = msg.trim();
			StyledDocument doc = textArea.getStyledDocument();
			SimpleAttributeSet left = new SimpleAttributeSet();
			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
			StyleConstants.setForeground(left, Color.BLACK);
		    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
		    try {
				doc.insertString(doc.getLength(), msg+"\n", left );
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    int len = textArea.getDocument().getLength();
			textArea.setCaretPosition(len);	
		}
		
		public void AppendImage(ImageIcon ori_icon) {
			Image ori_img  = ori_icon.getImage();
			Image new_img;
			ImageIcon new_icon;
			int width, height;
			width = ori_icon.getIconWidth();
			height = ori_icon.getIconHeight();
			//이미지 크기 맞추기
			width = 428; height = 314;
			
			new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			new_icon = new ImageIcon(new_img);
			
			g2d.drawImage(ori_img, 0, 0, drawPanel.getWidth(), drawPanel.getHeight(), drawPanel);
			gc.drawImage(panelImage, 0, 0, drawPanel.getWidth(), drawPanel.getHeight(), drawPanel);
		}
}
