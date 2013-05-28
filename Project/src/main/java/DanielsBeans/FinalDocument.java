package DanielsBeans;

import java.io.Serializable;

public class FinalDocument implements Serializable{

	private static final long serialVersionUID = 4477855769404674683L;
	
	
	private boolean MeetsBusinessGoals;

	public boolean doesMeetBusinessGoals() {
		return MeetsBusinessGoals;
	}

	public void setMeetsBusinessGoals(boolean meetsBusinessGoals) {
		MeetsBusinessGoals = meetsBusinessGoals;
	}
	
}
