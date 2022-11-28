

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

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.ImageObserver;
import java.io.File;
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
	private JTextField username1;
	private JTextField username2;
	private JTextField username3;
	private JTextField username4;
	private JTextField showWord;
	private JTextPane textArea;
	private JButton btnInput;
	
	private JLabel score1Label_score;
	private JLabel score2Label_score;
	private JLabel score3Label_score;
	private JLabel score4Label_score;
	private JLabel presenterLabel;
	
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
	
	//Draw용 속성 시작=====================================
	int startX; 
	int startY; 
	int endX; 
	int endY;
	    
	private Graphics gc;
	private Graphics2D g2d;
	private int pen_size = 2; // minimum 2
		
	// 그려진 Image를 보관하는 용도, paint() 함수에서 이용한다.
	private Image panelImage = null; 
	//Draw용 속성 끝======================================
	
	private String word; //제시어
	private String initw; //초성
	private String firstw; //첫글자
	
	private GameFrame gf;
	private LobbyPanel lp;
	private User user; //현재 사용자
	private Room room; //현재 방
	// Mouse Event 수신 처리
	boolean flag = false;
	MyMouseEvent mouse = new MyMouseEvent();
	
	public GamePanel(GameFrame gf, LobbyPanel lp, User user) {
		this.gf = gf;
		this.lp = lp;
		this.user = user;
		this.room = user.room;
		System.out.print(user.room + " dndndndndn\n");
		setLayout(null);
		setBounds(0, 0, 863, 572);
		
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
		//showWord.setText("딸기맛 사탕");
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
		JLabel user1Label = new JLabel(userImg);
		user1Label.setBounds(106, 112, 68, 110);
		add(user1Label);
		username1 = new JTextField();
		username1.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		username1.setText("빈 자리");
		username1.setBounds(37, 129, 68, 22);
		username1.setEnabled(false);
		username1.setColumns(10);
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
		
		username3 = new JTextField();
		username3.setText("빈 자리");
		username3.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		username3.setBounds(36, 231, 68, 22);
		username3.setEnabled(false);
		username3.setColumns(10);
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
		
		username2 = new JTextField();
		username2.setText("빈 자리");
		username2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		username2.setBounds(676, 125, 68, 22);
		username2.setEnabled(false);
		username2.setColumns(10);
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
		
		username4 = new JTextField();
		username4.setText("빈 자리");
		username4.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		username4.setBounds(674, 228, 68, 22);
		username4.setEnabled(false);
		username4.setColumns(10);
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
				System.out.println("현재 게임방 번호 : " + room.id);
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
				System.out.println("퇴장하는 게임방 번호 : " + room.id);
				
				Data data = new Data(user, "602", "gameRoomExit"); //방 퇴장
				data.room = room;
				lp.SendObject(data);
				
				gf.dispose();
			}
		});
		add(btnClose);
		//------게임 나가기 버튼 끝
		
		//------힌트 버튼
		JButton btnHint = new JButton("HINT");
		btnHint.setBounds(79, 390, 75, 30);
		btnHint.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
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
		
		JButton btnPic = new JButton("밑그림");
		btnPic.setBounds(79, 430, 75, 30);
		add(btnPic);
		//------힌트 버튼 끝
		
		//------현재 출제자 Label
		presenterLabel = new JLabel("");
		presenterLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		presenterLabel.setBounds(253, 10, 324, 34);
		add(presenterLabel);
		//------현재 출제자 Label 끝
		
		//버튼 이벤트 주기===============================
		btnEraser.addActionListener(new ActionListener() { //지우개 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				Data data = new Data(user, "503", "erase");
				SendObject(data);
				g2d.setColor(Color.WHITE);
			}
		});
		
		btnBlack.addActionListener(new ActionListener() { //검정색 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				Data data = new Data(user, "503", "black");
				SendObject(data);
				g2d.setColor(Color.BLACK);
			}
		});
		
		btnBlue.addActionListener(new ActionListener() { //파란색 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				Data data = new Data(user, "503", "blue");
				SendObject(data);
				g2d.setColor(Color.BLUE);
			}
		});
		
		btnGreen.addActionListener(new ActionListener() { //초록색 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				Data data = new Data(user, "503", "green");
				SendObject(data);
				g2d.setColor(Color.GREEN);
			}
		});
		
		btnYellow.addActionListener(new ActionListener() { //노란색 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				Data data = new Data(user, "503", "yellow");
				SendObject(data);
				g2d.setColor(Color.YELLOW);
			}
		});
		
		btnRed.addActionListener(new ActionListener() { //빨간색 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Red\n");
				Data data = new Data(user, "503", "red");
				SendObject(data);
				g2d.setColor(Color.RED);
			}
		});
		
		btnClear.addActionListener(new ActionListener() { //초기화 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				Data data = new Data(user, "503", "drawPanelInit");
				SendObject(data);
				g2d.setColor(Color.WHITE); //그리는 색상 => panel의 배경색
				g2d.fillRect(0,0, drawPanel.getWidth(),  drawPanel.getHeight());
				gc.drawImage(panelImage, 0, 0, drawPanel);
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
			clip1= AudioSystem.getClip();
			File audioFile = new File("sound/gameRoom.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip1.open(audioStream);
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

            g2d.drawLine(startX, startY, endX, endY);       
            gc.drawImage(panelImage, 0, 0, drawPanel);
            
            startX = endX; 
            startY = endY;
            
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
			g2d.drawLine(startX, startY, endX, endY);  
			gc.drawImage(panelImage, 0, 0, drawPanel);
			
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
			} else if(data.code.equals("703")) { //내가 출제자인 경우
				drawPanel.addMouseMotionListener(mouse);
				drawPanel.addMouseListener(mouse);
				System.out.println(user.name + "인 내가 출제자다!");
			
				showWord.setText(word); //제시어 출제자 화면에만 출력
			} else if(data.code.equals("704")) { //그림을 그리는 사용자를 GamePanel의 presenterLabel에 출력 요청하는 데이터 수신
				presenterLabel.setText("[" + data.msg + "] 그리는 중...");
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
				if(publishLoca==1) { 
					score1Label_score.setText(Integer.parseInt(score1Label_score.getText())+7+"");
				} else if(publishLoca==2) {
					score2Label_score.setText(Integer.parseInt(score2Label_score.getText())+7+"");
				} else if(publishLoca==3) {
					score3Label_score.setText(Integer.parseInt(score3Label_score.getText())+7+"");
				} else if(publishLoca==4) {
					score4Label_score.setText(Integer.parseInt(score4Label_score.getText())+7+"");
				}
				
				if(user.loca == publishLoca) { //기존출제자가 나인 경우에만
					//마우스 이벤트X
					System.out.println("기존 출제자:  " + user.name + "==============");
					showWord.setText(""); //제시어 출제자 화면에만 출력
					drawPanel.removeMouseMotionListener(mouse);
					drawPanel.removeMouseListener(mouse);
					System.out.println("마우스 이벤트 종료================ ");
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
					
					System.out.println("현재 단어: " + word);
					showWord.setText(word); //제시어 출제자 화면에만 출력
				}
				//다음 출제자인 경우(턴 바꾸기) 끝
				
				//그 다음 할 것: 일단 로직 서버 "400", gp "400" 다시 분석 후 일단 되는지나 확인하자. 그후 서버에서 다음턴인 사용자 gp로 제시어 송신?
				//다음 턴 일때, 그림판화면 지워져 있어야함!
				//"400" => "900?" 으로 고쳐야함 word()랑 프로토콜 겹침
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
					
					//제시어를 맞춘 경우
					if(msg.equals(word)) {
						data = new Data(user, "900", msg); //정답 알림 송신
						SendObject(data);
						
						//그림판 초기화
						data = new Data(user, "503", "drawPanelInit");
						SendObject(data);
						g2d.setColor(Color.WHITE); //그리는 색상 => panel의 배경색
						g2d.fillRect(0,0, drawPanel.getWidth(),  drawPanel.getHeight());
						gc.drawImage(panelImage, 0, 0, drawPanel);
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
}
