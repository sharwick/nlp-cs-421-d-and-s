
public class Test {
	
	
	public static void testScore(ImportData id, int col, String title) {
		int absDiff=0;
		
		System.out.print("\n\n" + title.toUpperCase() + ":");
		System.out.print("\nN\t");
		System.out.print("Calc\t");
		System.out.print("Actual\t");
		
		
		// Preliminary technique: Count number of newlines and periods
		for (int i=0; i<20; i++) {
			System.out.print("\n" + (i+1) + "\t");
			System.out.print(Functions.Subscore3a(id.data[i]) + "\t");
			System.out.print(id.scores[i][col]);
			
			absDiff += Math.abs(Functions.Subscore3a(id.data[i]) - id.scores[i][col]);
		}
		
		System.out.println("\n\nTotal absolute difference = " + absDiff);
	}

}
