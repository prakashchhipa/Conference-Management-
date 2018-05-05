package conference.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class ConferenceScheduler {

	/**
	 * @param args
	 */
	// List<Track> tracks = new ArrayList<Track>();
	List<Session> scheduledSessions = new ArrayList<Session>();

	public void scheduleConference() {
		try {

			List<String> talkDetails = TalkParser
					.readInputFile("D:\\pro\\ConferenceManagement\\input.txt");
			Conference conference = new Conference();
			Map<Integer, Set<List<Integer>>> combinationMap = null;
			if (null != talkDetails && 0 < talkDetails.size()) {
				Map<Integer, Talk> talkList = conference
						.extractTalks(talkDetails);
				Map<Integer, Talk> talkListReference = new TreeMap<Integer, Talk>();
				talkListReference.putAll(talkList);
				int taskListInitialLength = talkList.size();

				while (null != talkListReference && 0 < talkListReference.size()) {
					combinationMap = new HashMap<Integer, Set<List<Integer>>>();

					talkListReference = extractSessions(conference,
							talkListReference, combinationMap);

					if (null != talkListReference && taskListInitialLength == talkListReference.size()) {
						List<Integer> listToRefer = getBestSuitable(
								combinationMap, talkListReference);

						if (null != listToRefer) {
							Map<Integer, Talk> scheduledTalks = new TreeMap<Integer, Talk>();
							Session session = new Session();

							for (Integer talkId : listToRefer) {
								scheduledTalks
										.put(talkId, talkList.get(talkId));
								talkListReference.remove(talkId);
							}
							session.setTalks(scheduledTalks);
							scheduledSessions.add(session);
							taskListInitialLength = talkListReference.size();
						}
					}

					combinationMap = null;
				}

				//null != talkListReference(talkList);
				// System.out.println(conference.populateTrack(talkList).size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<Integer> getBestSuitable(
			Map<Integer, Set<List<Integer>>> combinationMap,
			Map<Integer, Talk> lisOfTalk) {
		int sumOfDuration = 0, diff = 10000, key = -10;
		List<Integer> listToRefer = null;

		for (Entry<Integer, Set<List<Integer>>> entry : combinationMap
				.entrySet()) {

			for (List<Integer> listOfCombination : entry.getValue()) {
				for (Integer combinationPair : listOfCombination) {
					sumOfDuration += lisOfTalk.get(combinationPair)
							.getDuration();

				}
				if (180 < sumOfDuration && sumOfDuration < 240) {
					if (diff > 240 - sumOfDuration) {
						diff = 240 - sumOfDuration;
						listToRefer = listOfCombination;
						key = entry.getKey();
					}
				} else if (180 > sumOfDuration) {
					if (diff > 180 - sumOfDuration) {
						diff = 180 - sumOfDuration;
						listToRefer = listOfCombination;
						key = entry.getKey();
					}

				}
				diff = 10000;
			}

			//System.out.println(entry.getKey() + " " + entry.getValue());
		}

		return listToRefer;

	}

	private Map<Integer, Talk> extractSessions(Conference conference,
			Map<Integer, Talk> talkList,
			Map<Integer, Set<List<Integer>>> storageMap) {

		List<Integer> talkIds;
		if (2 == talkList.size()) {
			talkIds = new ArrayList<Integer>();
			int totalDuration = 0;
			for (Entry<Integer, Talk> entry : talkList.entrySet()) {
				talkIds.add(entry.getKey());
				totalDuration += entry.getValue().getDuration();
			}
			
			if(240 < totalDuration){
				List<Session> sessions = new ArrayList<Session>();
				for (Entry<Integer, Talk> entry : talkList.entrySet()) {
					Session session = new Session();
					Map<Integer, Talk> map = new TreeMap<Integer, Talk>();
					map.put(entry.getKey(), entry.getValue());
					session.setTalks(map);
					sessions.add(session);
					scheduledSessions.addAll(sessions);
				}	
			}
			else if(180 < totalDuration){
				Session session = new Session();
				session.setTalks(talkList);
				scheduledSessions.add(session);
				
			}
			else{
				Session session = new Session();
				session.setTalks(talkList);
				scheduledSessions.add(session);
				
			}
			return null;
		}
		for (int i = 2; i < talkList.size() - 1; i++) {

			talkIds = new ArrayList<Integer>();
			for (Entry<Integer, Talk> entry : talkList.entrySet()) {
				talkIds.add(entry.getKey());
			}
			Set<List<Integer>> combinationSet = conference.populateCombination(
					talkIds, i);

			for (List<Integer> combinationList : combinationSet) {
				int talkCombinationDuration = 0;
				for (Integer talkId : combinationList) {
					Talk talk = talkList.get(talkId);
					talkCombinationDuration += talk.getDuration();
				}
				Map<Integer, Talk> scheduledTalks = new TreeMap<Integer, Talk>();
				if (181 == talkCombinationDuration) {
					Session session = new Session();
					for (List<Integer> scheduledTalkIds : combinationSet) {
						for (Integer talkId : scheduledTalkIds) {
							scheduledTalks.put(talkId, talkList.get(talkId));
						}
						session.setTalks(scheduledTalks);
						scheduledSessions.add(session);
						
					}
					for (Entry<Integer, Talk> entry : scheduledTalks.entrySet()) {
						talkList.remove(entry);
					}
					// talkList.(scheduledTalks);
					System.out.println("180 : " + scheduledTalks.size());
					return talkList;
				} else if (241 == talkCombinationDuration) {
					Session session = new Session();
					for (List<Integer> scheduledTalkIds : combinationSet) {
						for (Integer talkId : scheduledTalkIds) {
							scheduledTalks.put(talkId, talkList.get(talkId));
						}
						session.setTalks(scheduledTalks);
						scheduledSessions.add(session);
						
					}
					for (Entry<Integer, Talk> entry : scheduledTalks.entrySet()) {
						talkList.remove(entry);
					}
					System.out.println("240 : " + scheduledTalks.size());
					return talkList;
				}
			}
			storageMap.put(i, combinationSet);

		}

		return talkList;
	}

	public static void main(String[] args) {
		ConferenceScheduler scheduler = new ConferenceScheduler();
		scheduler.scheduleConference();
		System.out.println(scheduler.scheduledSessions);
	}
}
