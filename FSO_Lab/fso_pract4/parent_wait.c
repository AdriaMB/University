#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main(){
    int val = 0;
    int status;
    pid_t pid[5];

    for(int i = 0; i < 5; i++){
        val = fork();
        pid[i] = val;
        if(val == 0){
            printf("Child created in iteration = %d\n", i);
            exit(i);
        }
    }
    sleep(10);
    while(wait(&status)>0);
    exit(0);
}
