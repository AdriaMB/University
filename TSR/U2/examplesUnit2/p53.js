const ev = require('events')
const emitter = new ev.EventEmitter() 	// DON’T FORGET NEW OPERATOR !!!!!

const e1 = "print", e2 = "read"		// Names of the events.

function createListener( eventName ) {
	let num = 0
	return function () {
		console.log("Event " + eventName + " has " +
			"happened " + ++num + " times.")
	}
}

// Listener functions are registered in the event emitter.
emitter.on(e1, createListener(e1))
emitter.on(e2, createListener(e2))
// There might be more than one listener for the same event.
emitter.on(e1, function() {
	console.log( "Something has been printed!!")
})

// Generate the events periodically...
setInterval(function() {emitter.emit(e1)}, 2000) // First event emitted every 2s
setInterval(function() {emitter.emit(e2)}, 3000) // Second event emitted every 3s