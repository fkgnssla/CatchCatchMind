import java.io.Serializable;
import java.util.Vector;

public class Room implements Serializable {
	private static final long serialVersionUID = 1L;
	long id; //방 번호
	String title; //방 제목
	String mode; //게임모드
	User admin; //방장
	int currentUserCount = 1; //현재 인원 수
	int maxUserCount; //최대 인원 수
	String status; //현재 상태(대기중, 게임중)
	boolean allReady = false; //모두 준비했는지
	Vector<User> userVec = new Vector<User>(); //방에 있는 사용자
	
	//사용자 입장
	public void addUser() {
		currentUserCount+=1;
	}
	//사용자 퇴장
	public void deleteUser() {
		currentUserCount-=1;
	}
	
	public Room(long id, String title, String mode, User admin) {
		this.id = id;
		this.title = title;
		this.mode = mode;
		this.admin = admin;
		if(mode.equals("3인전")) maxUserCount = 3;
		else if(mode.equals("4인전")) maxUserCount = 4;
		this.status = "대기중";
	}
}