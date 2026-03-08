import re

r1 = re.compile('[.;?!]')  # se utiliza para separar por los signos de puntuacion        
r2 = re.compile('\W+')     # quitar todos los alfanuméricos

a = "hola,,,,,,, que t!!!al "
print(r2.sub(" ", a))
print(r1.sub(" ", a))


b = "spam, egg"
ini = r2.sub(' ', b).lower()
ini = "$ " + ini
ini = ini.split(' ')

print(ini)

if " ":
    print(True)
else:
    print(False)

print("$ " * 10)

c = [1, 2, 3]
c = tuple(c)
print(c)

d = (c, 1)
d = [c[i] for i in range(3, len(c))]
d.append(4)
d = tuple(d)
print(d)

l = [1,2,3,4,5,6,7]
n = 3
d = [l[i] for i in range(n+1,len(l))]
f = l[len(l)-(n-1):len(l)]
print(f)

a = "1 2"
b = a
b.split(" ")
print(b)