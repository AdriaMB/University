#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int Globalvariable;

int main(){
    int i;
    long iterations = 4000000;
    for(i = 0; i < (iterations); i++){
        Globalvariable++;

    }
    printf("Globalvariable= %d\n", Globalvariable);

    return 0;
}
