#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

int main(int argc, char* argv[]){
    int fd;
    char *arch = "output.txt";
    mode_t fd_mode = S_IRWXU;

    fd = open(arch, O_RDWR| O_CREAT, fd_mode);
    //fd = open(arch, O_CREAT || O_RDWR, fd_mode);
    if(dup2(fd, STDOUT_FILENO) == -1){
        printf("Error calling dup2\n");
        exit(-1);
    }

    fprintf(stdout, "bye\n");
    fprintf(stderr, "error not redirected\n");
    fprintf(stderr, "check the file\n");
    //fprintf(stdout, "hi\n");
    close(fd);
    exit(0);

}
