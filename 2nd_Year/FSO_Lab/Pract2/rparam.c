#include <stdio.h>

void permute(double *x, double *y);

void main(void){
    double a = 1.0, b = 2.0;
    printf("a = %f, b = %f\n", a, b);
    permute(&a,&b);
    printf("a = %f, b = %f\n", a, b);

}

void permute(double *x, double *y){

    double temp;
    temp = *x;
    *x = *y;
    *y = temp;
}
