import java.awt.event.MouseEvent;

public class Data {
	String code; //프로토콜(정답알림은 프로토콜만으로)
	User user; //사용자
	String message; //메세지
	Room room; //방
	int time; //남은 시간(해당 라운드)
	MouseEvent mouse_e; //마우스 이벤트
}
