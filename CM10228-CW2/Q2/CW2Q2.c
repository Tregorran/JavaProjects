#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <stdbool.h>

int numOfnames;

void QuickSort(char **NamesArray, int start, int end);
int compare(char *name1, char *name2);
void swap(char **a, char **b);
int StringLength(const char *word);

int main(){
	numOfnames = 0;
	
	FILE *nameFile = fopen("names.txt","r");
	
	if (nameFile == NULL){
		printf("Cannot open file.\n");
        return 1;
	} else {
		char ch;
		//Counts the number of names in the text file
		while ((ch = fgetc(nameFile)) != EOF){
			if (ch == ','){
				numOfnames++;
			}
		}
		rewind(nameFile);
	}
	
	char **names;
	names = malloc(numOfnames * sizeof(char *));
	
	int column = 0;
	int row = 0;
	char ch;
	
	names[row] = malloc(50 * sizeof(char));
	
	//Reads each word into the 2d array "names"
	while ((ch = fgetc(nameFile)) != EOF){
		if (ch == ','){
			names[row][column] = '\0';
			row++;
			names[row] = malloc(50*sizeof(char));
			column = 0;
		} else {
			names[row][column] = ch;
			column++;
		}
	}
	int i = 0;
	
	QuickSort(names, 0, numOfnames);
	
	//Writes the quicksorted list of names into the file sortedNames
	FILE *sortedFile = fopen("sortedNames.txt", "w"); 
	fputs(names[0], sortedFile);
	for (int i = 1; i < numOfnames+1; i ++){

		fputs(",", sortedFile);
		fputs(names[i], sortedFile);
	}
	
	printf("Completed\n");
	fclose(nameFile);
	return 0;
}

//Quick sorts the array between a start and an end by using the end as the pivot 
void QuickSort(char **NamesArray, int start, int end){
	
	int pivot = end;
	
	bool stop = false;
	/*Compares a words at one end with the pivot and compares a words at the other end with the pivot
	 * if the first word is larger than the pivot and the other word is smaller than the pivot swap places.
	 */
	for (int i = start; (i < end) && !stop; i++){
		for (int j = end-1; (j > start-1) && !stop; j--) {
			if (compare(NamesArray[i], NamesArray[end]) >= 0){
				if (compare(NamesArray[j], NamesArray[end]) <= 0){
					swap(&NamesArray[i], &NamesArray[j]);
					break;
				}
			} else {
				break;
			}
			
			//Have reached a mid point, places the pivot there.
			if (i == j){
				for (int k = 0; k < end; k++){
					if (compare(NamesArray[k], NamesArray[end]) > 0){
						swap(&NamesArray[k], &NamesArray[end]);
						pivot = k;
						break;
						stop = true;
					}
				}
			}
		}
	}
	
	//Checks if the above or below the pivot have been fully sorted
	for (int i = start; i < pivot; i++) {
		if (compare(NamesArray[i], NamesArray[i+1]) > 0) {
			QuickSort(NamesArray, start, pivot-1);
		}
	}
	
	for (int i = pivot+1; i < end; i++){
		if (compare(NamesArray[i], NamesArray[i+1]) > 0) {
			QuickSort(NamesArray, pivot+1, end);
		}
	}
}

//Comapares the names given to find out the difference in the alphabetical order
int compare(char *name1, char *name2){
	
	int difference = 0;
	//First check which name to use for the for loop so that it doesn't go out of range
	if (StringLength(name1) > StringLength(name2)){
		for (int i = 0; i < StringLength(name2); i++){
			if (name1[i] != name2[i]){
				difference = name1[i] - name2[i];
				return difference;
			}
		}
	} else {
		for (int i = 0; i < StringLength(name1); i++){
			if (name1[i] != name2[i]){
				difference = name1[i] - name2[i];
				return difference;
			}
		}
	}
	
	return difference;
}

//Swaps the "position" of the names
void swap(char **a, char **b) {
	char *temp = *a;
	*a = *b;
	*b = temp;
}

//Calculates the length of the given char array
int StringLength(const char *word){
	int i = 0;
	for (i = 0; word[i] != '\0'; i++);
	return i;
}