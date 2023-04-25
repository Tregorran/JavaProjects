#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define bufferSize 5000

void replaceWords(char *line, char *redactWord);
char* CopyString(char *to, const char *from);
char* findWord(char *line, char *redactWord, int length);
char* StrCat(char *to, const char *from);
int compare(const char *begin, const char *pattern);
int StringLength(const char *word);
int ToLower(int c);

int main() {
    char textPath[100];
	char redactPath[100];
    char buffer[bufferSize];
	
	printf("Enter path of the block of text(.txt): ");
    scanf("%s", textPath);
	
	printf("Enter path of redact list(.txt): ");
    scanf("%s", redactPath);
	
	FILE *textFile = fopen(textPath,"r");
	FILE *redactFile = fopen(redactPath,"r");
	FILE *redactedFile = fopen("redactedText", "w"); 
	
	//Counts number of redact words in list
	int count = 0;
	if (redactFile == NULL){
		printf("Cannot open file %s.\n", redactPath);
        return 1;
	} else {
		char ch;
		while ((ch = fgetc(redactFile)) != EOF){
			if (ch == '\n'){
				count++;
			}
		}
		count += 1;
		rewind(redactFile);
	}
	
	//Reads in contents of redact list
	char **redactWords;
	redactWords = malloc(count * sizeof(char *));
	
	int row = 0;
	int column = 0;
	char ch;
	
	//Stores each word from the redact list file
	redactWords[row] = malloc(50 * sizeof(char));
	while ((ch = fgetc(redactFile)) != EOF) {
		if (ch == '\n'){
			row++;
			redactWords[row] = malloc(50 * sizeof(char));
			column = 0;
		} else {
			redactWords[row][column] = ch;
			column++;
		}
	}
	
    if (textFile == NULL) {
        printf("\nCould not open %s.\n", textPath);
		return 1;
    }

	//Read each line from the textFile
    while ((fgets(buffer, bufferSize, textFile)) != NULL) {
		
		for (int i = 0; i < count; i++) {
			replaceWords(buffer, redactWords[i]);	
		}
	
		//Write out the line with redacted words into the redactedFile
        fputs(buffer, redactedFile);
    }

    fclose(textFile);
	fclose(redactFile);
    fclose(redactedFile);
	
	printf("Completed\n");
	
    return 0;
}

//Replace words in the line given by the redact list
void replaceWords(char *line, char *redactWord) {
	char lineTemp[bufferSize];
	char *pos;
	int redactLen;
    int index = 0;
	
	redactLen = StringLength(redactWord);
	
	//finds position of each redact word in the line
    while ((pos = findWord(line, redactWord, redactLen)) != NULL) {
		//For number of stars
		int stars = redactLen;		
		
        //Copies the line
        CopyString(lineTemp, line);

        //Gets the index of the found word
        index = pos - line;
		
		//Add terminating character after each word
        line[index] = '\0';

        //Concatenate line with stars
		while (stars > 0){
			StrCat(line, "*");
			stars -=1;
		}
        
        //Concatenate line, with remaining words after
        StrCat(line, lineTemp + index + redactLen);
    }
}

//Find the redact word in the line, returns the pointer to that position
char* findWord(char *line, char *redactWord, int length) {
	
	//Where the line starts
	char *begin = line;
	
	//The redact word;
	char *redactPattern = redactWord;
	
	while (*begin != '\0') {
		//Checks if the redact word is found at the position
		if ((ToLower(*begin) == ToLower(*redactPattern)) && compare(begin, redactPattern)) {
			
			char *before = begin-1;
			char *after = begin;
			
			//Checks that there isn't a letter before or after the word to redact, otherwise don't redact it
			if ((before[0]>='a' && before[0]<='z') || (before[0]>='A' && before[0]<='Z')) {
				begin++;
			} else if ((after[length]>='a' && after[length]<='z') || (after[length]>='A' && after[length]<='Z')) {
				begin++;
			} else {
				return begin;
			}
		}
		begin++;
	}

	return NULL;
}

//Checks at "begin" if the redact word is there
int compare(const char *begin, const char *pattern) {
	//When begin and pattern are the same
	while (ToLower(*begin) == ToLower(*pattern)) {
		if (ToLower(*begin) != ToLower(*pattern)) {
			return 0;
		}
		begin++;
		pattern++;
	}
	
	//Returns 1 or 0 depending if the redactWord is terminating character
	return (*pattern == '\0');
}

//Calculates length of char array
int StringLength(const char *word){
	int i = 0;
	for (i = 0; word[i] != '\0'; i++);
	return i;
}

//Copies contents of char array to a different char array
char* CopyString(char *to, const char *from){
	int i;
	for (i = 0; from[i] != '\0'; i++){
		to[i] = from[i];
	}

	to[i]= '\0';

	return to;
}

//Appends string pointed to, to the end of "to"
char* StrCat(char *to, const char *from){
    CopyString(to + StringLength(to), from);
    return to;
}

//Replaces tolower
int ToLower(int c) {
	
	if ((c >= 65) && (c <= 90)){
		c += 32;
	}
	
	return c;
}