
import java.lang.Runtime;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

import edu.stanford.nlp.ling.TaggedWord;
import java.util.Arrays.*;

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
	 * Subscore 1a: word order
	 * @param Essay
	 * @return float
	 * @author Dave
	 */
	public static float Subscore1a(Essay theEssay) {
		
		float score = 5;
		
		// obtain array of tagged words from essay
		ArrayList<TaggedWord>[] taggedWords = theEssay.getTaggedWords();
		
		// taggedWords[0].toString() -> [My/PRP$, sister/NN, live/VBP, in/IN, the/DT, U.S./NNP, ...
		// taggedWords[0].get(0).toString() -> My/PRP$
		
		// We will check for the following errors
		int error1 = 0; // verb found at beginning of sentence
		int error2 = 0; // adj must precede a noun
		int error3 = 0; // verb verb found consecutively
		int error4 = 0; // cannot have IN VB consecutively
		int error5 = 0; // adverb must follow verb
		int error6 = 0; // verb at end of sentence
		int error7 = 0; // IN/CD at beginning of sentence
		int error8 = 0; // NN NN found consecutively
		
		// check error1
		for (int i = 0; i < taggedWords.length; ++i) {
			
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				if (POS.charAt(0) == 'V') { // verb found
					if (j == 0) {
						error1++;
					}
					else {
						
						String POS2 = TagWord.getPOS(taggedWords[i].get(j-1).toString());
						if (POS2.charAt(0) == '.') {
							error1++;
						}
					}
				}
			}
		}
		
		if (error1 > 0) {
			score -= 1;
		}
		
		// check error2
		for (int i = 0; i < taggedWords.length; ++i) {	
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				
				if (POS.charAt(0) == 'J') { // adjective found
					
					if ((j+1) < taggedWords[i].size()) {
						
						String POS2 = TagWord.getPOS(taggedWords[i].get(j+1).toString());
						
						if(POS2.charAt(0) != 'N') {
							//++error2;
						}
					}
				}
			}
		}
		
		if (error2 > 0) {
			score -= 1;
		}
		
		// check error3
		for (int i = 0; i < taggedWords.length; ++i) {
			
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				if (POS.charAt(0) == 'V') { // verb found
					
					if (j != 0) {
						
						String POS2 = TagWord.getPOS(taggedWords[i].get(j-1).toString());
						
						if (POS2.charAt(0) == 'V') {
							error3++;
						}
						
					}
					
				}
				
			}
			
		}
		
		if (error3 > 0) {
			score -= 1;
		}
		
		// check error 4 
		for (int i = 0; i < taggedWords.length; ++i) {
			
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				if (POS.charAt(0) == 'V') { // verb found
					
					if (j != 0) {
						String POS2 = TagWord.getPOS(taggedWords[i].get(j - 1).toString());
						
						if (POS2.charAt(0) == 'I') {
							++error4;
						}
					}
					
				}
				
			}
			
		}
		
		for (int i = 0; i < error4; ++i) {
			score -= 1;
		}
		
		// check error5
		for (int i = 0; i < taggedWords.length; ++i) {
			
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
		
				if (POS.charAt(0) == 'R') { // adverb found
					
					if ((j+1) < taggedWords[i].size()) {
						
						String POS2 = TagWord.getPOS(taggedWords[i].get(j+1).toString());
						
						if(POS2.charAt(0) != 'V') {
							//++error5;
						}
						
					}
					
				}
					
			
			}
			
		}
		if (error5 > 0) {
			score -= 1;
		}
			
		// error6
		for (int i = 0; i < taggedWords.length; ++i) {
			
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				if (POS.charAt(0) == 'V') { // verb found
					
					if (j == (taggedWords[i].size() - 1)) {
						error6++;
					}
				}
			}
		}
		for (int i = 0; i < error6; ++i) {
			score -= 1;
		}
		
		// error7
		for (int i = 0; i < taggedWords.length; ++i) {
			
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				if (POS.equals("IN") || POS.equals("CD")) {
					if (j == 0 || (j == taggedWords[i].size() - 1)) {
						error7++;
					}
				}
			}
		}
		for (int i = 0; i < error7; ++i) {
			score -= 1;
		}
		
		// error8
		for (int i = 0; i < taggedWords.length; ++i) {
			
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				
				if (POS.equals("NN")) {
					if ((j+1) < taggedWords[i].size()) {
						String POS2 = TagWord.getPOS(taggedWords[i].get(j+1).toString());
						
						if (POS2.equals("NN")) {
							++error8;
						}
					}
				}

			}
		}
		for (int i = 0; i < error8; ++i) {
			score -= 1;
		}
		
		return Math.max(0, score);
	}
	
	/**
	 * Subscore 1b: Subject-verb agreement
	 * @param Essay
	 * @return float
	 * @author Dave
	 */
	public static float Subscore1b(Essay theEssay) {
		
		float score = 5;
		
		// obtain array of tagged words from essay
		ArrayList<TaggedWord>[] taggedWords = theEssay.getTaggedWords();
		
		// count errors
		int errorCount = 0;
		
		boolean multipleSubjects = false;
		
		// check for to be verb errors (specifically if the subject is plural with AND, then check for singular verb errors)
		// Mike AND John is here (error)
		for (int i = 0; i < taggedWords.length; i++) {
			multipleSubjects = false;
			
			
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				// and is encountered as subject
				String word = TagWord.getWord(taggedWords[i].get(j).toString());
				
				if (word.equals("and")) {
					multipleSubjects = true;
				}
				
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				
				if (POS.charAt(0) == 'V') {
					
					String word2 = TagWord.getWord(taggedWords[i].get(j).toString());
					
					// the following is singular 
					if (POS.equals("VBZ") || word2.equals("live") || word2.equals("is") ||  word2.equals("am") || word2.charAt(word.length() - 1) == 's') {
						
						if (multipleSubjects) {
							++errorCount;
						}	
					}
					
					multipleSubjects = false;
				}
			}
		}
		
		// other verb agreement (i.e. Error: We likes ...)
		// check for a plural subject (and I) and look for error where following verb has 's' at the end
		// Different than first error checking because not looking for AND keyword in subject here
		for (int i = 0; i < taggedWords.length; ++i) {
		
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				String word = TagWord.getWord(taggedWords[i].get(j).toString());
				
				// plural words and "I"
				if (word.equals("We") || word.equals("They") || word.equals("we") || word.equals("they") || word.equals("I")) {
					
					if ((j+1) < taggedWords[i].size()) {
						
						String POS = TagWord.getPOS(taggedWords[i].get(j + 1).toString());
						
						// Verb found
						if (POS.equals("VBZ")) {
							++errorCount;
						}
					}
				}	
			}
		}
		
		// check for this error
		// I am(VBP) came(VBD)
		for (int i = 0; i < taggedWords.length; ++i) {
			
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				
				if ((j+1) < taggedWords[i].size()) {
					String POS2 = TagWord.getPOS(taggedWords[i].get(j + 1).toString());
					
					if (POS.equals("VBP") && POS2.equals("VBD")) {
						errorCount++;
					}
				}
			}
		}
		
		// now check for no verbs present at all in sentence
		for (int i = 0; i < taggedWords.length; ++i) {
			boolean getVerb = false;
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				
				if (POS.charAt(0) == 'V') {
					getVerb = true;
				}	
			}
			if (!getVerb) {
				errorCount++;
			}
		}
		
		// Try to resolve some errors from run ons
		// Cannot have PRP JJ (i.e. She happy ...) because verb is not present
		// Cannot have PRP IN (i.e. She in ...)
		for (int i = 0; i < taggedWords.length; ++i) {
			
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				
				if ((j+1) < taggedWords[i].size()) {
					String POS2 = TagWord.getPOS(taggedWords[i].get(j + 1).toString());
					
					if ((POS.equals("PRP") && POS2.equals("JJ")) || (POS.equals("PRP") && POS2.equals("IN"))) {
						errorCount++;
					}
				}
			}
		}
		
		score = 5 - errorCount;
		
		return Math.max(score, 0);
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
		
		// Various errors: 
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
	 * Subscore 1d: Sentence formation
	 * @param theEssay	An Essay object
	 * @return
	 * @author Shannon Harwick
	 */
	public static float Subscore1d(Essay theEssay) {
		float score=5;
		float errors=0;  // each type of error will subtract a point
		ArrayList<TaggedWord>[] taggedWords = theEssay.getTaggedWords();	

		// Various errors: 
				int error1=0, error2=0, error3=0,error4=0, error5=0;
				
				for (int i=0; i<taggedWords.length; i++) {
					// If only one long sentence is created, essay gets a 1 if the sentence was ended with EOS and 0 otherwise.
					if (taggedWords.length==1) {
						errors=5;
						
						// Check if author used EOS character; if so, then give 1 point.
						TaggedWord lastTW=taggedWords[0].get(taggedWords[0].size()-1);
						if ( TagWord.getPOS(lastTW.toString()).equals("."))
							errors--;
						
						break;
					}
					
					int subjectCount=0;
					int verbCount=0;
					
					for (int j=2; j<taggedWords[i].size(); j++ ) {
						String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
						String POSprev = TagWord.getPOS(taggedWords[i].get(j-1).toString());
						String POSprevprev = TagWord.getPOS(taggedWords[i].get(j-2).toString());
						String word = TagWord.getWord(taggedWords[i].get(j).toString());
						String wordprev = TagWord.getWord(taggedWords[i].get(j-1).toString());
						String wordprevprev = TagWord.getWord(taggedWords[i].get(j-2).toString());
						
						// (i) Missing possessive: "my son name" (PRP$+NN+NN)
						if (POSprevprev.equals("PRP$") && (POSprev.equals("NN")) && (POS.equals("NN"))) 
							error1++;
						
						// (ii) PRP$+JJ+IN (or maybe any adjective not touched by a noun or to-be verb or followed by another JJ or a conjunction CC): MY LIVE IN CHICAGO
						if ((POSprev.equals("JJ")) && !( POS.charAt(0)=='N' || POSprevprev.charAt(0)=='N' || POSprevprev.charAt(0)=='V'||POS.equals("JJ") || POSprevprev.equals("JJ") || POS.equals("CC") || POSprevprev.equals("CC"))) {
							// Special exception for "1 year old" or "32 years old"
							if (wordprev.toLowerCase().equals("old") && (wordprevprev.toLowerCase().equals("year") || wordprevprev.toLowerCase().equals("years") ) )
								;
							else
								error2++;
						}
						
						// (iii) Sentence has a Noun or PRP and a verb
						if (POSprevprev.charAt(0)=='N' || POSprevprev.equals("PRP") || POS.charAt(0)=='N' || POS.equals("PRP")) 
							subjectCount++;
						if ((POSprevprev.charAt(0)=='V' && !POSprevprev.equals("VBG")) || (POS.charAt(0)=='V' && !POS.equals("VBG")))
							verbCount++;
						
						// (iv) DT + !(NOUN or JJ or #)
						if (POSprevprev.equals("DT") && !(POSprev.charAt(0)=='N' || POSprev.charAt(0)=='J' || wordprev.equals("CD"))) 
							error4++;
						
						// (v) "an Mexico"
						String first = wordprev.toUpperCase().substring(0, 1);
						if (wordprevprev.toUpperCase().equals("AN") && !first.matches("[AEIOU]")) {
							error5++;
						}
						
						first = word.toUpperCase().substring(0, 1);
						if (wordprev.toUpperCase().equals("AN") && !first.matches("[AEIOU]")) {  
							error5++;
						}
					}
					
					if (subjectCount*verbCount==0)
						error3++;
					
				}
				if (error1>0)
					errors++;
				if (error2>0)
					errors++;
				if (error3>0)
					errors++;
				if (error4>0)
					errors++;
				if (error5>0)
					errors++;
		
		return Math.max(0, score-errors);
	}
	
	/**
	 * PART 2A 
	 * 
	 * For this subscore, the strategy is to:
	 * 
	 * 1) Collect all pronouns in array
	 * 2) 	Give points for third person
	 * 3) 	Penalize for second person
	 * 4) 	Eliminate not third person
	 * 5) For remaining array of pronouns, check for antecedents
	 * 6) 	Adjust score depending on level of ambiguity of antecedents
	 * 
	 * Note that there are some differences between my calculated grades
	 * and the actual grades because I do not agree with all of the human grades.
	 * For instance, essay 5 follows coherence guidelines, yet the actual human
	 * grade does not reflect this (it assigns a grade of 2). Under my judgment, 
	 * this grade does not reflect the essays coherency. 
	 * 
	 * @param theEssay
	 * @return subscore
	 * @author Dave
	 */
	public static float Subscore2a(Essay theEssay) {
		
		// starting score of 3; increment/decrement depending on coherence
		float score = 3;
		
		// array list of pronouns
		ArrayList<String> pronouns = new ArrayList<String>();
		
		// obtain array of tagged words from essay
		ArrayList<TaggedWord>[] taggedWords = theEssay.getTaggedWords();
		
		int second = 0;
		int third = 0;
		
		boolean male = false;
		int antecedentCorrect = 0;
		int antecedentWrong = 0;
		
		// obtain list of pronouns
		for (int i = 0; i < taggedWords.length; i++) {			
			for (int j = 0; j < taggedWords[i].size(); ++j) {
				
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				String word = TagWord.getWord(taggedWords[i].get(j).toString());
				
				if (word.equalsIgnoreCase("son") || word.equals("husband")) {
					male = true;
				}
				else if (word.equalsIgnoreCase("daughter") || word.equals("wife")) {
					male = false;
				}
				
				// give points for third person and add to array list
				if (word.equalsIgnoreCase("he") || word.equalsIgnoreCase("she") || word.equals("they")) {
					third++;
					
					// figure out nature (gender) of antecedent; i.e. if she is used then female entity should be introduced
					if (word.equalsIgnoreCase("he") || word.equalsIgnoreCase("him") || word.equalsIgnoreCase("his")) {
						if (!male) {
							antecedentWrong++;
						}
						else {
							antecedentCorrect++;
						}
					}
					if (word.equalsIgnoreCase("she") || word.equalsIgnoreCase("her") || word.equalsIgnoreCase("hers")) {
						if (!male) {
							antecedentWrong++;
						}
						else {
							antecedentCorrect++;
						}
					}
				}
				
				// incorrect usage of second person in essay
				else if(word.equalsIgnoreCase("you") || word.equalsIgnoreCase("your")) {
					second++;
				}
							
			}
		}
				
		int positiveScore = third + antecedentCorrect;
		int negativeScore = second + antecedentWrong;
		
		int compScore = positiveScore - negativeScore;
		
		if (compScore >= 2) {
			score += 2;
		}
		else if (compScore == 1) {
			score += 1;
		}
		else if (compScore == -1) {
			score -= 1;
		}
		else if (compScore == -2) {
			score -= 2;
		}
		else if (compScore <= -3) {
			score -= 3;
		}
		
		score = Math.max(0, score);
		score = Math.min(5, score);
		
		return score;
	}


	/**
	 * I don't base subscore 2b on family as is suggested in the instructions
	 * because I don't think family is a necessary or sufficient topic for an 
	 * autobiography.  Furthermore, an essay could be entirely about the authors
	 * family rather than him/herself.  That essay should get a 0.
	 * 
	 * I also strongly disagree with many of the score assignments that were given
	 * to the essays for this category.  For example, I don't feel essay 4 should get
	 * full points.  It is a short essay that is more about the author's family than
	 * the author.  If essay 14 gets a 1, so should essay 4.
	 * 
	 * Factors Considered: number of personal pronouns, percentage of words that are
	 * personal pronouns, how many times "I" is used, whether or not past tense is used 
	 * (for past tense, I also include "from" since that can indicate someone saying 
	 * where he's from).
	 * 
	 * PART 2B TO BE COMPLETED
	 * @param theEssay
	 * @return
	 */
	public static float Subscore2b(Essay theEssay) {
		
		float score=5;
		int errors=0;
		

		// Count the number of personal pronouns: I, me, my, we, us, our, ours, mine
		String meWords="i,me,my,we,us,our,ours,mine";
		String[] meWordsArray = meWords.split(",");
		int NmeWords=0;
		int ratioOfMeWords=0;
		int iCount=0;
		int pastCount=0;
		
		String essay = theEssay.getEssay();
		essay = essay.replace("."," ");
		essay = essay.replace(","," ");
		essay = essay.replace("\n"," ");
		essay = essay.replace("  "," ");
		essay = essay.toLowerCase();
		String[] words = essay.split(" ");
		Arrays.sort(words);
		Arrays.sort(meWordsArray);
		
		for (int i=0; i<words.length; i++) {
			if ( java.util.Arrays.binarySearch(meWordsArray,words[i].toLowerCase())>=0)
				NmeWords++;
			if (words[i].equals("i"))
				iCount++;
		}
		if (words.length>0) 
			ratioOfMeWords = (int) Math.round( ( (float) NmeWords) /words.length*5.0*10.0);
		
		if (ratioOfMeWords<0.1)
			errors++;
		
		// Compute percentage of total number of words.  If >10%, give 6.  Otherwise scale down.
		
		// Deduct a point if there are fewer than 6 me words and another if fewer than 3.
		if (NmeWords<6)
			errors++;
		if (NmeWords<3)
			errors++;
		
		// If fewer than 3 instances of "I", deduct a point.
		if (iCount<3)
			errors++;
		
		// If no past tense is used (i.e., no history), deduct 1 point
		
		ArrayList<TaggedWord>[] taggedWords = theEssay.getTaggedWords();
		
		for (int i=0; i<taggedWords.length; i++) {
			for (int j=0; j<taggedWords[i].size(); j++) {
				String POS = TagWord.getPOS(taggedWords[i].get(j).toString());
				String word = TagWord.getWord(taggedWords[i].get(j).toString());
				
				if (POS.equals("VBD"))
					pastCount++;
				if (word.toLowerCase().equals("from"))
					pastCount++;
			}
		}
		if (pastCount==0)
			errors++;
		
		score = 5-errors;
		
		/*
		// TESTING:
		System.out.println("\n\nWords: ");
		for (int j=0; j<words.length; j++) 
			System.out.println(words[j]);
		System.out.println("\nNmeWords: " + NmeWords + " and Nwords: " + words.length);
		*/
		
		return score;
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
