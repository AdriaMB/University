#include <stdio.h>


int main(){
    char Data[5] = {'a','b','c','d','e'};
    char *p;
    int i;
    p = Data+2;
//    printf(*p);
//    printf("\n");

    p = Data;
//    printf(*p);
//    printf("\n");

    for(i = 0; i < 5; i++){
        printf("Data[%u]=%c \n", i, Data[i]);
        printf("Data[%u]=%c \n", i, (*p-32));

    }
}


