#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<unistd.h>

int main(int argc, char *argv[]){
    pid_t childpid;
    int status, x;
    char* arguments[] = {"ls", "-R", "/", 0};

    childpid = fork();
    if(childpid == -1){
        printf("fork failed\n");
        exit(1);
    } else if(childpid == 0){
        if(execvp(arguments[0], arguments) < 0){
            printf("Could not execute: ls\n");
            exit(1);
        }
    }
    x = wait(&status);
    if(x != childpid){
        printf("Child has been interrupted by a signal\n");
    }
    exit(0);
}
