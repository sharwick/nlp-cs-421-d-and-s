import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Create array of essay objects
		int N_files = 20;
		Essay[] essays = new Essay[N_files];
		
		for (int i=1; i<=N_files; i++) {
			essays[i-1] = new Essay("./essay-corpus/" + i + ".txt");
		}
		
		float[][] scores = ImportData.ImportScores("./essay-corpus/grades.txt", N_files);
		
		
		int col1a=1, col1b=2, col1c=3, col1d=4, col2a=5, col2b=6, col3a = 7;
		
		// Test 3a
		//Test.testScore(essays, col3a, "Part 3a", scores);
		//Test.testScore(essays, col1c, "Part 1c", scores);
		//Test.testScore(essays, col1d, "Part 1d", scores);
		//Test.testScore(essays, col1a, "Part 1a", scores);
		Test.testScore(essays, col1b, "Part 1b", scores);
		
		// Running POS tagger: returns an array of arrayLists.  
		//		Elements of the array are sentences
		//		Elements in each array list are WORD/POS combos (e.g., "the/DT")
		//		The tagger is not good at finding the end of sentence if a period is missing
		/*
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
		*/
	
		

		// TEST Essay
//		System.out.println("\n\nTEST ESSAY: \n");
//		Essay essay1 = new Essay("./essay-corpus/19.txt");
//		System.out.println("\n\n" + essay1.getEssay());
//		System.out.println("\nScore 3a = " + essay1.get3a());
//		System.out.println("\nScore 1c = " + essay1.get1c());
//		System.out.println("\nScore 1d = " + essay1.get1d());
	}

}
