/* change_memory1.c */
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int main(int argc, char *argv[])
{
    pid_t childpid;
    int status, x;
    //char* arguments[] = {"ls", "-R", "/", NULL};
    for(int i = 1; i < argc; i++ ){


    }


    childpid = fork();
    if (childpid == -1) {
        fprintf(stderr, "fork failed \n");
        exit(1);
    } else if (childpid == 0) {
        if (execvp(argv[1], &argv[2] ) < 0) {
            fprintf(stderr, "Could not execute ls \n");
            exit(1);
        }
    }
    x = wait(&status);
    if (x != childpid){
        fprintf(stderr, "Child has been interrupted by a signal \n");
    }
    /*else{
        printf("PID: %ld,   ChildPID: %ld,   PPID: %ld\n", (long)getpid(), (long)childpid, (long)getppid());
    }
    */
    exit(0);
}

/**
 * Con la ejecución ./change_memory2 ps -l, argv contendrá
 * {"./change_memory2", "ps", "-l"}
 * Como al ejecutar execvp el PATH ya está incluido (./change_memory2)
 *
 */
