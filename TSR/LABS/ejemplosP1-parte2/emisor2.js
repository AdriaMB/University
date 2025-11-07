const ev = require('events')
const emitter = new ev.EventEmitter()
const e1='e1', e2='e2'

// We define a listener, which acts like a callback to the event
function handler (event,n) {
	return (incr)=>{ // listener with param. WHAT IS incr ?????
 		n+=incr
 		console.log(event + ':' + n)
 	}
}

// Listener fucntions are registered in the event emitter with the event they
// are listening to. Each time the event is thrown, the listener
// is called IN THE SAME TURN, IMMEDIATELY AFTER THE EVENT IS EMITTED

// Each listener is executed in a sequential way, and in case there are more than 1 listener for the same event, they are executed in the same way they were established
emitter.on(e1, handler(e1,0)) // The listener handler() will be listening for an event e1 to occur
emitter.on(e2, handler(e2,'')) // implicit type casting


console.log('\n\n--------------------------- init\n\n')
for (let i=1; i<4; i++){
	emitter.emit(e1,i) // sequence, iteration, generation with param
	//  <---------------------------------------- NOW THE LISTENER IS EXECUTED
	console.log("I am after the emit " + i)
}

console.log('\n\n--------------------------- intermediate\n\n')
for (let i=1; i<4; i++) emitter.emit(e2,i) // sequence, iteration, generation with param

console.log('\n\n--------------------------- end')
