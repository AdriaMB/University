#include <semaphore.h>
#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
sem_t s1,s2,s3;
int x;


void *func_thread1(void *a)
{
  sem_wait(&s1);
  sem_wait(&s2);
  x=x+1;
  printf("[1] x = %d\n", x);
  sem_post(&s3);
  sem_post(&s1);
  sem_post(&s2);
  sleep(2);
}
void *func_thread2(void *b)
{
sleep(5);
  sem_wait(&s2);
  sem_wait(&s1);
  sem_wait(&s3);
  x=10*x;
  printf("[2] x = %d\n", x);
  sem_post(&s2);
  sem_post(&s1);
}

int main()
{ 
   pthread_t h1,h2  ;
   x = 1;
   sem_init(&s1,0,1); /*Inicializa a 1*/
   sem_init(&s2,0,1); /*Inicializa a 1*/
   sem_init(&s3,0,0); /*Inicializa a 0*/

   pthread_create(&h1,NULL,func_thread1,NULL);
   pthread_create(&h2,NULL,func_thread2,NULL);
   pthread_join(h1,NULL);
   pthread_join(h2,NULL);
}
