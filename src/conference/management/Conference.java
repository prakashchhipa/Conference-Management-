package conference.management;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Conference {
	public int calculateTotalTalkDuration(Map<Integer, Talk> talks) {
		int totalTalkDuration = 0;
		for (Entry<Integer, Talk> entry : talks.entrySet()) {
			totalTalkDuration += entry.getValue().getDuration();
		}
		return totalTalkDuration;
	}

	public int calculateTotalTracks(Map<Integer, Talk> talks) {
		int totalTracks = 0;
		int totalTalkDuration = 0;
		totalTalkDuration = calculateTotalTalkDuration(talks);
		totalTracks += totalTalkDuration
				/ (SessionType.PRE_LUNCH.getTimeLimit() + SessionType.POST_LUNCH
						.getTimeLimit());
		if (0 < totalTalkDuration
				% (SessionType.PRE_LUNCH.getTimeLimit() + SessionType.POST_LUNCH
						.getTimeLimit()))
			totalTracks += 1;
		return totalTracks;
	}

	public Set<List<Integer>> populateCombination(List<Integer> talkIds,
			int combinationSize) {
		Set<List<Integer>> combinationSet = null;

		combinationSet = Combination.getInstance().getTalkCombination(talkIds,
				combinationSize);
		return combinationSet;
	}

	public Map<Integer, Talk> extractTalks(List<String> talkDetails) {
		Map<Integer, Talk> talks = new TreeMap<Integer, Talk>();
		int i = 0;
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = talkDetails.iterator(); iterator.hasNext();) {
			String talkDetail = (String) iterator.next();
			int lastSpace = talkDetail.lastIndexOf(" ");
			String title = talkDetail.substring(0, lastSpace);
			String duration = talkDetail.substring(lastSpace + 1);
			int talkDuration = 0;
			if (duration.endsWith("min")) {
				talkDuration = Integer.parseInt(duration.substring(0,
						duration.indexOf("min")));
			} else if (duration.endsWith("lightning")) {
				if (0 == duration.indexOf("lightning")) {
					talkDuration = 5;
				} else {
					talkDuration = 5 * Integer.parseInt(duration.substring(0,
							duration.indexOf("lightning")));
				}
			}
			talks.put(i, new Talk(i, title, talkDuration));
			i++;
		}
		// calculateTotalTracks(talks);
		return talks;
	}

	private Map<Integer, Talk> populateSession(Map<Integer, Talk> talks,
			int timeLimit) {
		Map<Integer, Talk> session = new TreeMap<Integer, Talk>();
		int totalDuration = 0;
		for (Entry<Integer, Talk> entry : talks.entrySet()) {
			if (totalDuration + entry.getValue().getDuration() < timeLimit) {
				totalDuration += entry.getValue().getDuration();
				session.put(entry.getKey(), entry.getValue());
			} else if (totalDuration + entry.getValue().getDuration() == timeLimit) {
				totalDuration += entry.getValue().getDuration();
				session.put(entry.getKey(), entry.getValue());
				break;
			}
		}
		return session;
	}

	public List<Track> populateTrack(Map<Integer, Talk> talks)
			throws IOException {
		List<Track> tracks = new ArrayList<Track>();
		int totalTracks = calculateTotalTracks(talks);
		for (int i = 0; i < totalTracks; i++) {
			Track track = new Track();

			Session session = new Session();
			session.setTalks(populateSession(talks,
					SessionType.PRE_LUNCH.getTimeLimit()));
			// System.out.println("Morning Session : " + session.getTalks());
			for (Entry<Integer, Talk> entry : talks.entrySet()) {
				talks.remove(entry);
			}

			track.setPreLunchSession(session);

			session = new Session();
			session.setTalks(populateSession(talks,
					SessionType.POST_LUNCH.getTimeLimit()));
			// System.out.println("Evening Session : " + session.getTalks());
			for (Entry<Integer, Talk> entry : talks.entrySet()) {
				talks.remove(entry);
			}
			track.setPostLunchSession(session);

			tracks.add(track);
		}

		return tracks;
	}
}
