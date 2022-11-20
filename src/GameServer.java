//JavaObjServer.java ObjectStream 기반 채팅 Server

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class GameServer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextArea textArea;
	private JTextField txtPortNumber;

	private ServerSocket socket; // 서버소켓
	private Socket client_socket; // accept() 에서 생성된 client 소켓
	private Vector UserVec = new Vector(); // 연결된 사용자를 저장할 벡터
	private Vector RoomVec = new Vector(); // 생성된 방을 저장할 벡터
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private static int roomId = 1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameServer frame = new GameServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 338, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 300, 298);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea); //JScrollPane에 JPanel 추가하는 메서드

		JLabel lblNewLabel = new JLabel("Port Number");
		lblNewLabel.setBounds(13, 318, 87, 26);
		contentPane.add(lblNewLabel);

		txtPortNumber = new JTextField();
		txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNumber.setText("30000");
		txtPortNumber.setBounds(112, 318, 199, 26);
		contentPane.add(txtPortNumber);
		txtPortNumber.setColumns(10);

		JButton btnServerStart = new JButton("Server Start");
		btnServerStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new ServerSocket(Integer.parseInt(txtPortNumber.getText()));
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				AppendText("Chat Server Running.."); //textArea에 문자열 출력
				btnServerStart.setText("Chat Server Running..");
				btnServerStart.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
				txtPortNumber.setEnabled(false); // 더이상 포트번호 수정못 하게 막는다
				AcceptServer accept_server = new AcceptServer(); 
				accept_server.start(); //새로운 참가자 accept
			}
		});
		btnServerStart.setBounds(12, 356, 300, 35);
		contentPane.add(btnServerStart);
	}

	// 새로운 참가자 accept() 하고 user thread를 새로 생성한다.
	class AcceptServer extends Thread {
		@SuppressWarnings("unchecked")
		public void run() {
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
				try {
					AppendText("Waiting new clients ...");
					client_socket = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
					AppendText("새로운 참가자 from " + client_socket);
					
					// User 당 하나씩 Thread 생성
					UserService new_user = new UserService(client_socket);
					UserVec.add(new_user); // 새로운 참가자 배열에 추가
					new_user.start(); // 만든 객체의 스레드 실행
					AppendText("현재 참가자 수 " + UserVec.size());
				} catch (IOException e) {
					AppendText("accept() error");
					// System.exit(0);
				}
			}
		}
	}

	//textArea에 문자열 출력
	public void AppendText(String str) {
		// textArea.append("사용자로부터 들어온 메세지 : " + str+"\n");
		textArea.append(str + "\n");
		textArea.setCaretPosition(textArea.getText().length()); //맨아래로 스크롤한다.
	}

	public void AppendObject(Data data) {
		// textArea.append("사용자로부터 들어온 object : " + str+"\n");
		textArea.append("code = " + data.code + "\n"); //프로토콜
		textArea.append("id = " + data.user.name + "\n"); //사용자 이름
		textArea.append("data = " + data.msg + "\n"); //정보
		textArea.setCaretPosition(textArea.getText().length()); //맨아래로 스크롤한다.
	}

	// User 당 생성되는 Thread
	// Read One 에서 대기 -> Write All
	class UserService extends Thread {
		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
		private DataOutputStream dos;

		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		private Socket client_socket;
		private Vector user_vc;	
		public String UserName = "";
		public String UserStatus;

		//user나 room을 Data 객체에 넣는 경우 값이 안 바뀌어 있을 수 있기에 확인용도로만 사용추천
		public User user = null; 
		public Room room = null; 
		
		public UserService(Socket client_socket) {
			// TODO Auto-generated constructor stub
			// 매개변수로 넘어온 자료 저장
			this.client_socket = client_socket;
			this.user_vc = UserVec;
			try {
				oos = new ObjectOutputStream(client_socket.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(client_socket.getInputStream());
			} catch (Exception e) {
				AppendText("userService error");
			}
		}

		//입장메세지 + 사용자 리스트 
		public void Login() {
			AppendText("새로운 참가자 " + UserName + " 입장.");
			WriteOne("Welcome to Java chat server\n");
			WriteOne(UserName + "님 환영합니다.\n"); // 연결된 사용자에게 정상접속을 알림
			
			//이때 내 자신에게 사용자 리스트를 보내야함(내 데이터는 빼고)
			for (int i = 0; i < user_vc.size(); i++) {
				UserService us = (UserService) user_vc.elementAt(i);
				if(!us.UserName.equals(UserName))
					WriteOneObject(new Data(us.user, "800", "userList"));
			}
			//사용자 리스트 끝
			
			//이때 내 자신에게 방 리스트를 보내야함(내 데이터는 빼고)
			for (int i = 0; i < RoomVec.size(); i++) {
				Room roomData = (Room) RoomVec.elementAt(i);
				Data sendData = new Data(new User("sample"), "600", "currentRoom");
				sendData.room = roomData;
				WriteOneObject(sendData);
			}
			//방 리스트 끝
			
			String msg = "새로운 사용자가 입장 하였습니다.\n";
			WriteOthers(msg); //아직 user_vc에 새로 입장한 user는 포함되지 않았다. (아직 추가하지 않았기에)
			
			//기존 사용자들의 사용자 리스트에 새로운 사용자 추가
			Data sendData = new Data(user,"800","userList");
			WriteOthersObject(sendData);
		}

		public void Logout() {
			//사용자 리스트 갱신하라는 데이터 보내야함
			String msg = "퇴장"; //Clientsms "퇴장"을 받으면 "801"(사용자 리스트 갱신해줘) 전송
			UserVec.removeElement(this); // Logout한 현재 객체를 벡터에서 지운다
			
			WriteAll(msg); // 나를 제외한 다른 User들에게 전송(보내기전 벡터에서 삭제했기에 가능)
			AppendText("사용자 " + "[" + UserName + "] 퇴장. 현재 참가자 수 " + UserVec.size());
		}

		// 모든 User들에게 방송. 각각의 UserService Thread의 WriteOne() 을 호출한다.
		public void WriteAll(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user.UserStatus == "O")
					user.WriteOne(str);
			}
		}
		// 모든 User들에게 Object를 방송. 채팅 message와 image object를 보낼 수 있다
		public void WriteAllObject(Object ob) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user.UserStatus == "O")
					user.WriteOneObject(ob);
			}
		}

		// 나를 제외한 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteOthers(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this && user.UserStatus == "O")
					user.WriteOne(str);
			}
		}
		
		// 나를 제외한 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteOthersObject(Object ob) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this && user.UserStatus == "O")
					user.WriteOneObject(ob);
			}
		}	

		// Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
		public byte[] MakePacket(String msg) {
			byte[] packet = new byte[BUF_LEN];
			byte[] bb = null;
			int i;
			for (i = 0; i < BUF_LEN; i++)
				packet[i] = 0;
			try {
				bb = msg.getBytes("euc-kr");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (i = 0; i < bb.length; i++)
				packet[i] = bb[i];
			return packet;
		}

		// UserService Thread가 담당하는 Client 에게 1:1 전송
		//입장 및 퇴장, /list 의 경우에 이 메소드가 호출된다.
		public void WriteOne(String msg) {
			try {
				Data data = new Data(user, "200", msg);
				oos.writeObject(data); //송신
			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}

		public void WriteOneObject(Object ob) {
			try {
			    oos.writeObject(ob);
			} 
			catch (IOException e) {
				AppendText("oos.writeObject(ob) error");		
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout();
			}
		}
		
		public User newUser(User user) {
			User sendUser = new User(user.name);
			sendUser.victoryCount = user.victoryCount;
			sendUser.exp = user.exp;
			sendUser.gold = user.gold;
			sendUser.consonantItem = user.consonantItem;
			sendUser.firstLetterItem = user.firstLetterItem;
			sendUser.ready = user.ready;
			sendUser.room = user.room;
			sendUser.loca = user.loca;
			
			return sendUser;
		}
		
		public Room newRoom(Room room) {
			Room sendRoom = new Room(room.title, room.mode, room.admin);
			sendRoom.id = room.id;
			sendRoom.currentUserCount = room.currentUserCount;
			sendRoom.maxUserCount = room.maxUserCount;
			sendRoom.status = room.status;
			sendRoom.allReady = room.allReady;
			
			return sendRoom;
		}
		
		//각각의 사용자마다 돌아가는 스레드이다.
		public void run() {
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
				try {
					Object obcm = null;
					String msg = null;
					Data data = null;
					if (socket == null)
						break;
					try {
						obcm = ois.readObject(); //수신을 기다린다!!!!!!!!!!!!
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
					if (obcm == null) //예외
						break;
					if (obcm instanceof Data) {
						data = (Data) obcm;
						System.out.println("Server: " + data.msg + " " + data.code+"\n");
						AppendObject(data); //textArea에 보낸 사용자 + 프로토콜 + 메세지 출력
					} else
						continue;
					if (data.code.matches("100")) { //로그인
						UserName = data.user.name;
						UserStatus = "O"; // Online 상태
						user = data.user;
						System.out.println(user + " " + data.user + "\n");
						Login();
					}else if (data.code.matches("200")) { //채팅
						msg = String.format("[%s] %s", data.user.name, data.msg);
						AppendText(msg); // server 화면에 출력
						//args[0]: 사용자 이름, args[1]: 메세지
						String[] args = msg.split(" "); // 단어들을 분리한다.
						WriteAllObject(data);
						//
					}else if (data.code.matches("201")) { //정답알림
		                  
		            }else if (data.code.matches("250")) { //게임방 채팅
		            	msg = String.format("[%s] %s", data.user.name, data.msg);
		            	AppendText(msg);
		            	String[] args = msg.split(" ");
		            	for(int i=0; i < user_vc.size(); i++) { 
		            		UserService usData = (UserService)user_vc.get(i);
		            		if(usData.room!=null) {
		            			if(usData.room.id == room.id) {
		            				System.out.println(msg);
		            				usData.WriteOneObject(new Data(user, "250", msg));
		            			}
		            		}
		            	}
		            }else if (data.code.matches("300")) { //게임 준비
		                  
		            } else if (data.code.matches("301")) { //게임 시작
		                  
		            } else if (data.code.matches("400")) { //키워드 받기
		                  
		            } else if (data.code.matches("500") || data.code.matches("501") || data.code.matches("502")) { //마우스 이벤트
		            	System.out.println(user.name + " " + room.id + ": MOUSE\n");
		            	
		            	//해당 방의 사용자들에게만 보낸다.
		            	for(int i=0; i < user_vc.size(); i++) { 
		            		//속해있는 사용자에게 마우스이벤트를 보낸다.
		            		UserService usData = (UserService)user_vc.get(i);
		            		if(usData.room!=null) { //방이 있는 UserService만 판단
			            		if(usData.room.id == room.id) {
			            			usData.WriteOneObject(data);
			            		}
		            		}
		            	}
		            } else if (data.code.matches("503")) { //펜 색상 변경
		            	//해당 방의 사용자들에게만 보낸다.
		            	for(int i=0; i < user_vc.size(); i++) { 
		            		//속해있는 사용자에게 마우스이벤트를 보낸다.
		            		UserService usData = (UserService)user_vc.get(i);
		            		if(usData.room!=null) { //방이 있는 UserService만 판단
			            		if(usData.room.id == room.id) {
			            			usData.WriteOneObject(data);
			            		}
		            		}
		            	}
		            } else if (data.code.matches("600")) { //방 생성
		                System.out.println("잘 받았다리~\n");
		                data.room.id = (roomId++);
		                room = data.room; //현재 방
		                RoomVec.add(data.room); //벡터에 저장
		                //방 만든 사용자에게 게임화면으로 이동하라고 송신 "600" => data의 user와 이름이 같으면 이동
		                WriteOneObject(data);
		                //모든 사용자에게 방 목록 추가됐음을 알리는 데이터 송신 "600"
		                WriteOthersObject(data);
		            } 
		            else if (data.code.matches("601")) { //방 입장
		            	int roomID = Integer.parseInt(data.msg);
		            	System.out.println("roomID : " + roomID);
		            	//Room room = data.room;
		            	//roomID 비교해서 찾기
		            	for(int i=0; i < RoomVec.size(); i++) { 
		            		Room roomData = (Room)RoomVec.get(i);
		            		if(roomData.id == roomID) //id같으면
		            		{
		            			//방에 사용자 입장
		            			roomData.addUser(); //인원 수 갱신
//		            			roomData.userVec.add(user); //사용자 목록 갱신
		            		
		            			//사용자 객체의 방 속성 갱신
		            			user.room = newRoom(roomData); //이래야 클라로 가는 user의 room이 제대로 잘 들어간다.
		            			
		            			room = user.room; //현재 방
		            			
		            			//게임내 플레이어 위치 설정
		            			user.loca = roomData.currentUserCount;
//		            			System.out.println(roomData.currentUserCount + "\n");
//		            			System.out.println(user + " " + user.loca + "\n");
//		            			System.out.println(data.user);
		            			
		            			//게임내 플레이어 위치 설정
		            			
		            			//사용자 클라에 방 입장 송신
		            			Data rdata = new Data(newUser(user), "601", "enterRoom");
		            			rdata.room = newRoom(roomData);
		            			WriteOneObject(rdata);
		            			
		            			//모든 사용자에게 방 인원이 추가됐음을 알리는 데이터 송신 "601"
		            			System.out.println("aa: " + data.code);
		            			data.room = newRoom(roomData);
		            			WriteAllObject(data);
		            			break;
		            			//data.user.room = room;
		            		}
		            	}
		            } else if(data.code.matches("1000")) { //현재 user 출력
		            	System.out.println(user);
		            }
		            else if (data.code.matches("602")) { //방 퇴장
		                Room room = data.room;
		                room.deleteUser(); //방에 사용자 퇴장
		            } else if (data.code.matches("700")) { //게임 방에 모든 플레이어 화면 갱신
		            	//같은 방에 있는 사용자의 UserService
		            	Vector<UserService> sameRoomUs = new Vector<UserService>();
		            	//같은 방에 있는 사용자 찾아 sameRoomUs에 저장
		            	for(int i=0; i < user_vc.size(); i++) { 
		            		UserService usData = (UserService)user_vc.get(i);
		            		if(usData.room!=null) { //방이 있는 UserService만 판단
			            		if(usData.room.id == room.id) {
			            			sameRoomUs.add(usData);
			            		}
		            		}
		            	}
		            	//같은 방에 있는 사용자마다 자신의 정보를 송신
		            	for (int i=0;i<sameRoomUs.size();i++) {
		            		UserService myUsData = (UserService)sameRoomUs.get(i);
		            		for (int j=0;j<sameRoomUs.size();j++) {
		            			UserService sendUs = (UserService)sameRoomUs.get(j);
			            		Data sendData = new Data(newUser(myUsData.user), "700", "plz"); //새로 Data 만들어야 잘 넘어감!
			            		System.out.println(sendData.user.name+ " " + sendData.user.loca +"\n");
			            		sendUs.WriteOneObject(sendData);
			            	}
		            	}
		            } else if (data.code.matches("701")) { //게임 종료 알림
		                  
		            } else if (data.code.matches("801")) { //사용자 리스트 갱신(아예 새로 받기)
		            	for (int i = 0; i < user_vc.size(); i++) {
							UserService us = (UserService) user_vc.elementAt(i);
							if(us.UserName != UserName)
								WriteOneObject(new Data(us.user, "800", "userList"));
						}
		            }
				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
						ois.close();
						oos.close();
						client_socket.close();
						Logout(); // 에러가난 현재 객체를 벡터에서 지운다
						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝
			} // while
		} // run
	}

}
