'use strict'

function f1 (a,b,c) {
	console.log ("recibo " + arguments.length + " argumentos")
	for( let i = 0; i < arguments.length; i++){
		console.log(arguments[i])

	}

	return a+b+c;
}

let result1 = f1 (1,2,3)
let result2 = f1 (1,2,3,4,5,6)
let result3 = f1 (1)

console.log ("result1: " + result1)
console.log ("result2: " + result2)
console.log ("result3: " + result3)

let vector = [1,2,3,4]

console.log ("resultv1: " + f1 (...vector)) // Lee el vector y asigna a, b y c de manera correspondinete
console.log ("resultv2: " + f1 (vector))
console.log(vector.length)
console.log(typeof(f1(vector)))