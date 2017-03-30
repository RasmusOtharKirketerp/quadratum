package quadratum;

public class Board {

	// private
	//   Board
	static Quadratum[][] board;
	static int sizeX;
	static int sizeY;
	//   Team
	static Team[] players;
	static int countTeams;


	// Constructor
	public Board(int sizeX, int sizeY) {
		Board.sizeX = sizeX;
		Board.sizeY = sizeY;
		board = new Quadratum[sizeX][sizeY];
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				board[x][y] = new Quadratum(x, y, 50, 0);
				// board[x][y].print2Console();
			}
		}
		System.out.println("New board - Welcome to Quadratum!");
	}
	
	public int getOwner(int teamId, int bx, int by){
		return board[bx][by].getTeamId();
	}
	public void setOwner(int teamId, int bx, int by){
		board[bx][by].setTeamId(teamId);
	}
	
	public void handleResFrom(Turn t){ 
		int fx = Turn.getFromX();
		int fy = Turn.getFromY();
		
		// extract res from FROM CELL if player own it
		if ( getOwner(Turn.getTeamId(), fx, fy) == Turn.getTeamId()) {
			board[fx][fy].substractRes(Turn.getRes());
		}
		else
		{
			// play has moved resources from a cell he do not own
			// TODO
			Thread.dumpStack();			
		}
		
		
		
	}
	public void handleResTo(Turn t){
		int x    = Turn.getToX();
		int y    = Turn.getToY();
		int res  = Turn.getRes();
		int tid  = Turn.getTeamId();
		int bid  = board[x][y].getTeamId(); 
		int bres = board[x][y].getRes();
		
		
		// Add res to TO CELL if team owns it
		if (getOwner(tid, x, y) == Turn.getTeamId()) {
			board[x][y].addRes(res);
		}
		else
		{
			// play has moved resources to a cell he do not own
			if (bid == 0) // no owner
			{
				board[x][y].addRes(res);
			}
			if (bid != 0) // enemy owner
			{
				// who has most resources
				if (res > bres) // team will overtake cell with own res - enemy/board res
				{
					  board[x][y].addRes(res - bres);
					  board[x][y].setTeamId(Turn.getTeamId());
				}
				if (res == bres) // team will loose cell to no one
				{
					  board[x][y].setRes(0);
					  board[x][y].setTeamId(0);
				}
				if (res < bres) // team will not overtake cell but reduce enemy res
				{
					board[x][y].substractRes(bres - res);
				}
			}
			
		}
		
		
		
	}
	
	
	public void doTurn(Turn t){
		System.out.println("New Turn");
		handleResFrom(t);
		handleResTo(t);		
	
	}
	
	public void setPlayerCount(int numOfTeams){
		players = new Team[numOfTeams+1];
		// we uses team = 0 as no one team
		countTeams = players.length+1;
	}
	
	public void addPlayer(Team t)
	{
		players[t.getTeamId()] = t;
	}


	public void print2console() {
		for (int x = 0; x < Board.sizeX; x++) {
			
			for (int y = 0; y < Board.sizeY; y++) {
				System.out.print(" r(" + board[x][y].getRes()+") ");
				System.out.print(" team(" + board[x][y].getTeamId()+") ");
			}
			System.out.println();
		}

	}

}
