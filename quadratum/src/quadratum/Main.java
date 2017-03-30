package quadratum;

public class Main {
	
	static Board b = new Board(5,5);
	
	static Team t1 = new Team();
	
	
	public static void main(String[] args) {
		t1.setTeamId(1);
		t1.setTeamName("Rex");
		b.setPlayerCount(1);
		b.addPlayer(t1);
		
		b.setOwner(1, 2, 2);
		
		Turn t = new Turn();
		Turn.setFromX(2);
		Turn.setFromY(2);
		Turn.setToX(3);
		Turn.setToY(3);
		Turn.setRes(4);
		Turn.setTeamId(t1.getTeamId());
		
		
			
		b.print2console();
		//for (int i = 0; i < 10; i++) {
			b.doTurn(t);
			b.print2console();
			
		//}
		
		
	  System.out.println("Ending Quadratum");
	}

}
