public class Question1b {
    public static void main(String[] args) {
	
		ProgTimer("hello", 1);
		ProgTimerOp("hello", 1);
	
		System.out.println(ProgTimer("hello", 1));
		System.out.println(ProgTimer("hello", 100));
		System.out.println(ProgTimer("hello", 1000));
		System.out.println(ProgTimer("hello", 10000));
		
		System.out.println(ProgTimerOp("hello", 1));
		System.out.println(ProgTimerOp("hello", 100));
		System.out.println(ProgTimerOp("hello", 1000));
		System.out.println(ProgTimerOp("hello", 10000));
	}
	
	public static String ProgTimer(String s, int n){
	
		long start;
		long end;
		long result;
		double seconds;
		
		StringRepeater stringRepeater = new StringRepeater();
		
		//Times the method
		start = System.nanoTime();
		stringRepeater.repeatString(s, n);
		end = System.nanoTime();
		
		result = end - start;//Calculates how long the method takes
		seconds = (double)result / 1_000_000_000;//Converts result to seconds
		
		return("T(" + n + ") = " + (Double.toString(seconds)) + " seconds");
	}
	
	public static String ProgTimerOp(String s, int n){
	
		long start;
		long end;
		double total;
		long numRepeats = 1000;
		double mean;
		double seconds;
		
		StringRepeater stringRepeater = new StringRepeater();
		
		start = System.nanoTime();
		
		for (int i=0; i<numRepeats; i++){
			stringRepeater.repeatString(s, n);
		}
		
		end = System.nanoTime();
		
		total = end-start;//The total time for 1000 repeats of the method
		mean = total/numRepeats;
		seconds = (double)mean / 1_000_000_000;

		return("T(" + n + ") = " + (Double.toString(seconds)) + " seconds");
	}
}
