#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <semaphore.h>


#define NTHREADS 8
#define ITERATIONS 40000000

int sharedVar = 0;
int control = 0;
sem_t sem;        //semaphore

void *Increment(void *ptr) {
    int i, aux_variable;
    int iter = *(int *)ptr;
    
    for (i=0; i<iter; i++){
        sem_wait(&sem);
        aux_variable = sharedVar;
        aux_variable++;
        sharedVar = aux_variable;
        sem_post(&sem);
    }
}  

int main() {
    int i, niter;
    pthread_t t[NTHREADS];
    pthread_attr_t attr;
    int ret;
    
    pthread_attr_init(&attr);
    
    ret = sem_init(&sem, 0, 1); //initialization
    
    niter = ITERATIONS / NTHREADS;
    
    for (i=0; i <NTHREADS; i++) 
        pthread_create(&t[i], &attr, Increment, &niter);
        
    for (i=0; i <NTHREADS; i++) 
        pthread_join(t[i], NULL);

    printf("Global variable= %d\n", sharedVar);

    return 0;
}
