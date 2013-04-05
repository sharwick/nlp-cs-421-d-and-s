
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
		
		Functions.runPOSTagger("./essay-corpus/1.txt");
	}

}
