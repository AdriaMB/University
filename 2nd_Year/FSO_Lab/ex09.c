#include <stdio.h>
#include <sys/wait.h>           // pid_t wait(int *status);
#include <sys/types.h>
#include <errno.h>
#include <stdlib.h>
#include <unistd.h>             // it includes the fork(), getpid(), getppid() and sleep()


#define NPROCESSES 4

int main(void)
{
    pid_t pid[NPROCESSES];
    int i, status;

    for(i = 0; i < NPROCESSES; i++){
        pid[i] = fork();
        if(pid[i] == 0){

            printf("I am the child %ld my parent is %ld\n", (long)getpid(), (long)getppid());
            sleep(10);
            exit(0);
        }

    }
    //NOW WAITING FOR THE THIRD CHILD
    if(waitpid(pid[2], &status, 0) == pid[2]){
        printf("My third child has finished\n");
    }

    return 0;

}
