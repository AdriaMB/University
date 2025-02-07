#include<stdio.h>
#define SIZE_ROW 100
#define NUM_ROWS 10

//struct NAME{  values  } ;
struct ROW{
    float data[SIZE_ROW];
    float addition;
};

//A
struct ROW rows[NUM_ROWS];

//B
void sum_row(struct ROW *pf){
    for(int i = 0; i < SIZE_ROW; i++){
        pf->addition += pf->data[i];
    }

}

//Init rows with value i*j
void init_rows(){
    int i, j;
    for(i = 0; i < NUM_ROWS; i++){
        for(j = 0; j < SIZE_ROW; j++){
            rows[i].data[j] = (float)i*j;
        }
    }
}

int main(){
    int i;
    float total_sum;

    init_rows();

    //C
    total_sum = 0;
    for(i = 0; i < NUM_ROWS; i++){
        sum_row(&rows[i]);
        printf("Row %u addition result is %f\n", i, rows[i].addition);
        total_sum += rows[i].addition;
    }
    printf("The final addition result is %f\n", total_sum);
}
