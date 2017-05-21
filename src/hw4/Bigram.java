package hw4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Bigram {
	private ArrayList<fourTuple> bigram = new ArrayList<>();
	@SuppressWarnings("uncheked") private LinkedList<fourTuple>[] probs = new LinkedList[500];
	private Unigram unigram =  new Unigram();
	
	/**
	 * Constructor for the Bigram model. Initialized to the given data in the text file except all probabilities are initialized to zero.
	 * Using fourTuples object to keep data organized.
	 */
	public Bigram() /*throws FileNotFoundException, IOException*/ {
		List<String> temp = new ArrayList<String>();
		try {
			FileReader file = new FileReader("hw4_bigram.txt");
			BufferedReader reader = new BufferedReader(file);
			String triples;
			
			while((triples = reader.readLine()) != null) {
				temp.add(triples);
			}
			file.close();
			reader.close();
		} catch( FileNotFoundException a) {
			System.out.println("File not found");
		} catch ( IOException b) {
			System.out.println("io exception");
		}
		
		String[] tempStr = temp.toArray(new String[]{});
		for(int i=0; i<tempStr.length; i++) {
			String triples[] = tempStr[i].split("\t");
			bigram.add(new fourTuple(Integer.parseInt(triples[0]), Integer.parseInt(triples[1]), Integer.parseInt(triples[2]), 0));
		}
		
		for(int i=0; i<probs.length; i++) {
			probs[i] = new LinkedList<>();
		}
	}
	
	/**
	 * Calculates the probabilitis of all words in the bigram model
	 */
	public void calculateAllProbs() {
		
		for(int i=0; i<bigram.size(); i++) {
			//Getting current data from ith Triple
			fourTuple temp = bigram.get(i);
			int firstWord = temp.getIndex1();
			int secondWord = temp.getIndex2();
			int count = temp.getCount();
			int countParent = getParentCount(firstWord - 1);
			double prob = (double)count / (double)countParent;
			
			fourTuple newItem = new fourTuple(firstWord, secondWord, count, prob);
			
			//Adding 
			probs[firstWord - 1].add(newItem);
		}
	}
	
	/**
	 * Returns the count of the given parent of the current word
	 * 
	 * @param parent - parent of the word we are calculating the probability for
	 * @return returns the count of the parent
	 */
	public int getParentCount(int parent) {	
		return unigram.getCount(parent);
	}
	
	
	/**
	 * Returns a fourTuple at given index
	 * 
	 * @param index - position of the fourTuple
	 * @return - fourTuple at given index
	 */
	public fourTuple get(int index) {
		return bigram.get(index);
	}

	/**
	 * Returns the size of the bigram model
	 * 
	 * @return
	 */
	public int size() {
		return bigram.size();
	}
	
	/**
	 * Returns the probability of a fourTuple at index 
	 * 
	 * @param index - position of the fourTuple
	 * @return - probability of the fourTuple at index 
	 */
	public LinkedList<fourTuple> getProbs(int index) {
		return probs[index];
	}
}
