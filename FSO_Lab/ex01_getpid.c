#include <stdio.h>
#include <unistd.g>

int main(void){
    printf ("\nProcess ID: %ld\n", (long)getpid());
    printf("Parent process ID: %d\n", (long)getppid());
    while(1);

    return 0;

}
