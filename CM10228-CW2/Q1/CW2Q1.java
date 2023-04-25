import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;

public class CW2Q1 {

	public static void main(String[] args) {
		
		File file = new File("names.txt");
		String st;
		String[] names = null;
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (Exception e) {
			System.out.println("Could not find file.");
			System.exit(0);
		}
		
		try {
			//Read names
			while ((st = br.readLine()) != null) {
				names = st.split(",");
			}
			br.close();
			
			//Bubble sort
			String temp;
			for (int j = 0; j < names.length - 1; j++) {
				for (int i = 0; i < names.length - 1; i++) {
					if (compare(names[i], names[i+1]) > 0){
						temp = names[i+1];
						names[i+1] = names[i];
						names[i] = temp;
					}
				}
			}
			
			
			File outputFile = new File("sortedNames.txt");
			BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));
			
			String sortedNames = Arrays.toString(names);
			
			//removes the square brackets
			sortedNames = sortedNames.replaceAll("\\[", "").replaceAll("\\]","").replaceAll(" ","");
			
			output.write(sortedNames);
			output.flush();
			output.close();
			System.out.println("Completed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*Compares the two names given by returning the difference between
	 * the next letter in each that is not the same as the other*/
	public static int compare(String name1, String name2) {
		int difference = 0;
		if (name1.length() > name2.length()) {
			for (int j = 0; j < name2.length(); j++) {
				//Gets the difference between the two letters that are not the same
				if ((int)name1.charAt(j) != (int)name2.charAt(j)) {
					difference = (int)name1.charAt(j) - (int)name2.charAt(j);
					return difference;
				}
			}
		} else {
			for (int i = 0; i < name1.length(); i++) {
				//Gets the difference between the two letters that are not the same
				if ((int)name1.charAt(i) != (int)name2.charAt(i)) {
					difference = (int)name1.charAt(i) - (int)name2.charAt(i);
					return difference;
				}
			}
		}
		return difference;
	}
}