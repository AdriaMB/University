#include <stdio.h>
#include <ctype.h>
#define SIZE_STRING 200

int main(){
    //Character pointers to cpy the input string
    char *p1, *p2;

    //Define the string variables
    char string[SIZE_STRING];
    char string2[SIZE_STRING];

    //Read from console
    scanf("%[^\n]s", string);

    //Convert to uppercase
    p1 = string;
    p2 = string2;
    while( *p1 != '\0'){


        if(*p1 != toupper(*p1)) *p1 = *p1 - 32;
        *p2 = *p1;

        p1++;
        p2++;
    }

    //Append the null value at the end of the string
    *p2 = '\0';

    //OUT string2;

    p2 = string2;
    printf("%s\n", string2);
}
