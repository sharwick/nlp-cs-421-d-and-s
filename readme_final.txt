                                                                     
                                                                     
                                                                                                             project 2 for CS421 - University of Illinois at Chicago
Name1 sharwi2(at)uic.edu
Name2 dbai(at)uic.edu
-------------------------------------------------------------------------
--->SETUP<---------------------------------------------------------------

1. Our JAR file should be placed in the same directory as the "stanford-postagger-full-2012-11-11" folder obtained from downloading and extracting the following (v. 3.1.4): 

	http://nlp.stanford.edu/software/stanford-postagger-2012-11-11.zip

as well as the "stanford-parser-2012-11-12" folder downloaded from: 

	http://nlp.stanford.edu/software/stanford-parser-2012-11-12.zip

2. From the directory containing the JAR file and the POS tagger, our program can be run on a folder of essays as follows:

java -jar project2_Bai_Harwick.jar essays

I.e., the folder you run our program in should have the following files:

FOLDER/project2_Bai_Harwick.jar
FOLDER/stanford-postagger-full-2012-11-11/...
FOLDER/stanford-parser-2012-11-12/...
FOLDER/essays/...

-------------------------------------------------------------------------
--->INPUT<---------------------------------------------------------------
	
Input is a folder containing only essays.


-------------------------------------------------------------------------
--->OUTPUT<--------------------------------------------------------------

The final output (output to standard out) is a list of seven subscores and a total score for the essay, space delimited:
1a 1b 1c 1d 2a 2b 3a Total 
	

-------------------------------------------------------------------------
--->FILES<---------------------------------------------------------------

The output is sent to the file output.txt.
	

-------------------------------------------------------------------------
--->TECHNIQUE<-----------------------------------------------------------

See report.


