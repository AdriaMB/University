#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

#define NPROCESSES 4

int main(void){

    pid_t pid;
    int i;



    for(i=0; i<NPROCESSES; i++ ){

        pid = fork();

 //       if(pid!= 0){
  //          break;
//        }

        printf("I am the child with PID %ld, my parent is %ld\n", (long) getpid(), (long)getpid());

        printf("This is my %d loop\n", i);
        sleep(1);

    }
    sleep(5);
    return 0;

}
