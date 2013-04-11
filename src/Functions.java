
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
		
		//  5 - [# of errors]
		
		// Error 1: # of sentences with no verb (can lose up to 3 points)
		int error1=0;
		for (int i=0; i<taggedWords.length; i++) {
			int verbCount = 0;
			
			for (int j=0; j<taggedWords[i].size(); j++ ) {
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				if (POS.charAt(0) == 'V')
					verbCount++;
			}
			
			if (verbCount==0)
				error1++;
			//String temp = taggedWords[i];
		}
		if (error1>0)
			errors+= Math.min(error1, 3);
		
		// Error 2: Incorrect use of ING - lose point if preceded by noun or pronoun (up to 2 points)
		int error2=0;
		
		for (int i=0; i<taggedWords.length; i++) {
			
			for (int j=1; j<taggedWords[i].size(); j++ ) {
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				String POSprev = TagWord.getPOS(taggedWords[i].get(j-1).toString());
				
				if (POS.equals("VBG") && (POSprev.equals("NN") || POSprev.equals("NNP") || POSprev.equals("PRP"))) 
					error2++;
			}
			
		}
		if (error2>0)
			errors+= Math.min(error2, 2);
		
		// Error 3: Missing auxiliary - no 'do' before 'not'
		int error3=0;
		
		for (int i=0; i<taggedWords.length; i++) {
			
			for (int j=1; j<taggedWords[i].size(); j++ ) {
				String word = TagWord.getWord(taggedWords[i].get(j).toString());
				String POSprev = TagWord.getPOS(taggedWords[i].get(j-1).toString());
				
				if (word.equals("not") && (POSprev.charAt(0)!='V')) 
					error3++;
			}
			
		}
		if (error3>0)
			errors++;
		
		// Random errors: (i) VBP+VBN
				int error4=0, error5=0, error6=0, error7=0, error8=0;
				
				for (int i=0; i<taggedWords.length; i++) {
					
					for (int j=1; j<taggedWords[i].size(); j++ ) {
						String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
						String POSprev = TagWord.getPOS(taggedWords[i].get(j-1).toString());
						String word = TagWord.getWord(taggedWords[i].get(j).toString());
						String wordprev = TagWord.getWord(taggedWords[i].get(j-1).toString());
						
						// (i) VBP+VBN
						if (POS.equals("VBN") && (POSprev.equals("VBP"))) 
							error4++;
						
						// (ii) VBP + VB!G - e.g., AM HAVE
						if (POS.charAt(0)=='V' && !POS.equals("VBG") && (POSprev.equals("VBP"))) 
							error5++;
						
						// (iii) BECAUSE + IS
						if (wordprev.toUpperCase().equals("BECAUSE") && POS.equals("VBZ")) 
							error6++;
						// (iv) IN + NN
						if (POS.equals("NN") && POSprev.equals("IN")) 
							error7++;
						// (v) TO + VBG
						if (POS.equals("NN") && POSprev.equals("TO")) 
							error8++;
						// (vi) IN + VB!G
						if (POS.charAt(0)=='V' && !POS.equals("VBP") && POSprev.equals("IN")) 
							error8++;
						// (vii) VBP + VBD (I'm came)
						if (POS.equals("VBD") && POSprev.equals("VBP")) 
							error8++;
					}
					
				}
				if (error4>0)
					errors++;
				if (error5>0)
					errors++;
				if (error6>0)
					errors++;
				if (error7>0)
					errors++;
				if (error8>0)
					errors++;
		
		
		return Math.max(1, score-errors);
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
