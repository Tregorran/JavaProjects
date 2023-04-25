#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

char* sort(int keyLen, int rows, char table[rows][keyLen]);
void swap(char *first, char *second);
int StringLength(const char *word);

int main() {
	char keyWord[100];
	char textPath[100];
	
	printf("Enter path of a block of text to encrypt(.txt): ");
    scanf("%s", textPath);
	
	printf("Enter your keyword to encrypt the text: ");
    scanf("%s", keyWord);

	FILE *textFile = fopen(textPath,"r");
	FILE *encryptedFile = fopen("EncryptedText.txt","w");
	
	char ch;
	char word[100];
	int character = 0;
	char symbol;
	
	//Read each character until a symbol is found then characters found so far is a word
	while((ch = fgetc(textFile)) != EOF) {
		if (isalpha(ch) != 0){
			word[character] = ch;
			character++;
		} else {
			symbol = ch;
			word[character] = '\0';
			
			int keyLen = StringLength(keyWord);
			int wordLen = StringLength(word);

			//How many letters are on the last row
			int remain = wordLen % keyLen;
			
			//Number of rows including keyword
			int rows = (wordLen/keyLen) + 1;
			if (remain != 0){
				rows += 1;
			}
			
			char table[rows][keyLen];
			
			//Add keyword to top of array
			for (int i = 0; i < keyLen; i++){
				table[0][i] = keyWord[i];
			}
			
			//Add text to array
			int k = 0;
			for (int j = 1; j < rows; j++){
				for (int i = 0; i < keyLen; i++){
					table[j][i] = word[k];
					k++;
				}
			}
			
			//Add xs for missing letters
			if (remain != 0){
				for (int i = remain; i < keyLen; i++){
					table[rows-1][i] = 'x';
				}
			}
			
			//Sort the keyword and the columns
			char *sortedWords;
			sortedWords = sort(keyLen, rows, table);
			
			//Write word to file
			fputs(sortedWords, encryptedFile);
			fputc(symbol, encryptedFile);
			
			//Reset for next word to encrypt
			memset(word, 0, sizeof(word));
			character = 0;
		}
	}
	
	printf("Completed\n");
	
	return 0;
}

//Sorts the letters in the keyword in order including the columns
char* sort(int keyLen, int rows, char table[rows][keyLen]){
	int true = 1;
	
	while(true == 1) {
		true = 0;
		
		//sorts the keyword and columns
		for (int i = 0; i < keyLen-1; i++) {
			if (table[0][i] > table[0][i+1]){
				for (int j = 0; j < rows; j++){
					swap(&table[j][i], &table[j][i+1]);
				}
				true = 1;
			}
		}
	}
	
	//Stores the table into a 1d array of just the encrypted text
	char *sortedWord = (char*)malloc(100 * sizeof(char));
	int k = 0;
	for (int i = 1; i < rows; i++){
		for (int j = 0; j < keyLen; j++){
			sortedWord[k] = table[i][j];
			k++;
		}
	}

	return sortedWord;
}

//Swaps the values of the two pointers
void swap(char *first, char *second){
	char temp = *first;
	*first = *second;
	*second = temp;
}

//Calculates the length of the char array
int StringLength(const char *word){
	int i = 0;
	for (i = 0; word[i] != '\0'; i++);
	return i;
}