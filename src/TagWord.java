import java.util.ArrayList;

public class TagWord {

	String word;
	String pos;
	
	/*
	 * input a tagged word and output the word
	 */
	public static String getWord(String s) {
		ArrayList<Character> chars = new ArrayList<Character>();
		String output="";
		char c = 'a';
		int i = 0;
		while (c != '/') {
			
			c = s.charAt(i);
			if (c != '/') {
				chars.add(c);
				output+=c;

				i++;
			}
			else {
				break;
			}
		}
		//return chars.toString();
		return output;
	}
	
	/*
	 * input a tagged word and output the POS
	 */
	public static String getPOS(String s) {
		int limit = s.length();
		int start = s.indexOf("/") + 1;
		String output="";
		
		ArrayList<Character> chars = new ArrayList<Character>();
		char c = 'a';
		
		for  (int i = start; i < limit; i++) {
			c = s.charAt(i);
			chars.add(c);
			output+=c;
		}
		//return chars.toString();
		return output;
	}
}
