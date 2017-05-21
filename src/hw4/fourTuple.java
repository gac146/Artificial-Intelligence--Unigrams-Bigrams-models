package hw4;

public class fourTuple {
	
	private int index1; //word at position w
	private int index2; //word at position w-1
	private int count; //number of occurrences of the sequence (w | w-1) in the given corpus
	private double prob; //probability of given sequence of words
	
	/**
	 * Constructor
	 * 
	 * @param i1 - word at position w
	 * @param i2 - word at position (w-1)
	 * @param count - count of given sequence (w | w-1)
	 * @param prob - probability of sequence. P(w | w-1)
	 */
	public fourTuple(int i1, int i2, int count, double prob){
		index1 = i1;
		index2 = i2;
		this.count = count;
		this.prob = prob;
	}
	
	/**
	 * @return the 'w' word of the fourTuple
	 */
	public int getIndex1() {
		return index1;
	}
	
	/** 
	 * @return the 'w-1' word of the fourTuple
	 */
	public int getIndex2() {
		return index2;
	}
	
	/**
	 * @return count of occurrences of sequence in the fourTuple
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * @return probability of the sequence in the fourTuple
	 */
	public double getProb() {
		return prob;
	}
	
	/**
	 * Sets the probability of the fourTuple. 
	 * 
	 * @param newProb - New probability of sequence in fourTuple
	 */
	public void setProb(double newProb) {
		prob = newProb;
	}
}
