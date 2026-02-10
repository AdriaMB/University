#include <stdlib.h>
#include <stdio.h>
#include <string.h>
 
int sharedVar = 0;

int main() {
    int i;
    int aux_variable;
    long iterations = 40000000;
    for (i=0; i<(iterations); i++) {
        aux_variable = sharedVar;
        aux_variable++;
        sharedVar = aux_variable;
    }
    printf("Shared Variable= %d\n",sharedVar);
  
    return 0;
}
