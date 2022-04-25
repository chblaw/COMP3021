package lab9;

/**
 * 
 * COMP 3021
 * 
 * This is a class that prints the maximum value of a given array of 90 elements
 * 
 * This is a single threaded version.
 * 
 * Create a multi-thread version with 3 threads:
 * 
 * one thread finds the max among the cells [0,29] another thread the max among
 * the cells [30,59] another thread the max among the cells [60,89]
 * 
 * Compare the results of the three threads and print at console the max value.
 * 
 * 
 * @author valerio
 *
 */
public class FindMax implements Runnable {
	// this is an array of 90 elements
	// the max value of this array is 9999
	static int[] array = { 1, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2, 3, 4543,
			234, 3, 454, 1, 2, 3, 1, 9999, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3, 1, 34, 5, 6, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3 };

	private int begin, end, result;

	public static void main(String[] args) throws InterruptedException {
//		new FindMax().printMax();
		FindMax FindMax1 = new FindMax(0, 29);
		FindMax FindMax2 = new FindMax(30, 59);
		FindMax FindMax3 = new FindMax(60, 89);

		Thread t1 = new Thread(FindMax1);
		Thread t2 = new Thread(FindMax2);
		Thread t3 = new Thread(FindMax3);

		t1.start();
		t2.start();
		t3.start();

		t1.join();
		t2.join();
		t3.join();

		int t1Result = FindMax1.getResult();
		int t2Result = FindMax2.getResult();
		int t3Result = FindMax3.getResult();

		int max = Math.max(t1Result, t2Result);
		max = Math.max(max, t3Result);

		System.out.println("the max value is " + max);
	}

	public void printMax() {
		// this is a single threaded version
		int max = findMax(0, array.length - 1);
		System.out.println("the max value is " + max);
	}

	/**
	 * returns the max value in the array within a give range [begin,range]
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private int findMax(int begin, int end) {
		// you should NOT change this function
		int max = array[begin];
		for (int i = begin + 1; i <= end; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}

	public FindMax(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}

	public int getResult() {
		return this.result;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		result = findMax(begin, end);
	}
}
