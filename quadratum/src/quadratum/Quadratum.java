package quadratum;

public class Quadratum {

	// private
	private int x, y;
	private int res;

	// team ID
	// team ID = 0 no one owns this
	private int teamId;

	public Quadratum(int x, int y, int res, int teamId) {
		super();
		this.x = x;
		this.y = y;
		this.res = res;
		this.teamId = teamId;
	}

	// *******************************************************
	// * public *
	// *******************************************************

	public void print2Console() {
		System.out.print("(");
		System.out.print(this.x);
		System.out.print(",");
		System.out.print(this.y + ")");
		System.out.print(" Ressources = " + this.res);
		// new Line
		System.out.println();

	}

	// get and set
	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRes() {
		return res;
	}

	public void setRes(int res) {
		this.res = res;
	}
	
	public void addRes(int amount)
	{
		this.res += amount;
	}
	
	public void substractRes(int amount)
	{
		this.res -= amount;
	}

}
