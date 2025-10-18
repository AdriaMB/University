'use strict';

let global1_let = "variable global 1"
var global2_var = "variable global 2"
let global3 = "variable global 3"

function f1 (arg) {
	global1_let = arg;    // Puedo también modificar las otras globales?
	global2_var = arg;
	global3 = arg;
	console.log ("global1= " + global1_let)
	console.log ("global2= " + global2_var)
	console.log ("global3= " + global3)
}

function f2 () {

	let local1_let = 5;
	

	for (let let_i=0; let_i<5; let_i++) {
		console.log ("let_i: " + let_i)
		var variable_var = 3;
	}
	console.log ("variable_var: " + variable_var) 
	// Sí está de
	// definida, incluso cuando esta ha sido definida dentro del loop
	

	for (var var_i=0; var_i<5; var_i++) {
		console.log ("var_i: " + var_i)
	}
	console.log ("var_i: " + var_i)

	console.log ("fin --> var_i=" + var_i)

	var local_let = var_i - 5; // Me equivoco al teclear "local_let1"
	console.log ("local1_let=" + local1_let)
	console.log ("local_let=" + local_let)


	//A esto se le conoce como Hosting de var. Básicamente, 
	//el intérprete considera esto como:
	//var local2_let; (IMPLICITO)
	console.log("local2_let: " + local2_let) ///Undefined
	var local2_let = 10;
	console.log("local2_let: " + local2_let) //10
	
	//Problema con var: puede ser definida multiples veces seguidas,
	// y alterada en cualquier parte del código donde sea accesible:
	var saludar = "hey, hola";
    var tiempos = 4;
    if (tiempos > 3) {
        var saludar = "dice Hola tambien"; 
    }
    console.log(saludar) // "dice Hola tambien"


	//Let puede modificarse, pero no cvolver a declararse. 
	//let saludar = "dice Hola";
	//let tiempos = 4;
	/*
	if (tiempos > 3) {
			let hola = "dice Hola tambien";
			console.log(hola);// "dice Hola tambien"
		}
			*/
	//console.log(hola) // hola is not defined


	// ADEMÁS, let y var NO PUEDE DECLARARSE EN EL MISMO BLOQUE

}

f1 ("nuevo valor")
f2 ();

