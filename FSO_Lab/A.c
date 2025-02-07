#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

int main(void){
    int aux, i;
    int a = 10, b = 5;
    for(i = 0; i < 3; i++){
        aux = fork();
        if(aux == 0){
            printf("Message 1 i = %d\n", i);
            sleep(a);
            execlp("ls", "ls", "-la", NULL);
            exit(0);
        }
    }
    printf("Message 3 i=%d\n", i);
    sleep(b);
    exit(0);
}
