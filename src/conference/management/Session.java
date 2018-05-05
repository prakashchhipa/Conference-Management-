package conference.management;

import java.util.Map;
import java.util.TreeMap;

public class Session {
	private Map<Integer, Talk> talks;

	public Session() {
		talks = new TreeMap<Integer, Talk>();
	}

	public Session(Map<Integer, Talk> talks) {
		this.talks = talks;
	}

	/**
	 * @return the talks
	 */
	public Map<Integer, Talk> getTalks() {
		return talks;
	}

	/**
	 * @param talks
	 *            the talks to set
	 */
	public void setTalks(Map<Integer, Talk> talks) {
		this.talks = talks;
	}
	
	@Override
	public String toString(){
		return "\n"+talks;
	}
}
