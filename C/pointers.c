#include <stdio.h>
int main()
{   int x; /* integer variable */
    int y; /* integer variable */
    int *px; /* pointer to integer */
    x = 5; 
    px = &x; /* px = address of x */
    y = *px; /* y = x */
    printf("x = %d\n", x); 
    printf("y = %d\n", y); 
    printf("*px = %d\n", *px);
    printf("px (&x) = %p\n", px);

    return 0;
}