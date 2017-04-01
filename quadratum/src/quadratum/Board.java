package quadratum;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.text.DecimalFormat;
import java.util.Iterator;

public class Board {

	// private
	// Board
	private Quadratum[][] board;
	long sizeX;
	long sizeY;
	// Team
	static Team[] players;
	static int countTeams;

	// Constructors
	public Board(int sizeX, int sizeY, int startRes) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		board = new Quadratum[sizeX][sizeY];
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				board[x][y] = new Quadratum(x, y, startRes, 0);
				// board[x][y].print2Console();
			}
		}
		System.out.println("New board - Welcome to Quadratum!");
	}

	public void WriteBoardToCon() {
		int countCells = 0;
		System.out.println("WritingBoardToCon");
		System.out.println(" SizeX:" + sizeX);
		System.out.println(" SizeY:" + sizeY);
		System.out.println(" Writeing all cells on board to console...");
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				board[x][y].print2Console();
				countCells += 1;
			}
		}
		System.out.println(" Cells on board :" + countCells);

	}

	public Board() {

		System.out.println("Welcome to Quadratum...");
		System.out.println("Board loaded...");
	}

	public long getOwner(int bx, int by) {
		return board[bx][by].getTeamId();
	}

	public Color getColor(int teamId) {
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

	// handle resources methods

	// setResources
	// used when initializing start cells to owner
	public void initSetResources(int x, int y, int res) {
		board[x][y].setRes(res);
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
		long bid = board[x][y].getTeamId();
		long bres = board[x][y].getRes();
		long tid = t.getTeamId();

		// Add res to TO CELL if team owns it or no one owns it
		if (bid == tid || (bid == 0)) {
			board[x][y].addRes(res);
			board[x][y].setTeamId(tid);
		} else {
			// play has moved resources to a cell he do not own
			if (bid != 0) // enemy owner
			{
				// who has most resources
				if (res > bres) // team will overtake cell with own res -
								// enemy/board res
				{
					board[x][y].addRes(res - bres);
					board[x][y].setTeamId(tid);
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
		if (validateTurn(t)) {
			handleResFrom(t);
			handleResTo(t);
			System.out.print("Turn executed! Moved : " + t.getRes());
			System.out.print(" res from (" + t.getFromX() + "," + t.getFromY() + ") to ");
			System.out.print(" (" + t.getToX() + "," + t.getToY() + ").");
			System.out.println();
		}
		addResToAll();

	}

	private void addResToAll() {
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				if (board[x][y].getTeamId() > 0) // only if someone has the cell
					if (board[x][y].getRes() < 100) // if under 100 res
						board[x][y].setRes(board[x][y].getRes() + 1);
				;
			}
		}

	}

	private boolean validateTurn(Turn t) {
		boolean retval = false;
		int x = t.getFromX();
		int y = t.getFromY();
		int res = t.getRes();

		if (t.validate()) {
			// validate if player is moving to many resources from x,y
			if (res <= board[x][y].getRes())
				retval = true;
		}

		if (!retval) {
			System.out.println("Invalid turn, sorry...");
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
		DecimalFormat xyformat = new DecimalFormat("00");
		DecimalFormat resformat = new DecimalFormat("000");
		for (int x = 0; x < this.sizeX; x++) {

			for (int y = 0; y < this.sizeY; y++) {
				System.out.print("(" + xyformat.format(x) + "," + xyformat.format(y) + ")");
				System.out.print(" t(" + board[x][y].getTeamId() + ") ");
				System.out.print(" r(" + resformat.format(board[x][y].getRes()) + ") ");

			}
			System.out.println();
		}

	}

	@SuppressWarnings("unchecked")

	public JSONObject convertBoard2JSON() {
		JSONObject boardObj = new JSONObject();
		JSONArray listOfCells = new JSONArray();

		boardObj.put("world", "Quadratum");
		boardObj.put("sizeX", sizeX);
		boardObj.put("sizeY", sizeY);

		for (int x = 0; x < this.sizeX; x++) {
			for (int y = 0; y < this.sizeY; y++) {
				listOfCells.add(board[x][y].convertCell2JSON());
			}
		}

		boardObj.put("cells", listOfCells);

		System.out.println(boardObj);

		return boardObj;
	}

	public void convertJSON2Board(JSONObject boardObj) {
		int x;
		int y;
		
		this.sizeX = (long) boardObj.get("sizeX");
		this.sizeY = (long) boardObj.get("sizeY");
		
		board = new Quadratum[(int) sizeX][(int) sizeY];
		

		JSONArray listOfCells = (JSONArray) boardObj.get("cells");

		for (int i = 0; i < listOfCells.size(); i++) {
			Quadratum cell = new Quadratum((JSONObject) listOfCells.get(i));
			x = (int) cell.getX();
			y = (int) cell.getY();
			board[x][y] = cell;
		}

	}

	public void convertJSON2File(String filename) {

		try (FileWriter file = new FileWriter(filename)) {
			file.write(convertBoard2JSON().toJSONString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public Board(String filename) throws org.json.simple.parser.ParseException {

		JSONParser parser = new JSONParser();

		JSONObject boardObj = new JSONObject();

		JSONArray listOfCells = new JSONArray();

		try {

			Object obj = parser.parse(new FileReader("quardratum.json"));

			JSONObject jsonObject = (JSONObject) obj;

			System.out.println(jsonObject);
			System.out.println("JSON loaded from file to JSON...");

			System.out.println("Starting to parse JSON...");

			boardObj.put("sizeX", jsonObject.get("sizeX"));
			boardObj.put("sizeY", jsonObject.get("sizeY"));

			// loop array
			JSONArray cells = (JSONArray) jsonObject.get("cells");
			Iterator<String> iterator = cells.iterator();
			while (iterator.hasNext()) {
				System.out.println("Loading cells...");
				// System.out.println(iterator.next());
				listOfCells.add(iterator.next());

			}
			System.out.println("Loading cells...done!");
			boardObj.put("cells", listOfCells);
			System.out.println("Count loaded:" + listOfCells.size());

			convertJSON2Board(boardObj);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
