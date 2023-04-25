#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <ctype.h>
#include <stdbool.h>

int addName(char **hashTable, char *name, int hashValue);
void removeName(char **hashTable, char *name, int hashValue);
bool searchName(char **hashTable, char *name, int hashValue);
int hash(char *name);
int StrCmp(const char* str1, const char* str2);

//Orginal size of hash table
int sizeOfTable = 6000;
//newTableSize used to increase size of table
int newTableSize;

int main() {	
	newTableSize = sizeOfTable;
	
	char** namesHashTable = malloc(sizeOfTable * sizeof(char *));
	char *name;
	name = malloc(50 * sizeof(char *));
	int hashDes = 0;
	char ch;
	int character = 0;
	int index;
	
	FILE *namesFile = fopen("names.txt","r");
	
	//Reads in the names to be placed in the hash table
	while((ch = fgetc(namesFile)) != EOF) {
		if (isalpha(ch) != 0){
			name[character] = ch;
			character++;
		} else if (ch == ','){
			name[character] = '\0';
			
			//Gets hash of name and add name to hash table
			index = hash(name);
			hashDes = addName(namesHashTable, name, index);
			namesHashTable[hashDes] = name;
			
			name = malloc(50*sizeof(char));
			character = 0;
		}
	}
	
	//Demontrates removing names
	name = "ABRAM";//At 6005 in table
	index = hash(name);
	removeName(namesHashTable, name, index);
	
	//Prints the hash table
	for (int i = 0; i < newTableSize; i++){
		if (namesHashTable[i] == NULL){
			printf("%d : %s :\n", i, namesHashTable[i]);
		} else {
			printf("%d : %s : %d\n", i, namesHashTable[i], hash(namesHashTable[i]));
		}
	}
	
	//Demonstrates finding names
	name = "CHASE";
	index = hash(name);
	bool found = searchName(namesHashTable, name, index);
	if (found == true){
		printf("Found CHASE\n");
	}
	return 0;
}

int addName(char **hashTable, char *name, int hashValue){
	//When looking for a free space reaches the end of the table
	if (hashValue > newTableSize-1){
		newTableSize++;
		
		//Creates new table that is 1 larger the current hashtable
		char **namesHashTable2;
		namesHashTable2 = malloc(newTableSize * sizeof(char *));
		
		//points to the new table
		hashTable = namesHashTable2;
		return hashValue;
	}
	//When there is a free space
	if (hashTable[hashValue] == NULL){
		return hashValue;
	//If the name is already in the hash table
	} else if (hashTable[hashValue] == name){
		return hashValue;
	} else {
		addName(hashTable, name, hashValue+1);
	}
}

void removeName(char **hashTable, char *name, int hashValue){
	//Couldn't find the name
	if (hashValue > newTableSize){
		return;
	}
	//Compares the names in the hash table with the name given
	if (StrCmp(hashTable[hashValue],name) == 0){
		hashTable[hashValue] = NULL;
		return;
	} else {
		removeName(hashTable, name, hashValue+1);
	}
}

bool searchName(char **hashTable, char *name, int hashValue){
	bool found = false;
	//Compares the names in the hash table with the name given
	if (StrCmp(hashTable[hashValue],name) == 0) {
		found = true;
		printf("\n%d - ", hashValue);
	} else {
		found = searchName(hashTable, name, hashValue+1);
	}
	return found;
}

//Puts the name through the hash function
int hash(char *name){
	
	unsigned int hash = 0;
	//Loops until end of word
    for (int i = 0 ; name[i] != '\0' ; i++)
    {
        hash = 31*hash + name[i];
    }
	//Mod hash to the size of the table
    return hash % sizeOfTable;
}

//Replaces the strcmp function to compare strings
int StrCmp(const char* str1, const char* str2){
	while (true){
		//Loops and compares each letter in the strings given
		if (*str1 != '\0'){
			if (*str2 == '\0'){
				 return  1;
			} else if (*str2 > *str1){
				 return -1;
			} else if (*str1 > *str2){
				 return 1;
			}
		} else {
			break;
		}
		str1++;
        str2++;
	}
	
	if (*str2 != '\0'){
		return -1;
	}

    return 0;
}