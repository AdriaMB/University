#include<stdio.h>
#include<ctype.h>
#define SIZE_STRING 200

int main() {
    // Character pointers to copy the input string
    char *p1, *p2;
    // A) Define the string variables string and string2
    char string[SIZE_STRING];
    char string2[SIZE_STRING];
    // B) Read string in the console
    scanf("%[^\n]s", string);

    // C) Convert to uppercase
    p1 = string;
    p2 = string2;
    while (*p1 != '\0') {
        // Copy p1 to p2 subtracting 32 if necessary
        if(*p1 != toupper(*p1)) *p1 = *p1 - 32;
        *p2 = *p1;
        p1++;
        p2++;
    }
    // Remember to append the null value at the end of string2
    *p2 = '\0';

    // D) Out in the console string2.
    printf("%s\n", string2);


    return 0;
}
