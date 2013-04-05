import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

class Tagger {

	/*
  private Tagger() {}

  
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("usage: java TaggerDemo modelFile fileToTag");
      return;
    }
  */ 
	/*
    MaxentTagger tagger = new MaxentTagger(args[0]);
    List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader(args[1])));
    for (List<HasWord> sentence : sentences) {
      ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
      System.out.println(Sentence.listToString(tSentence, false));
    }
    */
	
	
    public static ArrayList<TaggedWord>[] RunTagger(String model, String inFile) {
    	try{
    		
    		
    		MaxentTagger tagger = new MaxentTagger(model);
        	List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader(inFile)));
        	
        	ArrayList<TaggedWord>[] returnArray;
        	ArrayList<TaggedWord> temp;
        	//Class<ArrayList<TaggedWord>> clazz = temp.getClass();
        	//returnArray = (ArrayList<TaggedWord>[]) Array.newInstance(clazz.getComponentType(), sentences.size()); //new ArrayList<TaggedWord>[sentences.size()];
        	returnArray = new ArrayList[sentences.size()];
        	
        	int i=0;
            for (List<HasWord> sentence : sentences) {
              ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
              System.out.println(Sentence.listToString(tSentence, false));
              
              
              returnArray[i++] = tSentence;
            }	
            
            return returnArray;
    	} catch (Exception e) {
    		System.err.println("ERROR: " + e);
    	}
    	
    	return null;
    	
    }
    
   

}
