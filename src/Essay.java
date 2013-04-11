import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import edu.stanford.nlp.ling.TaggedWord;

public class Essay {
	
	// data members
	private String essay; // entire essay
	private int sentenceCount; // total count of sentences
	private ArrayList<String> sentenceList = new ArrayList<String>(); // list of sentences
	private int score1a, score1b, score1c, score1d, score2a, score2b, score3a; // sub scores
	private float scoreTotal; // total scores
	private ArrayList<TaggedWord>[] taggedWords;
	

	
	// Default constructor
	public Essay() {
		ZeroScores();
		essay=null;
	}
	
	public Essay(String filename) {
		ZeroScores();
		essay = ImportData.ImportFile(filename);
		
		taggedWords = runPOSTagger(filename);
		CalculateScores();
	}
	
	/**
	 * Set all scores to 0
	 */
	public void ZeroScores() {
		sentenceCount = 0;
		score1a = 0;
		score1b = 0;
		score1c = 0;
		score1d = 0;
		score2a = 0;
		score2b = 0;
		score3a = 0;
		scoreTotal = 0.0f;
	}
	
	// getter/setter methods for sentenceCount===============================
	public int getSentenceCount() {
		return sentenceCount;
	}
	private void setSentenceCount(int count) {
		this.sentenceCount = count;
	}
	// =======================================================================
	
	// getter/setter methods for sub scores===================================
	public int get1a() {
		return score1a;
	}
	public int get1b() {
		return score1b;
	}
	public int get1c() {
		return score1c;
	}
	public int get1d() {
		return score1d;
	}
	public int get2a() {
		return score2a;
	}
	public int get2b() {
		return score2b;
	}
	public int get3a() {
		return score3a;
	}
	private void set1a(int score) {
		this.score1a = score;
	}
	private void set1b(int score) {
		this.score1b = score;
	}
	private void set1c(int score) {
		this.score1c = score;
	}
	private void set1d(int score) {
		this.score1d = score;
	}
	private void set2a(int score) {
		this.score2a = score;
	}
	private void set2b(int score) {
		this.score2b = score;
	}
	private void set3a(int score) {
		this.score3a = score;
	}
	// =======================================================================
	
	// getter/setter methods for scoreTotal=================================
	public float getScoreTotal() {
		return scoreTotal;
	}
	private void setScoreTotal(int score) {
		this.scoreTotal = score;
	}
	// ========================================================================
	
	// getter/setter other=================================
		public ArrayList<TaggedWord>[] getTaggedWords() {
			return taggedWords;
		}
		
		// ========================================================================
		
	
	
	
	
	// toString method
	@Override
	public String toString() {
		return sentenceList.toString();
	}
	
	// getter/setter methods for sentences========================================
	public String getSentence(int i) {
		return sentenceList.get(i);
	}
	private void setSentence(int i, String s) {
		sentenceList.set(i, s);
	}
	// ===========================================================================
	
	// getter/setter methods for essay============================================
	public String getEssay() {
		return essay;
	}
	private void setEssay(String s) {
		essay = s;
	}
	// ==========================================================================

	/*
	 * Add a new sentence
	 */
	public void addSentence(String s) {
		sentenceList.add(s);
		incrementSentenceCount();
	}

	// increment or decrement sentence count
	private void incrementSentenceCount() {
		sentenceCount++;
	}
	private void decrementSentenceCount() {
		sentenceCount--;
	}
	
	/*
	 * Calculates total score rounded to nearest half and stores in scoreTotal
	 */
	public void calculateTotal() {
		float score = (score1a + score1b + score1c + 2 * score1d + score2a + 3 * score2b + score3a) / 10.0f;
		float rounded = (float)(Math.ceil(score * 2) / 2.0);
		
		scoreTotal = rounded;
	}
	
	// clear arraylist of sentences
	public void clearSentenceList() {
		sentenceList.clear();
		scoreTotal = 0;
	}
	
	
	/*
	 * Parse into its separate sentences and set sentence count
	 */
	public void parseEssay() throws IOException {
		
		
		// read in file
		Scanner scan = new Scanner(essay);
		ArrayList<String> list = new ArrayList<String>();
		while (scan.hasNextLine()){
			String s = scan.nextLine();
		    list.add(s);
		}
		scan.close();
		 
		// list contains all lines of file
		
		// write out each line to a separate file
		int counter = 0;
		for(int i = 0; i < list.size(); i++) {
			FileWriter writer = new FileWriter("out" + counter + ".txt"); 
			if (counter == 0) {
				writer.write(list.get(i).toString());
			}
			else {
			
				String s = list.get(i).toString();
				writer.write(s);
				
			}
		  
			counter++;
			writer.close();
		}
				

		String model = "./stanford-postagger-full-2012-11-11/models/wsj-0-18-left3words.tagger";
		
		
		String filename = "out0.txt";
		ArrayList<TaggedWord>[] output = Tagger.RunTagger(model, filename); // now run tagger on the specified file

		ArrayList<String> words = new ArrayList<String>();
	
		for (int i = 0; i < output.length; i++) {
				
				for (int j = 0; j < output[i].size(); j++) {
					
				
					String s = output[i].get(j).toString(); // obtain tagged word
					
					String word = TagWord.getWord(s);
					String tag = TagWord.getPOS(s);
							
					System.out.println(word);
					
					
				}
		
		}
		
		
		// .......
		
		
	
	}
	
	
	
	
	private ArrayList<TaggedWord>[] runPOSTagger(String filename) {
		String model = "./stanford-postagger-full-2012-11-11/models/wsj-0-18-left3words.tagger";
		ArrayList<TaggedWord>[] output = Tagger.RunTagger(model, filename);
		return output;
	}
	
	
	// ==========================================================================
	// Calculate scores
	private void CalculateScores() {
		
		// Fill in other scores as they are available
		this.set1c((int) Functions.Subscore1c(this));
		this.set1d((int) Functions.Subscore1d(this));
		this.set3a((int) Functions.Subscore3a(this));
	}
	
	
	
}
