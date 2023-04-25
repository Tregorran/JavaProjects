public class Sorting {
	public static void insertionSort (int[][] data) {
		//2d array has only one row or less
		if (data.length < 2){
			return;
		}
		
		int rows = data.length;		
		int columns = data[0].length;
		
		int markerRow = 1;
		int[] temp;
		
		//sorts arrays
		while (markerRow != rows){
			for (int i = markerRow; i > 0; i--){
				int compared = compareLines(data[i-1], data[i]);
				if (compared == 1){
					temp = data[i];
					data[i] = data[i-1];
					data[i-1] = temp;
				} else if (compared == -1){
					break;
				}
			}
			markerRow++;
		}
	}
	
	     
   static int[][] mergeSort(int data[][]){
		int[][] newData = data;
        newData = inPlaceSort(newData, 0 ,newData.length-1);
		return newData;
    } 
	
	

	public static int[][] inPlaceSort(int data[][], int start, int end){
		if (start < end){
			//split in half
            int middle = start+(end-start)/2;
			
			//split left and right
            inPlaceSort(data, start, middle);
			inPlaceSort(data, middle+1, end);
			
            return merge(data, start, middle, end); 
        } else {
			return data;
		}
	}
	
	
	
	static int[][] merge(int data[][], int start, int middle, int end) 
    {
		//get the other start index
		int otherStart = middle + 1;
		
		while ((start <= middle) && ((otherStart) <= end)) {
			//in the right order
			if (compareLines(data[start], data[otherStart]) <= -1) { 
                start++;
            } else {
				//shift to the right by one
				int temp[] = data[otherStart];
				
				for (int i = otherStart; i > start; i--){
					data[i] = data[i-1];
				}
                data[start] = temp;
				
				start++; 
                middle++;
				otherStart++;
			}
		}
		return data;
    } 
	
	public static int[][] hybridSort(int[][] list, int threshold){
		int[][] newList = list;
		return hybridPlaceSort(newList, 0, newList.length-1, threshold);
	}
	
	public static int[][] hybridPlaceSort(int[][] data, int start, int end, int threshold){
		if (start < end){
			//split in half
			int middle = start+(end-start)/2;
			int size = end-start+1;
			
			if (size <= threshold){
				hybridInsert(data, start, end);
				return data;
			}
			
			hybridPlaceSort(data, start, middle, threshold);
			hybridPlaceSort(data, middle+1, end, threshold);
			
			return merge(data, start, middle, end); 
		} else {
			return data;
		}
	}
	

	public static int[][] hybridInsert (int[][] data, int start, int end) {
		//2d array has only one row or less
		int length = end - start;
		
		if (length < 2){
			return data;
		}
		
		int rows = length+1;		
		int columns = data[0].length;
		
		int markerRow = start;
		int[] temp;
		
		//sort arrays
		while (markerRow != start+rows){
			for (int i = markerRow; i > start; i--){
				int compared = compareLines(data[i-1], data[i]);
				if (compared == 1){
					temp = data[i];
					data[i] = data[i-1];
					data[i-1] = temp;
				} else if (compared == -1){
					break;
				}
			}
			markerRow++;
		}
		return data;
	}

	static int compareLines(int[] a, int[] b) {  // You may make this public if you wish
	  int n = a.length;
	  if (n != b.length)
		  return (a[b.length-1]+b[a.length-1]);  // this gives an error
	  int i = 0;
	  while (i < n && a[i] == b[i])
		  i++;   // skip equal elements
	  if (i == n)
		  return 0;
	  if (a[i] < b[i])
		  return -1;
	  else return 1;
	}
}