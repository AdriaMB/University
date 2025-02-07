#include <stdio.h>
#define N 10        /*Define global constants*/

int Number;

int main()
{

    printf("Write the number to be displayed:");

    int i;
    scanf("%d", &Number);

    printf("\nThe first %d natural numbers are:\n", Number);
    for (i = 0; i < Number; i++)
    {
        printf ("   %d  \n", i);
    }
    printf("BYE\n");
    return(0);
}
