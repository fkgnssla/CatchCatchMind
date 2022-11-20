import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	String name; //닉네임
	long victoryCount=0; //이긴 횟수
	long exp=0; //경험치
	long gold=0; //골드
	boolean consonantItem=false; //초성 아이템 유무
	boolean firstLetterItem=false; //첫 글자 아이템 유무
	boolean ready=false; //준비완료 
	Room room; //속한 방
	int loca=1; //플레이어 위치 (1,2,3,4)
	int score=0;

	public User(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", victoryCount=" + victoryCount + ", exp=" + exp + ", gold=" + gold
				+ ", consonantItem=" + consonantItem + ", firstLetterItem=" + firstLetterItem + ", ready=" + ready
				+ ", room=" + room + ", loca=" + loca + "]";
	}
	
	
}

