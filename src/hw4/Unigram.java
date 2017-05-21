package hw4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Unigram {
	
	private Integer[] unigram;
	private HashMap<Integer, Double> probs = new HashMap<>();
	
	/**
	 * Constructor - Fills all of the data for the unigram model with the given data in text file
	 * @throws FileNotFoundException 
	 */
	public Unigram() /*throws FileNotFoundException, IOException*/ {
		List<Integer> temp = new ArrayList<Integer>();
		try {
			FileReader file = new FileReader("hw4_unigram.txt");
			BufferedReader reader = new BufferedReader(file);
			String count;
			
			while((count = reader.readLine()) != null) {
				temp.add(Integer.parseInt(count));
			}
			file.close();
			reader.close();
		} catch( FileNotFoundException a) {
			System.out.println("File not found");
		} catch ( IOException b) {
			System.out.println("io exception");
		}
		
		unigram = temp.toArray(new Integer[]{});		
	}
	
	
	/**
	 * Returns the count of a word at index lth
	 * 
	 * @param lth - index of the word
	 * @return - count of the word at index lth
	 */
	public int getCount(int lth) {		
		return unigram[lth];
	}
	
	/**
	 * Adds up all of the counts of all words 
	 * 
	 * @return - returns the total count
	 */
	public int getTotalCount() {
		int totalCount = 0;
		for(int i=0; i<unigram.length; i++) {
			totalCount += unigram[i];
		}
		return totalCount;
	}
	
	/**
	 * Calculates the probabilities of every word
	 * 
	 * @param total - count of all words
	 */
	public void calculateAllProbs(int total) {
		double prob = 0;
		
		for(int i=0; i<unigram.length; i++) {
			prob = (double)unigram[i] / (double)total;
			probs.put(i, prob);
		}
	}
	
	/**
	 * Returns the probability of a word at a specific index in the unigram model
	 * 
	 * @param index - position where the word can be found
	 * @return - returns the word probability 
	 */
	public double getProbs(Integer index) {
		return probs.get(index);
	}

}
