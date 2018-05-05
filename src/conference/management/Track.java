package conference.management;

public class Track {
	private Session preLunchSession;
	private Session postLunchSession;

	public Track() {
		preLunchSession = new Session();
		postLunchSession = new Session();
	}

	/**
	 * @return the preLunchSession
	 */
	public Session getPreLunchSession() {
		return preLunchSession;
	}

	/**
	 * @param preLunchSession
	 *            the preLunchSession to set
	 */
	public void setPreLunchSession(Session preLunchSession) {
		this.preLunchSession = preLunchSession;
	}

	/**
	 * @return the postLunchSession
	 */
	public Session getPostLunchSession() {
		return postLunchSession;
	}

	/**
	 * @param postLunchSession
	 *            the postLunchSession to set
	 */
	public void setPostLunchSession(Session postLunchSession) {
		this.postLunchSession = postLunchSession;
	}

}
