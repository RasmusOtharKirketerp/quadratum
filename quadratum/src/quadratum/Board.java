package quadratum;

import java.awt.Color;

public class Board {

	// private
	// Board
	static Quadratum[][] board;
	int sizeX;
	int sizeY;
	// Team
	static Team[] players;
	static int countTeams;

	// Constructor
	public Board(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		board = new Quadratum[sizeX][sizeY];
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				board[x][y] = new Quadratum(x, y, 50, 0);
				// board[x][y].print2Console();
			}
		}
		System.out.println("New board - Welcome to Quadratum!");
	}

	public int getOwner(int bx, int by) {
		return board[bx][by].getTeamId();
	}
	
	public Color getColor(int teamId){
		Color retCol = Color.black;
		if (teamId == 0)
			retCol = Color.black;
		if (teamId == 1)
			retCol = Color.green;
		if (teamId == 2)
			retCol = Color.BLUE;
		if (teamId == 3)
			retCol = Color.gray;
		
		return retCol;
			
	}
	

	public void setOwner(int teamId, int bx, int by) {
		board[bx][by].setTeamId(teamId);
	}

	public void handleResFrom(Turn t) {
		int fx = t.getFromX();
		int fy = t.getFromY();

		// extract res from FROM CELL if player own it
		if (getOwner(fx, fy) == t.getTeamId()) {
			board[fx][fy].substractRes(t.getRes());
		} else {
			// play has moved resources from a cell he do not own
			Thread.dumpStack();
			System.exit(929);
		}

	}

	public void handleResTo(Turn t) {
		int x = t.getToX();
		int y = t.getToY();
		int res = t.getRes();
		int bid = board[x][y].getTeamId();
		int bres = board[x][y].getRes();

		// Add res to TO CELL if team owns it
		if (getOwner(x, y) == t.getTeamId()) {
			board[x][y].addRes(res);
		} else {
			// play has moved resources to a cell he do not own
			if (bid == 0) // no owner
			{
				board[x][y].substractRes(res);
			}
			if (bid != 0) // enemy owner
			{
				// who has most resources
				if (res > bres) // team will overtake cell with own res -
								// enemy/board res
				{
					board[x][y].addRes(res - bres);
					board[x][y].setTeamId(t.getTeamId());
				}
				if (res == bres) // team will loose cell to no one
				{
					board[x][y].setRes(0);
					board[x][y].setTeamId(0);
				}
				if (res < bres) // team will not overtake cell but reduce enemy
								// res
				{
					board[x][y].substractRes(bres - res);
				}
			}

		}

	}

	public void doTurn(Turn t) {
		System.out.println("New Turn");
		validateTurn(t);
		handleResFrom(t);
		handleResTo(t);

	}

	private boolean validateTurn(Turn t) {
		boolean retval = false;
		int x = t.getFromX();
		int y = t.getFromY();
		int res = t.getRes();
		
		if (t.validate())
		{
			//validate if player is moving to many resources from x,y
			if (res <= board[x][y].getRes())
				retval = true;
		}
			
		if (!retval)
		{
			Thread.dumpStack();
			System.exit(999);
		}
		
		return retval;
	}

	public void setPlayerCount(int numOfTeams) {
		players = new Team[numOfTeams + 1];
		// we uses team = 0 as no one team
		countTeams = players.length + 1;
	}

	public void addPlayer(Team t) {
		players[t.getTeamId()] = t;
	}

	public void print2console() {
		for (int x = 0; x < this.sizeX; x++) {

			for (int y = 0; y < this.sizeY; y++) {
				System.out.print(" r(" + board[x][y].getRes() + ") ");
				System.out.print(" team(" + board[x][y].getTeamId() + ") ");
			}
			System.out.println();
		}

	}

}
