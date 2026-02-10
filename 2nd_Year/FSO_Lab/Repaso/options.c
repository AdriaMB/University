#include<stdio.h>

int main(int argc, char *argv[]){

    for(int i = 1; i < argc; i++){
        switch(argv[i][1]){
            case 'c':
                printf("Argument %d is Compile\n", i);
                break;
            case 'E':
                printf("Argument %d is Preprocess\n", i);
                break;
            case 'i':
                printf("Argument %d is Include %s\n", i, argv[i+1]);
                break;
        }
        if(argv[i+1][0] == '/'){
            break;
        }
    }
}
