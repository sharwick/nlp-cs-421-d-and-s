import java.io.*;


public class ImportData {

	
	
	private int N_files = 20;
	private int maxSize = 100;
	public String[][] data = new String[N_files][maxSize];
	
	
	
	public ImportData() {
		for (int i=1; i<=N_files; i++) {
			//String temp[] = ImportFile("./essay-corpus/" + i + ".txt");
			
			data[i-1] = ImportFile("./essay-corpus/" + i + ".txt");
		}
		
	}
	
	public String[] ImportFile(String filename) {
		String[] input= new String[maxSize];
		int i=0;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String thisLine;
			
			while ( (thisLine=br.readLine()) != null) {
				input[i++] = thisLine;
			}
			
			br.close();
		} catch (IOException e) {
			System.err.println("Error: " + e);
		}
		
		return input;
	}
	
	
	/**
	 * Method prints all data elements, line delimited.
	 */
	public void PrintData() {
		String temp=null;
		for (int i=0; i<data.length; i++) {
			System.out.println("\nEssay #" + (i+1) + ": ");
			
			for (int j=0; j<maxSize; j++) {
				if ( (temp = data[i][j])!=null) { 
					System.out.println(temp);	 
				}
			}
		}
	}
	
}
