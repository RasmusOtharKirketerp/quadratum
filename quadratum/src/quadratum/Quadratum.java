package quadratum;

import org.json.simple.JSONObject;

public class Quadratum {

	// private
	private long y;
	private long x;
	private long res;

	// team ID
	// team ID = 0 no one owns this
	private long teamId;

	public Quadratum(int x, int y, int res, int teamId) {
		super();
		this.x = x;
		this.y = y;
		this.res = res;
		this.teamId = teamId;
	}
	public Quadratum() {
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

	@SuppressWarnings("unchecked")
	public JSONObject convertCell2JSON() {
		JSONObject cellObj = new JSONObject();
		cellObj.put("X", this.x);
		cellObj.put("Y", this.y);
		cellObj.put("res", this.res);
		cellObj.put("teamId", this.teamId);
		return cellObj;

	}

	public Quadratum(JSONObject JSONCellObj) {
		this.x = (long) JSONCellObj.get("X");
		this.y = (long) JSONCellObj.get("Y");
		this.res = (long) JSONCellObj.get("res");
		this.teamId = (long) JSONCellObj.get("teamId");
	}

	// get and set
	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long tid) {
		this.teamId = tid;
	}

	public long getX() {
		return x;
	}

	public long getY() {
		return y;
	}

	public long getRes() {
		return res;
	}

	public void setRes(long l) {
		this.res = l;
	}

	public void addRes(long l) {
		this.res += l;
	}

	public void substractRes(long l) {
		this.res -= l;
	}

}
