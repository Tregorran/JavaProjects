public class Question1a {
    public static void main(String[] args) {
		
		ProgTimer("hello", 1);
		
		System.out.println(ProgTimer("hello", 1));
		System.out.println(ProgTimer("hello", 100));
		System.out.println(ProgTimer("hello", 1000));
		System.out.println(ProgTimer("hello", 10000));
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
}
