import java.util.Scanner;

public class PassiveGame {
	public static void main(String[] args) {

/*		
		Charact hong = new Charact();
		hong.name="홍길동";
		hong.getSward();
		hong.printItem();
		
		Charact kim = new Charact();
		kim.name="김길동";
		kim.getSward();
		kim.printItem();
		
		hong.attack(kim); 홍길동이 김길동을 공격
*/
		
		Scanner scn = new Scanner(System.in);
		int size = 3;
		Charact[] chars = new Charact[size];
		int cnt = 0;
		
		while(true) {
			System.out.println("캐릭터 이름을 입력하세요.");
			String name = scn.nextLine();
			Charact c = new Charact();
			c.name = name;
			c.getSward();
		
			if(size == cnt) {
				//배열 크기 증가
				Charact[] tmp = new Charact[size * 2];
				size *= 2;
			
				for(int i = 0; i<chars.length; i++) {
					tmp[i] = chars[i];
				}
			
				chars = tmp;
			}
		
			chars[cnt] = c;
			cnt++;
		
			System.out.println(c.name + "캐릭터를 생성하였습니다.");
			System.out.println("추가로 생성하려면 c를 입력하고, 생성을 멈추시려면 x를 입력하시오.");
			String sw = scn.nextLine();
		
			if(sw.equalsIgnoreCase("x")) {
				break;
			
			} else if(sw.equalsIgnoreCase("c")){
				continue;
			
			} else {
				System.out.println("잘못된 키를 입력하셨습니다. 게임을 종료합니다.");
				System.exit(0);
			}
		}//while end(1)
		
		//System.out.println(Arrays.toString(chars));
		
		while(true) {
			System.out.println("공격할 캐릭터와 방어할 캐릭터의 이름을 쓰세요. (게임을 종료하려면 \"종료\" 라고 입력하세요.)");
			System.out.println("공격할 캐릭터");
			String attName = scn.nextLine();
		
			if(attName.equals("종료")) {
				break;
			}
		
			System.out.println("방어할 캐릭터");
			String depName = scn.nextLine();
		
			if(depName.equals("종료")) {
				break;
			}
		
			int attIndex = -1; /* [보충필요] attIndex를 -1로 설정한 이유? 배열은 0부터 시작하므로?
								attIndex 이하의 목적은 배열 안에 존재하는 캐릭터를 찾는 것, (배열은 0부터 시작하므로)
								가장 처음인 0부터 공격할 캐릭터가 나올 수 있음을 가정하고 설정한 값? */
		
			for(int i = 0; i<chars.length; i++) {
				if(chars[i].toString().equals(attName)){
					attIndex = i;
					break;
				}
			}
		
			int depIndex = -1;
			for(int i = 0; i<chars.length; i++) {
				if(chars[i].toString().equals(depName)){
					depIndex = i;
					break;
				}
			}
		
			if(attIndex == -1) {
				System.out.println("공격할 캐릭터가 존재하지 않습니다.");
				continue;
			}
		
			if(depIndex == -1) {
				System.out.println("방어할 캐릭터가 존재하지 않습니다.");
				continue;
			}
			
			if(chars[attIndex].getEnerge()<=0) {
				System.out.println(chars[attIndex] + "는 이미 죽었습니다.");
				continue;
			}
			
			if(chars[depIndex].getEnerge()<=0) {
				System.out.println(chars[depIndex] + "는 이미 죽었습니다.");
				continue;
			}
			
			chars[attIndex].attack(chars[depIndex]);
		
		}//while end(2)
		
		for(int i = 0; i<chars.length; i++) {
			if(chars[i] != null) {
				if(chars[i].getEnerge()<=0) {
					System.out.println(chars[i] + ": 죽었습니다.");
					
				} else {
					System.out.println(chars[i] + ":" + chars[i].getEnerge());
				}
			}
		}
	}//main end
}//class PassiveGame end


//아이템 설계
class ItemSward{
	private String ownerName = "";
	private String swardName = "";
	public int min = 1;
	public int max = 100;
	private final int NORMAL = 1;
	private final int SILVER = 2;
	private final int GOLD = 3;
	
	public void printSwardName() {
		System.out.println(swardName); //private로 지정해서 메소드로 한 번 호출해서 불러낸다.
	}
	
	public void getItem(String charName) {
		ownerName = charName;
		int rNumber = (int)(Math.random() * (3) + 1);
		
		//System.out.println(rNumber);
		
		switch(rNumber) {
			case NORMAL: 
				swardName = "초급칼";
				break;
				
			case SILVER:
				swardName = "중급칼";
				min = 50; 
				break;
				
			case GOLD:
				swardName = "고급칼";
				max = 200;
		}
		
		System.out.println(ownerName + " 님에게 " + swardName + " 이 지급되었습니다.");
	}
}//class ItemSward end

class Charact {
	@Override
	public String toString() {
		return name;
	}
	
	public String name = "";
	private int energe = 500;
	
	public int getEnerge() { /* [보충필요] 마우스 우클릭 > source > get***, what? private를 public으로 출력?
								원칙 : 캐릭터 에너지 표기X, 예외 설정  */
		return energe;
	}
	
	private ItemSward sward = null; //what?
	
	public void attack(Charact target) { //공격계열 캐릭터 설정? what?
		int damege = (int)(Math.random() * (sward.max - sward.min) + sward.min); //캐릭터가 공격한 데미지
		target.depend(this, target, damege);
		}
	
	public void depend(Charact source, Charact target, int demege) { //방어계열 캐릭터 설정? what?
		int rNumber = (int)(Math.random() * (50 - 1) + 1); //방어력
		if(demege-rNumber>0) { //공격캐릭터의 공격력-방어캐릭터의 방어력
				target.energe -= demege-rNumber;
			};
			
		currentState(source, target, demege-rNumber); //현재 상태 출력
		}
	
	public void currentState(Charact source, Charact target, int totalDemege) { //현재 상태
		System.out.println(target.name+" 님이"+source.name+" 님에게 공격당하였습니다.");
		if(totalDemege <= 0) {
			System.out.println(source.name + " 님의 데미지가 무효화되었습니다.");
		
		} else {
			System.out.println(source.name + " 님이 데미지 :" + totalDemege);
		
		}
			System.out.println(source.name +" 님이" + totalDemege +" (으)로 공격하여");
			System.out.println(target.name +" 님의 에너지가" + target.energe +" 만큼 남았습니다.");
		}
	
	public void getSward() {
		sward = new ItemSward();
		sward.getItem(name);
	}
		
	public void printItem() { //보유한 아이템 이름 호출
		sward.printSwardName();
	}
	
	public ItemSward getS() {
		return sward;
	}
}//class charact end