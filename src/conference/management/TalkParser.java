package conference.management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TalkParser {
	private TalkParser() {

	}

	public static List<String> readInputFile(String file) throws IOException {
		List<String> talkDetails = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(
				new File(file)));
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() > 1) {
				talkDetails.add(line);
			}
		}
		return talkDetails;
	}
}
