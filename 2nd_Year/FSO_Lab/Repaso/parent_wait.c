// ALL THESE LIBRARIES ARE NECESSARY
#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>    // exit(),
#include<sys/types.h> // fork(), wait()
#include<sys/wait.h>

int main(int argc, char* argv[]){
    int i;
    for(i = 0; i < 5; i++){
        if(fork() == 0){
            printf("Child created in iteration = %d\n", i);
            sleep(6);
            exit(i);
        }
    }
    sleep(3);
    int *status;
    while(wait(status) != -1);
    exit(0);

}
