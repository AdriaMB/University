#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

void *function(void *p){
    printf("I am a happy brother\n");
    sleep(10);
    printf("And I love sleeping\n");
}

int main(void){
    pthread_t id_thread;
    pthread_attr_t attributes;

    printf("Main thread: start\n");
    pthread_attr_init(&attributes);
    pthread_create(&id_thread, &attributes, function, NULL);
    printf("Main thread: I have created a brother\n");
    pthread_join(id_thread, NULL);
    printf("Main thread: That's all folks!\n");

}
