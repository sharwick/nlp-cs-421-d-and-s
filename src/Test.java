
public class Test {
	
	
	public static void testScore(Essay[] essays, int col, String title, float[][] scores) {
		int absDiff=0;
		
		System.out.print("\n\n" + title.toUpperCase() + ":");
		System.out.print("\nN\t");
		System.out.print("Calc\t");
		System.out.print("Actual\t");
		
		
		// Preliminary technique: Count number of newlines and periods
		for (int i=0; i<20; i++) {
			float calcScore = Functions.Subscore1c(essays[i]);
			
			System.out.print("\n" + (i+1) + "\t");
			System.out.print(calcScore + "\t");
			System.out.print(scores[i][col]);
			
			absDiff += Math.abs(calcScore - scores[i][col]);
		}
		
		System.out.println("\n\nTotal absolute difference = " + absDiff);
	}

}
