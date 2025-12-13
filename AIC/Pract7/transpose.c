#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/time.h>

#define NMAX 8192
#define BLOCKSIZE 8

void transpose_blocking( int n, int *dst, int *src ) {
    int bi,bj,i,j;

    // 3 LOOPS. bj, j, i                                    ~1400ms
    /**
    for(bj = 0; bj < n; bj += BLOCKSIZE)
        for( j = bj; j < bj + BLOCKSIZE; j++ )
            for( i = 0; i < n; i++ )
                dst[j+i*n] = src[i+j*n];

    */
    // 3 LOOPS. bi, i, j                                    ~7000ms
    /*
    for(bi = 0; bi < n; bi += BLOCKSIZE)
        for( i = bi; i < bi + BLOCKSIZE; i++ )
            for( j = 0; j < n; j++ )
                dst[j+i*n] = src[i+j*n];
    */





    // 4 LOOPS. bj, bi, j, i                                ~349ms
    /*
     for(bj = 0; bj < n; bj += BLOCKSIZE)
         for(bi = 0; bi < n; bi += BLOCKSIZE)
            for( j = bj; j < bj + BLOCKSIZE; j++ )
                for( i = bi; i < bi + BLOCKSIZE; i++ )
                    dst[j+i*n] = src[i+j*n];

    */
    // 4 LOOPS. bj, bi, j, i                                ~191ms
    /*
     for(bj = 0; bj < n; bj += BLOCKSIZE)
        for(bi = 0; bi < n; bi += BLOCKSIZE)
            for( i = bi; i < bi + BLOCKSIZE; i++ )
                for( j = bj; j < bj + BLOCKSIZE; j++ )
                    dst[j+i*n] = src[i+j*n];
    */






    // 4 LOOPS. bi, bj, i, j                                ~287ms
    /*
    for(bi = 0; bi < n; bi += BLOCKSIZE)
        for(bj = 0; bj < n; bj += BLOCKSIZE)
            for( i = bi; i < bi + BLOCKSIZE; i++ )
                for( j = bj; j < bj + BLOCKSIZE; j++ )
                    dst[j+i*n] = src[i+j*n];
    */

    // 4 LOOPS. bi, bj, j, i                                ~160ms      BEST
    /*
    for(bi = 0; bi < n; bi += BLOCKSIZE)
        for(bj = 0; bj < n; bj += BLOCKSIZE)
            for( j = bj; j < bj + BLOCKSIZE; j++ )
                for( i = bi; i < bi + BLOCKSIZE; i++ )
                    dst[j+i*n] = src[i+j*n];
    */

}

void transpose( int n, int *dst, int *src ) {
    int i,j;

    //OUTER LOOP ES j                       ~1200-1400ms

    for( j = 0; j < n; j++ )
        for( i = 0; i < n; i++ )
            dst[j+i*n] = src[i+j*n];

    //OUTER LOOP ES i                       ~7000-5000ms
    /*
    for( i = 0; i < n; i++ )
        for( j = 0; j < n; j++ )
            dst[j+i*n] = src[i+j*n];
    */

    // Cuando el outer loop es i, el tiempo total de ejecución es 5964 ms
    // Cuando el outer loop es j, el tiempo total se reduce a 1250 ms
    // Esto es debido a que cuando el loop de dentro es j, para poder LEER los datos de src debemos cargar más veces distintos bloques de memoria, resultando en una ejecución más costosa
    // Si en cambio el loop de dentro es i, aprovechamos mejor la memoria cargada, demostrando que es más importante para la ejecución del programa cargar bloques de memoria de src que de dst (tal vez porque el programa no llega a cargar ningún bloque de dst, sino que directamente escribe en memoria los datos)

}



int main( int argc, char **argv ) {
    int i,j;

    int *A = (int*)malloc( NMAX*NMAX*sizeof(int) );
    int *B = (int*)malloc( NMAX*NMAX*sizeof(int) );

    srand48( time( NULL ) );
    for( i = 0; i < NMAX*NMAX; i++ ) A[i] = lrand48( );
    for( i = 0; i < NMAX*NMAX; i++ ) B[i] = lrand48( );

    struct timeval start, end;

    gettimeofday( &start, NULL );
    transpose( NMAX, B, A );
    gettimeofday( &end, NULL );

    double seconds = (end.tv_sec - start.tv_sec) + 1.0e-6 * (end.tv_usec - start.tv_usec);
    printf( "original: %g milisegundos\n", seconds*1e3 );

    gettimeofday( &start, NULL );
    transpose_blocking( NMAX, B, A );
    gettimeofday( &end, NULL );

    seconds = (end.tv_sec - start.tv_sec) + 1.0e-6 * (end.tv_usec - start.tv_usec);
    printf( "blocking: %g milisegundos\n", seconds*1e3 );

    for( i = 0; i < NMAX; i++ ) {
        for( j = 0; j < NMAX; j++ ) {
            if( B[j+i*NMAX] != A[i+j*NMAX] ) {
                printf("Error!!!! La transposición no se ha realizado correctamente!!\n");
                exit( -1 );
            }
        }
    } 

    free( A );
    free( B );
    return 0;
}

