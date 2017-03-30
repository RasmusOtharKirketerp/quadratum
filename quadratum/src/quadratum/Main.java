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
		t.setFromX(2);
		t.setFromY(2);
		t.setToX(3);
		t.setToY(3);
		t.setRes(4);
		t.setTeamId(t1.getTeamId());
		t.validate();
		
		
			
		b.print2console();
		//for (int i = 0; i < 10; i++) {
			b.doTurn(t);
			b.print2console();
			
		//}
		
		
	  System.out.println("Ending Quadratum");
	}

}
