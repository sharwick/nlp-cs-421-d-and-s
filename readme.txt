                                                                     
                                                                     
                                                                                                             project 1 for CS421 - University of Illinois at Chicago
Name1 sharwi2(at)uic.edu
Name2 dbai(at)uic.edu
-------------------------------------------------------------------------
--->SETUP<---------------------------------------------------------------

1. Our JAR file should be placed in the same directory as the "stanford-postagger-full-2012-11-11" folder obtained from downloading and extracting the following (v. 3.1.4): 

http://nlp.stanford.edu/software/stanford-postagger-2012-11-11.zip

2. From the directory containing the JAR file and the POS tagger, our program can be run on an individual file as follows:

java -jar project1_Bai_Harwick.jar test_essay.txt

I.e., the folder you run our program in should have the following files:

FOLDER/project1_Bai_Harwick.jar
FOLDER/stanford-postagger-full-2012-11-11/...
FOLDER/test_essay.txt

All essays should be in this folder.  Alternatively, they can all be in a subfolder, but this required changing the command to run the program.  Suppose the essays are in a subfolder:

FOLDER/essays/test_essay.txt

Then the command would instead be 

java -jar project1_Bai_Harwick.jar essays/test_essay.txt

-------------------------------------------------------------------------
--->INPUT<---------------------------------------------------------------
	
Input is a file containing a single essay.  See above for command line format.


-------------------------------------------------------------------------
--->OUTPUT<--------------------------------------------------------------

The final output (output to standard out) is a list of seven subscores and a total score for the essay, space delimited:
1a 1b 1c 1d 2a 2b 3a Total 
	

-------------------------------------------------------------------------
--->FILES<---------------------------------------------------------------

No files are created beyond the essay files.  Output is printed to stout but not printed to a file.
	

-------------------------------------------------------------------------
--->TECHNIQUE<-----------------------------------------------------------


For each part, we initially assign a score of 5 and then take points away for errors.  Error counts are performed for each sentence.  The idea is that in an essay of 6 sentences, a score can reasonably be assigned based on the number of errors per sentence.  In order to avoid over-penalizing for repeated errors of the same type, we usually allow only one point to be lost for a particular error.  However, we allow a greater loss for more significant errors.  Furthermore, very common errors are easier to detect, making us more confident in taking away a full point for these errors.  E.g., we subtract multiple points if several sentences do not have a verb.  However, if two sentences mis-use a gerund (e.g., "to going"), only one point would be taken away.  

We determined types of errors to include in our analysis by reading all the essays.  The following are errors we have included in our analysis for each part.

1a
 - verb found at beginning of sentence
 - adj must precede a noun
 - verb verb found consecutively
 - cannot have IN VB consecutively
 - adverb must follow verb
 - verb at end of sentence
 - IN/CD at beginning of sentence
 - NN NN found consecutively

1b
 - Is there agreement between subject and verb (singular/plural)?
 - Check agreement for cases where 'and' is encountered as subject.
 - Incorrect verb usage (e.g., "I am(VBP) came(VBD)")
 - Are there no verbs present at all in sentence?

1c
 - Are there sentences with no verb?
 - Are there incorrect uses of -ING?
 - Are there missing auxiliary verbs? ("I not like")
 - Awkward or incorrect verb usage (VBP+VBN, VBP + VB!G, BECAUSE + IS, IN + NN, TO + VBG, IN + VB!G)

1d
 - Did author have multiple sentences or one long sentence?
 - Are there missing possessives ("my son name")
 - Are there incorrectly used possessives (PRP$ + JJ + IN such as "my live in Chicago")
 - Does sentence have both a noun/pronoun and a verb?
 - Are determiners used correctly?  ("the in Chicago")
 - Is "an" used correctly? ("an Mexico")



-------------------------------------------------------------------------
--->TODO<----------------------------------------------------------------
	

Our results are good on the test data, but we have several plans for part 2:

1) Implement parts 2a and 2b.  These are currently set to 3 for every essay.  We will incorporate the coherence algorithm from Chapter 21 to match pronouns.

2) Extend analysis of part 1D to use methods recently covered in class.  

3) So far, Shannon worked on 1c, 1d, and 3a, while Dave worked on 1a and 1b.  We will need to test each other's work and to look for improvements that the other one of us did not think of.  

4) We have not considered errors that did not occur in our sample of 20 essays.  A lot of the sample is from Spanish/Portuguese speakers.  If we suddenly get a lot of essays from more distant languages (Chinese, Arabic, etc.), our program may not work well because it's trained too specifically to our input.  

5) Sentence count is currently based on the number of end of sentence characters ('.') and the number of new lines.  We need to put more thought into how to handle run on sentences that are poorly punctuated (e.g., six sentences lumped together without periods).  

6) We need to discuss whether there are errors that should cause the author to lose points in multiple categories.  Certain errors are related to multiple sub-parts.  We may want to move around some of our errors across subparts.  

7) Should the subparts be calculated together?  E.g., if an author gets a score of 1 on verb usage (1C), should that person automatically score very low on sentence formation?  Or does our analysis already capture this?

8) Certain errors we have constructed are not always errors but are likely errors in elementary writing samples such as these.  E.g., NN + NN is quite possible in well-written English but probably indicates an error in our essays.  E.g., "My/PRP$ fathers/NNS lives/NNS in/IN Mexico/NNP" contains NNS + NNS because the tagger could not successfully tag the sentence due to errors.  However, one could write "put on your party/NNS hat/NNS", which is correct.  We need to look at errors such of these and consider whether there are alternatives or make sure that over-counting errors does not too greatly impact the score. 





