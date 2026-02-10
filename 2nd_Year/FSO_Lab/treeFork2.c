#include <stdio.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>


#define NPROCESSES 3
int main(void){

    pid_t pid;

    printf("I am parent with pid %ld\n", getpid());

   int i = 0;
    for(; i<NPROCESSES; i++){

        for(int j = 0; j < NPROCESSES-i; j++){
            pid = fork();
            if(pid == 0){
                printf("I am a child with PID %ld, and my parent is PID %ld\n", (long)getpid(), (long)getppid());
                break;
            }

        }

        if(pid != 0){
            exit(0);
        }

    }


}




/*
 for(int i = 0 ; i<NPROCESSES; i++){

     if( i == 0  && getpid() != 0){
         pid = fork();

         }
         if( i <= 1  && getpid() != 0){
             pid = fork();
             }
             if( i <= 2  && getpid() != 0){
                 pid = fork();
                 }

                 switch(pid){
                     case -1:
                         printf("An error has occurred\n");
                         break;
                     case 0:
                         printf("I am a child with PID %ld, and my parent is PID %ld\n", (long)getpid(), (long)getppid());
                         break;
                     default:
                         printf("I am parent with PID %ld\n", (long)getpid());
                         exit(0);

                         }
                         *
*/
