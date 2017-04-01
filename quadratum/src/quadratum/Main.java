package quadratum;



public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		Board b = new Board(5,5,0);
		Team t1 = new Team();
		t1.setTeamId(1);
		t1.setTeamName("Rex");
		b.setPlayerCount(2);
		b.addPlayer(t1);
		
		b.setOwner(1, 2, 2);
		b.initSetResources(2, 2, 10);
	    
		
			
		Turn t = new Turn();
		t.setFromX(2);
		t.setFromY(2);
		t.setToX(3);
		t.setToY(3);
		t.setRes(1);
		t.setTeamId(t1.getTeamId());
		t.validate();

		Turn t2 = new Turn();
		t2.setFromX(3);
		t2.setFromY(3);
		t2.setToX(3);
		t2.setToY(4);
		t2.setRes(10);
		t2.setTeamId(t1.getTeamId());
		t2.validate();
		
	
		b.print2console();
		for (int i = 0; i < 100; i++) {
			b.doTurn(t);
			b.print2console();
			if (i % 2 == 0)
				b.doTurn(t2);
			//Thread.sleep(100);
		}
		//"D:\Users\Rasmus Othar\git\quadratum\quadratum"
		b.convertJSON2File("quardratum.json");
		
	  System.out.println("Ending Quadratum");
	}

}
