import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JMenuBar;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

public class LobbyPanel extends JPanel {
	
	private String UserName;
	private JTextField txtInput;
	private JTextField nickname;
	private ImageIcon userImage = new ImageIcon(LobbyPanel.class.getResource("image/onion.png"));
	private Image background = new ImageIcon(LobbyPanel.class.getResource("image/back.png")).getImage();
	private JTable roomTable; //방 목록 테이블
	private JTable onlineUserTable; //온라인 사용자 테이블
	private String curRoomID;
	
	private JTextPane textArea;
	private JButton btnSend;
	
	private static final long serialVersionUID = 1L;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private Socket socket; // 연결소켓
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public User user;
	
	//음향
	private Clip clip1; //배경음악
	
	private LobbyPanel lp;
	private CreateRoomFrame crf;
	private GameFrame gf;
	private GameFrame gf2;
	DefaultTableModel model;
	String record[];
	
	public LobbyPanel(String username, String ip_addr, String port_no) {
		setBounds(100, 100, 863, 572);
		setLayout(null);
		//음향 시작
//		loadAudioBack();
//		clip1.start(); //배경음악 시작
		//음향 종료
		
		lp = this;
		
		//메뉴바 시작
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 863, 40);
		add(menuBar);
		
		JLabel title = new JLabel(" CatchMind");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		menuBar.add(title);
		
		JLabel lblNewLabel_2 = new JLabel("                                   ");
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.ITALIC, 16));
		menuBar.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("                                   ");
		lblNewLabel_3.setFont(new Font("맑은 고딕", Font.ITALIC, 16));
		menuBar.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("              ");
		lblNewLabel_4.setFont(new Font("맑은 고딕", Font.ITALIC, 16));
		menuBar.add(lblNewLabel_4);
		
		JLabel createRoomLabel = new JLabel("방 만들기");
		createRoomLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		createRoomLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				crf = new CreateRoomFrame(lp);
			}
			public void mouseEntered(MouseEvent e) {
				createRoomLabel.setFont(new Font("맑은 고딕",Font.BOLD,18));
				createRoomLabel.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				createRoomLabel.setFont(new Font("맑은 고딕",Font.BOLD,16));
				createRoomLabel.setForeground(Color.BLACK);
			}
		});
		menuBar.add(createRoomLabel);
		//메뉴바 끝
		
		//방 목록 시작
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 537, 343);
		add(scrollPane);
		
		//방 목록 테이블
		roomTable = new JTable();
		scrollPane.setViewportView(roomTable);
		roomTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"방 번호", "모드", "방 제목", "방장", "현재 인원", "상태"
			})
			{
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			}
		);
		roomTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // 더블클릭
					int row = roomTable.getSelectedRow();
					for (int i = 0; i < roomTable.getColumnCount(); i++) {
						System.out.print(roomTable.getModel().getValueAt(row,i )+"\t");
					}
					//이때, 방 번호만 추출해서 서버에 보낸 뒤 해당 room 클라로 받고 게임입장하면 될 듯?
					curRoomID = (String) roomTable.getValueAt(row, 0);
					String curRoomMode = (String) roomTable.getValueAt(row, 1);
					String curRoomTitle = (String) roomTable.getValueAt(row, 2);
					//Object curAdmin = roomTable.getValueAt(row, 3);
					//Object curRoomUsers = roomTable.getValueAt(row, 4);
					
					Data data = new Data(user, "601", curRoomID);
					//data.room = room;
					SendObject(data);
				} 
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
		//방 목록 끝
		
		//전체채팅 시작
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 403, 537, 97);
		add(scrollPane_1);
		
		textArea = new JTextPane(); //채팅창
		scrollPane_1.setViewportView(textArea);
		
		JLabel chatLabel = new JLabel("대화창 --- Chatting Room");
		scrollPane_1.setColumnHeaderView(chatLabel);
		
		txtInput = new JTextField();
		txtInput.setBounds(10, 505, 453, 21);
		add(txtInput);
		txtInput.setColumns(10);
		
		btnSend = new JButton("입력");
		btnSend.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		btnSend.setBounds(473, 505, 74, 23);
		add(btnSend);
		//전체채팅 끝
		
		//내 정보 시작
		JLabel lblNewLabel = new JLabel(userImage);
		lblNewLabel.setBounds(559, 50, 123, 130);
		add(lblNewLabel);
		
		UserName = username;
		user = new User(username);
		
		nickname = new JTextField();
		nickname.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		nickname.setBounds(559, 190, 123, 21);
		nickname.setText(UserName);
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
		
		//사용자 리스트 테이블
		onlineUserTable = new JTable();
		onlineUserTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"닉네임", "이긴 횟수", "경험치"
				})
				{
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				}
		);

		scrollPane_2.setViewportView(onlineUserTable);
		
		//온라인 사용자
		
		//------연결 / 채팅
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			Data data = new Data(user,"100","Hello");
			SendObject(data);

			ListenNetwork net = new ListenNetwork();
			net.start();
			TextSendAction action = new TextSendAction();
			btnSend.addActionListener(action);
			txtInput.addActionListener(action);
			txtInput.requestFocus();
			
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppendText("connect error");
		}
		//------연결 끝
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
	
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button을 누르거나 메시지 입력하고 Enter key 치면
			if (e.getSource() == btnSend || e.getSource() == txtInput) {
				String msg = null;
				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
				msg = txtInput.getText();
				SendMessage(msg);
				txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				if (msg.contains("/exit")) // 종료 처리
					System.exit(0);
			}
		}
	}
	
	// 화면에 출력
	public void AppendText(String msg) {
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
			
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
	// 화면 우측에 출력
	public void AppendTextR(String msg) {
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.	
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(right, Color.BLUE);	
	    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
		try {
			doc.insertString(doc.getLength(),msg+"\n", right );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
	
	}	
		
	// Server Message를 수신해서 화면에 표시
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {
					Object obcm = null;
					String msg = null;
					Data data;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					if (obcm instanceof Data) {
						data = (Data) obcm;
						System.out.println("Client: " + data.msg + " " + data.code + " " + data.user.name +"\n");
						msg = String.format("[%s]\n%s", data.user.name, data.msg);
					} else
						continue;
					//Server -> Client
					switch (data.code) {
						case "200": // chat message
							if(data.msg.equals("퇴장")) { //사용자 리스트 갱신을 위해 사용자 리스트 초기화
								DefaultTableModel model=(DefaultTableModel)onlineUserTable.getModel();
								model.setRowCount(0); //사용자 리스트(JTable) 초기화

								repaint();
								
								//모든 사용자의 리스트 요청
								Data sendData = new Data(user,"801","userList update"); 
								SendObject(sendData);
							}
							if (data.user.name.equals(UserName))
								AppendTextR(msg); // 내 메세지는 우측에
							else
								AppendText(msg);
							break;
						case "500":
						case "501":
						case "502":
						case "503":
							gf.DoMouseEvent(data);
							break;
						case "600": //방 생성
							if(data.user.name.equals(UserName)) { //내가 생성한 경우(게임화면으로 이동)
								gf = new GameFrame(lp, user);
								gf.revalidate();
								gf.repaint();
							} 
							
							//남이 생성한 경우(방 목록 테이블 추가)
							//"방 번호", "모드", "방 제목", "방장", "현재 인원", "상태"
							Room dataRoom = data.room;
							model = (DefaultTableModel)roomTable.getModel();
							record = new String[] {""+dataRoom.id, dataRoom.mode, dataRoom.title, dataRoom.admin.name, 
									dataRoom.currentUserCount+"/"+dataRoom.maxUserCount, dataRoom.status};
							model.addRow(record);
							
						case "601": //방 입장 
							if(data.msg.equals("enterRoom")) {
								gf2 = new GameFrame(lp);
								gf2.revalidate();
								gf2.repaint();
							}
							
						case "800": //사용자리스트에 새로운 사용자 추가
							User dataUser = data.user;
							model = (DefaultTableModel)onlineUserTable.getModel();
							record = new String[] {dataUser.name, ""+dataUser.victoryCount, ""+dataUser.exp};
							model.addRow(record);
							break;
					}
				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
						ois.close();
						oos.close();
						socket.close();
						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝

			}
		}
	}
	// Server에게 network으로 전송
	public void SendMessage(String msg) {
		try {
			Data data = new Data(user,"200",msg);
			
			oos.writeObject(data);
		} catch (IOException e) {
			AppendText("oos.writeObject() error");
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}
	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			AppendText("SendObject Error");
		}
	}
}