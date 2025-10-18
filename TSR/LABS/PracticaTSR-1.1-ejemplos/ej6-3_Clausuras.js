
// Clausuras de variables y funciones.

function gen (y) {
	var x = 100;
	function inner (){
		x++;
		console.log("incremento de x:  " + x);
		return x;
	}
	return function(){
		y++;
		console.log("y: " + y);
		console.log("y + inner(): ",y + inner());
		return y;
	}

}

let func = gen(-100); // the closure is func, and it is 
				      // composed by x, y and the function 
					  // inner(), which are the elements 
					  // accessible from the anonymous 
					  // function returned

					  //aka. A closure is a function that
					  // has access to the parent scope, after the
					  //parent function has closed
func();
func();
func();

