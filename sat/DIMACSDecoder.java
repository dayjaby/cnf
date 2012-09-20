package sat;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DIMACSDecoder {

	String encoding;

	Map<String, Boolean> results = new HashMap<String, Boolean>();
	Map<String, String> mappedVars = new HashMap<String, String>();
	int vars = 0;
	int clauses = 0;

	public void decodeSATOutput(String s) {
		StringTokenizer lines = new StringTokenizer(s, "\n");
		while (lines.hasMoreTokens()) {
			StringTokenizer line = new StringTokenizer(lines.nextToken(), " ");
			while (line.hasMoreTokens()) {
				String token = line.nextToken();
				if (token.charAt(0) != '-')
					results.put(token, true);
				else
					results.put(token.substring(1), false);
			}
		}
	}

	public void decodeMapping(String s) {
		StringTokenizer lines = new StringTokenizer(s, "\n");
		while (lines.hasMoreTokens()) {
			StringTokenizer line = new StringTokenizer(lines.nextToken(), " ");
			String key = "";
			String val = "";
			if (line.hasMoreTokens()) key = line.nextToken();
			if (line.hasMoreTokens()) val = line.nextToken();
			if(key.length()>0 && val.length()>0) {
				mappedVars.put(key, val);
			}
		}
	}

	public boolean getValue(Variable var) {
		//System.out.println("Look for: " + var.getName());
		return results.get(mappedVars.get(var.getName()));
	}

}
