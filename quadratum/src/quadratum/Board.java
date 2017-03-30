package quadratum;

public class Board {
	
	//private
	static Quadratum[][] board;
	
	//public
	public static Quadratum[][] getBoard() {
		return board;
	}

	public static void setBoard(Quadratum[][] board) {
		Board.board = board;
	}
	
	
	public Board(int sizeX, int sizeY)
	{
		board = new Quadratum[sizeX][sizeY];
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				board[x][y] = new Quadratum(x,y,10);			
			}
		}
	}
	

}
