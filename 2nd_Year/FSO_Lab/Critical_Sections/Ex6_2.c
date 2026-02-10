#include <stdio.h>
#include <pthread.h>

int key = 0;

//SOLUTION A
void *thread(void *p){

    while(1){
        while(test_and_set(&key)){
            printf("I am waiting\n");

        }
        /*Critical Section */

        printf("I entered\n");
        key = 0;

        /*Remaining section */
    }
}



int main(int argc, char **argv){
    pthread_t th0, th1;
    pthread_attr_t attr;

    pthread_attr_init(&attr);
    pthread_create(&th0, &attr, thread, NULL);

}




