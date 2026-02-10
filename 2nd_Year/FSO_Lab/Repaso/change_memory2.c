#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<unistd.h>

int main(int argc, char *argv[]){
    pid_t childpid;
    int status, x;
   // char* arguments[] = {"ls", "-R", "/", 0};

    childpid = fork();
    if(childpid == -1){
        printf("fork failed\n");
        exit(1);
    } else if(childpid == 0){
        //Debemos pasar 1) el nombre del file que vamos a ejecutar[const char *file], y 2) el argv que tendría el archivo en su ejecución normal[char *const argv[]], que incluye tanto el nombre del archivo como de sus otros argumentos
        if(execvp(argv[1], &argv[1]) < 0){
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
