#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <sched.h>

#define NTHREADS 4
#define ITERATIONS 20000000

int sharedVar = 0;
int control = 0;

void *Increment(void *ptr) {
    int i, aux_variable;
    int iter = *(int *)ptr;
    
    for (i=0; i<iter; i++){
        while (__sync_lock_test_and_set(&control, 1)) sched_yield();
        aux_variable = sharedVar;
        aux_variable++;
        sharedVar = aux_variable;
        control = 0;
        //__sync_lock_release(&control);
    }
}  

int main() {
    int i, niter;
    pthread_t t[NTHREADS];
    pthread_attr_t attr;
   
    pthread_attr_init(&attr);
    
    niter = ITERATIONS;
    
    for (i=0; i <NTHREADS; i++) 
        pthread_create(&t[i], &attr, Increment, &niter);
        
    for (i=0; i <NTHREADS; i++) 
        pthread_join(t[i], NULL);

    printf("Global variable= %d\n", sharedVar);

    return 0;
}
