#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(){
    int val = 0;
    for(int i = 0; i < 5; i++){
        val = fork();
        if(val == 0){
            printf("Child created in iteration = %d\n", i);
            exit(i);
        }
    }
    sleep(10);
    exit(0);
}
