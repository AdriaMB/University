#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <stdlib.h>

#define CONFIG_X86_CMPXCHG

#define NTHREADS 8
#define ITERATIONS 40000000

int sharedVar = 0;
int control = 0;

int test_and_set(int *spinlock) {
  int ret;
  __asm__ __volatile__(
    "bts %0, %1"
    : "=r"(ret), "=m"(*spinlock)
    : "r"(1), "m"(*spinlock)
    );
  return ret;
}



void *Increment(void *ptr) {
    int i, aux_variable;
    int iter = *(int *)ptr;
    
    for (i=0; i<iter; i++){
        while (test_and_set(&control)) ;
        aux_variable = sharedVar;
        aux_variable++;
        sharedVar = aux_variable;
        control = 0;
    }
}  

int main() {
    int i, niter;
    pthread_t t[NTHREADS];
    pthread_attr_t attr;
   
    pthread_attr_init(&attr);
    
    niter = ITERATIONS / NTHREADS;
    int kk = 0;
    int k = test_and_set(&kk);
    printf("kk: %d ret: %d\n", kk, k); 
    //exit(0);
    sleep(2);
    for (i=0; i <NTHREADS; i++) 
        pthread_create(&t[i], &attr, Increment, &niter);
        
    for (i=0; i <NTHREADS; i++) 
        pthread_join(t[i], NULL);

    printf("Global variable= %d\n", sharedVar);

    return 0;
}
