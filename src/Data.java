import java.awt.event.MouseEvent;
import java.io.Serializable;

public class Data implements Serializable{
	private static final long serialVersionUID = 1L;
	String code; //프로토콜(정답알림은 프로토콜만으로)
	User user; //사용자
	String msg; //메세지
	Room room; //방
	int time; //남은 시간(해당 라운드)
	MouseEvent mouse_e; //마우스 이벤트
	
	public Data(User user, String code, String msg) {
		this.user = user;
		this.code = code;
		this.msg = msg;
	}
}
