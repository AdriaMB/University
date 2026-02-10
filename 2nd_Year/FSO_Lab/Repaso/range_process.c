// ALL THESE LIBRARIES ARE NECESSARY
#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>    // exit(),
#include<sys/types.h> // fork(), wait()

int main(int argc, char* argv[]){
    int i;
    for(i = 0; i < 5; i++){
        if(fork() == 0){
            printf("Child created in iteration = %d\n", i);
            sleep(20);
            exit(i);
        }
    }
    sleep(10);
    exit(0);

}
