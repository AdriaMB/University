#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main(){
    int i;
    pid_t val_return = 0;
    int final_state;

    for(i = 0; i < 4; i++){
    val_return = fork();
        if(val_return == 0){
            printf("I am a child created at loop %d and with Pid %ld\n", i, (long)getpid());

        }else{
            printf("Parent %ld, iteration %d\n", (long)getpid(), i);
            printf("I have created a child %ld\n", (long)val_return);
            break;
        }

    }

    while(wait(&final_state) > 0){
        printf("Parent %ld iteration %d \n", (long)getpid(), i);
        printf("Mys child said %d\n", WEXITSTATUS(final_state));
        //returns the exit status of the child.
        printf("My child said %d \n", final_state/256);

    }
    exit(i);
}

/*
 * while(wait()) se utiliza para esperar a TODOS los hijos de un
 * proceso, ya que repetirá la instruccion de esperar hasta que de
 * error o no hayan más hijos.
 *
 * El proceso exit(i) asigna el valor de (i x 256) a la dirección de
 * memoria de *status en el wait. Por eso, si queremos mostrar el valor
 * "real" de status dividimos entre 256 || ejecutamos WEXITSTATUS
 */
