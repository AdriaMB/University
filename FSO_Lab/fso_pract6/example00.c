#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

#define NCHAR 20

int main(int argc, char *argv[]){
    int fdin;
    int count;
    int nreads, nbytes;
    char fname[20];
    char buf[NCHAR];

    if(argc!= 2){
        fprintf(stderr, "Usage: %s from_file\n", argv[0]);
        exit(1);
    }


    strcpy(fname, argv[1]);

    fdin = open(fname, O_RDONLY);

    if(fdin == -1){
        fprintf(stderr, "Could not open");
        exit(1);
    }

    nreads = 0;
    nbytes = 0;
    while((count = read(fdin, buf, sizeof(buf))) > 0){
        nreads++;
        nbytes = nbytes + count;
    }

    printf("Number of reads = %d Number of chars read = %d\n", nreads, nbytes);



}
