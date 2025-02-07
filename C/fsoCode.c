#include <stdio.h>
int main()
{
 int k;
 long l;
 float x;
 double d;
 char a,b,c,z[5];
 void *pInt;
 float *pFloat;
 double *pDouble;

 printf ("Sizes: char: %d int: %d, long: %d, float: %d, double: %d\n",
 sizeof(a), sizeof(k), sizeof(l), sizeof(x), sizeof(d));
 printf("Pointer sizes: int: %d, float: %d, double: %d\n",
 sizeof(pInt), sizeof(pFloat), sizeof(pDouble));
 printf("Pointer addresses: \n pDouble: \t0x%x\n pFloat: \t0x%x\n
pInt: \t0x%x\n", &pDouble, &pFloat, &pInt);
 printf ("Var Addresses: \n z: \t0x%x \n c: \t0x%x \n b: \t0x%x \n a:
\t0x%x\n d: \t0x%x \n x: \t0x%x \n l: \t0x%x\n k: \t0x%x\n",
 &z, &c, &b, &a, &d, &x, &l, &k);
 printf("Pointer addresses: \n pDouble: \t%d\n pFloat: \t%d\n pInt: \t%d\n",
 &pDouble - &pDouble, (int)&pFloat - (int)&pDouble, (int)&pInt - (int)&pDouble);
 printf ("Var Addresses: \n z: \t%d \n c: \t%d \n b: \t%d \n a: \t%d\n d: \t%d \n x: \t\n l: \t%d\n k: \t%d\n",
 (int)&z - (int)&pDouble, (int)&c - (int)&pDouble, (int)&b - (int)&pDouble, (int)&a -
(int)&pDouble, (int)&d - (int)&pDouble, (int)&x - (int)&pDouble, (int)&l -
(int)&pDouble, (int)&k - (int)&pDouble);
 return 0;
}