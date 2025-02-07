#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

int main(int argc, char* argv[]){
    int fd;
    char *arch = "ls_output.txt";
    mode_t fd_mode = S_IRWXU;

    fd = open(arch, O_RDONLY, fd_mode);
    //fd = open(arch, O_CREAT || O_RDWR, fd_mode);
    if(dup2(fd, STDIN_FILENO) == -1){
        printf("Error calling dup2\n");
        exit(-1);
    }

    if(execl("/bin/cat", "cat", arch, NULL) == -1){
        fprintf(stderr, "Ein Probleme ist geschehen\n");
        exit(-1);
    }

}
