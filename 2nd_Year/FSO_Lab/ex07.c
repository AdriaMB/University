#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

int main(void)
{
    int status;
    pid_t pid = fork();

    char* arguments[] = {"ls", "-l", 0};

    switch(pid){
        case -1:
            printf("The child process could not been created\n");
            break;
        case 0:
            printf("I am the child with PID %ld, the current directory content is: \n", (long)getpid());
            if(execvp ("ls", arguments) == -1){
                printf("Error in exec\n");
                exit(0);
            }
            break;
        default:
            printf("I am the parent process with PID %ld and my child is %d.\n", (long)getpid(), pid);
    }
    return 0;

}
