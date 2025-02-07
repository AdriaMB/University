#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

int main(int argc, int *argv[]){
    int val1, val2, status;
    int i, pid;
    printf("Process proc\n");
    val1 = fork();

    if(val1 == 0){
        printf("I am a child\n");
        for( i = 0; i < 2; i++){
            val2 = fork();
            if (val2 == 0){
                if (i == 0){
                    printf("Orphan child \n");
                    sleep(30);
                    exit(0);
                }//if
                if(i == 1){
                    printf("Zombie child\n");
                    sleep(10);
                    exit(0);
                }//if
            }//if
        }//for
        sleep(20);
        exit(4);
    }
    while((pid = wait(&status)) > 0)
        printf("hijo esperado %d, estado %d\n", pid, status/256);
    exit(0);
}//main
