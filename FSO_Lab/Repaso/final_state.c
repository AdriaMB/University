// ALL THESE LIBRARIES ARE NECESSARY
#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>    // exit(),
#include<sys/types.h> // fork(), wait()
#include<sys/wait.h>

int main(int argc, char* argv[]){
    int i;
    pid_t val_return;
    int final_state;
    for(i = 0; i < 4; i++){
        val_return = fork(); // fork() returns pid_t that will be stored
        if(val_return == 0){
            printf("Child %ld created in iteration %d \n", (long)getpid(), i);
        }else{
            printf("Parent %ld, iteration %d\n", (long)getpid(), i);
            printf("I have created a child %ld\n", (long)val_return);
            break;
        }
    }
    while(wait(&final_state) > 0){
        printf("Parent %ld iteration %d\n", (long)getpid(), i);
        printf("My child said %ld\n", WEXITSTATUS(final_state));
        printf("My child said %d\n", final_state/256);
    }
    exit(i);
}
