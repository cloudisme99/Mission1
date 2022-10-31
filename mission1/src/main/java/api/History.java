package api;

import java.sql.Timestamp;
import java.util.Date;

public class History {

	private int HISTORY_ID;
	private double LAT;
	private double LNT;
	private Timestamp SEARCH_DT;
	private String time;
	

	public int getHISTORY_ID() {
		return HISTORY_ID;
	}

	public void setHISTORY_ID(int hISTORY_ID) {
		HISTORY_ID = hISTORY_ID;
	}

	public double getLAT() {
		return LAT;
	}

	public void setLAT(double lAT) {
		LAT = lAT;
	}

	public double getLNT() {
		return LNT;
	}

	public void setLNT(double lNT) {
		LNT = lNT;
	}
//
//	public Date getSEARCH_DT() {
//		return SEARCH_DT;
//	}
//
//	public void setSEARCH_DT(Date sEARCH_DT) {
//		SEARCH_DT = sEARCH_DT;
//	}

	public Timestamp getSEARCH_DT() {
		return SEARCH_DT;
	}

	public void setSEARCH_DT(Timestamp sEARCH_DT) {
		SEARCH_DT = sEARCH_DT;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
