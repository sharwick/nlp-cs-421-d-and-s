import java.io.*;
import java.util.StringTokenizer;
import java.util.*;

public class ImportData {
	
	private int N_files = 20;
	private int scoreColumns = 10; // identifier, 7 subparts, actual final score, and computed final score
	public String[] data = new String[N_files];
	public float[][] scores = new float[N_files][scoreColumns];
	
	public ImportData() {
		for (int i=1; i<=N_files; i++) {
			data[i-1] = ImportFile("./essay-corpus/" + i + ".txt");
		}
		
		ImportScores("./essay-corpus/grades.txt", N_files);
	}
		
	/**
	 * Import a single file.  Output is a single string, including newline characters.
	 * @param filename
	 * @return String 
	 */
	public String ImportFile(String filename) {
		String input=null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String thisLine;
			
			while ( (thisLine=br.readLine()) != null) {
				if (input==null) 
					input = thisLine;
				else
					input += "\n" + thisLine;
			}
			
			br.close();
		} catch (IOException e) {
			System.err.println("Error: " + e);
		}
		
		return input;
	}	
	
	
	
	/**
	 * Method prints all data elements, with a header showing the Essay Number.
	 */
	public void PrintData() {
		for (int i=0; i<data.length; i++) {
			System.out.println("\nEssay #" + (i+1) + ": ");
			System.out.println(data[i]);
		}
	}
	
	/**
	 *  Import array of scores 
	 */
	public void ImportScores(String filename,int N_files) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String thisLine;
			
			// Skip first five lines
			for (int i=0; i<5; i++) 
				thisLine=br.readLine();
			
			// Read individual scores from file (rows are in reverse order)
			int row=19;		
			while ( (thisLine=br.readLine()) != null) {

				int col=0;				
				String[] rowValues = thisLine.split("\t");
				
				for (col=0; col<rowValues.length; col++)
					scores[row][col] = Float.parseFloat(rowValues[col]);
				
				scores[row][col] = Functions.ComputeFinalScore(	scores[row][1],
																scores[row][2],
																scores[row][3],
																scores[row][4],
																scores[row][5],
																scores[row][6],
																scores[row][7]);
				
				row--;
			}
			
			br.close();
		} catch (IOException e) {
			System.err.println("Error: " + e);
		}
	}
	
	/**
	 * Method prints all scores, with a header showing the Essay Number.
	 */
	public void PrintScores() {
		for (int i=0; i<scores.length; i++) {
			System.out.println("\n\nEssay #" + (i+1) + ": ");
			
			for( int j=0; j<scoreColumns; j++) {
				System.out.print("\t" + scores[i][j]);
			}
			
		}
	}
}
