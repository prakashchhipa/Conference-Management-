package conference.management;

public class Talk implements Comparable<Talk> {

	private int id;
	private String title;
	private int duration;

	Talk(int id, String title, int duration) {
		this.id = id;
		this.title = title;
		this.duration = duration;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public int compareTo(Talk task) {
		return task.duration - this.duration;
	}

	@Override
	public String toString() {
		return "\n"+id+" : " + title + " : " + duration + "min";
	}
}
