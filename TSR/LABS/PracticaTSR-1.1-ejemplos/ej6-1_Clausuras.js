'use strict'

function f1() {
	console.log ("hola") // No closure, as it does not reference any variable
}
f1();

let x = "hola"
function f2() {
	console.log (x) // closure = x, as it references the variable x, ALTHOUGH technically x is global, so it doesn't count
}
f2();


function f3 (arg) { // usual pattenr of closures
	let i=0; // both arg and i are local to the closure, and can be accessed by it
	return function () {
		i++;
		console.log (arg + i);
	} 
}

function f4 (arg){
	let j = 0 + arg; // With only the variable in the function, f4 behaves normally (j is lost after exectution and created when we call f4)

	return function (){
		j++;
		console.log(j);

	}
}

let f = f3 ("hola");
f();
f();

let g = f4(2)
g()
g()
