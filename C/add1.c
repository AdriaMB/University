#include <stdio.h>

int main(){
    printf("Please, enter a number: \n");
    int N;
    scanf("%d", &N);
    int add = 0;
    while(N > 0){
        add = add + N;
        N--;
    }
    printf("The addition is = %d \n", add);
    return 0;
}