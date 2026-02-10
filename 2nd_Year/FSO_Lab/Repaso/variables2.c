#include <stdio.h>

int a = 0;

void inc_a(void){
    //int a;    To work with the global value, we cannot declare a local variable with its name.
    a++;
}

int former_value(int v){
    static int s;
    int temp = s;
    s = v;
    return temp;
}
int main(){
    int b = 2;
    inc_a();
    former_value(b);
    printf("a = %d, b = %d\n", a, b);
    a++;
    b++;
    inc_a();
    b = former_value(b);
    printf("a = %d, b = %d\n", a, b);

}
