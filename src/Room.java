
public class Room {
	long id; //방 번호
	String mode; //게임모드
	String title; //방 제목
	User admin; //방장
	int currentUserCount; //현재 인원 수
	int maxUserCount; //최대 인원 수
	String status; //현재 상태(대기중, 게임중)
	boolean allReady; //모두 준비했는지

	//사용자 입장
	public void addUser() {
		currentUserCount+=1;
	}
	//사용자 퇴장
	public void deleteUser() {
		currentUserCount-=1;
	}
}
