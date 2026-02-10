#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

int sharedVar = 0;

void *Increment(void *ptr) {
    int i, aux_variable;
    int iter = *(int *)ptr;
    
    for (i=0; i<iter; i++){
        //aux_variable = sharedVar;
        //aux_variable++;
        //sharedVar = aux_variable;
        sharedVar++;
    }
}  

int main() {
    long iterations = 20000000;
    pthread_t t1, t2;
    pthread_attr_t attr;
   
    pthread_attr_init(&attr);
    pthread_create(&t1, &attr, Increment, &iterations);

        
    pthread_create(&t2, &attr, Increment, &iterations);
  
    pthread_join(t1, NULL);

    pthread_join(t2, NULL);
    printf("Global variable= %d\n", sharedVar);

    return 0;
}
