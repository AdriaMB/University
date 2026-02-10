#include <pthread.h>
#include <stdio.h>
#include <unistd.h>

//WE MUST COMPILE WITH -lpthread
void *Increment(void *);

void *func_period(void *);

void *My_Print(void *);

int GlobalVariable = 0;

int main(int argc, char *argv[]){

    pthread_t tharray[8];
    pthread_attr_t attr;
    long iterations = 1000;
    int i;

    for (i = 0;i < 8; i++){
        pthread_create(&tharray[i], NULL, Increment, &iterations);
    }
    for (i = 0;i < 8; i++){
        pthread_join(tharray[i], NULL);
    }

    printf("%d \n", GlobalVariable);

//   int period1 = 2;
//   pthread_create(&th1, NULL, func_period, &period1);


//    pthread_create(&th1, NULL, My_Print, "Hello ");
//    pthread_create(&th2, NULL, My_Print, "World");
/**
    for(int j = 0; j < 10; j++){
        pthread_join(tharray[j], NULL);
    }
/**
    pthread_join(th1, NULL);
    pthread_join(th2, NULL);
*/

    return 0;
}


void *Increment(void *ptr){
    long i, *iter;
    iter = (long *)ptr;

    for(i = 0; i < *iter; i++){
        GlobalVariable++;
    }
}

void *func_period(void *arg){
    int period, i;
    period = *((int *)arg);
    for(int i = 0; i < 10; i++){
        printf("Pthread (period %d): \n", period);
        printf("%ld \n", (long)pthread_self()); //pthread_self returns the thread's ID
        sleep(period);
    }
}

void *My_Print(void *ptr){
    char *message;
    message = (char*)ptr;

    printf("I am a trhead and say: %s \n", message);
    sleep(3);
    printf("I am a thread and finish \n");

}
