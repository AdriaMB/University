#include <stdio.h>

int main(int argc, char *argv[]) {   

     for(int i = 1; i < argc-1; i++){
          switch(argv[i][1]){
               case 'c':
                    printf("Argument %d is %s \n",i, "Compile");
                    break;
               case 'E':
                    printf("Argument %d is %s \n",i, "Preprocess");
                    break;
               case 'i':
                    printf("Argument %d is %s  %s \n",i, "Include  ",  argv[i+1]);
                    break;
               default:
                    break;

          }

     }

}

