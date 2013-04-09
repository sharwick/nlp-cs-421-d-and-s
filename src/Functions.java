
import java.lang.Runtime;
import java.util.ArrayList;
import java.io.*;

import edu.stanford.nlp.ling.TaggedWord;

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
	
	// Call POS tagger
	// ./stanford-postagger.sh models/wsj-0-18-caseless-left3words-distsim.tagger ../essay-corpus/1.txt
	public static String runPOSTagger(String filename) {
		String command = "./stanford-postagger-full-2012-11-11/stanford-postagger.sh models/wsj-0-18-caseless-left3words-distsim.tagger ../essay-corpus/1.txt";
		
		command="./stanford-postagger-full-2012-11-11/stanford-postagger.sh models/wsj-0-18-caseless-left3words-distsim.tagger ../essay-corpus/1.txt";
		String output=null;
		
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader( new InputStreamReader(p.getInputStream()));
			output = br.toString();
			
			String line=null;
			
			int test=0;
			while( (line=br.readLine()) != null) {
				System.out.println(line);
				
				System.out.println("T"+ test++);
			}
			
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		return output;
	}

	
	
	/**
	 * Subscore 1c: Verb tense / missing verb / extra verb
	 * @param sentence
	 * @return
	 * @author Shannon Harwick
	 */
	public static float Subscore1c(Essay theEssay) {
		float score=5;
		float errors=0;  // each type of error will subtract a point
		
		ArrayList<TaggedWord>[] taggedWords = theEssay.getTaggedWords();
		
		
		
		// OR:  5 - [# of errors]
		
		// Error 1: # of sentences with no verb
		int error1=0;
		for (int i=0; i<taggedWords.length; i++) {
			int verbCount = 0;
			
			for (int j=0; j<taggedWords[i].size(); j++ ) {
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				if (POS == "VBZ")
					verbCount++;
			}
			
			if (verbCount==0)
				error1++;
			//String temp = taggedWords[i];
		}
		if (error1>0)
			errors++;
		
		return Math.max(0, score-errors);
	}	
	
	
	/**
	 * Subscore 3a: Sentence length
	 * @param sentence
	 * @return
	 * @author Shannon Harwick 
	 */
	public static float Subscore3a(Essay theEssay) {
		float score=0;
		String essayString = theEssay.getEssay();
		
		// Count number of delimiters: newline or period
		for (int i=0; i<essayString.length(); i++) {
			char current = essayString.charAt(i);
			if (current=='\n' || current=='.')
				score++;
		}
		score = (float) Math.min(score, 5.0);
		
		return score;
	}
	
}
