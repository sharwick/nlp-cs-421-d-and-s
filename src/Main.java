import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImportData id = new ImportData();
		id.PrintData();
		id.PrintScores();
		
		int col1a=1, col1b=2, col1c=3, col1d=4, col2a=5, col2b=6, col3a = 7;
		
		// Test 3a
		Test.testScore(id, col3a, "Part 3a");
		
		
		// Running POS tagger: returns an array of arrayLists.  
		//		Elements of the array are sentences
		//		Elements in each array list are WORD/POS combos (e.g., "the/DT")
		//		The tagger is not good at finding the end of sentence if a period is missing
		
		String model = "./stanford-postagger-full-2012-11-11/models/wsj-0-18-left3words.tagger";
		String filename = "./essay-corpus/1.txt";
		ArrayList<TaggedWord>[] output = Tagger.RunTagger(model, filename);
		
		// Print the elements of the array and then the individual elements of each ArrayList
		for(int i=0; i<output.length; i++) {
			System.out.println(output[i].toString());
			System.out.println("Sentence " + i);
			for (int j=0; j<output[i].size(); j++) {
				System.out.println( output[i].get(j));
			}
		}
		
	}

}
