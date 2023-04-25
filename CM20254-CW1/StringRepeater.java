public class StringRepeater {
	
	public StringBuffer repeatString(String s, int n){
        StringBuffer result = new StringBuffer();
        for(int i=0; i<n; i++) {
			result.append(s);
        }
        return result;
    }
}
