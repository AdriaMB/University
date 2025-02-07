#include <stdio.h>

int squareOf(int x){
     return x*x;
}

int main()
{
    printf("Please, enter a number:,\n");
    int x;
    scanf("%d", &x);
    int res = squareOf(x);
    printf("The square of %d is: %d \n", x, res);
    return 0;
}


    