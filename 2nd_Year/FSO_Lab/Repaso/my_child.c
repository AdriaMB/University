#include <stdio.h>      // standard input output
#include <stdlib.h>     // standard library
#include <unistd.h>     // standard symbolic constants and types
                        /**
                         * Defines miscellaneous symbolic constants and types, and declares miscellaneous functions. It includes various constants necessary for threads and fork implementation
                        */

int main(int argc, char *argv[]){
    printf("Parent process %ld\n", (long)getpid());
    fork();
    printf("I am %ld process, my parent is %ld\n", (long)getpid(),
          (long)getppid());
    sleep(15);
    return 0;
}
