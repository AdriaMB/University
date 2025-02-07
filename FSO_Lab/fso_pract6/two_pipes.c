// a_pipe.c
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

int main (int argc,char *argv[]) {
    int i;
    char* arguments1 [] = { "ls", "-la", 0 };
    char* arguments2 [] = {"grep", "ejemplo", 0};
    char* arguments3 [] = { "wc", "-l", 0 };

    int fildes[2];
    int fildes2[2];
    int fd;

    if((fd = open("result.txt", O_RDWR | O_CREAT, S_IRWXU)) == -1){
        fprintf(stderr, "Ups, error\n");
        exit(-1);
    }

    pid_t pid;
    // Parent process creates a pipe
    if ((pipe(fildes)==-1)) {
        fprintf(stderr,"Pipe failure  \n");
        exit(-1);
    }

    if((pipe(fildes2) == -1)){
        fprintf(stderr, "Error\n");
        close(fildes[0]);
        close(fildes[1]);
    }
    for (i=0;i<3;i++) {
        pid=fork(); // Creates a child process
        if ((pid==0) && (i==0)) {
            // Child process redirects its output to the pipe
            dup2(fildes[1], STDOUT_FILENO);
            // Child process closes pipe descriptors
            close(fildes[0]);
            close(fildes[1]);
            close(fildes2[0]);
            close(fildes2[1]);
            // Child process changes its memory image
            if ( execvp("ls",arguments1)<0) {
                fprintf(stderr,"ls not found \n");
                exit(-1);
            }
        } else if ((pid==0) && (i==1)) {
            // Child process redirects its input to the pipe
            dup2(fildes[0], STDIN_FILENO);
            dup2(fildes2[1], STDOUT_FILENO);
            // Child process closses pipe descriptors
            close(fildes[0]);
            close(fildes[1]);
            close(fildes2[0]);
            close(fildes2[1]);
            // Child process changes its memory image
            if (execvp("grep",arguments2)<0) {
                fprintf(stderr,"grep not found \n");
                exit(-1);
            }
        } else if ((pid==0) && (i==2)) {
            // Child process redirects its input to the pipe
            dup2(fildes2[0], STDIN_FILENO);
            dup2(fd, STDOUT_FILENO);
            // Child process closses pipe descriptors
            close(fildes[0]);
            close(fildes[1]);
            close(fildes2[0]);
            close(fildes2[1]);
            // Child process changes its memory image
            if (execvp("wc",arguments3)<0) {
                fprintf(stderr,"wc not found \n");
                exit(-1);
            }
        }
    }

    // Parent process closes pipe descriptors
    close(fildes[0]);
    close(fildes[1]);
    close(fildes2[0]);
    close(fildes2[1]);
    for (i = 0; i < 3; i++) wait(NULL);
    return(0);
}
