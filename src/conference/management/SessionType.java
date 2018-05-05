package conference.management;

public enum SessionType {
	PRE_LUNCH(180), POST_LUNCH(240);
	
	private int timeLimit;
	
	private SessionType(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public int getTimeLimit(){
		return timeLimit;
	}
}
