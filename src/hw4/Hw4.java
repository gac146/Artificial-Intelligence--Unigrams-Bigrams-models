package hw4;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

public class Hw4 {
	
	private static Vocab vocab = new Vocab();
	private static Unigram unigram = new Unigram();
	private static Bigram bigram = new Bigram();
	private static final int T = unigram.getTotalCount();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		unigram.calculateAllProbs(T);
		System.out.println("Questio 4.3   ---    PART A");
		PMLUnigram(0, 'M');
		System.out.println("---------------------------------------------------");
		
		
		System.out.println("Questio 4.3   ---    PART B");
		bigram.calculateAllProbs();
		PMLBigram("ONE");
		System.out.println("---------------------------------------------------");
		
		System.out.println("Questio 4.3   ---    PART C");
		System.out.println("Lu part c) = " + new DecimalFormat("#0.00").format(pLu("The market fell by one hundred points last week.")));
		System.out.println("Lb part c) = " + new DecimalFormat("#0.00").format(pLb("The market fell by one hundred points last week.")));
		System.out.println("---------------------------------------------------");
		
		System.out.println("Questio 4.3   ---    PART D");
		System.out.println("Lu part d) = " + new DecimalFormat("#0.00").format(pLu("The fourteen officials sold fire insurance.")));
		System.out.println("Lb part d) = " + new DecimalFormat("#0.00").format(pLb("The fourteen officials sold fire insurance.")));
		System.out.println("---------------------------------------------------");
		
		System.out.println("Questio 4.3   ---    PART E");
		mix("The fourteen officials sold fire insurance.");
		
	}
	
	/**
	 * Prints out the Maximum likelihood estimate of the unigram distribution over words that contain character w at the lth position
	 *  @param lth - position(index) of character w in the word
	 *  @param w - character of word(s) to calculate
	 */
	private static void PMLUnigram(int lth, char w) {
		ArrayList<Integer> listInts = vocab.getIndexes(w, lth);
		ArrayList<String> listStr = vocab.getWords(w, lth);
		
		for(int i=0; i<listStr.size(); i++){
			double prob = unigram.getProbs(listInts.get(i));
			String word = listStr.get(i);
			System.out.print("PML(w = " + word + ") = " );
			System.out.println(new DecimalFormat("#0.00000000").format(prob));
		}
	}
	
	/**
	 * Prints out the PML of a bigram model given a word
	 * @param curr - word to compute bigram model for
	 */
	private static void PMLBigram(String curr) {
		int index = vocab.getIndex(curr);
		LinkedList<fourTuple> temp = bigram.getProbs(index);
		fourTuple[] mostProb = new fourTuple[temp.size()];
		//loading values
		for(int i=0; i<temp.size();i++) {
			mostProb[i] = temp.get(i);
		}
		
		mostProb = sort(mostProb);
		
		for(int i=0; i<10; i++) {
			int index1 = mostProb[i].getIndex1() - 1;
			int index2 = mostProb[i].getIndex2() - 1;
			
			System.out.print("PML(w' = " + vocab.get(index2) + "|");
			System.out.print("w = " + vocab.get(index1) + ") = ");
			System.out.println(new DecimalFormat("#0.00000000").format(mostProb[i].getProb()));
		}
	}
	
	/**
	 * Sorts from larger to smaller according to fourTuples probabilities
	 * @param arr
	 * @return
	 */
	private static fourTuple[] sort(fourTuple[] arr) {
		
		for(int i = 0; i < arr.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j].getProb() > arr[index].getProb())
                    index = j;
      
            fourTuple smaller = arr[index]; 
            arr[index] = arr[i];
            arr[i] = smaller;
        }
        return arr;
	}
	
	/**
	 * Computes the log of the product of the probabilities of each word on a given sentence
	 * for the unigram model
	 * 
	 * @param sentence - given sentence to compute
	 * @return log of the product of all probabilities
	 */
	private static double pLu(String sentence) {
		String str[] = sentence.replaceAll("[^a-zA-Z ]", "").toUpperCase().split(" ");
		double p = 1;
		
		for(String s : str) {
			int index = vocab.getIndex(s);
			p *= unigram.getProbs(index);
		}
		return Math.log(p);
	}
	
	/**
	 * Computes the log of the product of the probabilities of each word on a given sentence
	 * for the bigram model
	 * 
	 * @param sentence - given sentence to compute
	 * @return log of the product of probabilities for bigram model or zero
	 */
	private static double pLb(String sentence) {
		String str[] = sentence.replaceAll("[^a-zA-Z ]", "").toUpperCase().split(" ");
		String beg = "<s>";
		double p = 1;
		LinkedList<fourTuple> curr = bigram.getProbs(vocab.getIndex(beg));

		//starting prob
		for(fourTuple l : curr){
			if(l.getIndex2() == (vocab.getIndex(str[0])+1)) { p = l.getProb(); break;}
		}
		if( p == 1) p = 0;
		
		//rest of sentence
		if(str.length > 1) {
			for(int i=0; i<str.length - 1; i++) {
				double old = p;
				LinkedList<fourTuple> temp = bigram.getProbs(vocab.getIndex(str[i]));
				for(fourTuple l : temp) {
					if(l.getIndex2() == (vocab.getIndex(str[i+1]) + 1)) {
							p *= l.getProb(); 
							break;
						}
				}
				if( p == old) {p=0; break;}
			}
		}		
		return p == 0 ? 0 : Math.log(p);
	}
	
	/**
	 * Prints the mix model given a sentence
	 * 
	 * @param sentence - given sentence to compute
	 */
	private static void mix(String sentence) {
		
		for(double lambda = 0; lambda <= 1; lambda += 0.01) {
			System.out.println("Lambda = " + new DecimalFormat("#0.00").format(lambda) + " AND Lm = " + 
								new DecimalFormat("#0.00").format(computeMix(lambda, sentence)));
		}
	}
	
	/**
	 * Computes the mix model, that is, a mixture between the bigram and unigram model
	 * 
	 * @param lambda - lambda variable
	 * @param sentence - sentence to compute
	 * @return returns probability of mix model
	 */
	private static double computeMix(double lambda, String sentence) {
		return (((1-lambda) * pLu(sentence)) + (lambda * pLb(sentence)));
	}
}
