import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CW2Q6 {

	public static void main(String[] args) {
		File textFile = null;
		String userInput = null;
		BufferedReader textBr = null;
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Type in the path to the textfile you want proper nouns redact from(.txt): ");
		
		//Gets user's input
		try {
			userInput = input.readLine();
			textFile = new File(userInput);
			textBr = new BufferedReader(new InputStreamReader(new FileInputStream(textFile), "UTF-8"));
		} catch (Exception e) {
			System.out.println("Could not find file.");
			System.exit(0);
		}
		
		//Will contain the words to be redacted.
		ArrayList<String> redactWords = new ArrayList<>();
		
		try {
			//Removes special symbols at start of file
			textBr = new BufferedReader(new InputStreamReader( new FileInputStream(textFile), "UTF-8"));
			textBr.mark(1);
			if (textBr.read() != 0xFEFF) {
				 textBr.reset();
			}
			
			String textSt;
			while ((textSt = textBr.readLine()) != null) {
				String lineSplit[] = textSt.split("[\\s()]");
				
				//Go through each of the splitted by spaces and brackets parts
				for (int i = 0; i < arrayLength(lineSplit); i++) {
					
					if (!(lineSplit[i].isEmpty())) {
						
						//Checks that the first letter is a capital and we are on at least the second word
						if ((Character.toString(lineSplit[i].charAt(0)).matches("[A-Z]")) && (i > 0)){
								
								if (!(lineSplit[i-1].isEmpty())) {
									
									//Continues only if the last character is a , or the last character is a letter or number
									if ((lineSplit[i-1].charAt(wordLength(lineSplit[i-1])-1) == ',') || (Character.toString(lineSplit[i-1].charAt(wordLength(lineSplit[i-1])-1)).matches("[A-Za-z0-9]"))) {
										
										String word = lineSplit[i];
										
										//Removes all special symbols apart from - and '
										word = word.replaceAll("[^A-Za-z-]", "");
										
										//Removes 's on the end of a word
										String apostPart[] = word.split("'");
										String apostWord = apostPart[0];
										
										//Checks that the word is not already in the arraylist
										int found = 0;
										for (int j = 0; j < redactWords.size(); j++) {
											if (apostPart[0].equals(redactWords.get(j))){
												found = 1;
											}
										}
										
										if (found == 0) {
											redactWords.add(apostPart[0]);
										}
									}
								}
						}
					}
				}
			}
			
			//Removes roman Numerals
			for (int i = 0; i < redactWords.size(); i++){
				if (redactWords.get(i).matches("[IVXLCDM]+")){
					redactWords.remove(i);
				}
			}
		
			textBr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Reads the textfile and replaces the words that are to be redacted and stores the new text in redactedText.txt
		String textSt;
		try {
			textBr = new BufferedReader(new InputStreamReader( new FileInputStream(textFile), "UTF-8"));
			PrintWriter outs = new PrintWriter("redactedText.txt");
			
			textBr.mark(1);
			if (textBr.read() != 0xFEFF) {
				 textBr.reset();
			}
			while ((textSt = textBr.readLine()) != null) {
				//Replace the words in the line that are in the redactwords arraylist
				outs.println(replaceText(textSt, redactWords));
			}

			textBr.close();
			outs.flush();
			outs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Completed");
	}
	
	//Replaces the words in the line that are in the redactwords arraylist
	private static String replaceText(String textSt, ArrayList<String> redactWords) {
		String word = "";
		int index = 0;
		
		ArrayList<String> words = new ArrayList<>();
		
		//Stores each word and symbol separately
		for (int i = 0; i < wordLength(textSt); i++) {
			//Checks that the character is a letter
			if (!(Character.toString(textSt.charAt(i)).matches("[A-Za-z]"))) {
				words.add(word);
				words.add(Character.toString(textSt.charAt(i)));
				word = "";
			} else{
				word += textSt.charAt(i);
			}
		}
		words.add(word);
		
		//Replaces each redact word in the text with *s equivalent to their own lengths
		for (int i = 0; i < words.size(); i++) {
			for (int j = 0; j < redactWords.size(); j++) {
					
					//checks if word is in redact list
					if (words.get(i).equals(redactWords.get(j))) {
						String stars = "";
						
						//Gets the amount of stars needed
						for (int k = 0; k < wordLength(redactWords.get(j)); k++) {
							stars += "*";
						}
						words.set(i, stars);
					}
			}
		}
		
		//Rebuilds the line with the redact words as *s
		String finalText = "";
		for (int i = 0; i < words.size(); i++) {
			finalText += words.get(i);
		}
		
		return finalText;
	}
	
	//Calculates the length of the given string
	private static int wordLength(String word){
		//Splits each character into the array
		String[] characters = word.split("");
		int i = 0;
		String c;
		if (word.isEmpty()) {
			return 0;
		}
		try {
			while (true) {
				c = characters[i];
				i++;
			}
		} catch (Exception e) {
			return i;
		}
	}
	
	//Calculates the length of an array given
	private static int arrayLength(String[] array){
		int i = 0;
		String c;
		try {
			while (true) {
				c = array[i];
				i++;
			}
		} catch (Exception e) {
			return i;
		}
	}
}