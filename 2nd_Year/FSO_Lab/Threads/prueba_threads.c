#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

void *work(void *);
void *Increment(void *ptr);

int GlobalVariable = 0;

int main(int argc, char **argv){

    pthread_t tharray[8];
    pthread_attr_t attr;
    char word[10] = "Hello ";
    long iterations = 1000;

    //pthread_create(&tharray[0], &attr, work, word);
    for (int i = 0;i < 2; i++){
        pthread_create(&tharray[i], &attr, Increment, &iterations);
    }

    //  word == &word[0] == word + 0
    //  El casting (void *)word también es correcto, pero
    //  no es necesario, ya que al pedir como parametro un
    //  pointer void, acepta cualquier tipo
    //pthread_create(&tharray[1], &attr, work, "World\n");


    pthread_join(tharray[0], NULL);
    pthread_join(tharray[1], NULL);

    printf("%d\n", GlobalVariable);

    return 0;

}


void *work(void *ptr){
    char *message;
    message = (char *)ptr;
    //  Aquí sí que es necesario castearlo, ya que vamos a
    //  utilizar message como una string, y nos daría error
    //  si pasaramos el pointer void.
    printf("%s", message);

}


void *Increment(void *ptr){
    long i, *iter;
    iter = (long *)ptr;

    //printf("Hola\n");
    for(i = 0; i < *iter; i++) GlobalVariable++;
}
