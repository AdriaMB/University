#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

int main(int argc, char *argv[])
{
    int i, myproc;
    int a[15], b[5], c[5];


    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &myproc);

    if(myproc == 0){
        for(i=0; i<15;i++){
            a[i] = i+1;
        }
    }

    MPI_Scatter(a, 5, MPI_INT, b, 5, MPI_INT, 0, MPI_COMM_WORLD);

    for(i = 0; i<5; i++){
        printf("I am proc %d and I got %d\n", myproc, b[i]);

    }

    MPI_Reduce(b, c, 10, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);

    for(i = 0; i<5; i++){
        printf("I am proc %d and I sum %d\n", myproc, c[i]);

    }


    MPI_Finalize();
    return 0;
}
