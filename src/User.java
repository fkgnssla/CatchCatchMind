import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	String name; //닉네임
	long victoryCount; //이긴 횟수
	long exp; //경험치
	long gold; //골드
	boolean consonantItem; //초성 아이템 유무
	boolean firstLetterItem; //첫 글자 아이템 유무
	boolean ready; //준비완료 
	Room room; //속한 방
	
	public User(String name) {
		this.name = name;
	}
}