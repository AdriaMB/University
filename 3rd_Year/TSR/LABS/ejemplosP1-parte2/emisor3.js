// The code must call the 'stage' function a first time. Withing the 'stage' func, the call
// to 'stage' must be reprogrammed so that the program executes a series of
// 'stages'. 
//
// Each stage must start with a delay of 2-5 seconds. Each stage should do the following
// 		1- Emit the events e1 and e2, passing the value of the variable 'inc' as the associated value
// 		2- Increase variable 'inc' one unit
//		3- Show by console the delay in the execution of the stage



const ev = require('events') // We need to import the event module
const emitter = new ev.EventEmitter() // We create the object to use the module
const e1='e1', e2='e2'
let inc=0, t

function rand() { // debe devolver valores aleat en rango [2000,5000) (ms)
 	// Math.floor(x) devuelve la parte entera del valor x
 	// Math.random() devuelve un valor en el rango [0,1)
	return Math.floor(Math.random()*(5000-2000)+2000)

}

function handler (e,n) { // e es el evento, n el valor asociado
 	return (inc) => { 
		n = n + inc // for event e1, it will increase the counter by inc. For e2, it will append the inc value 
		console.log(e + "-->" + n)
	

	} // el oyente recibe un valor (inc)
}

emitter.on(e1, handler(e1,0))
emitter.on(e2, handler(e2,''))

function etapa() {
 	//The call to stage must be reprogrammed so that it executes as series of stages

	//console.log("During etapa()")
	setTimeout(function(){emitter.emit(e1, inc)}, t)
	setTimeout(function(){emitter.emit(e2, inc)}, t)
	//console.log("After setInterval")
	console.log("stage " + inc + " started after " + t + " ms")
	


	t = rand()
	inc++
	setTimeout(etapa, t)

}

console.log("Before everything")
setTimeout(etapa,t=rand()) // we call estapa the first time

