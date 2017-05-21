/*
 * Author: Gustavo Carbone
 * Date: 05/05/2017
 */

package hw4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Vocab {
	
	private String[] vocab;
	
	/**
	 * Constructor - reads word from given text/dictionary
	 * @throws FileNotFoundException 
	 */
	public Vocab() /*throws FileNotFoundException, IOException*/ {
		List<String> temp = new ArrayList<String>();
		try {
			FileReader file = new FileReader("hw4_vocab.txt");
			BufferedReader reader = new BufferedReader(file);
			String word;
			
			while((word = reader.readLine()) != null) {
				temp.add(word);
			}
			file.close();
			reader.close();
		} catch( FileNotFoundException a) {
			System.out.println("File not found");
		} catch ( IOException b) {
			System.out.println("io exception");
		}
		
		vocab = temp.toArray(new String[]{});		
	}
	
	/**
	 * Finds all words that contain a specific letter at position index
	 * 
	 * @param letter - character to search for
	 * @param index - position of the character in the word
	 * @return - array list containing words meeting criteria
	 */
	public ArrayList<Integer> getIndexes(char letter, int index) {
		ArrayList<Integer> list = new ArrayList<>();
		
		for(int i=0; i < vocab.length; i++) {
			if(vocab[i].charAt(index) == letter){
				list.add(i);
			}
		}		
		return list;
	}
	
	/**
	 * Finds the index of a certain word in the dictionary
	 * 
	 * @param word
	 * @return
	 */
	public int getIndex(String word) {
		int index = 0;
		
		for(int i=0; i < vocab.length; i++) {
			if(vocab[i].equals(word)){
				index = i;
				break;
			}
		}		
		return index;
	}
	
	/**
	 * Searches for all words containing a specific letter at position index
	 * 
	 * @param letter - character the word must have
	 * @param index - position of the letter within the word
	 * @return - list of all words meeting criteria
	 */
	public ArrayList<String> getWords(char letter, int index) {
		ArrayList<String> list = new ArrayList<>();
		
		for(int i=0; i < vocab.length; i++) {
			if(vocab[i].charAt(index) == letter){
				list.add(vocab[i]);
			}
		}		
		return list;
	}
	
	/**
	 * Prints all words in dictionary
	 */
	public void print() {
		for(int i=0; i<vocab.length;i++) {
			System.out.println(vocab[i]);
		}
	}
	
	/**
	 * Returns a word at a specific index
	 * 
	 * @param index - index of word in dictionary
	 * @return - word at specified index
	 */
	public String get(int index) {
		return vocab[index];
	}
}
