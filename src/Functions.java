
public class Functions {

	/**
	 * The following are float values for the respective parts of the score
	 * @param in1a  
	 * @param in1b
	 * @param in1c
	 * @param in1d
	 * @param in2a
	 * @param in2b
	 * @param in3a
	 * @return Float value representing the final score (after rounding).
	 */
	public static float ComputeFinalScore(	float in1a,
											float in1b, 
											float in1c, 
											float in1d, 
											float in2a, 
											float in2b, 
											float in3a) {
		
		float finalScore = (in1a + in1b + in1c + 2*in1d + in2a + 3*in2b + in3a)/10;
		finalScore = ((float) Math.round(finalScore*2)) /2 ;
		
		return finalScore;
		
	}
	
	
	/**
	 * Subscore 3a: Sentence length
	 * @param sentence
	 * @return
	 */
	public static float Subscore3a(String sentence) {
		float score=0;
		
		// Count number of delimiters: newline or period
		for (int i=0; i<sentence.length(); i++) {
			char current = sentence.charAt(i);
			if (current=='\n' || current=='.')
				score++;
		}
		score = (float) Math.min(score, 5.0);
		
		return score;
	}
	
}
