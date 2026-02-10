#include <stdio.h>
#include <sys/wait.h>           // pid_t wait(int *status);
#include <sys/types.h>
#include <errno.h>
//#include <stdlib.h>
#include <unistd.h>             // it includes the fork(), getpid(), getppid() and sleep()



int main(void)
{
    int status;
    pid_t pid = fork();

    switch(pid){
        case -1:
            printf(" The child process could not be created\n");
            break;
        case 0:
            printf("I am the child process with PID %ld and my parent is %ld\n", (long)getpid(), (long)getppid());
            sleep(20);
            printf("I have finished \n");
            break;
        default:
            printf("I am the parent process with PID %ld and my child is %d. Waiting... \n", (long)getpid(), pid);
            if(wait(&status) != -1){
                printf("My child has ended ok\n");
            }
            break;
    }

    return 0;

}
