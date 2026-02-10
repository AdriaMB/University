/*** ej1_getpid.c *****/
#include <stdio.h>
int main(void)
{
    printf("\nProcess ID: %ld\n", (long)getpid());
    printf(“Parent process ID: %ld\n", (long)getppid());
    while(1);
    return 0;
}
