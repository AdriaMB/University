/**
 * Sample program "Hello World" with pthreads.
 * To compile type:
 * gcc hello.c -lpthread -o Hello
 */
#include <stdio.h>
#include <pthread.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>

void * Print (void * ptr) {
    char * men;
    men =(char*) ptr;
    // EXERCISE 1.b
    usleep(2);
    write (1, men, strlen(men));
}
int main() {
    pthread_attr_t attrib;
    pthread_t thread1, thread2;

    pthread_attr_init(&attrib);

    pthread_create (&thread1, &attrib, Print, "Hello");
    pthread_create (&thread2, &attrib, Print, "World \n");

    // EXERCISE 1.a
    usleep(1);
    pthread_exit(0);
  //  pthread_join (thread1, NULL);
  //  pthread_join (thread2, NULL);

    return 0;
}
